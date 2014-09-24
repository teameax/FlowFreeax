package is.ru.flowfreeax.services;

import android.content.Context;
import android.database.Cursor;
import is.ru.flowfreeax.database.PuzzlesAdapter;

import java.util.List;

/**
 * Global singleton object that is used to keep track of the state of the game
 * Created by joddsson on 17.9.2014.
 */
public class Global {

    //region Member Variables
    private List<Puzzle> puzzles;
    private List<Pack> packs;
    private Context context;
    private PuzzlesAdapter puzzlesAdapter;
    private Puzzle currentPuzzle = null;
    public int iterator = 0;

    private long startTime;

    private static Global mInstance = new Global();

    //endregion

    //region Getters and Setters

    public static Global getInstance() {
        return mInstance;
    }

    private Global() {}

    public void setPuzzles(List<Puzzle> puzzles) {
        this.puzzles = puzzles;
        this.iterator = 0;
    }

    public void setContext(Context c) {
        this.context = c;
        this.puzzlesAdapter = new PuzzlesAdapter(context);
    }

    public List<Pack> getPacks() {
        return packs;
    }

    public void setPacks(List<Pack> packs) {
        this.packs = packs;
    }
    //endregion

    /**
     * Finds the next puzzle in the list and starts timing
     * @return Next puzzle in list
     */
    public Puzzle getPuzzle() {
        if (iterator >= puzzles.size()) {
            iterator = 0;
        }
        currentPuzzle = puzzles.get(iterator);
        startTime = System.nanoTime();
        return currentPuzzle;
    }

    public String getPuzzlesType() {
        return puzzles.get(0).getType();
    }

    /**
     * Marks the current puzzle as finished in the database
     */
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
        iterator++;
    }
}
