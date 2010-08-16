package de.dfki.km.text20.diagnosis.util;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

/**
 * @author rb
 *
 */
public class CommonFunctions {
    /**
     * @param low
     * @param hi
     * @param n
     * @return returns true, if n is inside the range between low and hi (including low and hi value)  
     */
    public static boolean isBetweenIncludes(final float low, final float hi, final float n) {
        return (n >= low) && (n <= hi);
    }

    /**
     * @param low
     * @param hi
     * @param n
     * @return returns true, if n is inside the range between low and hi (excluding low and hi value)  
     */
    public static boolean isBetweenExcludes(final int low, final int hi, final int n) {
        return (n > low) && (n < hi);
    }

    /**
     * @param low
     * @param hi
     * @param n
     * @return returns true, if n is inside the range between low and hi (excluding low and hi value)  
     */
    public static boolean isBetweenExcludes(final float low, final float hi, final float n) {
        return (n > low) && (n < hi);
    }

    /**
     * @param low
     * @param hi
     * @param n
     * @return returns true, if n is inside the range between low and hi (including low and hi value)  
     */
    public static boolean isBetweenIncludes(final int low, final int hi, final int n) {
        return (n >= low) && (n <= hi);
    }

    /**
     * @param val
     * @param lowerLimit
     * @param upperLimit
     * @return clipped float value
     * 
     * limits the given value to a range between lowerLimit and upperLimit
     * if the value is > upperLimit the function returns the upper limit value
     * if the value is < lowerLimit the function returns the lower limit value
     * otherwise the given value will be returned unchanged
     */
    public static float limitFloat(final float val, final float lowerLimit,
                                   final float upperLimit) {
        return Math.min(Math.max(val, lowerLimit), upperLimit);
    }

    /**
     * @param val
     * @return clipped float value
     * 
     * limits the given value to a fixed range between 0.0 and 1.0
     * if the value is > 1.0 the function returns 1.0
     * if the value is < 0.0 the function returns 0.0
     * otherwise the given value will be returned unchanged
     * 
     */
    public static float limitFloat(final float val) {
        return limitFloat(val, 0.0f, 1.0f);
    }

    /**
     * @param val
     * @param lowerLimit
     * @param upperLimit
     * @return clipped float value
     * 
     * limits the given value to a range between lowerLimit and upperLimit
     * if the value is > upperLimit the function returns the upper limit value
     * if the value is < lowerLimit the function returns the lower limit value
     * otherwise the given value will be returned unchanged
     */
    public static long limitLong(final long val, final long lowerLimit,
                                 final long upperLimit) {
      return Math.min(Math.max(val, lowerLimit), upperLimit);
  }

    /**
     * @param val
     * @param lowerLimit
     * @param upperLimit
     * @return
     * 
     * limits the given value to a range between lowerLimit and upperLimit
     * if the value is > upperLimit the function returns the upper limit value
     * if the value is < lowerLimit the function returns the lower limit value
     * otherwise the given value will be returned unchanged
     */
    public static long limitInteger(final int val, final int lowerLimit,
                                 final int upperLimit) {
      return Math.min(Math.max(val, lowerLimit), upperLimit);
  }


    /**
     * A more convenient way to call SwingUtilities
     * 
     * @param r
     * @return .
     */
    public static boolean invokeAndWaitAndShutup(Runnable r) {
        try {
            SwingUtilities.invokeAndWait(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * TODO: DOCUMENT ME!!!!!  
     * 
     * calculates an average value of values from  a eyeTrackingRingBuffer,
     * starting from the i-th element with clusterSize elements  
     * 
     * @param i  start index 
     * @param clusterSize number of values 
     * @param eyeTrackingRingBuffer the ringbuffer
     * @param select 0: left eye;  1: right eye
     * @return average value
     */
    //    private float getAverageValue(final int i, final int select) {
    //
    //        float sum = 0;
    //        float ps = 0;
    //        int ignored = 0;
    //        for (int j = 0; j < this.clusterSize; j++) {
    //
    //            final int readPosition = (this.ringBuffer.getWritePointer() + i * this.clusterSize + j -1) % this.ringBuffer.size();
    //            //            System.out.println(i+j + ". " + readPosition);
    //            try {
    //                switch (select) {
    //
    //                case 0: {
    //                    ps = this.ringBuffer.get(readPosition).getPupilSizeLeft() / 10;
    //                }
    //                    break;
    //
    //                case 1: {
    //                    ps = this.ringBuffer.get(j + i).getPupilSizeRight() / 10;
    //                }
    //                    break;
    //
    //                default:
    //                    break;
    //                }
    //
    //                //                ps = (float) (Math.random() * 0.35); 
    //                if (ps > 0) {
    //                    sum += ps;
    //                } else {
    //                    ignored++;
    //                }
    //            } catch (final Exception e) {
    //                ignored++;
    //            }
    //        }
    //
    //        float avg = 0;
    //        try {
    //            avg = sum / (this.clusterSize - ignored);
    //        } catch (final Exception e) {
    //            return 0;
    //        }
    //        return avg;
    //    }
}
