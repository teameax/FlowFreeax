package is.ru.flowfreeax.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.LinearLayout;
import is.ru.flowfreeax.R;
import is.ru.flowfreeax.services.Global;

public class PlayActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        Global global = Global.getInstance();
        global.setContext(this);

        //Switch between light and dark theme
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        LinearLayout play_layout = (LinearLayout)findViewById(R.id.play);
        boolean switchOn = preferences.getBoolean("theme_label", false);

        if(switchOn){
            play_layout.setBackgroundColor(Color.WHITE);
        }
    }
}