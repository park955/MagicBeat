package jeonghu.magicbeat;

import android.widget.Button;

/**
 * Created by Chukwudi on 9/25/2017.
 */

class BeatButton
{
    private Button button;
    private String name;

    BeatButton(Button x){
        this.button = x;
        name = null;
    }


    /*
    private MediaPlayer beat;
    private boolean isInitialized = false;
    public int milliSeconds = 0;
    private Button button;
    private ToggleButton Tbutton;


    BeatButton(MediaPlayer beat, Button button){
        this.beat = beat;
        this.beat.setLooping(false);
        this.button = button;
    }

    BeatButton(MediaPlayer beat, ToggleButton tbutton){
        this.beat = beat;
        this.beat.setLooping(false);
        this.Tbutton = tbutton;
    }

    MediaPlayer getBeat(){
        return this.beat;
    }
    ToggleButton getTbutton(){
        return this.Tbutton;
    }

    void pause() {
        if(beat.isPlaying()) {
            milliSeconds = beat.getCurrentPosition();
            beat.pause();
        }
    }

    void pauseOrPlay(){
        if(beat.isPlaying()) {
            milliSeconds = beat.getCurrentPosition();
            beat.pause();
        }
        else{
            beat.seekTo(milliSeconds);
            beat.start();
        }
    }

    void reset() {
        milliSeconds = 0;
        beat.seekTo(milliSeconds);
    }

    void resume() {
        beat.seekTo(milliSeconds);
        beat.start();
    }
    */


}
