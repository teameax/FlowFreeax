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
    private int iterator = 0;
    private PuzzlesAdapter puzzlesAdapter;
    private Puzzle currentPuzzle = null;

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
        puzzlesAdapter = new PuzzlesAdapter(context);
    }

    public List<Pack> getPacks() {
        return packs;
    }

    public void setPacks(List<Pack> packs) {
        this.packs = packs;
    }

    public Puzzle getPuzzle() {
        Cursor cursor;
        for (Puzzle puzzle : puzzles) {
            cursor = puzzlesAdapter.queryPuzzles(puzzle.getPid(), puzzle.getType());
            cursor.moveToNext();
            if (cursor.getInt(4) == 0) {
                currentPuzzle = puzzle;
                break;
            }
            cursor.close();

        }
        if (currentPuzzle == null ) {
            currentPuzzle = puzzles.get(0);
        }
        return currentPuzzle;
    }

    public void markAsFinished() {
        long value = puzzlesAdapter.updatePuzzle(currentPuzzle.getPid(), currentPuzzle.getSize(), currentPuzzle.getType(), true);
        System.out.println(value);
    }
}