package is.ru.flowfreeax.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import is.ru.flowfreeax.R;
import is.ru.flowfreeax.database.PuzzlesAdapter;

/**
 * Created by DrepAri on 23.9.14.
 */
public class AchievementActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PuzzlesAdapter puzzlesAdapter = new PuzzlesAdapter(this);

        Cursor cursor = puzzlesAdapter.queryPuzzles();
        startManagingCursor(cursor);

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.achievement, cursor,
                            new String[] {"pid", "type", "finished"}, new int[] {R.id.pid, R.id.type, R.id.finished});

        this.setListAdapter(simpleCursorAdapter);
    }
}