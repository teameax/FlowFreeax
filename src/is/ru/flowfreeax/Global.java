package is.ru.flowfreeax;

import java.util.List;

/**
 * Created by joddsson on 17.9.2014.
 */
public class Global {
    public List<Pack> mPacks;

    ///
    private static Global mInstance = new Global();

    public static Global getInstance() {
        return mInstance;
    }

    private Global() {}
}
