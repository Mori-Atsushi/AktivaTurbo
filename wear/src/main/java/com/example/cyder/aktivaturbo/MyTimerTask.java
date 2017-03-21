package com.example.cyder.aktivaturbo;

import android.content.Context;
import java.util.TimerTask;
import android.os.Handler;

/**
 * Created by atsushi on 2017/03/21.
 */

public class MyTimerTask extends TimerTask {
    private Handler handler;
    private Context context;

    public MyTimerTask(Context context) {
        handler = new Handler();
        this.context = context;
    }

    @Override
    public void run() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                ((MainActivity)context).InvalidateScreen();
            }
        });
    }
}
