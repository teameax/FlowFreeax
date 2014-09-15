package is.ru.flowfreeax;

import android.graphics.Paint;
import android.util.Log;

/**
 * Created by DrepAri on 15.9.14.
 */
public class Route {

    private Bubble[] _bubbles  = new Bubble[2];
    private Cellpath _cellpath = new Cellpath();
    private Paint _paint       = new Paint();

    public Route() {
    }

    public Route(Bubble start, Bubble end) {
        _bubbles[0] = start;
        _bubbles[1] = end;
    }

    public void createStart(int col, int row) {
        _bubbles[0] = new Bubble(col, row, _paint);
    }

    public void createEnd(int col, int row) {
        _bubbles[1] = new Bubble(col, row, _paint);
    }

    public boolean isInRoute(int col, int row) {
        Coordinate coordinate = new Coordinate(col, row);
        if (_cellpath.getCoordinates().indexOf(coordinate) == _cellpath.getCoordinates().size() - 1) {
            return true;
        }
        else {
            for (int i = 0; i < _bubbles.length; i++) {
                if(_bubbles[i].getRow() == row && _bubbles[i].getCol() == col) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isFinished() {
        boolean firstFound = false, lastFound = false;

        if (_cellpath.getCoordinates().isEmpty()) {
            return false;
        }
        Coordinate first = _cellpath.getCoordinates().get(0);
        Coordinate last = _cellpath.getCoordinates().get(_cellpath.getCoordinates().size() - 1);

        if (first == last) {
            return false;
        }


        for (Bubble bubble :_bubbles) {
            if (bubble.getCol() == first.getCol()
                    && bubble.getRow() == first.getRow()) {
                firstFound = true;
            }
            else if (bubble.getCol() == last.getCol()
                    && bubble.getRow() == last.getRow()) {
                lastFound = true;
            }
        }
        return firstFound && lastFound;
    }


    //region Getters and Setters

    public Bubble[] getBubbles() {
        return _bubbles;
    }

    public Cellpath getCellpath() {
        return _cellpath;
    }

    public Bubble getStart() {
        return _bubbles[0];
    }

    public void setStart(Bubble start) {
        _bubbles[0] = start;
    }

    public Bubble getEnd() {
        return _bubbles[1];
    }

    public void setEnd(Bubble end) {
        _bubbles[0] = end;
    }

    public Paint getPaint() {
        return _paint;
    }
    //endregion
}
