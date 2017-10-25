package jeonghu.magicbeat;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

/**
 * Created by Jeonghu on 10/25/17.
 */

public class NoteButton extends AppCompatButton{

    String note;
    int position = 0;

    NoteButton(Context context){
        super(context);
    }
    NoteButton(Context context, String note, int position){
        this(context);
        this.note = note;
        this.position = position;
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

}
