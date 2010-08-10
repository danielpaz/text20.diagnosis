package de.dfki.km.text20.diagnosis.util;

import java.util.HashMap;
import java.util.Map;

import de.dfki.km.text20.services.trackingdevices.eyes.EyeTrackingEvent;

/**
 * 
 * TODO: REWORK THIS!!!! As soon as two thread access this class you'll hear a loud bang.
 * 
 * @author ABu
 *
 */
public class EyeTrackingEventEvaluator {

    /**
     * Diagnosis state of both eyes.
     * 
     * @author rb
     */
    public enum DiagState {
        /** */
        BAD,
        /** */
        VAGUE,
        /** */
        OK,
        /** */
        OFF,
        /** */
        ON
    }

    /**
     * @author rb
     *
     */
    public enum DataPartition {
        /**
         * 
         */
        HEAD_DISTANCE,
        /**
         * 
         */
        HEAD_POSITION,
        /**
         * 
         */
        GAZE_POSITION,
        /**
         * 
         */
        PUPIL_SIZE,
        /**
         * 
         */
        OVER_ALL
    }

    /** limits for valid pupil size values; need not to distinguish between left and right */
    public static float MAX_PUPILSIZE = 5.0f;
    /** limits for valid pupil size values; need not to distinguish between left and right */
    public static float MIN_PUPILSIZE = 2.0f;

    /** limits for valid eyePosition values; need not to distinguish between left and right */
    public static float X_EYE_POSITION_MAX = 0.8f;

    /** limits for valid eyePosition values; need not to distinguish between left and right */
    public static float X_EYE_POSITION_MIN = 0.2f;

    /** limits for valid eyePosition values; need not to distinguish between left and right */
    public static float X_HEAD_POSITION_MAX = 0.8f;

    /** limits for valid eyePosition values; need not to distinguish between left and right */
    public static float X_HEAD_POSITION_MIN = 0.3f;

    /** limits for valid eyePosition values; need not to distinguish between left and right */
    public static float Y_EYE_POSITION_MAX = 0.8f;

    /** limits for valid eyePosition values; need not to distinguish between left and right */
    public static float Y_EYE_POSITION_MIN = 0.2f;

    /** limits for valid eyePosition values; need not to distinguish between left and right */
    public static float Y_HEAD_POSITION_MAX = 0.8f;

    /** limits for valid eyePosition values; need not to distinguish between left and right */
    public static float Y_HEAD_POSITION_MIN = 0.3f;

    /** limits for valid eyePosition values; need not to distinguish between left and right */
    public static float EYE_DISTANCE_MAX = 0.8f;

    /** limits for valid eyePosition values; need not to distinguish between left and right */
    public static float EYE_DISTANCE_MIN = 0.3f;

    /** limits for valid eyePosition values; need not to distinguish between left and right */
    public static float Z_HEAD_POSITION_MAX = 0.8f;

    /** limits for valid eyePosition values; need not to distinguish between left and right */
    public static float Z_HEAD_POSITION_MIN = 0.3f;

    private static boolean headInXRange;
    private static boolean headInYRange;
    private static boolean headInZRange;
    private static boolean leftEyeDetected;
    private static boolean leftEyeInXRange;
    private static boolean leftEyeInYRange;
    private static boolean leftEyeInZRange;
    private static boolean rightEyeDetected;
    private static boolean rightEyeInXRange;
    private static boolean rightEyeInYRange;
    private static boolean rightEyeInZRange;

    //    private static DiagState currRateStatus = DiagState.BAD;

    /**
     * calculates the quality of a given event
     *
     * @return DIAG_STATE_BAD, DIAG_STATE_OK, DIAG_STATE_VAGUE 
     *  
     * @param e
     *  
     */
    @SuppressWarnings("unused")
    public static DiagState evaluateEvent(final EyeTrackingEvent e) {

        // default State, will be returned if no other condition matches
        DiagState diagnosisState = DiagState.VAGUE;

        final float xhp = e.getHeadPosition()[0];
        final float yhp = e.getHeadPosition()[1];
        final float zhp = e.getHeadPosition()[2];

        final float xlep = e.getLeftEyePosition()[0];
        final float ylep = e.getLeftEyePosition()[1];
        final float zlep = e.getLeftEyePosition()[2];
        final float xrep = e.getRightEyePosition()[0];
        final float yrep = e.getRightEyePosition()[1];
        final float zrep = e.getRightEyePosition()[2];

        final float leftEyeDistance = e.getLeftEyeDistance();
        final float rightEyeDistance = e.getRightEyeDistance();

        leftEyeDetected = leftEyeDistance != -1.0 ? true : false;
        rightEyeDetected = rightEyeDistance != -1.0 ? true : false;
        headInXRange = CommonFunctions.isBetweenIncludes(X_HEAD_POSITION_MIN, X_HEAD_POSITION_MAX, xhp);
        headInYRange = CommonFunctions.isBetweenIncludes(Y_HEAD_POSITION_MIN, Y_HEAD_POSITION_MAX, yhp);
        headInZRange = CommonFunctions.isBetweenIncludes(Z_HEAD_POSITION_MIN, Z_HEAD_POSITION_MAX, zhp);

        leftEyeInXRange = CommonFunctions.isBetweenIncludes(X_EYE_POSITION_MIN, X_EYE_POSITION_MAX, xhp);
        leftEyeInYRange = CommonFunctions.isBetweenIncludes(Y_EYE_POSITION_MIN, Y_EYE_POSITION_MAX, yhp);
        leftEyeInZRange = CommonFunctions.isBetweenIncludes(EYE_DISTANCE_MIN, EYE_DISTANCE_MAX, zhp);

        rightEyeInXRange = CommonFunctions.isBetweenIncludes(X_EYE_POSITION_MIN, X_EYE_POSITION_MAX, xhp);
        rightEyeInYRange = CommonFunctions.isBetweenIncludes(Y_EYE_POSITION_MIN, Y_EYE_POSITION_MAX, yhp);
        rightEyeInZRange = CommonFunctions.isBetweenIncludes(EYE_DISTANCE_MIN, EYE_DISTANCE_MAX, zhp);

        //bad status condition rules
        if (!isBothEyesDetected()) {
            diagnosisState = DiagState.BAD;
        } else
        //ok status condition rules
        if (isHeadInValidRange()) {
            diagnosisState = DiagState.OK;
        }

        return diagnosisState;
    }

    /**
     * @return .
     */
    public static boolean isBothEyesDetected() {
        return isLeftEyeDetected() && isRightEyeDetected();
    }

    /**
     * @return .
     */
    public static boolean isHeadInValidRange() {
        return isHeadInXRange() && isHeadInYRange() && isHeadInZRange();
    }

    /**
     * @return .
     */
    public static boolean isHeadInXRange() {
        return headInXRange;
    }

    /**
     * @return .
     */
    public static boolean isHeadInYRange() {
        return headInYRange;
    }

    /**
     * @return .
     */
    public static boolean isHeadInZRange() {
        return headInZRange;
    }

    /**
     * @return .
     */
    public static boolean isLeftEyeDetected() {
        return leftEyeDetected;
    }

    /**
     * @return .
     */
    public static boolean isLeftEyeInXRange() {
        return leftEyeInXRange;
    }

    /**
     * @return .
     */
    public static boolean isLeftEyeInYRange() {
        return leftEyeInYRange;
    }

    /**
     * @return .
     */
    public static boolean isLeftEyeInZRange() {
        return leftEyeInZRange;
    }

    /**
     * @return .
     */
    public static boolean isRightEyeDetected() {
        return rightEyeDetected;
    }

    /**
     * @return .
     */
    public static boolean isRightEyeInXRange() {
        return rightEyeInXRange;
    }

    /**
     * @return .
     */
    public static boolean isRightEyeInYRange() {
        return rightEyeInYRange;
    }

    /**
     * @return .
     */
    public static boolean isRightEyeInZRange() {
        return rightEyeInZRange;
    }

    /**
     * @param e
     * @return .
     */
    public static DiagState[] evaluatePupilSize(final EyeTrackingEvent e) {
        final DiagState state[] = { DiagState.BAD, DiagState.BAD };
        if (CommonFunctions.isBetweenIncludes(MIN_PUPILSIZE, MAX_PUPILSIZE, e.getPupilSizeLeft())) {
            state[0] = DiagState.OK;
        }
        if (CommonFunctions.isBetweenIncludes(MIN_PUPILSIZE, MAX_PUPILSIZE, e.getPupilSizeRight())) {
            state[1] = DiagState.OK;
        }
        return state;
    }

    /**
     * @param event
     * @return .
     */
    public static Map<DataPartition, DiagState> getOverallQualityStatus(final EyeTrackingEvent event) {
        final DiagState eval = evaluateEvent(event);

        // FIXME: WTF is this?
        final Map<DataPartition, DiagState> m = new HashMap<DataPartition, DiagState>();
        m.put(DataPartition.HEAD_DISTANCE, eval); // JStatusIndicator.getDiagState(isHeadInValidRange()));
        m.put(DataPartition.HEAD_POSITION, eval); // JStatusIndicator.getDiagState(isHeadInValidRange()));

        return m;
    }

    /**
     * This method returns the worst of a free number of diagnosis states
     * of type DiagState. This function assumes, that the numeric order
     * of the DiagState enumeration ist from good to bad 
     * 
     * @param args states to compare 
     * @return the worst of all supplied states
     */
    public static DiagState getWorstState(final DiagState... args) {
        DiagState state = DiagState.OK;
        for (int i = 0; i < args.length; i++) {
            if (args[i] == DiagState.BAD) { return DiagState.BAD; }
            if (args[i].ordinal() < state.ordinal()) {
                state = args[i];
            }
        }
        return state;
    }
}
