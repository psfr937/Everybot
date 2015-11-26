package com.example.everybot;

import android.util.Log;

/**
 * Created by Raymond on 06/11/2015.
 */

public class Triangulator {

    public static Point2D triangulate(AccessPoint p1, AccessPoint p2,
                                      AccessPoint p3){
        double A, B, C, D, E, F, DetX, DetY, Det;
        A = -2 * p1.getCoordinates().getX() + 2 * p3.getCoordinates().getX();
        B = -2 * p1.getCoordinates().getY() + 2 * p3.getCoordinates().getY();
        C = -2 * p2.getCoordinates().getX() + 2 * p3.getCoordinates().getX();
        D = -2 * p2.getCoordinates().getY() + 2 * p3.getCoordinates().getY();
        E = Math.pow(p1.getDistance(), 2) - Math.pow(p3.getDistance(), 2)
                - Math.pow(p1.getCoordinates().getX(), 2)
                + Math.pow(p3.getCoordinates().getX(), 2)
                - Math.pow(p1.getCoordinates().getY(), 2)
                + Math.pow(p3.getCoordinates().getY(), 2);
        F = Math.pow(p2.getDistance(), 2) - Math.pow(p3.getDistance(), 2)
                - Math.pow(p2.getCoordinates().getX(), 2)
                + Math.pow(p3.getCoordinates().getX(), 2)
                - Math.pow(p2.getCoordinates().getY(), 2)
                + Math.pow(p3.getCoordinates().getY(), 2);
        // Using Cramers Rule
        Det = A * D - B * C;
        DetX = E * D - B * F;
        DetY = A * F - E * C;
        Log.i("Mat","A = " + A + " B = " + B + " C = " + C + " D = " + D + " E = " + E + " F = " + F);
        Log.i("Det", "Det: " + String.valueOf(Det) + " DetX: " + String.valueOf(DetX) + "DetY: " + String.valueOf(DetY));
        return new Point2D(DetX / Det, DetY / Det);

    }
}
