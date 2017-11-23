package com.retrofitmoredownload;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.retrofitmoredownload.net.DownloadThread;

import cn.jzvd.JZVideoPlayerStandard;



public class MainActivity extends AppCompatActivity implements DownloadThread.ReturnProgress {



    private String TAG = "=======MainActivity=======";
    private long leng=0;

    Handler handler = new Handler();
    private ProgressBar pb1;

    private JZVideoPlayerStandard jps1;
    private TextView tv;
    private DownloadThread downloadThread;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        pb1 = (ProgressBar) findViewById(R.id.pb);

        jps1 = (JZVideoPlayerStandard) findViewById(R.id.JPS);
        tv = (TextView) findViewById(R.id.tv);
        Log.i(TAG, "onCreate: FilePath" + getCacheDir());

        //播放视频

        String path = getCacheDir() + "/c.mp4";
        String path2 = "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4";
        jps1.setUp(path, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");

        Glide.with(MainActivity.this).load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640").into(jps1.thumbImageView);
//
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {

        boolean b = jps1.backPress();
        if (b) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jps1.removeAllViews();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void retrunProgre(final long len, final long length) {
        leng+=len;
        Log.i(TAG, "retrunProgre: leng:" + leng + ";length:" + length);

        Runnable run = new Runnable() {
            @Override
            public void run() {
                int progress = (int) (leng * 100 / length);

                pb1.setProgress(progress);
                tv.setText(progress + "%");
            }
        };
        handler.postDelayed(run, 1000);
    }

    @SuppressLint("LongLogTag")
    public void Click(View view){
        int id = view.getId();
        switch (id) {
            case R.id.start:
                Log.i(TAG, "Click: "+"kaishi");
                downloadThread = new DownloadThread("2449_bfbbfa3cea8f11e5aac3db03cda99974.f20.mp4", getCacheDir() + "/c.mp4",this);
                downloadThread.setThreadNum(3);
                downloadThread.setrP(this);

                downloadThread.setDown(true);
                downloadThread.start();
                jps1.startVideo();
                break;
            case R.id.pause:
                Log.i(TAG, "Click: "+"pause");
                downloadThread.setDown(false);
                break;
        }
    }

}
