package is.ru.flowfreeax;

import java.util.List;

/**
 * Created by joddsson on 18.9.2014.
 */
public class Puzzle {
    private int mSize;
    private List<String> mFlows;

    public Puzzle ( int size, List<String> flows ){
        mSize = size;
        mFlows= flows;
    }

    public List<String> getFlows() { return mFlows; }
    public int getSize() { return mSize; }

    @Override
    public String toString() {
        return "Puzzle{" +
                "mSize='" + mSize + '\'' +
                ", mFlows=" + mFlows +
                '}';
    }
}
