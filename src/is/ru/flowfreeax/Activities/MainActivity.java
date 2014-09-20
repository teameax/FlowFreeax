package is.ru.flowfreeax.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import is.ru.flowfreeax.services.Pack;
import is.ru.flowfreeax.R;
import is.ru.flowfreeax.services.XmlReader;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

//Called when the activity is first created.
public class MainActivity extends Activity {
    XmlReader reader = new XmlReader();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
            List<Pack> packs = new ArrayList<Pack>();
            reader.readPack(getAssets().open("packs/packs.xml"), packs);
            reader.mGlobals.setPacks(packs);
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void buttonClick(View view){
        Button button       = (Button) view;
        int id              = button.getId();
        String openRegular  = reader.mGlobals.getPacks().get(0).getFile();
        String openMania    = reader.mGlobals.getPacks().get(1).getFile();

        try {
            if (id == R.id.button_play) {
                reader.openRegular(getAssets().open(openRegular));
                startActivity(new Intent(this, PlayActivity.class));
            }

            else if (id == R.id.button_options) {
                startActivity(new Intent(this, OptionsActivity.class));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
