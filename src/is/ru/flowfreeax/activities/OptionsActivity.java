package is.ru.flowfreeax.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import is.ru.flowfreeax.R;

/**
 * Created by joddsson on 15.9.2014.
 */
public class OptionsActivity extends PreferenceActivity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource( R.xml.options );
    }
}
