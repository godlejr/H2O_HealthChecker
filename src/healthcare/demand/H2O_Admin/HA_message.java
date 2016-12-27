package healthcare.demand.H2O_Admin;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import etc.ViewMethod;
import etc.ViewMethod_l;
import etc.Views;

public class HA_message extends Activity {
    /**
     * Called when the activity is first created.
     */
    //
    Context context;
    Resources res;
    //
    ViewMethod vm = new ViewMethod();
    Views vs = new Views();
    //
    ImageView back;
    TextView userid;
    ScrollView sv;
    LinearLayout dynamic;
    LinearLayout bot;
    EditText message;
    TextView send;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ha_message);

        adjustViews();
        defineEvents();
    }

    private void adjustViews(){
        context = getApplicationContext();
        res = getResources();
        //
        back = (ImageView)findViewById(R.id.back);
        userid = (TextView)findViewById(R.id.userid);
        sv = (ScrollView)findViewById(R.id.sv);
        dynamic = (LinearLayout)findViewById(R.id.dynamic);
        bot = (LinearLayout)findViewById(R.id.bot);
        message = (EditText)findViewById(R.id.message);
        send = (TextView)findViewById(R.id.send);
        //
        vm.resizeSingleView(back, res, R.drawable.icon_back, "frame", 150, 150);
        vm.reformSingleTextBasedView(context, userid, 60, "bold");
        vm.resizeSingleView(sv, "frame", 0, 0, 0, 150, 0, 200);
        vm.resizeSingleView(bot, "frame", 1080, 200, 0, 0, 0, 0, 50, 30, 50, 30);
        vm.reformSingleTextBasedView(context, message, 30, "regular", "linear", 800, 140, 0, 0, 0, 0, 30, 20, 30, 20);
        vm.reformSingleTextBasedView(context, send, 45, "bold", "linear", 180, 140);
    }

    private void defineEvents(){


    }
}
