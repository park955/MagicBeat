package jeonghu.magicbeat;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Jeonghu on 10/19/17.
 */

public class MakeActivity extends Activity {

    int maxPosition = 8;
    int currentPosition = 8;
    ArrayList<LinearLayout> rows = new ArrayList<>();

    //To access the NoteButton at i,j
    //NoteButton x = (NoteButton) rows.get(i).getChildAt(j);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);

        setUpScreen();

        //Right Arrow Button
        ((ImageButton) findViewById(R.id.rightArrow)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(maxPosition == currentPosition)
                    Toast.makeText(getApplicationContext(), "Can't go to further right", Toast.LENGTH_SHORT).show();
                else{
                    //SHIFT THE VIEW HERE
                }
            }
        });

        //Left Arrow Button
        ((ImageButton) findViewById(R.id.leftArrow)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(maxPosition-8 == 0)
                    Toast.makeText(getApplicationContext(), "Can't go to further Left", Toast.LENGTH_SHORT).show();
                else{
                    //SHIFT THE VIEW HERE
                }
            }
        });

        //Delete Button - Complete!
        ((ImageButton) findViewById(R.id.deleteButton)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                for(LinearLayout ll:rows){
                    for(int i=1; i<=maxPosition; i++){
                        NoteButton x = (NoteButton) ll.getChildAt(i);
                        if(x.isChecked()) {
                            //Uncheck all the checked buttons
                            x.click(view);
                        }
                    }
                }
                Toast.makeText(getApplicationContext(), "Loop cleared", Toast.LENGTH_SHORT).show();
            }
        });

        //Add Button
        ((ImageButton) findViewById(R.id.addButton)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                maxPosition += 4;
                for(int i = maxPosition-4; i<=maxPosition; i++) {
                    for (LinearLayout ll : rows) {

                        TextView note = (TextView) ll.getChildAt(0);
                        ll.addView(makeNoteButton((String)note.getText(), i));

                    }
                }
                Toast.makeText(getApplicationContext(), "Loop length increased by 4", Toast.LENGTH_SHORT).show();
            }
        });

        //Save Button
        //Extract Information out of all the NoteButtons and create a midi file!
        ((ImageButton) findViewById(R.id.saveButton)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText title = (EditText) findViewById(R.id.loop_name);
//                File output = new File(title.getText()+".mid");

                //Let's make some MIDI files!!
                //com.leff.midi.MidiTrack

                for(LinearLayout ll:rows){
                    for(int i=1; i<=8; i++){
                        NoteButton x = (NoteButton) ll.getChildAt(i);
                        if(x.isChecked()) {
                            //Record the position & change them to the MIDI files


                        }
                    }
                }

                //Toast.makeText(getApplicationContext(),title.getText() , Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setUpScreen() {

        LinearLayout orig = (LinearLayout) findViewById(R.id.note_picker_view);
        orig.setWeightSum(13.0f);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);

        for (int i = 0; i < 13; i++) {
            LinearLayout ll = new LinearLayout(this);
            ll.setLayoutParams(linearLayoutParams);
            rows.add(ll);
            orig.addView(ll);
        }

        int counter = 0;
        for (LinearLayout ll : rows) {
            //if i = 0, make sure to put a text view instead of a button
            TextView tv = new TextView(this);
            tv.setLayoutParams(buttonParams);
            tv.setText(num2Note(counter));
            counter++;
            ll.addView(tv);
            for (int i = 1; i < 9; i++) {
                //create a notebutton class (derive from button)
//                NoteButton x = new NoteButton(this,tv.getText().toString(),i+currentPosition-8,color);
//                x.setText(Integer.toString(i+currentPosition-8)+x.getNote());
//                if(color){
//                    x.setBackgroundColor(Color.DKGRAY);
//                    x.setTextColor(Color.WHITE);
//                } else x.setBackgroundColor(Color.WHITE);
//
//                x.setLayoutParams(buttonParams);
                ll.addView(makeNoteButton(tv.getText().toString(),i+currentPosition-8));
            }
        }
    }

    private boolean whatColor(String note){
        //true is black; false is white
        if(note.indexOf("#") == 1) return true;
        else return false;
    }

    private String num2Note(int x){
        switch(x){
            case 0: return "C";
            case 1: return "B";
            case 2: return "A#";
            case 3: return "A";
            case 4: return "G#";
            case 5: return "G";
            case 6: return "F#";
            case 7: return "F";
            case 8: return "E";
            case 9: return "D#";
            case 10: return "D";
            case 11: return "C#";
            case 12: return "C";
            default: return null;
        }
    }

    private NoteButton makeNoteButton(String note, int position){
        NoteButton x = new NoteButton(this,note,position,whatColor(note));
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);

        x.setLayoutParams(buttonParams);
        x.setText(Integer.toString(position)+note);
        if(whatColor(note)){
            x.setBackgroundColor(Color.DKGRAY);
            x.setTextColor(Color.WHITE);
        } else x.setBackgroundColor(Color.WHITE);

        return x;

    }


}
