package jeonghu.magicbeat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MakeActivity extends AppCompatActivity {

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

        for (LinearLayout ll : rows) {
            for (int i = 0; i < 9; i++) {
                //if i = 0, make sure to put a text view instead of a button
                TextView tv = new TextView(this);
                tv.setLayoutParams(buttonParams);
                tv.setText("C");
                ll.addView(tv);

                //create a notebutton class (derive from button)
            }
        }
    }
}
