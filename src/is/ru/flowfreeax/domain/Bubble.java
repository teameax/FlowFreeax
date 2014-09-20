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

    public void setCol(int col) {
        this.coordinate.setCol(col);
    }

    public int getRow() {
        return coordinate.getRow();
    }

    public void setRow(int row) {
        this.coordinate.setRow(row);
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
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
