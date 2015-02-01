package com.custom.circleprogress;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    CircleProgressBar mCircleProgressBarStroke;
    CircleProgressBar mCircleProgressBarFill;
    PerformLoadProgress mPerformLoadProgress;
    Handler mLoopHandler = new Handler();
    private int mProgress = 0;
    private static final int DEFAULT_LOAD_GAP = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircleProgressBarStroke = (CircleProgressBar) findViewById(R.id.id_circle_progress_bar_stroke);
        mCircleProgressBarFill = (CircleProgressBar) findViewById(R.id.id_circle_progress_bar_fill);
        mLoopHandler.postDelayed(new PerformLoadProgress(), 60);
        mPerformLoadProgress = new PerformLoadProgress();
        Toast.makeText(this, "正准备加载...", Toast.LENGTH_SHORT).show();

        findViewById(R.id.id_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoopHandler.removeCallbacks(mPerformLoadProgress);
                mProgress = 0;
                mLoopHandler.post(mPerformLoadProgress);
            }
        });
    }

    private class PerformLoadProgress implements Runnable {
        @Override
        public void run() {
            if (mProgress > mCircleProgressBarStroke.getMaxProgress()) {
                mLoopHandler.removeCallbacks(this);
                return;
            }
            mProgress += DEFAULT_LOAD_GAP;
            mCircleProgressBarStroke.setProgress(mProgress);
            mCircleProgressBarFill.setProgress(mProgress);
            mLoopHandler.postDelayed(this, 200);
        }
    }
}
