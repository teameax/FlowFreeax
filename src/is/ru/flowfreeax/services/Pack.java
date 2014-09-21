package is.ru.flowfreeax.services;

/**
 * Created by joddsson on 17.9.2014.
 */
public class Pack {
    private String mName;
    private String mDescription;
    private String mFile;

    Pack( String name, String description, String file )  {
        mName           = name;
        mDescription    = description;
        mFile           = file;
    }

    String getName() { return mName; }
    String getDescription() { return mDescription; }
    public String getFile() { return mFile; }
}
