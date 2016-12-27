package healthcare.demand.H2O_Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import etc.PHPReader;
import etc.ViewMethod_l;
import etc.Views;

public class HA_login extends Activity {
    /**
     * Called when the activity is first created.
     */
    //
    Context context;
    Resources res;
    //
    ViewMethod_l vml = new ViewMethod_l();
    Views vs = new Views();
    //
    ImageView logo_top;
    LinearLayout ll_id, ll_pw, ll_save;
    ImageView icon_id, icon_pw;
    EditText et_id, et_pw;
    ImageView checkbox;
    TextView save;
    TextView login;

    // SERVER & PREFERENCES
    PHPReader php;
    String id, pwd;
    SharedPreferences loginInfo;
    SharedPreferences.Editor editor;
    String pref_id, pref_pw;
    String name;
    boolean isChecked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ha_login);

        adjustViews();
        defineEvents();

    }

    private void adjustViews() {
        context = getApplicationContext();
        res = getResources();
        //
        logo_top = (ImageView) findViewById(R.id.logo_top);
        ll_id = (LinearLayout) findViewById(R.id.ll_id);
        ll_pw = (LinearLayout) findViewById(R.id.ll_pw);
        ll_save = (LinearLayout) findViewById(R.id.ll_save);
        icon_id = (ImageView) findViewById(R.id.icon_id);
        icon_pw = (ImageView) findViewById(R.id.icon_pw);
        et_id = (EditText) findViewById(R.id.et_id);
        et_pw = (EditText) findViewById(R.id.et_pw);
        checkbox = (ImageView) findViewById(R.id.checkbox);
        save = (TextView) findViewById(R.id.save);
        login = (TextView) findViewById(R.id.login);
        //
        vml.resizeSingleView(logo_top, res, R.drawable.image_1, "linear", 1920, 524);
        vml.resizeSingleView(ll_id, "linear", 0, 0, 0, 0, 0, 40);
        vml.resizeSingleView(ll_pw, "linear", 0, 0, 0, 0, 0, 20);
        vml.resizeSingleView(ll_save, "linear", 0, 0, 0, 0, 0, 60);
        vml.resizeSingleView(icon_id, res, R.drawable.icon_id, "linear", 100, 85);
        vml.resizeSingleView(icon_pw, res, R.drawable.icon_pw, "linear", 100, 85);
        vml.reformSingleTextBasedView(context, et_id, 28, "regular", "linear", 560, 85);
        vml.reformSingleTextBasedView(context, et_pw, 28, "regular", "linear", 560, 85);
        vml.resizeSingleView(checkbox, res, R.drawable.check, "linear", 65, 45);
        vml.reformSingleTextBasedView(context, save, 26, "regular", "linear", 595, 45);
        vml.reformSingleTextBasedView(context, login, 45, "bold", "linear", 660, 85);
        vs.customBox(login, "#ffffff", "#ffffff", 43, 1);

    }

    private void defineEvents() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(HA_login.this, HA_message.class));
                startActivity(new Intent(HA_login.this, HA_monitor.class));
            }
        });

    }
}
