package is.ru.flowfreeax.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import is.ru.flowfreeax.R;
import is.ru.flowfreeax.services.Global;
import is.ru.flowfreeax.services.Pack;
import is.ru.flowfreeax.services.Puzzle;
import is.ru.flowfreeax.services.XmlReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity created when the app is run.
 */
public class MainActivity extends Activity {
    XmlReader reader = new XmlReader(this);
    List<Puzzle> regularPuzzles = null;
    List<Puzzle> maniaPuzzles = null;
    Global global = Global.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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

    /**
     * Handle user touch input.
     * @param view The view the input is received.
     */
    public void buttonClick(final View view){
        Button button       = (Button) view;
        int id              = button.getId();
        String openRegular  = reader.mGlobals.getPacks().get(0).getFile();
        String openMania    = reader.mGlobals.getPacks().get(1).getFile();

        try {
            if (id == R.id.button_play) {
                playSound();
                if (regularPuzzles == null) {
                    regularPuzzles = reader.openRegular(getAssets().open(openRegular));
                    global.setPuzzles(regularPuzzles);
                }
                else if (global.getPuzzlesType() != "regular") {
                    global.setPuzzles(regularPuzzles);
                }
                startActivity(new Intent(this, PlayActivity.class));
            }
            else if(id == R.id.button_timeTrial){
                playSound();
                if (maniaPuzzles == null) {
                    maniaPuzzles = reader.openMania(getAssets().open(openMania));
                    global.setPuzzles(maniaPuzzles);
                }
                else if (global.getPuzzlesType() != "mania") {
                    global.setPuzzles(maniaPuzzles);
                }
                startActivity(new Intent(this, ManiaActivity.class));

            }
            else if (id == R.id.button_options) {
                playSound();
                startActivity(new Intent(this, OptionsActivity.class));
            }
            else if (id == R.id.button_achievements) {
                playSound();
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
    public void playSound() {
        final MediaPlayer mp = new MediaPlayer();

        //Check if the audio switch is on in the options activity and play the sound if so.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Boolean audioOn = preferences.getBoolean("board_labels", false);

        if(audioOn){
            if(mp.isPlaying()){
                mp.stop();
                mp.reset();
            }
            try {
                mp.reset();
                AssetFileDescriptor afd;
                afd = getAssets().openFd("button3.mp3");
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