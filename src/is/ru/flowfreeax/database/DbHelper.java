package is.ru.flowfreeax.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "FLOWFREE_DB";
    public static final int DB_VERSION = 1;

    public static final String TablePuzzles = "puzzles";
    public static final String[] TablePuzzlesCols = { "_id", "pid", "size", "type", "finished" };

    private static final String sqlCreateTablePuzzles =
            "CREATE TABLE puzzles(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " pid INTEGER NOT NULL," +
                    " size INTEGER NOT NULL," +
                    " type TEXT," +
                    " finished INTEGER NOT NULL" +
                    ");";

    private static final String sqlDropTablePuzzles =
            "DROP TABLE IF EXISTS puzzles;";

    public DbHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( sqlCreateTablePuzzles );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( sqlDropTablePuzzles );
        onCreate( db );
    }
}