package is.ru.flowfreeax.activities;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import is.ru.flowfreeax.R;
import is.ru.flowfreeax.database.PuzzlesAdapter;
import is.ru.flowfreeax.services.Global;

public class OptionsActivity extends PreferenceActivity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource( R.xml.options );

        //Reset the game in the options.
        Preference reset = (Preference)findPreference("reset_label");
        reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
            @Override
            public boolean onPreferenceClick(Preference preference) {
                resetGame();
                return true;
            }
        });
    }
    public void resetGame(){
        PuzzlesAdapter puzzlesAdapter = new PuzzlesAdapter(this);
        puzzlesAdapter.dropDatabase();
    }
}
