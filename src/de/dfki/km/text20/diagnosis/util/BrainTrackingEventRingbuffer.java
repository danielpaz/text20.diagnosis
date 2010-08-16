package de.dfki.km.text20.diagnosis.util;

import de.dfki.km.text20.services.trackingdevices.brain.BrainTrackingEvent;

/**
 * @author rb
 *
 */
public class BrainTrackingEventRingbuffer extends RingBuffer<BrainTrackingEvent> {

    /** */
    private static final long serialVersionUID = -5401717626046752048L;

//    /**
//     * @param args
//     */
//    public static void main(final String[] args) {
//        final EyeTrackingEventRingbuffer rb = new EyeTrackingEventRingbuffer();
//        
//        rb.setWritePointer((int) (Math.random() * 200));
//        for (int i = 1; i <= rb.size(); i++) {
//            rb.addEvent(null);
//        }
//        
//    }
        
    /**
     * Default constructor; sets ringbuffer size to default 
     */
    public BrainTrackingEventRingbuffer() {
        this(-1);
    }

    /**
     * Constructor with ringbuffer size parameter
     *  
     * @param ringbufferSize 
     */
    public BrainTrackingEventRingbuffer(final int ringbufferSize) {
        super(BrainTrackingEvent.class, ringbufferSize);
    }

//        /**
//         * @param panel
//         */
//        public void showEyeTrackingEventBuffer(final EyeTrackingEventPanel panel) {
//            final boolean runState = isRecording();
//            setRecording(false);
//    
//            for (int i = 0; i < this.actualSize; i++) {
//                if (panel != null) {
//                    ((EyeTrackingEventDataPanel) panel).getTextArea().append(i + ": " + this.eventBuffer[i] + "\n");
//                } else {
//                    System.out.println(i + ": " + this.eventBuffer[i]);
//                }
//            }
//    
//            setRecording(runState);
//        }

}
