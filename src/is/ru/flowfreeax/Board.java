package is.ru.flowfreeax;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class Board extends View {

    private final int NUM_CELLS = 5;
    private int m_cellWidth;
    private int m_cellHeight;

    private Rect m_rect                 = new Rect();
    private Paint m_paintGrid           = new Paint();
    private Paint m_paintPath           = new Paint();
    private Path m_path                 = new Path();

    private List<Route> m_routes = new ArrayList<Route>();
    private Route m_currentRoute;


    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_paintGrid.setStyle( Paint.Style.STROKE );
        m_paintGrid.setColor( Color.GRAY );

        m_paintPath.setStyle( Paint.Style.STROKE );
        m_paintPath.setStrokeWidth(32);
        m_paintPath.setStrokeCap( Paint.Cap.ROUND );
        m_paintPath.setStrokeJoin( Paint.Join.ROUND );
        m_paintPath.setAntiAlias( true );

        //TODO: Hardoded route fix for milestone 3
        m_routes.add(new Route());
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


        //TODO: Hardcoded change for milestone 3
        Route route = m_routes.get(0);
        route.getPaint().setColor(Color.RED);

        route.createStart(0, 0);
        route.createEnd(2, 3);

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

        for (Route route : m_routes) {
            for(Bubble bubble : route.getBubbles()) {
                canvas.drawCircle(colToX(bubble.getCol()) + m_cellWidth/2, rowToY(bubble.getRow()) + m_cellHeight/2, 50, bubble.getPaint());
            }

            Cellpath cellpath = route.getCellpath();
            m_path.reset();
            if (!cellpath.isEmpty()) {
                List<Coordinate> coordinateList = cellpath.getCoordinates();
                Coordinate co = coordinateList.get(0);
                m_path.moveTo(colToX(co.getCol()) + m_cellWidth / 2,
                        rowToY(co.getRow()) + m_cellHeight / 2);
                for (int i = 1; i < coordinateList.size(); ++i) {
                    co = coordinateList.get(i);
                    m_path.lineTo(colToX(co.getCol()) + m_cellWidth / 2,
                            rowToY(co.getRow()) + m_cellHeight / 2);
                }
            }
            m_paintPath.setColor(route.getPaint().getColor());
            canvas.drawPath( m_path, m_paintPath);
        }

    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        int c = xToCol( x );
        int r = yToRow( y );

        if ( c >= NUM_CELLS || r >= NUM_CELLS ) {
            return true;
        }



        if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
            m_currentRoute = findRoute(c, r);
            if (m_currentRoute == null) {
                return true;
            }

            Coordinate coordinate = new Coordinate(c, r);

            if (m_currentRoute.getCellpath().getCoordinates().size() == 0) {
                if (m_currentRoute.getEnd().getCoordinate().equals(coordinate)
                        || m_currentRoute.getStart().getCoordinate().equals(coordinate)) {
                    m_currentRoute.getCellpath().append(coordinate);
                }
            }
            else {
                m_currentRoute.getCellpath().append(coordinate);
            }

        }
        else if ( event.getAction() == MotionEvent.ACTION_MOVE ) {
            if ( m_currentRoute != null && !m_currentRoute.getCellpath().isEmpty() ) {
                List<Coordinate> coordinateList = m_currentRoute.getCellpath().getCoordinates();
                Coordinate last = coordinateList.get(coordinateList.size()-1);
                if ( areNeighbours(last.getCol(),last.getRow(), c, r)) {
                    m_currentRoute.getCellpath().append(new Coordinate(c, r));
                    invalidate();
                }

                if( m_currentRoute.isFinished()) {
                    Log.d("MOVE", "FINISHED");
                    m_currentRoute = null;
                }
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            if ( m_currentRoute != null && m_currentRoute.isFinished()) {
                Log.d("UP", "FINISHED");
                m_currentRoute = null;
            }
        }
        return true;
    }

    //region Private Helpers

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



    private Route findRoute(int col, int row) {
        for (Route route : m_routes) {
            if (route.isInRoute(col, row)) {
                return route;
            }
        }

        return null;
    }



    private boolean areNeighbours( int c1, int r1, int c2, int r2 ) {
        return Math.abs(c1-c2) + Math.abs(r1-r2) == 1;
    }

    //endregion

}
