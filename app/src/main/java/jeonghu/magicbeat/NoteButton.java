package jeonghu.magicbeat;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created by Jeonghu on 10/25/17.
 */

public class NoteButton extends AppCompatButton {

    private String note;
    private int position = 0;
    private boolean isChecked = false;
    private boolean color = true;

    NoteButton(Context context){
        super(context);
    }

    NoteButton(final Context context, String note, int position, boolean color){
        this(context);
        this.note = note;
        this.position = position;
        this.color = color;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                click(view);
            }
        });
    }

    public void setNote(String note){
        this.note = note;
    }

    public String getNote(){
        return this.note;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition(){
        return this.position;
    }

    public boolean isChecked(){
        return isChecked;
    }

    public void click(View v){
        isChecked = !isChecked;
        if(isChecked) {
            Log.i(TAG, "onClick: thiis gets here");
            this.setBackgroundColor(Color.GREEN);
        } else {
            if(color){
                this.setBackgroundColor(Color.DKGRAY);
                this.setTextColor(Color.WHITE);
            } else{
                this.setBackgroundColor(Color.WHITE);
                this.setTextColor(Color.BLACK);
            }
        }


    }

}
