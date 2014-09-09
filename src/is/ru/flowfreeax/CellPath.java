package is.ru.flowfreeax;
import java.util.ArrayList;
import java.util.List;

public class CellPath {
   private ArrayList<Coordiante> m_path = new ArrayList<Coordiante>();

    public void append(Coordiante co){
        m_path.add(co);
    }

    public List<Coordiante> getCoordinates(){
        return m_path;
    }

    public void reset(){
        m_path.clear();
    }

    public boolean isEmpty(){
        return m_path.isEmpty();
    }
}
