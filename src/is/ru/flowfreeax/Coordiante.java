package is.ru.flowfreeax;

public class Coordiante {
    private int m_col;
    private int m_row;

    Coordiante(int col, int row){
        m_col = col;
        m_row = row;
    }

    public int getCol(){
        return m_col;
    }

    public int getRow(){
        return m_row;
    }
}
