package is.ru.flowfreeax.services;

import android.content.Context;
import android.database.Cursor;
import is.ru.flowfreeax.database.PuzzlesAdapter;

import java.util.List;

/**
 * Created by joddsson on 17.9.2014.
 */
public class Global {
    private List<Puzzle> puzzles;
    private List<Pack> packs;
    private Context context;
    private PuzzlesAdapter puzzlesAdapter;
    private Puzzle currentPuzzle = null;
    public int iterator = 0;

    private long startTime;

    private static Global mInstance = new Global();

    public static Global getInstance() {
        return mInstance;
    }

    private Global() {}

    public List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public void setPuzzles(List<Puzzle> puzzles) {
        this.puzzles = puzzles;
    }

    public void setContext(Context c) {
        this.context = c;
        this.iterator = 0;
        this.puzzlesAdapter = new PuzzlesAdapter(context);
    }

    public List<Pack> getPacks() {
        return packs;
    }

    public void setPacks(List<Pack> packs) {
        this.packs = packs;
    }

    public Puzzle getPuzzle() {
        currentPuzzle = puzzles.get(iterator);
        iterator++;

        if (iterator == puzzles.size()) {
            iterator = 0;
        }
        startTime = System.nanoTime();
        return currentPuzzle;
    }

    public void markAsFinished() {
        long elapsedTime = System.nanoTime() - startTime;
        Cursor cursor = puzzlesAdapter.queryPuzzles(currentPuzzle.getPid(), currentPuzzle.getType());
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            long currentBestTime = cursor.getLong(5);

            if (currentBestTime > elapsedTime || currentBestTime == -1) {
                puzzlesAdapter.updatePuzzle(currentPuzzle.getPid(), currentPuzzle.getSize(), currentPuzzle.getType(), true, elapsedTime);
            }
            else {
                puzzlesAdapter.updatePuzzle(currentPuzzle.getPid(), currentPuzzle.getSize(), currentPuzzle.getType(), true, currentBestTime);
            }
        }
    }

    public void updateAchievements(boolean levelFinished) {
        puzzlesAdapter.updateAchivements(levelFinished);
    }
}
