package de.dfki.km.text20.diagnosis.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ABu, rb
 *
 * @param <T> 
 */
public class RingBuffer<T> implements Serializable {

    /** */
    private static final long serialVersionUID = -5401717626046752048L;

    /** */
    private boolean recording = false;

    /** */
    private int writePointer = 0;

    /** */
    final int DEFAULT_BUFFER_SIZE = 300;

    /** */
    T[] eventBuffer;

    final Lock dataLock = new ReentrantLock();

    /**
     * Default constructor; sets ringbuffer size to default
     * 
     * @param classz 
     */
    public RingBuffer(final Class<T> classz) {
        this(classz, -1);
    }

    /**
     * Constructor with ringbuffer size parameter
     * 
     * @param classz 
     * @param ringbufferSize 
     */
    @SuppressWarnings("unchecked")
    public RingBuffer(final Class<T> classz, final int ringbufferSize) {
        int actualSize = (ringbufferSize > 0) ? ringbufferSize : this.DEFAULT_BUFFER_SIZE;
        this.eventBuffer = (T[]) Array.newInstance(classz, actualSize);
    }

    /**
     * Adds an event (only when the ring buffer is recording) 
     * 
     * @param e
     */
    public void addEvent(final T e) {
        if (!isRecording()) { return; }

        this.dataLock.lock();
        try {
            this.writePointer = this.writePointer % size();
            this.eventBuffer[this.writePointer++] = e;
        } finally {
            this.dataLock.unlock();
        }
    }

    /**
     * 
     */
    public void continueRecording() {
        setRecording(true);
    }

    /**
     * Returns a value where 
     * 
     * @param i
     * @return .
     */
    public T get(final int i) {
        return this.eventBuffer[(getWritePointer() + i) % size()];
    }

    /**
     * @return .
     */
    private int getWritePointer() {
        return this.writePointer;
    }

    /**
     * @return .
     */
    public boolean isRecording() {
        return this.recording;
    }

    /**
     * resets write Pointer to the buffers begin and starts recording
     */
    public void restartRecording() {
        this.dataLock.lock();
        try {
            this.writePointer = 0;
            setRecording(true);
        } finally {
            this.dataLock.unlock();
        }
    }

    /** */
    public void stopRecording() {
        setRecording(false);
    }

    /**
     * @param f
     * @throws IOException
     */
    public void writeBufferToFile(final File f) throws IOException {
        final Vector<T> evBuf = new Vector<T>();

        this.dataLock.lock();
        try {
            for (final T element : this.eventBuffer) {
                evBuf.add(element);
            }
        } finally {
            this.dataLock.unlock();
        }

        final ObjectOutputStream objOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
        objOut.writeObject(evBuf);
        objOut.close();
    }

    /**
     * @param recording
     */
    public void setRecording(final boolean recording) {
        this.recording = recording;
    }

    /**
     * @param size
     */
    @SuppressWarnings("unchecked")
    public void setRingbufferSize(final int size) {

        // Adjust the class' content
        this.dataLock.lock();
        try {
            // Create new array
            final Vector<T> v = new Vector<T>();
            v.setSize(size);

            // Assign to temporary variable�
            T x[] = (T[]) v.toArray();

            int copySize = Math.min(size, this.eventBuffer.length);
            // Copy the old data
            System.arraycopy(this.eventBuffer, 0, x, 0, copySize);

            this.writePointer = copySize;
            this.eventBuffer = x;
        } finally {
            this.dataLock.unlock();
        }
    }

    /**
     * @return .
     */
    public int size() {
        return this.eventBuffer.length;
    }

    /**
     * @return .
     */
    @SuppressWarnings("unused")
    private Vector<T> toVector() {
        final Vector<T> v = new Vector<T>();

        for (int i = this.writePointer; i < size(); i++) {
            final T element = this.eventBuffer[i];
            v.add(element);

        }
        for (int i = 0; i < this.writePointer; i++) {
            final T element = this.eventBuffer[i];
            v.add(element);
        }
        return v;
    }
}
