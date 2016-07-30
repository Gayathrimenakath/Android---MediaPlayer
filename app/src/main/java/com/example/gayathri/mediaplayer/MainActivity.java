package com.example.gayathri.mediaplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    private ProgressBar progressBar;
    //private Button play;
    private MediaPlayer mediaplayer;
    private Handler handler = new Handler();
    private TextView Songtimer;
    private long songStartTime;
    private ImageView Nowimage;
    private ImageButton play;
    //private ImageButton pause;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListeners();
    }

    private  void initHandler()
    {
        handler.postDelayed(updateUI,1000);
    }
    private Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            double seekPercentage = 100 * mediaplayer.getCurrentPosition() / mediaplayer.getDuration();

            progressBar.setProgress((int) seekPercentage);
            long seconds = (System.currentTimeMillis() - songStartTime) /1000;
            Songtimer.setText(String.format("%02d:%02d", seconds/ 60,seconds % 60));
            handler.postDelayed(this,1000);
        }
    };

    private void initListeners() {
        play.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(mediaplayer.isPlaying())
                        {
                            handler.removeCallbacks(updateUI);
                            mediaplayer.pause();
                            play.setImageResource(R.drawable.play_button);
                            //play.setText("Play");
                        }
                        else
                        {
                            initHandler();
                            mediaplayer.start();
                            songStartTime = System.currentTimeMillis();
                            play.setImageResource(R.drawable.pause_button);
                            //play.setText("Pause");
                        }
                    }
                }
        );
    }

    private void initView(){
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        play = (ImageButton) findViewById(R.id.play);
        //pause = (ImageButton) findViewById(R.id.pause);
        mediaplayer = MediaPlayer.create(this, R.raw.song);
        Songtimer = (TextView) findViewById(R.id.Songtimer);
        Songtimer.setText(String.format("%02d:%02d", 0, 0));
        Nowimage = (ImageView) findViewById(R.id.Nowimage);
        Nowimage.setImageDrawable(getResources().getDrawable(R.drawable.now_playing));
    }
}

