package is.ru.flowfreeax.domain;

import android.graphics.Paint;
import java.util.Arrays;

/**
 * Created by DrepAri on 15.9.14.
 */
public class Route {
    private Bubble[] _bubbles  = new Bubble[2];
    private Cellpath _cellpath = new Cellpath();
    private Paint _paint       = new Paint();

    /**
     * Creates an starting point for the route
     * @param col Column
     * @param row Row
     */
    public void createStart(int col, int row) {
        _bubbles[0] = new Bubble(col, row, _paint);
    }

    /**
     * Creates an end point for the route
     * @param col Column
     * @param row Row
     */
    public void createEnd(int col, int row) {
        _bubbles[1] = new Bubble(col, row, _paint);
    }

    /**
     * Checks if given coordinate is in route
     * @param col Column
     * @param row Row
     * @return True if in route, false otherwise
     */
    public boolean isInRoute(int col, int row) {
        Coordinate coordinate = new Coordinate(col, row);
        if (_cellpath.contains(coordinate)) {
            return true;
        }
        else {
            for (int i = 0; i < _bubbles.length; i++) {
                if(_bubbles[i].getCoordinate().equals(coordinate)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Checks if route is finished
     * @return True if finished, false otherwise
     */
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

    /**
     * Reverts the route back to coordinates previous to given coordinate
     * @param col Column
     * @param row Row
     */
    public void revert(int col, int row) {
        _cellpath.revertToNext(new Coordinate(col, row));
    }

    /**
     * Checks if coordinate is on either of the end points
     * @param col Column
     * @param row Row
     * @return True if on bubble, false otherwise
     */
    public boolean isOnBubbles(int col, int row) {
        for (Bubble bubble : _bubbles) {
            if (bubble.getCol() == col
                    && bubble.getRow() == row) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Route{" +
                "_bubbles=" + Arrays.toString(_bubbles) +
                ", _cellpath=" + _cellpath +
                ", _paint=" + _paint.getColor() +
                '}';
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
