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

}
