package jeonghu.magicbeat;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

/**
 * Created by Chukwudi on 9/25/2017.
 */

public class BeatButton extends AppCompatButton implements View.OnClickListener {
    MediaPlayer beat;
    boolean isInitialized = false;
    int milliSeconds = 0;

    public BeatButton(Context context) {
        super(context);
    }

    void initialize(MediaPlayer beat) {
        if (isInitialized) return;

        isInitialized = true;
        this.beat = beat;
    }

    void pause() {
        milliSeconds = beat.getCurrentPosition();
        beat.pause();
    }

    void reset() {
        milliSeconds = 0;
        beat.seekTo(milliSeconds);
    }

    void resume() {
        beat.seekTo(milliSeconds);
        beat.start();
    }

    @Override
    public void onClick(View view) {
        if (beat.isPlaying()) pause();
        else resume();
    }
}
