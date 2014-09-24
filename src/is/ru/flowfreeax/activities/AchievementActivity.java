package is.ru.flowfreeax.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import is.ru.flowfreeax.R;
import is.ru.flowfreeax.database.PuzzlesAdapter;

/**
 * Created by DrepAri on 23.9.14.
 */
public class AchievementActivity extends ListActivity {
    private PuzzlesAdapter puzzlesAdapter = new PuzzlesAdapter(this);
    private SimpleCursorAdapter simpleCursorAdapter;
    private Cursor cursor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PuzzlesAdapter puzzlesAdapter = new PuzzlesAdapter(this);

        cursor = puzzlesAdapter.queryPuzzles();
        startManagingCursor(cursor);

         simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.achievement, cursor,
                            new String[] {"pid", "type", "finished"}, new int[] {R.id.pid, R.id.type, R.id.finished}, 0);

        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if(columnIndex == 4){
                    ((ImageView)view).setImageResource(
                            ( cursor.getInt(columnIndex) == 0) ?
                                    R.drawable.bad_job : R.drawable.good_job);
                    return true;
                }
                return false;
            }
        });

        this.setListAdapter(simpleCursorAdapter);
    }
    @Override
    protected void onListItemClick( ListView l, View v, int position, long id ) {
        puzzlesAdapter.updateAchivements(true);
        cursor.requery();
    }
}