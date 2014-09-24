package is.ru.flowfreeax.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import is.ru.flowfreeax.R;
import is.ru.flowfreeax.services.Global;
import is.ru.flowfreeax.services.Pack;
import is.ru.flowfreeax.services.XmlReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    XmlReader reader = new XmlReader(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Global global = Global.getInstance();
        global.setContext(this);

        try {
            List<Pack> packs = new ArrayList<Pack>();
            reader.readPack(getAssets().open("packs/packs.xml"), packs);
            reader.mGlobals.setPacks(packs);
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void buttonClick(final View view){
        Button button       = (Button) view;
        int id              = button.getId();
        String openRegular  = reader.mGlobals.getPacks().get(0).getFile();
        String openMania    = reader.mGlobals.getPacks().get(1).getFile();

        try {
            if (id == R.id.button_play) {
                playSound("button3.mp3");
                reader.openRegular(getAssets().open(openRegular));
                startActivity(new Intent(this, PlayActivity.class));
            }
            else if(id == R.id.button_timeTrial){
                playSound("button3.mp3");
                reader.openMania(getAssets().open(openMania));
                startActivity(new Intent(this, ManiaActivity.class));

            }
            else if (id == R.id.button_options) {
                playSound("button3.mp3");
                startActivity(new Intent(this, OptionsActivity.class));
            }
            else if (id == R.id.button_achievements) {
                playSound("button3.mp3");
                startActivity(new Intent(this, AchievementActivity.class));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Play sound when buttons in main layout if the the audio switch is on in the options.
     */
    public void playSound(String sound) {
        final MediaPlayer mp = new MediaPlayer();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Boolean switchOn = preferences.getBoolean("board_labels", false);

        if(switchOn){
            if(mp.isPlaying()){
                mp.stop();
                mp.reset();
            }
            try {
                mp.reset();
                AssetFileDescriptor afd;
                afd = getAssets().openFd(sound);
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mp.prepare();
                mp.start();
            }
            catch (IllegalStateException e){
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}