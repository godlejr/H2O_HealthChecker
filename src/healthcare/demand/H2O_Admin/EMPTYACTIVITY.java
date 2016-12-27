package healthcare.demand.H2O_Admin;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import etc.ViewMethod_l;
import etc.Views;

public class EMPTYACTIVITY extends Activity {
    /**
     * Called when the activity is first created.
     */
    //
    Context context;
    Resources res;
    //
    ViewMethod_l vml = new ViewMethod_l();
    Views vs = new Views();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ha_login);

        adjustViews();
        defineEvents();
    }

    private void adjustViews(){
        context = getApplicationContext();
        res = getResources();
        //


    }

    private void defineEvents(){


    }
}
