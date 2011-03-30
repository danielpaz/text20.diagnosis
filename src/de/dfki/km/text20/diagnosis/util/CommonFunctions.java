package de.dfki.km.text20.diagnosis.util;


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
}
