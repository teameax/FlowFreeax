package is.ru.flowfreeax.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if(columnIndex == 4){
                    ((ImageView)view).setImageResource(
                            ( cursor.getInt(columnIndex) == 0) ?
                                    R.drawable.good_job : R.drawable.bad_job);
                    return true;
                }
                return false;
            }
        });

        this.setListAdapter(simpleCursorAdapter);
    }
}