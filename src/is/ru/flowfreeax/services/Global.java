package is.ru.flowfreeax.services;

import java.util.List;

/**
 * Created by joddsson on 17.9.2014.
 */
public class Global {
    private List<Puzzle> puzzles;
    private List<Pack> packs;

    private int iterator = 0;

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

    public List<Pack> getPacks() {
        return packs;
    }

    public void setPacks(List<Pack> packs) {
        this.packs = packs;
    }

    public Puzzle getPuzzle() {
        Puzzle result = puzzles.get(iterator);
        iterator++;
        return result;
    }
}
