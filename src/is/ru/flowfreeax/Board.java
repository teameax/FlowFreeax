package is.ru.flowfreeax;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.provider.SyncStateContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class Board extends View {

    private final int NUM_CELLS = 5;
    private int m_cellWidth;
    private int m_cellHeight;

    private Rect m_rect                 = new Rect();
    private Paint m_paintGrid           = new Paint();
    private Paint m_paintPath           = new Paint();
    private Path m_path                 = new Path();
    private ShapeDrawable m_shape = new ShapeDrawable( new OvalShape() );

    private Cellpath m_cellPath = new Cellpath();
    private List<Bubble> m_bubbles = new ArrayList<Bubble>();

    private int xToCol( int x ) {
        return (x - getPaddingLeft()) / m_cellWidth;
    }

    private int yToRow( int y ) {
        return (y - getPaddingTop()) / m_cellHeight;
    }

    private int colToX( int col ) {
        return col * m_cellWidth + getPaddingLeft() ;
    }

    private int rowToY( int row ) {
        return row * m_cellHeight + getPaddingTop() ;
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_paintGrid.setStyle( Paint.Style.STROKE );
        m_paintGrid.setColor( Color.GRAY );

        m_paintPath.setStyle( Paint.Style.STROKE );
        m_paintPath.setColor(Color.GREEN);
        m_paintPath.setStrokeWidth(32);
        m_paintPath.setStrokeCap( Paint.Cap.ROUND );
        m_paintPath.setStrokeJoin( Paint.Join.ROUND );
        m_paintPath.setAntiAlias( true );

        List<Coordinate> coordinateList = m_cellPath.getCoordinates();
        Log.d("Board", "PENIS " + String.valueOf(coordinateList));
    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );
        int width  = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int size = Math.min(width, height);
        setMeasuredDimension( size + getPaddingLeft() + getPaddingRight(),
                size + getPaddingTop() + getPaddingBottom() );
    }

    @Override
    protected void onSizeChanged( int xNew, int yNew, int xOld, int yOld ) {
        int sw = Math.max(1, (int) m_paintGrid.getStrokeWidth());
        m_cellWidth  = (xNew - getPaddingLeft() - getPaddingRight() - sw) / NUM_CELLS;
        m_cellHeight = (yNew - getPaddingTop() - getPaddingBottom() - sw) / NUM_CELLS;


        Paint red = new Paint();
        red.setColor(Color.RED);

        m_bubbles.add(new Bubble(m_cellWidth/2, m_cellHeight/2, red));
        m_bubbles.add(new Bubble(5*(m_cellWidth/2), 7*(m_cellHeight/2), red));
    }

    @Override
    protected void onDraw( Canvas canvas ) {
        for ( int r=0; r<NUM_CELLS; ++r ) {
            for (int c = 0; c<NUM_CELLS; ++c) {
                int x = colToX( c );
                int y = rowToY( r );
                m_rect.set(x, y, x + m_cellWidth, y + m_cellHeight);
                canvas.drawRect(m_rect, m_paintGrid);
            }
        }

        for(Bubble bubble : m_bubbles) {
            // TODO: This is hardcoded for milestone 2, fix that for milestone 3.
            canvas.drawCircle(bubble.getX(), bubble.getY(), 50, bubble.getPaint());
        }

        m_path.reset();
        if ( !m_cellPath.isEmpty() ) {
            List<Coordinate> colist = m_cellPath.getCoordinates();
            Coordinate co = colist.get( 0 );
            m_path.moveTo( colToX(co.getCol()) + m_cellWidth / 2,
                           rowToY(co.getRow()) + m_cellHeight / 2 );
            for ( int i=1; i<colist.size(); ++i ) {
                co = colist.get(i);
                m_path.lineTo( colToX(co.getCol()) + m_cellWidth / 2,
                                rowToY(co.getRow()) + m_cellHeight / 2 );
            }
        }

        canvas.drawPath( m_path, m_paintPath);
    }

    private boolean areNeighbours( int c1, int r1, int c2, int r2 ) {
        return Math.abs(c1-c2) + Math.abs(r1-r2) == 1;
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {

        int x = (int) event.getX();         // NOTE: event.getHistorical... might be needed.
        int y = (int) event.getY();
        int c = xToCol( x );
        int r = yToRow( y );

        if ( c >= NUM_CELLS || r >= NUM_CELLS ) {
            return true;
        }

        if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
            m_cellPath.reset();
            m_cellPath.append( new Coordinate(c,r) );
        }
        else if ( event.getAction() == MotionEvent.ACTION_MOVE ) {
            if ( !m_cellPath.isEmpty() ) {
                List<Coordinate> coordinateList = m_cellPath.getCoordinates();
                Coordinate last = coordinateList.get(coordinateList.size()-1);
                if ( areNeighbours(last.getCol(),last.getRow(), c, r)) {
                    m_cellPath.append(new Coordinate(c, r));
                    invalidate();
                }
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            if ( !m_cellPath.isEmpty() ) {
                List<Coordinate> coordinateList = m_cellPath.getCoordinates();
                Coordinate last = coordinateList.get(coordinateList.size()-1);
                Coordinate first = coordinateList.get(0);
                if ( !finishedRoute(first, last) ) {
                    m_cellPath.reset();
                    invalidate();
                }
            }
        }
        return true;
    }

    private boolean finishedRoute(Coordinate first, Coordinate last) {
        boolean firstFound = false, lastFound = false;

        for (Bubble bubble : m_bubbles) {
            if (xToCol(bubble.getX()) == first.getCol()
                    && yToRow(bubble.getX()) == first.getRow()) {
                firstFound = true;
            }
            else if (xToCol(bubble.getX()) == last.getCol()
                    && yToRow(bubble.getY()) == last.getRow()) {
                lastFound = true;
            }
        }
        return firstFound && lastFound;
    }

    public void setColor( int color ) {
        m_paintPath.setColor( color );
        invalidate();
    }
}
