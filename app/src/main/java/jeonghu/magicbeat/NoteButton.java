package jeonghu.magicbeat;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;

/**
 * Created by Jeonghu on 10/25/17.
 */

public class NoteButton extends AppCompatButton{

    private String note;
    private int position = 0;
    private NoteState noteState = NoteState.OFF;
    private boolean color = true;
    private int noteInt = 0;
    //MidiDriver midi;

    NoteButton(Context context){
        super(context);
    }

    NoteButton(final Context context, String note, int position, boolean color, int noteInt){
        this(context);
        this.note = note;
        this.position = position;
        this.color = color;
        this.noteInt = noteInt;
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
        if(noteState == NoteState.HIGH || noteState == NoteState.LOW)
        return true;

        return false;
    }

    public NoteState getNoteState(){
        return noteState;
    }

    public int getNoteInt(){
        return noteInt;
    }

    public NoteState click(){
        if(noteState == NoteState.HIGH) noteState = NoteState.HIGH;
        else if(noteState == NoteState.LOW) noteState = NoteState.OFF;
        else noteState = NoteState.LOW;


        if(noteState == NoteState.LOW) {
            this.setBackgroundColor(Color.GREEN);
        }
        else if(noteState == NoteState.HIGH){
            this.setBackgroundColor(Color.BLUE);
        }
        else {
            if(color){
                this.setBackgroundColor(Color.DKGRAY);
                this.setTextColor(Color.WHITE);
            } else{
                this.setBackgroundColor(Color.WHITE);
                this.setTextColor(Color.BLACK);
            }
        }

        return noteState;
    }

    public NoteState longclick(){
        if(noteState == NoteState.HIGH) noteState = NoteState.OFF;
        else if(noteState == NoteState.LOW) noteState = NoteState.HIGH;
        else noteState = NoteState.HIGH;

        if(noteState == NoteState.HIGH){
            this.setBackgroundColor(Color.BLUE);
        }
        else {
            if(color){
                this.setBackgroundColor(Color.DKGRAY);
                this.setTextColor(Color.WHITE);
            } else{
                this.setBackgroundColor(Color.WHITE);
                this.setTextColor(Color.BLACK);
            }
        }

        return noteState;
    }

}
