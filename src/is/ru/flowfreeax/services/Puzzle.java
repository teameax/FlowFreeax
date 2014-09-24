package is.ru.flowfreeax.services;

import java.util.List;

/**
 * POJO that represents a puzzle instance
 * Created by joddsson on 18.9.2014.
 */
public class Puzzle {
    private int mSize;
    private List<String> mFlows;
    private String mType;
    private int mPid;

    public Puzzle ( int size, List<String> flows, String type, int pid ){
        mSize  = size;
        mFlows = flows;
        mType  = type;
        mPid   = pid;
    }

    public List<String> getFlows() { return mFlows; }
    public int getSize() { return mSize; }
    public String getType() { return mType; }
    public int getPid() { return mPid; }

    @Override
    public String toString() {
        return "Puzzle{" +
                "mSize='" + mSize + '\'' +
                ", mFlows=" + mFlows +
                '}';
    }
}
