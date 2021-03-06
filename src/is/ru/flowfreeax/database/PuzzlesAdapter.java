package is.ru.flowfreeax.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * An adapter that uses DbHelper to implement game essential functions.
 * Created by DrepAri on 20.9.14.
 */
public class PuzzlesAdapter {

    SQLiteDatabase  db;
    DbHelper        dbHelper;
    Context         context;

    /**
     * Set context.
     * @param c Current context.
     */
    public PuzzlesAdapter(Context c) {
        context = c;
    }

    /**
     * Enable database read.
     * @return Current context.
     */
    public PuzzlesAdapter openToRead() {
        dbHelper = new DbHelper( context );
        db = dbHelper.getReadableDatabase();
        return this;
    }

    /**
     * Enable database write.
     * @return Current context.
     */
    public PuzzlesAdapter openToWrite() {
        dbHelper = new DbHelper( context );
        db = dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * Close database connection.
     */
    public void close() {
        db.close();
    }

    /**
     * Insert a puzzle in the database.
     * @param pid Puzzle pid.
     * @param size Puzzle size, e.g 5x5.
     * @param type Puzzle type, e.g Regular or Mania.
     * @param finished Has a given level been completed?
     * @param bestTime High score for mania mode.
     * @return The database values.
     */
    public long insertPuzzle( int pid, int size, String type, boolean finished, long bestTime ) {
        String[] cols = DbHelper.TablePuzzlesCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)pid).toString() );
        contentValues.put( cols[2], ((Integer)size).toString() );
        contentValues.put( cols[3], type );
        contentValues.put( cols[4], finished ? "1" : "0" );
        contentValues.put( cols[5], (Long.toString(bestTime)) );
        openToWrite();
        long value = db.insert(DbHelper.TablePuzzles, null, contentValues);
        close();
        return value;
    }

    /**
     * Insert a puzzle in the database.
     * @param pid Puzzle pid.
     * @param size Puzzle size, e.g 5x5.
     * @param type Puzzle type, e.g Regular or Mania.
     * @param finished Has a given level been completed?
     * @param bestTime High score for mania mode.
     * @return The database values.
     */
    public long updatePuzzle( int pid, int size, String type, boolean finished, long bestTime ) {
        String[] cols = DbHelper.TablePuzzlesCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)pid).toString() );
        contentValues.put( cols[2], ((Integer)size).toString() );
        contentValues.put( cols[3], type );
        contentValues.put( cols[4], finished ? "1" : "0" );
        contentValues.put( cols[5], (Long.toString(bestTime)) );
        openToWrite();
        long value = db.update(DbHelper.TablePuzzles, contentValues, cols[1] + "=" + pid + " and " + cols[3] + "=?", new String[] {type} );
        close();
        return value;
    }

    /**
     * Search all puzzles.
     * @return The result.
     */
    public Cursor queryPuzzles() {
        openToRead();
        Cursor cursor = db.query( DbHelper.TablePuzzles,
                DbHelper.TablePuzzlesCols, null, null, null, null, null);
        return cursor;
    }

    /**
     * Search specific puzzle.
     * @param pid Puzzle pid.
     * @param type Puzzle type, e.g Regular or Mania.
     * @return The result.
     */
    public Cursor queryPuzzles( int pid, String type) {
        openToRead();
        String[] cols = DbHelper.TablePuzzlesCols;
        Cursor cursor = db.query( DbHelper.TablePuzzles,
                cols, cols[1] + "=" + pid + " and " + cols[3] + "=?" , new String[] {type}, null, null, null);
        return cursor;
    }

    public long insertPuzzleIfNew( int pid, int size, String type, boolean finished, long bestTime) {
        Cursor cursor = queryPuzzles(pid, type);
        if (cursor.getCount() == 1) {
            cursor.close();
            return 0;
        }
        else {
            cursor.close();
            return insertPuzzle(pid, size, type, finished, bestTime);
        }
    }

    /**
     * Drop the database.
     */
    public void dropDatabase() {
        openToWrite();
        dbHelper.onUpgrade(db, 0,0 );
        close();
    }
}
