package is.ru.flowfreeax.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import is.ru.flowfreeax.R;
import is.ru.flowfreeax.database.PuzzlesAdapter;

public class PlayActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

    }
}