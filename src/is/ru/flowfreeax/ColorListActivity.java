package is.ru.flowfreeax;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ColorListActivity extends ListActivity {

    public class ColorElement {
        private int color;
        private String something_else;

        ColorElement( int col, String sth ) {
            color = col;
            something_else = sth;
        }
        int getColor() { return color; }
        String getSomethingElse() { return something_else; }

        public String toString() {
            return "" + colorStr(color) + " : " + something_else;
        }

        private String colorStr( int color ) {
            String str = "?";
            switch ( color ) {
                case Color.GREEN:
                    str = "Green";
                    break;
                case Color.RED:
                    str = "Red";
                    break;
            }
            return str;
        }
    }

    private List<ColorElement> mList = new ArrayList<ColorElement>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList.add( new ColorElement(Color.GREEN, "Frog") );
        mList.add( new ColorElement(Color.RED, "Strawberry") );

        ArrayAdapter<ColorElement> adapter = new ArrayAdapter<ColorElement>(
            this, android.R.layout.simple_list_item_1, mList );

        setListAdapter( adapter );
    }

    @Override
    protected void onListItemClick( ListView l, View v, int position, long id ) {
        ColorElement colorElement = (ColorElement) l.getAdapter().getItem( position );

        SharedPreferences settings = getSharedPreferences( "ColorPref", MODE_PRIVATE );
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt( "pathColor", colorElement.getColor() );
        editor.commit();
    }
}