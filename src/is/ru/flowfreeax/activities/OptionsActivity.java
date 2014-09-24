package is.ru.flowfreeax.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import is.ru.flowfreeax.R;
import is.ru.flowfreeax.database.PuzzlesAdapter;
import is.ru.flowfreeax.services.Global;

public class OptionsActivity extends PreferenceActivity{
    final int DIALOG_CONFIRM = 20;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource( R.xml.options );

        //Reset the game in the options.
        Preference reset = (Preference)findPreference("reset_label");
        reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){
            @Override
            public boolean onPreferenceClick(Preference preference) {
                displayDialog(DIALOG_CONFIRM);
                return true;
            }
        });
    }
    public void resetGame(){
        PuzzlesAdapter puzzlesAdapter = new PuzzlesAdapter(this);
        puzzlesAdapter.dropDatabase();
    }

    private void displayDialog( int id ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch ( id ) {
            case DIALOG_CONFIRM:
                builder.setMessage("Are you sure?");
                builder.setCancelable(true);
                builder.setPositiveButton( "yes", new resetClass() );
                builder.setNegativeButton( "no", null );
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private final class resetClass implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            resetGame();
        }
    }
}
