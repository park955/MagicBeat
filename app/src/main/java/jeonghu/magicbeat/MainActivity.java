package jeonghu.magicbeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.billthefarmer.mididriver.MidiDriver;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MidiDriver.OnMidiStartListener{

    static BeatButton[] mybeats = new BeatButton[12];
    ArrayList<LinearLayout> rows = new ArrayList<>();


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
        midix.stop();
    }
    //MIDI CODES ----------------------

    @Override
    public void onMidiStart(){
        sendMidi(0xc0,6);
    }

    private class playThreadFromNotes extends Thread{
        private int[][] notes;
        playThreadFromNotes(int[][] notes){
            this.notes = notes;
        }

        public void run(){
            for(int i=1; i<=8; i++){
                for(int j=0; j<13; j++){
                    if(notes[i][j]!=0){
                        sendMidi(0x90,notes[i][j],63);
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

        for(int i=0; i<12; i++){
            mybeats[i].setId(i);
            mybeats[i].getButton().setText(mybeats[i].getName());
        }

        FloatingActionButton makeLoop = (FloatingActionButton) findViewById(R.id.makeLoop);
        makeLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goMakeLoop;
                goMakeLoop = new Intent(MainActivity.this, MakeActivity.class);
                startActivity(goMakeLoop);

            }
        });

        FloatingActionButton removeLoop = (FloatingActionButton) findViewById(R.id.removeLoop);
        removeLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0; i<12; i++){
                    mybeats[i].emptyNotes();
                    mybeats[i].setName("EMPTY");
                    mybeats[i].getButton().setText(mybeats[i].getName());
                }
                Toast.makeText(getApplicationContext(), "All loops are cleared", Toast.LENGTH_SHORT).show();
            }
        });

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
                        playThreadFromNotes p = new playThreadFromNotes(bb.notes);
                        p.start();
                    }
                }
            });
        }


    }
}
