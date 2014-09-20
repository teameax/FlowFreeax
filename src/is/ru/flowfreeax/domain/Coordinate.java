package is.ru.flowfreeax.domain;

public class Coordinate {

    private int m_col;
    private int m_row;

    Coordinate( int col, int row ) {
        m_col = col;
        m_row = row;
    }

    public int getCol() {
        return m_col;
    }

    public int getRow() {
        return m_row;
    }

    public void setCol(int col) {
        this.m_col = col;
    }

    public void setRow(int row) {
        this.m_row = row;
    }

    @Override
    public boolean equals( Object other ) {
        if ( !(other instanceof Coordinate) ) {
            return false;
        }
        Coordinate otherCo = (Coordinate) other;
        return otherCo.getCol() == this.getCol()&& otherCo.getRow() == this.getRow();
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "m_col=" + m_col +
                ", m_row=" + m_row +
                '}';
    }
}
