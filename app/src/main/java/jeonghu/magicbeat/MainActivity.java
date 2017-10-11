package jeonghu.magicbeat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import static jeonghu.magicbeat.R.id.TbuttonM1;
import static jeonghu.magicbeat.R.id.TbuttonM3;
import static jeonghu.magicbeat.R.id.buttonb1;
import static jeonghu.magicbeat.R.id.buttonb2;
import static jeonghu.magicbeat.R.id.buttonb3;
import static jeonghu.magicbeat.R.id.buttonp1;
import static jeonghu.magicbeat.R.id.buttonp2;


public class MainActivity extends AppCompatActivity {

    BeatButton b1, b2, b3, p1, p2;
    TextView test;
    ProgressBar bar;

    BeatButton[] mybeats = new BeatButton[10];

    @Override
    public void onPause(){
        super.onPause();

        b1.pause();
        b2.pause();
        b3.pause();
        p1.pause();
        p2.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = new BeatButton(MediaPlayer.create(MainActivity.this,R.raw.beat01_bpm160),(Button)findViewById(buttonb1));
        b2 = new BeatButton(MediaPlayer.create(MainActivity.this,R.raw.beat02_bpm160),(Button)findViewById(buttonb2));
        b3 = new BeatButton(MediaPlayer.create(MainActivity.this,R.raw.beat03_bpm160),(Button)findViewById(buttonb3));
        p1 = new BeatButton(MediaPlayer.create(MainActivity.this,R.raw.piano01_bpm160),(Button)findViewById(buttonp1));
        p2 = new BeatButton(MediaPlayer.create(MainActivity.this,R.raw.piano02_bpm160),(Button)findViewById(buttonp2));

        test = (TextView)findViewById(R.id.textView);
        test.setText("Testing");

        bar = (ProgressBar) findViewById(R.id.progressBar);

        mybeats[0] = new BeatButton(MediaPlayer.create(MainActivity.this,R.raw.megalopiano_120c),(ToggleButton)findViewById(R.id.TbuttonM1));
        mybeats[1] = new BeatButton(MediaPlayer.create(MainActivity.this,R.raw.megalobass_120c),(ToggleButton)findViewById(R.id.TbuttonM2));
        mybeats[2] = new BeatButton(MediaPlayer.create(MainActivity.this,R.raw.megalobeat_120),(ToggleButton)findViewById(R.id.TbuttonM3));

        //ButtonReset
        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.pause();
                b2.pause();
                b3.pause();
                p1.pause();
                p2.pause();

                b1.reset();
                b2.reset();
                b3.reset();
                p1.reset();
                p2.reset();

                for(int i=0; i<3; i++){
                    mybeats[i].pause();
                    mybeats[i].reset();
                    mybeats[i].getTbutton().setChecked(false);
                }

            }
        });

/*
        findViewById(buttonb1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.pauseOrPlay();
            }
        });
        findViewById(buttonb2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b2.pauseOrPlay();
            }
        });
        findViewById(buttonb3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b3.pauseOrPlay();
            }
        });
        findViewById(buttonp1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1.pauseOrPlay();
            }
        });
        findViewById(buttonp2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p2.pauseOrPlay();
            }
        });
        */
        findViewById(TbuttonM1).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
              test.setText(Integer.toString(mybeats[0].getBeat().getDuration()));
            }
        });
        findViewById(TbuttonM3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                test.setText(Integer.toString(mybeats[2].getBeat().getDuration()));
            }
        });

        final Handler handler = new Handler();
        final int delay = 8000; //milliseconds
        //ProgressBar's maximum is also set to 8000

        handler.postDelayed(new Runnable(){
            public void run(){
                handler.postDelayed(this, delay);
                if(mybeats[0].getTbutton().isChecked()) mybeats[0].resume();
                if(mybeats[1].getTbutton().isChecked()) mybeats[1].resume();
                if(mybeats[2].getTbutton().isChecked()) mybeats[2].resume();
            }
        }, delay);

    }
}
