package jeonghu.magicbeat;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.billthefarmer.mididriver.MidiDriver;

import java.util.ArrayList;

/**
 * Created by Jeonghu on 10/19/17.
 */

public class MakeActivity extends Activity implements MidiDriver.OnMidiStartListener{
    int maxPosition = 8;
    int currentPosition = 8;
    ArrayList<LinearLayout> rows = new ArrayList<>();
    //ArrayList<Screen> screens = new ArrayList<>();
    MidiDriver midi;
    private int passedItemIndex;

    static LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
    static LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);

    //To access the NoteButton at i,j
    //NoteButton x = (NoteButton) rows.get(i).getChildAt(j);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);

        passedItemIndex = getIntent().getIntExtra("index", 0);
        Toast.makeText(getApplicationContext(), Integer.toString(passedItemIndex), Toast.LENGTH_SHORT).show();

        midi = new MidiDriver();

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
                            if(x.getNoteState() == NoteState.HIGH) x.longclick();
                            else x.click();

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
                /*
                maxPosition += 4;
                for(int i = maxPosition-4; i<=maxPosition; i++) {
                    for (LinearLayout ll : rows) {

                        TextView note = (TextView) ll.getChildAt(0);
                        ll.addView(makeNoteButton((String)note.getText(), i));

                    }
                }
                Toast.makeText(getApplicationContext(), "Loop length increased by 4", Toast.LENGTH_SHORT).show();
                */
                Toast.makeText(getApplicationContext(), "This feature is currently disabled", Toast.LENGTH_SHORT).show();
            }
        });

        //Play Button
        ((ImageButton) findViewById(R.id.playButton)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                playThread p = new playThread();
                p.start();

            }
        });

        //Save Button
        //Extract Information out of all the NoteButtons and create a midi file!
        ((ImageButton) findViewById(R.id.saveButton)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText title = (EditText) findViewById(R.id.loop_name);
//                File output = new File(title.getText()+".mid");

                int[][] notes = new int[9][13];
                for(int i=1; i<=8; i++){
                    for(int j=0; j<13; j++){
                        NoteButton x = (NoteButton) rows.get(j).getChildAt(i);
                        if(x.isChecked()){
                            if(x.getNoteState() == NoteState.HIGH)
                                notes[i][j] = x.getNoteInt()+12;
                            else
                                notes[i][j] = x.getNoteInt();
                        }
                    }
                }

                MainActivity.mybeats[passedItemIndex].notes = notes;

                if(title.getText().toString().isEmpty()){
                    MainActivity.mybeats[passedItemIndex].setName("Custom Loop");
                    MainActivity.mybeats[passedItemIndex].getButton().setText("Custom Loop");
                }
                else {
                    MainActivity.mybeats[passedItemIndex].setName(title.getText().toString());
                    MainActivity.mybeats[passedItemIndex].getButton().setText(title.getText().toString());
                }
                onBackPressed();

            }
        });


    }

    private class playThread extends Thread{
        public void run(){
            for(int i=1; i<=maxPosition; i++){
                for(int j=0; j<13; j++){
                    NoteButton x = (NoteButton) rows.get(j).getChildAt(i);
                    if(x.isChecked()){
                        if(x.getNoteState() == NoteState.HIGH)
                        sendMidi(0x90,x.getNoteInt()+12,63);
                        else
                            sendMidi(0x90,x.getNoteInt(),63);
                    }
                }
                //Pause Here
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setUpScreen() {

        LinearLayout orig = (LinearLayout) findViewById(R.id.note_picker_view);
        orig.setWeightSum(13.0f);

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
            case 0: return "C5";
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
            case 12: return "C4";
            default: return null;
        }
    }

    private int note2Num(String x){
        if(x.equals("C4")) return 48;
        else if(x.equals("C#")) return 49;
        else if(x.equals("D")) return 50;
        else if(x.equals("D#")) return 51;
        else if(x.equals("E")) return 52;
        else if(x.equals("F")) return 53;
        else if(x.equals("F#")) return 54;
        else if(x.equals("G")) return 55;
        else if(x.equals("G#")) return 56;
        else if(x.equals("A")) return 57;
        else if(x.equals("A#")) return 58;
        else if(x.equals("B")) return 59;
        else return 60;
    }

    //Specify notes
    private NoteButton makeNoteButton(String note, int position){
        final NoteButton x = new NoteButton(this,note,position,whatColor(note),note2Num(note));
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);

        x.setLayoutParams(buttonParams);
        x.setText(Integer.toString(position)+note);
        if(whatColor(note)){
            x.setBackgroundColor(Color.DKGRAY);
            x.setTextColor(Color.WHITE);
        } else x.setBackgroundColor(Color.WHITE);

        x.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NoteButton) view).click();
                if(x.getNoteState() == NoteState.LOW) sendMidi(0x90,x.getNoteInt(),63);
            }
        });
        x.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((NoteButton) view).longclick();
                if(x.getNoteState() == NoteState.HIGH) sendMidi(0x90,x.getNoteInt()+12,63);
                return true;
            }
        });

        return x;

    }

    @Override
    public void onResume(){
        super.onResume();
        midi.start();
    }

    @Override
    public void onPause(){
        super.onPause();
        midi.stop();
    }

    @Override
    public void onMidiStart(){
        sendMidi(0xc0,6);
    }

    protected void sendMidi(int m, int p){
        byte msg[] = new byte[2];
        msg[0] = (byte) m;
        msg[1] = (byte) p;
        midi.write(msg);
    }
    protected void sendMidi(int m, int n, int v){
        byte msg[] = new byte[3];
        msg[0] = (byte) m;
        msg[1] = (byte) n;
        msg[2] = (byte) v;
        midi.write(msg);
    }

    /**
     * fix the constructor
     * use screen class to hold the "screens"
    private class Screen {
        ArrayList<LinearLayout> rows = new ArrayList<>();

        //change current position based on index
        Screen() {
            int counter = 0;
            for (LinearLayout ll : rows) {
                //if i = 0, make sure to put a text view instead of a button
                TextView tv = new TextView(MakeActivity.this);
                tv.setLayoutParams(buttonParams);
                tv.setText(num2Note(counter));
                counter++;
                ll.addView(tv);
                for (int i = 1; i < 9; i++) {
                    //create a notebutton class (derive from button)
                    ll.addView(makeNoteButton(tv.getText().toString(),i+currentPosition-8));
                }
            }

        }
    }
     **/

}
