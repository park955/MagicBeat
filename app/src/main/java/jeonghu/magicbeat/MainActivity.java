package jeonghu.magicbeat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonb1,buttonb2,buttonb3,buttonp1,buttonp2;
    MediaPlayer beat01,beat02,beat03,piano01,piano02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonb1 = (Button) findViewById(R.id.buttonb1);
        buttonb2 = (Button) findViewById(R.id.buttonb2);
        buttonb3 = (Button) findViewById(R.id.buttonb3);
        buttonp1 = (Button) findViewById(R.id.buttonp1);
        buttonp2 = (Button) findViewById(R.id.buttonp2);

        beat01 = MediaPlayer.create(MainActivity.this,R.raw.beat01_bpm160);
        beat02 = MediaPlayer.create(MainActivity.this,R.raw.beat02_bpm160);
        beat03 = MediaPlayer.create(MainActivity.this,R.raw.beat03_bpm160);
        piano01 = MediaPlayer.create(MainActivity.this,R.raw.piano01_bpm160);
        piano02 = MediaPlayer.create(MainActivity.this,R.raw.piano02_bpm160);

    }

    public void pressedb1(View view){
        if(beat01.isPlaying()){
            beat01.pause();
        }
        else{
            if(beat02.isPlaying()) beat02.pause();
            if(beat03.isPlaying()) beat03.pause();

            beat01.setLooping(true);
            beat01.start();
        }
    }
    public void pressedb2(View view){
        if(beat02.isPlaying()){
            beat02.pause();
        }
        else{
            if(beat01.isPlaying()) beat01.pause();
            if(beat03.isPlaying()) beat03.pause();

            beat02.setLooping(true);
            beat02.start();
        }
    }
    public void pressedb3(View view){
        if(beat03.isPlaying()){
            beat03.pause();
        }
        else{
            if(beat01.isPlaying()) beat01.pause();
            if(beat02.isPlaying()) beat02.pause();

            beat03.setLooping(true);
            beat03.start();
        }
    }

    public void pressedp1(View view){
        if(piano01.isPlaying()){
            piano01.pause();
        }
        else{
            if(piano02.isPlaying()) piano02.pause();

            piano01.setLooping(true);
            piano01.start();
        }
    }

    public void pressedp2(View view){
        if(piano02.isPlaying()){
            piano02.pause();
        }
        else{
            if(piano01.isPlaying()) piano01.pause();

            piano02.setLooping(true);
            piano02.start();
        }
    }

    public void pressedReset(View view){
        if(beat01.isPlaying()) beat01.pause();
        if(beat02.isPlaying()) beat02.pause();
        if(beat03.isPlaying()) beat03.pause();
        if(piano01.isPlaying()) piano01.pause();
        if(piano02.isPlaying()) piano02.pause();

        beat01.reset();
        beat02.reset();
        beat03.reset();
        piano01.reset();
        piano02.reset();

        beat01.
    }
}
