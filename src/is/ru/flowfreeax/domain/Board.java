package is.ru.flowfreeax.domain;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import is.ru.flowfreeax.services.Global;
import is.ru.flowfreeax.services.Puzzle;

import java.util.ArrayList;
import java.util.List;


public class Board extends View {

    private int num_cells;
    private int m_cellWidth;
    private int m_cellHeight;
    private Puzzle m_puzzle;

    private Global global               = Global.getInstance();
    private Rect m_rect                 = new Rect();
    private Paint m_paintGrid           = new Paint();
    private Paint m_paintPath           = new Paint();
    private Path m_path                 = new Path();


    private List<Route> m_routes = new ArrayList<Route>();
    private Route m_currentRoute;


    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_paintGrid.setColor(Color.GRAY);
        m_paintGrid.setStyle(Paint.Style.STROKE);

        m_paintPath.setStyle( Paint.Style.STROKE );
        m_paintPath.setStrokeWidth(32);
        m_paintPath.setStrokeCap( Paint.Cap.ROUND );
        m_paintPath.setStrokeJoin( Paint.Join.ROUND );
        m_paintPath.setAntiAlias( true );

        getNewPuzzle();

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
        m_cellWidth  = (xNew - getPaddingLeft() - getPaddingRight() - sw) / num_cells;
        m_cellHeight = (yNew - getPaddingTop() - getPaddingBottom() - sw) / num_cells;

    }

    @Override
    protected void onDraw( Canvas canvas ) {
        for ( int r=0; r<num_cells; ++r ) {
            for (int c = 0; c<num_cells; ++c) {
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

        if ( c >= num_cells || r >= num_cells ) {
            return true;
        }

        if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
            m_currentRoute = findRoute(c, r);
            if (m_currentRoute == null) {
                return true;
            }
            System.out.println(m_currentRoute);

            Coordinate coordinate = new Coordinate(c, r);


            if (m_currentRoute.getCellpath().getCoordinates().size() == 0) {
                m_currentRoute.getCellpath().append(coordinate);
            }
            else if (m_currentRoute.getEnd().getCoordinate().equals(coordinate)
                    || m_currentRoute.getStart().getCoordinate().equals(coordinate)) {
                m_currentRoute.getCellpath().reset();
                m_currentRoute.getCellpath().append(coordinate);
            }
            else {
                m_currentRoute.getCellpath().append(coordinate);
            }

        }
        else if ( event.getAction() == MotionEvent.ACTION_MOVE ) {
            if ( m_currentRoute != null && !m_currentRoute.getCellpath().isEmpty() ) {
                List<Coordinate> coordinateList = m_currentRoute.getCellpath().getCoordinates();
                Coordinate last = coordinateList.get(coordinateList.size()-1);
                if ( areNeighbours(last.getCol(),last.getRow(), c, r) && !isTaken(c, r)) {
                    m_currentRoute.getCellpath().append(new Coordinate(c, r));
                    invalidate();
                }

                if( m_currentRoute.isFinished()) {
                    Log.d("MOVE", "FINISHED");
                    global.updateScore(5);
                    m_currentRoute = null;
                }
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            isLevelFinished();
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

    private void getNewPuzzle() {

        ArrayList<Integer> paints = new ArrayList<Integer>();
        paints.add(Color.RED);
        paints.add(Color.BLUE);
        paints.add(Color.GREEN);
        paints.add(Color.YELLOW);
        paints.add(Color.CYAN);
        paints.add(Color.MAGENTA);
        paints.add(Color.WHITE);

        m_puzzle = global.getPuzzle();
        num_cells = m_puzzle.getSize();
        m_routes.clear();

        int i = 0;
        for (String flow : m_puzzle.getFlows()) {
            Route route = new Route();
            route.getPaint().setColor(paints.get(i));

            route.createStart((int) flow.charAt(0) - 48, (int) flow.charAt(2) - 48);
            route.createEnd((int) flow.charAt(4) - 48, (int) flow.charAt(6) - 48);
            m_routes.add(route);
            i++;
        }

    }

    private void isLevelFinished() {
        for (Route route : m_routes) {
            if (!route.isFinished()) {
                return;
            }
        }
        global.markAsFinished();
        getNewPuzzle();
        invalidate();
    }

    private boolean isTaken(int col, int row) {
        for (Route route : m_routes) {
            if (route != m_currentRoute && route.isInRoute(col, row)) {
                return true;
            }
        }
        return false;
    }
    //endregion

}
