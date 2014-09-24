package is.ru.flowfreeax.activities;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import is.ru.flowfreeax.R;
import is.ru.flowfreeax.database.PuzzlesAdapter;

/**
 * Activity to display game progress.
 * Created by DrepAri on 23.9.14.
 */
public class AchievementActivity extends ListActivity {
    private static final String SCORE_NAME = "ScoreFile";
    private SimpleCursorAdapter simpleCursorAdapter;
    private Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PuzzlesAdapter puzzlesAdapter = new PuzzlesAdapter(this);

        cursor = puzzlesAdapter.queryPuzzles();
        startManagingCursor(cursor);

        // Specify list items in the achievements activity.
        simpleCursorAdapter =   new SimpleCursorAdapter(this, R.layout.achievement, cursor,
                                new String[] {"pid", "type", "size", "finished", "bestTime"},
                                new int[] {R.id.pid, R.id.type, R.id.size, R.id.finished, R.id.best}, 0);

        // Set custom values.
        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if(columnIndex == 2) {
                    ((TextView) view).setText(cursor.getInt(columnIndex) + " x " + cursor.getInt(columnIndex));
                    return true;
                }
                else if(columnIndex == 4) {
                    ((ImageView)view).setImageResource(
                            ( cursor.getInt(columnIndex) == 0) ?
                                    R.drawable.bad_job : R.drawable.good_job);
                    return true;
                }
                else if(columnIndex == 5) {
                    ((TextView) view).setText(
                            ( cursor.getLong(columnIndex) == -1 ) ?
                                    "Not finished" : Math.round(cursor.getLong(columnIndex)/1000000000.0) + " sek");
                    return true;
                }
                return false;
            }
        });
        this.setListAdapter(simpleCursorAdapter);

        // Get the high score for the mania game from database and display it.
        SharedPreferences scorePrefs = getSharedPreferences(SCORE_NAME, 0);
        int maniabest = scorePrefs.getInt("score", 0);

        getListView().setFooterDividersEnabled(true);
        LinearLayout footerView = (LinearLayout) getLayoutInflater().inflate(R.layout.score, null);
        TextView score = (TextView) footerView.findViewById(R.id.mania_best);
        score.setText("Time Trial high score: " + maniabest);
        getListView().addFooterView(footerView);
    }

}