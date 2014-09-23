package is.ru.flowfreeax.services;

import android.content.Context;
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
    private int score = 0;
    int iterator = 0;

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
        currentPuzzle = puzzles.get(iterator);
        iterator++;

        if (iterator == puzzles.size()) {
            iterator = 0;
        }
        return currentPuzzle;
    }

    public void markAsFinished() {
        long value = puzzlesAdapter.updatePuzzle(currentPuzzle.getPid(), currentPuzzle.getSize(), currentPuzzle.getType(), true);
    }

    public void updateScore(int deltaScore) {
        score += deltaScore;
    }

}
