package is.ru.flowfreeax.domain;

import android.graphics.Paint;

/**
 * Created by DrepAri on 12.9.14.
 */
public class Bubble {

    private Coordinate coordinate;
    private Paint paint;

    public Bubble(int col, int row, Paint paint) {
        this.coordinate = new Coordinate(col, row);
        this.paint = paint;
    }

    public int getCol() {
        return coordinate.getCol();
    }

    public int getRow() {
        return coordinate.getRow();
    }

    public Paint getPaint() {
        return paint;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public String toString() {
        return "Bubble{" +
                "coordinate=" + coordinate +
                ", paint=" + paint +
                '}';
    }
}
