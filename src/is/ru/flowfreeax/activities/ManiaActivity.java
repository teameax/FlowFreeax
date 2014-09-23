package is.ru.flowfreeax.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import is.ru.flowfreeax.R;

public class ManiaActivity extends Activity {
    CountDownTimer timer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        //Switch between light and dark theme
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        LinearLayout play_layout = (LinearLayout)findViewById(R.id.play);
        boolean switchOn = preferences.getBoolean("theme_label", false);

        if(switchOn){
            play_layout.setBackgroundColor(Color.WHITE);
        }
        setTimer();
    }

    public void setTimer(){
        setContentView(R.layout.play);
        timer = new CountDownTimer(10000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                TextView time = (TextView)findViewById(R.id.timer);
                time.setText("Time remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        }.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            timer.cancel();
            timer.onFinish();
        }
        return false;
    }
}