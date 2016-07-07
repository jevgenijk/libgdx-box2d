package com.aivars.firstgame.utils;

import com.aivars.firstgame.Constants;

public class Utils {

    public static float scale(int x) {
        return x / Constants.PPM;
    }

    public static float scale(float x) {
        return x / Constants.PPM;
    }

    public static double calcRotationAngleInDegrees(float x,float y, float x2, float y2) {
        double theta = Math.atan2(y2 - y, x2 - x);
        theta += Math.PI / 2.0;
        double angle = Math.toDegrees(theta);
        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }
}
