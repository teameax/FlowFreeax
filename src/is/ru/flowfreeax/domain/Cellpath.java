package is.ru.flowfreeax.domain;

import java.util.ArrayList;
import java.util.List;

public class Cellpath {

    private ArrayList<Coordinate> m_path = new ArrayList<Coordinate>();

    public void append( Coordinate co ) {
        int idx = m_path.indexOf(  co );
        if ( idx >= 0 ) {
            for ( int i=m_path.size()-1; i > idx; --i ) {
                m_path.remove(i);
            }
        }
        else {
            m_path.add(co);
        }
    }

    public List<Coordinate> getCoordinates() {
        return m_path;
    }

    public void reset() {
        m_path.clear();
    }

    public boolean isEmpty() {
        return m_path.isEmpty();
    }

    public boolean contains( Coordinate coordinate ) {
        int index = m_path.indexOf(coordinate);

        if(index >= 0) {
            return true;
        }
        return false;
    }

    public void revertToNext(Coordinate coordinate) {
        int index = m_path.indexOf(coordinate);
        if (index > 0) {
            append(m_path.get(index - 1));
        }
    }

}
