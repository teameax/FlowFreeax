package is.ru.flowfreeax;

/**
 * Created by joddsson on 18.9.2014.
 */
public class RegularChallenge {
    private String mSize;
    private String mFlows;

    RegularChallenge( String size, String flows ){
        mSize = size;
        mFlows= flows;
    }

    public String getFlows() { return mFlows; }
    public String getSize() { return mSize; }
}
