package jeonghu.magicbeat;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jeonghu on 10/19/17.
 */

public class MakeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);

        setUpScreen();
    }

    private void setUpScreen() {
        ArrayList<LinearLayout> rows = new ArrayList<>();
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
        boolean color = false;
        for (LinearLayout ll : rows) {
            //if i = 0, make sure to put a text view instead of a button
            TextView tv = new TextView(this);
            tv.setLayoutParams(buttonParams);
            switch(counter){
                case 0: tv.setText("C"); color = false; break;
                case 1: tv.setText("B"); color = false; break;
                case 2: tv.setText("A#"); color = true; break;
                case 3: tv.setText("A"); color = false; break;
                case 4: tv.setText("G#"); color = true; break;
                case 5: tv.setText("G"); color = false; break;
                case 6: tv.setText("F#"); color = true; break;
                case 7: tv.setText("F"); color = false; break;
                case 8: tv.setText("E"); color = false; break;
                case 9: tv.setText("D#"); color = true; break;
                case 10: tv.setText("D"); color = false; break;
                case 11: tv.setText("C#"); color = true; break;
                case 12: tv.setText("C"); color = false; break;
            }
            counter++;
            ll.addView(tv);
            for (int i = 1; i < 9; i++) {
                //create a notebutton class (derive from button)
//                TextView tvx = new TextView(this);
//                tvx.setLayoutParams(buttonParams);
//                tvx.setText("X");
//                ll.addView(tvx);
                NoteButton x = new NoteButton(this,tv.getText().toString(),i);
                x.setText(Integer.toString(x.getPosition())+x.getNote());
                if(color){
                    x.setBackgroundColor(Color.DKGRAY);
                    x.setTextColor(Color.WHITE);
                }
                else x.setBackgroundColor(Color.WHITE);
                x.setLayoutParams(buttonParams);
                ll.addView(x);
            }
        }
    }


}
