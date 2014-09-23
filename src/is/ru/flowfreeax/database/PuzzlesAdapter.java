package is.ru.flowfreeax.database;

/**
 * Created by DrepAri on 20.9.14.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PuzzlesAdapter {

    SQLiteDatabase db;
    DbHelper dbHelper;
    Context context;

    public PuzzlesAdapter(Context c) {
        context = c;
    }

    public PuzzlesAdapter openToRead() {
        dbHelper = new DbHelper( context );
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public PuzzlesAdapter openToWrite() {
        dbHelper = new DbHelper( context );
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public long insertPuzzle( int pid, int size, String type, boolean finished ) {
        String[] cols = DbHelper.TablePuzzlesCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)pid).toString() );
        contentValues.put( cols[2], ((Integer)size).toString() );
        contentValues.put( cols[3], type );
        contentValues.put( cols[4], finished ? "1" : "0" );
        openToWrite();
        long value = db.insert(DbHelper.TablePuzzles, null, contentValues );
        close();
        return value;
    }

    public long updatePuzzle( int pid, int size, String type, boolean finished ) {
        String[] cols = DbHelper.TablePuzzlesCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)pid).toString() );
        contentValues.put( cols[2], ((Integer)size).toString() );
        contentValues.put( cols[3], type );
        contentValues.put( cols[4], finished ? "1" : "0" );
        openToWrite();
        long value = db.update(DbHelper.TablePuzzles, contentValues, cols[1] + "="+ pid, null );
        close();
        return value;
    }

    public Cursor queryPuzzles() {
        openToRead();
        Cursor cursor = db.query( DbHelper.TablePuzzles,
                DbHelper.TablePuzzlesCols, null, null, null, null, null);
        return cursor;
    }

    public Cursor queryPuzzles( int pid, String type) {
        openToRead();
        String[] cols = DbHelper.TablePuzzlesCols;
        Cursor cursor = db.query( DbHelper.TablePuzzles,
                cols, cols[1] + "=" + pid + " and " + cols[3] + "=?" , new String[] {type}, null, null, null);
        return cursor;
    }

    public long insertPuzzleIfNew( int pid, int size, String type, boolean finished) {
        Cursor cursor = queryPuzzles(pid, type);
        if (cursor.getCount() == 1) {
            cursor.close();
            return 0;
        }
        else {
            cursor.close();
            return insertPuzzle(pid, size, type, finished);
        }
    }

    public void dropDatabase() {
        openToWrite();
        dbHelper.onUpgrade(db, 0,0 );
        close();
    }

    public long resetPuzzles() {
        String[] puzzles = DbHelper.TablePuzzlesCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put(puzzles[4], 0);
        openToWrite();
        long value = db.update(DbHelper.TablePuzzles,
                contentValues, null, null );
        close();
        return value;
    }
}
