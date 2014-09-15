package is.ru.flowfreeax;

import android.graphics.Paint;

/**
 * Created by DrepAri on 12.9.14.
 */
public class Bubble {

    private int x;
    private int y;
    private Paint paint;

    public Bubble(int x, int y, Paint paint) {
        this.x = x;
        this.y = y;
        this.paint = paint;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
