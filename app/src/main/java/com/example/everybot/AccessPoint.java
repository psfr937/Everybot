package com.example.everybot;

/**
 * Created by Raymond on 06/11/2015.
 */

import java.util.Date;

public class AccessPoint {
        public static final int MIN_SIGNAL_LEVEL = Integer.MIN_VALUE;

        String SSID;

        String macAddress;
        int signalLevel = 0;
        int signalFrequency = 0;
        Point2D coordinates = new Point2D(0, 0);
        double signalStrengthToDistanceRatio = 1;

        Date timestamp;

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public String getMacAddress() {
            return macAddress;
        }

        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress.toUpperCase();
        }

        public int getSignalLevel() {
            return signalLevel;
        }

        public void setSignalLevel(int signalLevel) {
            this.signalLevel = signalLevel;
        }

     public int getSignalFrequency(){
         return signalFrequency;
     }

    public void setSignalFrequency(int signalFrequency){
        this.signalFrequency = signalFrequency;
    }

        public Point2D getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Point2D coordinates) {
            this.coordinates = coordinates;
        }

        public double getSignalStrengthToDistanceRatio() {
            return signalStrengthToDistanceRatio;
        }

        public void setSignalStrengthToDistanceRatio(
                double signalStrengthToDistanceRatio) {
            this.signalStrengthToDistanceRatio = signalStrengthToDistanceRatio;
        }

        public String getSSID() {
            return SSID;
        }

        public void setSSID(String sSID) {
            SSID = sSID;
        }

        public double getDistance() {
            double exp = (27.55 - (20 * Math.log10(this.getSignalFrequency())) - Math.abs(this.getSignalLevel())) / 20.0;
            return Math.pow(10.0, exp);
            //return signalLevel*50;
            //return signalStrengthToDistanceRatio*(100+signalLevel);
        }
    }
