package ro.pub.cs.systems.eim.colocviu1_2.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Process;


import java.util.Date;

import ro.pub.cs.systems.eim.colocviu1_2.Colocviu1_2MainActivity;

public class ProcessingThread extends Thread {
    private Context context = null;
    private boolean isRunning = true;
    private int sum = 0;

    public ProcessingThread(Context context, int sum) {
        this.context = context;
        this.sum = sum;
    }

    @Override
    public void run() {
        Log.d(Colocviu1_2MainActivity.Constants.THREAD, "Thread has started! PID: " + Process.myPid());
        while (isRunning) {
            sleep();
            sendMessage();
            break;
        }
        Log.d(Colocviu1_2MainActivity.Constants.THREAD, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Colocviu1_2MainActivity.Constants.ACTION);
        intent.setPackage("ro.pub.cs.systems.eim.colocviu1_2");
        intent.putExtra(Colocviu1_2MainActivity.Constants.SENT,new Date(System.currentTimeMillis()) + "BROADCAST SENT WITH VALUE: " + sum);
        intent.putExtra(Colocviu1_2MainActivity.Constants.SUM, sum);
        Log.d("COLOCVIU",new Date(System.currentTimeMillis()) + "BROADCAST SENT WITH VALUE: " + sum);
        context.sendBroadcast(intent);

    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void stopThread() {
        isRunning = false;
    }


}
