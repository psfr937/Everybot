package com.example.everybot;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends Activity {
        private AccessPoint[] knownAPs;
        private int refreshRate = 1000; // ms second

        ImageView[] accessPoints = new ImageView[3];
        ImageView device;

        private WifiManager wifiManager;
        private WifiInfo wifiInfo;

        private void setUpKnownAPs() {
                ArrayList<AccessPoint> aps = new ArrayList<AccessPoint>();
                // AP1
                AccessPoint ap = new AccessPoint();
                ap.setMacAddress("00:22:55:e0:eb:71");

                ap.getCoordinates().setX(76);
                ap.getCoordinates().setY(1000);
                aps.add(ap);
                //ap.setSignalStrengthToDistanceRatio(2.3);

                // AP2
                ap = new AccessPoint();
                ap.setMacAddress("00:22:55:e0:89:61");

                ap.getCoordinates().setX(614);
                ap.getCoordinates().setY(1000);
                aps.add(ap);
                //ap.setSignalStrengthToDistanceRatio(2.3);

                // AP3
                ap = new AccessPoint();
                ap.setMacAddress("00:0e:83:ed:20:41");
                ap.getCoordinates().setX(152);
                ap.getCoordinates().setY(800);
                aps.add(ap);
                //ap.setSignalStrengthToDistanceRatio(2.3);

                // AP4
                ap = new AccessPoint();
                ap.setMacAddress("00:22:55:e0:b7:c1");
                ap.getCoordinates().setX(384);
                ap.getCoordinates().setY(800);
                aps.add(ap);
                //ap.setSignalStrengthToDistanceRatio(2.3);

                // AP5
                ap = new AccessPoint();
                ap.setMacAddress("00:0e:07:9a:9c:91");
                ap.getCoordinates().setX(384);
                ap.getCoordinates().setY(600);
                aps.add(ap);
                //ap.setSignalStrengthToDistanceRatio(2.3);

                // AP6
                ap = new AccessPoint();
                ap.setMacAddress("00:22:55:e0:d6:01");
                ap.getCoordinates().setX(228);
                ap.getCoordinates().setY(500);
                aps.add(ap);
                //ap.setSignalStrengthToDistanceRatio(2.3);

                // AP7
                ap = new AccessPoint();
                ap.setMacAddress("00:22:90:38:41:b1");
                ap.getCoordinates().setX(614);
                ap.getCoordinates().setY(500);
                aps.add(ap);
                //ap.setSignalStrengthToDistanceRatio(2.3);

                // AP8
                ap = new AccessPoint();
                ap.setMacAddress("00:22:55:e0:a1:91");
                ap.getCoordinates().setX(384);
                ap.getCoordinates().setY(400);
                aps.add(ap);
                //ap.setSignalStrengthToDistanceRatio(2.3);

                // AP9
                ap = new AccessPoint();
                ap.setMacAddress("00:22:55:e0:b3:f1");
                ap.getCoordinates().setX(228);
                ap.getCoordinates().setY(300);
                aps.add(ap);
                //ap.setSignalStrengthToDistanceRatio(2.3);

                // AP10
                ap = new AccessPoint();
                ap.setMacAddress("00:22:90:38:3e:61");
                ap.getCoordinates().setX(532);
                ap.getCoordinates().setY(300);
                aps.add(ap);
                //ap.setSignalStrengthToDistanceRatio(2.3);

                // AP11
                ap = new AccessPoint();
                ap.setMacAddress("000:22:90:38:43:d1");
                ap.getCoordinates().setX(384);
                ap.getCoordinates().setY(200);
                aps.add(ap);
                //ap.setSignalStrengthToDistanceRatio(2.3);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        32, 32);
                params.leftMargin = (int) ap.getCoordinates().getX();
                params.topMargin = (int) ap.getCoordinates().getY();
                accessPoints[0].setLayoutParams(params);

                knownAPs = aps.toArray(new AccessPoint[aps.size()]);

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.main);

                accessPoints[0] = (ImageView) findViewById(R.id.apImageView1);
                accessPoints[1] = (ImageView) findViewById(R.id.apImageView2);
                accessPoints[2] = (ImageView) findViewById(R.id.apImageView3);

                setUpKnownAPs();

                device = (ImageView) findViewById(R.id.pinImageView);

                wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
                wifiInfo = wifiManager.getConnectionInfo();
                new WifiScanningAsyncTask().execute((Void[]) null);
        }

        private boolean isStopScanning = false;
        @TargetApi(21)
        private class WifiScanningAsyncTask extends AsyncTask<Void, String, Void> {

                @Override
                protected Void doInBackground(Void... arg0) {
                        while (!HomeActivity.this.isStopScanning) {
                                WifiManager.WifiLock wifiLock = wifiManager.createWifiLock(
                                        WifiManager.WIFI_MODE_SCAN_ONLY,
                                        "WifiSignalStrengthCollector");
                                if (!wifiManager.isWifiEnabled()) {
                                        wifiManager.setWifiEnabled(true);
                                }
                                wifiLock.acquire();

                                publishProgress("Scanning...");

                                wifiManager.startScan();

                                List<ScanResult> scanResults = wifiManager.getScanResults();

                                if (scanResults != null) {
                                        publishProgress("Scanning found " + scanResults.size()
                                                + " access points.");

                                        // update signal strength level
                                        for (int i = 0; i < knownAPs.length; i++) {
                                                knownAPs[i]
                                                        .setSignalLevel(AccessPoint.MIN_SIGNAL_LEVEL);
                                                for (ScanResult scanResult : scanResults) {

                                                        if (knownAPs[i].getMacAddress().toLowerCase()
                                                                .compareTo(scanResult.BSSID.toLowerCase()) == 0) {

                                                                knownAPs[i].setSignalLevel(WifiManager.calculateSignalLevel(scanResult.level, 10));

                                                                knownAPs[i].setSignalFrequency(wifiInfo.getFrequency());
                                                                knownAPs[i].setSSID(scanResult.SSID);
                                                                knownAPs[i].setTimestamp(new Date());

                                                                publishProgress("found AP Mac Address = "
                                                                        + scanResult.BSSID + " at "
                                                                        + knownAPs[i].getCoordinates()
                                                                        + " with distance = "
                                                                        + knownAPs[i].getDistance());


                                                                break;
                                                        }
                                                }
                                        }

                                        // sort known access points with signal strength descending
                                        for (int i = 0; i < knownAPs.length - 1; i++) {
                                                for (int j = i + 1; j < knownAPs.length; j++) {
                                                        if (knownAPs[i].getSignalLevel() < knownAPs[j]
                                                                .getSignalLevel()) {
                                                                AccessPoint tmp = knownAPs[i];
                                                                knownAPs[i] = knownAPs[j];
                                                                knownAPs[j] = tmp;
                                                        }
                                                }
                                        }
                                        // if there are less than 3 access points discovered, show
                                        // the message
                                        if (knownAPs[2].getSignalLevel() == AccessPoint.MIN_SIGNAL_LEVEL) {
                                                final String s = "There are not enough known access points to calculate the position.";
                                                publishProgress(s);
                                                runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                                Toast toast = Toast.makeText(
                                                                        HomeActivity.this
                                                                                .getApplicationContext(),
                                                                        s, Toast.LENGTH_SHORT);
                                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                                toast.show();
                                                        }
                                                });

                                        } else {
                                                final Point2D position = Triangulator.triangulate(
                                                        knownAPs[0], knownAPs[1], knownAPs[2]);
                                                publishProgress("Device position: " + position);

                                                runOnUiThread(new Runnable() {

                                                        @Override
                                                        public void run() {
                                                                StringBuilder sb = new StringBuilder();
                                                                for (int i = 0; i < 3; i++) {
                                                                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                                                                32, 32);
                                                                        params.leftMargin = (int) knownAPs[i]
                                                                                .getCoordinates().getX();
                                                                        params.topMargin = (int) knownAPs[i]
                                                                                .getCoordinates().getY();
                                                                        accessPoints[i].setLayoutParams(params);

                                                                        sb.append("AP" + (i + 1) + " ("
                                                                                + accessPoints[i].getLeft() + ","
                                                                                + accessPoints[i].getTop() + ")"
                                                                                + "; ");

                                                                        Log.d("ACCESS_POINT",
                                                                                "left: "
                                                                                        + accessPoints[i].getLeft()
                                                                                        + ", top: "
                                                                                        + accessPoints[i].getTop());
                                                                }

                                                                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                                                        32, 32);
                                                                params.leftMargin = (int) position.getX();
                                                                params.topMargin = (int) position.getY();
                                                                device.setLayoutParams(params);
                                                                Log.d("POSITION", "left: " + device.getLeft()
                                                                        + ", top: " + device.getTop());

                                                                sb.append("DEVICE (" + device.getLeft() + ","
                                                                        + device.getTop() + ")");

//                                                              Toast.makeText(
//                                                                              HomeActivity.this
//                                                                                              .getApplicationContext(),
//                                                                              sb.toString(), Toast.LENGTH_LONG)
//                                                                              .show();

                                                                Toast toast = Toast.makeText(
                                                                        HomeActivity.this
                                                                                .getApplicationContext(),
                                                                        sb.toString(), Toast.LENGTH_SHORT);
                                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                                toast.show();
                                                        }
                                                });

                                        }

                                }

                                wifiLock.release();
                                try {
                                        Thread.sleep(refreshRate);
                                } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                }

                        }

                        return null;

                }

                protected void onProgressUpdate(String... progress) {
                        Log.d("SCANNER", progress[0]);
                }

                @Override
                protected void onPostExecute(Void result) {
                        super.onPostExecute(result);
                }

        }
}