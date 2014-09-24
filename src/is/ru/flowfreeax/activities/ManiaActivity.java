package is.ru.flowfreeax.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.widget.TextView;
import is.ru.flowfreeax.R;
import is.ru.flowfreeax.services.Global;

/**
 * Activity for the time trial mode.
 */
public class ManiaActivity extends Activity {
    CountDownTimer timer;
    public static final String SCORE_NAME = "ScoreFile";
    private Global global = Global.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        global.setContext(this);

        setTimer();
    }

    /**
     * Timer for mania mode.
     */
    public void setTimer(){
        setContentView(R.layout.play);
        timer = new CountDownTimer(60000, 1000){

            /**
             * Displays time left in real time
             * @param millisUntilFinished Milliseconds until time is finished.
             */
            @Override
            public void onTick(long millisUntilFinished) {
                TextView time = (TextView)findViewById(R.id.timer);
                time.setText("Time remaining: " + millisUntilFinished / 1000);
                if(millisUntilFinished / 1000 <= 5){
                    time.setTextColor(Color.RED);
                }
            }

            /**
             * Update high score in achievements and redirect user to main activity.
             */
            @Override
            public void onFinish() {
                SharedPreferences score = getSharedPreferences(SCORE_NAME, 0);
                int maniaScore = score.getInt("score", 0);
                if (maniaScore < global.iterator) {
                    SharedPreferences.Editor editor = score.edit();
                    editor.putInt("score", global.iterator);
                    editor.commit();
                }
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        }.start();
    }

    /**
     * If the back button is pushed: stop the timer, initialize high score iterator, and redirect to main activity.
     * @param keyCode What key is pressed.
     * @param event What should happen if param1 is pressed. Not used in this example.
     * @return If param1 is not pressed, do nothing.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            timer.cancel();
            global.iterator = 0;
            timer.onFinish();
        }
        return false;
    }
}