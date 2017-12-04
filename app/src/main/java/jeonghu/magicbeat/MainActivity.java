package jeonghu.magicbeat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.billthefarmer.mididriver.MidiDriver;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements MidiDriver.OnMidiStartListener{

    static BeatButton[] mybeats = new BeatButton[12];
    ArrayList<LinearLayout> rows = new ArrayList<>();
    public int bpm = 120;


    //MIDI CODES ----------------------
    MidiDriver midix = new MidiDriver();
    protected void sendMidi(int m, int n, int v){
        byte msg[] = new byte[3];
        msg[0] = (byte) m;
        msg[1] = (byte) n;
        msg[2] = (byte) v;
        midix.write(msg);
    }
    protected void sendMidi(int m, int p){
        byte msg[] = new byte[2];
        msg[0] = (byte) m;
        msg[1] = (byte) p;
        midix.write(msg);
    }
    @Override
    public void onResume(){
        super.onResume();
        midix.start();
    }

    @Override
    public void onPause(){
        super.onPause();

        Gson gson = new Gson();

        int[][][] stuff = new int[12][8][13];
        for (int i = 0; i < 12; i++) stuff[i] = mybeats[i].getNotes();

        String[] names = new String[12];
        for (int i = 0; i < 12; i++) names[i] = mybeats[i].getName();

        int[] bpms = new int[12];
        for (int i = 0; i < 12; i++) bpms[i] = mybeats[i].getBpm();

        SavedNotes sv = new SavedNotes(stuff, names,bpms);

        String json = gson.toJson(sv);
        SharedPreferences sp = this.getSharedPreferences(getPackageName(), Context.MODE_APPEND);
        sp.edit().putString("Notes", json).apply();

        midix.stop();
    }
    //MIDI CODES ----------------------

    @Override
    public void onMidiStart(){
        sendMidi(0xc0,6);
    }

    private class playThreadFromNotes extends Thread{
        private BeatButton x;
        playThreadFromNotes(BeatButton x){
            this.x = x;
        }

        public void run(){
            for(int i=0; i<8; i++){
                for(int j=0; j<13; j++){
                    if(x.getNotes()[i][j]!=0){
                        sendMidi(0x90,x.getNotes()[i][j],63);
                    }
                }
                //Pause Here
                try {
                    Thread.sleep(x.getDelay());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mybeats[0] = new BeatButton((Button)findViewById(R.id.button0));
        mybeats[1] = new BeatButton((Button)findViewById(R.id.button1));
        mybeats[2] = new BeatButton((Button)findViewById(R.id.button2));
        mybeats[3] = new BeatButton((Button)findViewById(R.id.button3));
        mybeats[4] = new BeatButton((Button)findViewById(R.id.button4));
        mybeats[5] = new BeatButton((Button)findViewById(R.id.button5));
        mybeats[6] = new BeatButton((Button)findViewById(R.id.button6));
        mybeats[7] = new BeatButton((Button)findViewById(R.id.button7));
        mybeats[8] = new BeatButton((Button)findViewById(R.id.button8));
        mybeats[9] = new BeatButton((Button)findViewById(R.id.button9));
        mybeats[10] = new BeatButton((Button)findViewById(R.id.button10));
        mybeats[11] = new BeatButton((Button)findViewById(R.id.button11));

        Gson gson = new Gson();

        SharedPreferences sp = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        String json = sp.getString("Notes", "");

        SavedNotes ns = gson.fromJson(json, SavedNotes.class);

        if (ns != null) Log.d("LOOK HERE", Arrays.toString(ns.savedNotes));

        //Load data
        for(int i=0; i<12; i++){
            mybeats[i].setId(i);

            if (ns == null) continue;

            if(ns.savedNotes[i] == null) continue;
            mybeats[i].setNotes(ns.savedNotes[i]);

            mybeats[i].setName(ns.names[i]);
            mybeats[i].getButton().setText(mybeats[i].getName());

            mybeats[i].setBpm(ns.bpm[i]);
        }

        //Trash can button
        FloatingActionButton removeLoop = (FloatingActionButton) findViewById(R.id.removeLoop);
        removeLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Empty all the buttons?");
                alertDialogBuilder.setNegativeButton("No", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialogBuilder.setPositiveButton("Yes", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<12; j++){
                            mybeats[j].emptyNotes();
                            mybeats[j].setName("EMPTY");
                            mybeats[j].getButton().setText(mybeats[j].getName());
                        }
                        Toast.makeText(getApplicationContext(), "All buttons are emptied", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //Help button
        FloatingActionButton helpbutton = (FloatingActionButton) findViewById(R.id.help);
        helpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("1. Long tap to add a melody!\n2. Tap to play the melody!\n3. Use up/down to control speed!");
                alertDialogBuilder.setNeutralButton("Ok", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //Beat buttons
        for( final BeatButton bb : mybeats ){
            bb.getButton().setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view) {

                    Intent goMakeLoop;
                    goMakeLoop = new Intent(MainActivity.this, MakeActivity.class);
                    goMakeLoop.putExtra("index",bb.getId());
                    startActivity(goMakeLoop);

                    return false;
                }
            });

            bb.getButton().setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //Check if the beatbutton is empty and if not play the "beat"
                    if(bb.notes==null){
                        Toast.makeText(getApplicationContext(), "This is empty. Long tap to add melody", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        playThreadFromNotes p = new playThreadFromNotes(bb);
                        p.start();
                    }
                }
            });
        }


    }
}
