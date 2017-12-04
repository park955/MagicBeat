package jeonghu.magicbeat;

import android.widget.Button;

/**
 * Created by Chukwudi on 9/25/2017.
 */

class BeatButton
{
    private Button button;
    private String name;
    private int id;
    int[][] notes;
    private int bpm;

    BeatButton(Button x){
        this.button = x;
        name = "Empty";
    }

    Button getButton(){
        return button;
    }
    int getId(){
        return id;
    }
    void setId(int x){
        this.id = x;
    }
    void setName(String name){
        this.name = name;
    }
    String getName(){
        return name;
    }
    void emptyNotes(){
        notes = null;
    }
    int[][] getNotes(){
        return notes;
    }
    void setNotes(Integer[][] notes){
        int[][] rip = new int[8][13];

        for (int i = 0; i < 8; i++){
            if (notes[i] == null) continue;
            for(int j=0; j<13; j++){
                if(notes[i][j] == null) continue;
                rip[i][j] = notes[i][j];
            }
        }
        this.notes = rip;
    }

    void setNotes(int[][] notes) {
        this.notes = notes;
    }
    void setBpm(int x){
        this.bpm = x;
    }
    int getBpm(){
        return bpm;
    }
    int getDelay(){
        double qqq = (double) bpm/60.0;
        qqq = 1000/qqq;
        return (int)qqq;
    }

}
