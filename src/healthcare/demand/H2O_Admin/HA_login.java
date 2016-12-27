package healthcare.demand.H2O_Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

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
        firstView();
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

        ll_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChecked == false){
                    isChecked = true;
                    checkbox.setImageResource(R.drawable.check);
                    save.setTextColor(Color.parseColor("#444444"));
                }
                else if(isChecked == true){
                    isChecked = false;
                    checkbox.setImageResource(R.drawable.check_1);
                    save.setTextColor(Color.parseColor("#858585"));
                }
            }
        });

        login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        login.setTextColor(Color.parseColor("#f5f5f5"));
                        vs.customBox(login, "#444444", "#444444", 60, 2);
                        break;
                    case MotionEvent.ACTION_CANCEL :
                        login.setTextColor(Color.parseColor("#444444"));
                        vs.customBox(login, "#f5f5f5", "#444444", 60, 2);
                        break;
                    case MotionEvent.ACTION_UP :
                        login.setTextColor(Color.parseColor("#444444"));
                        vs.customBox(login, "#f5f5f5", "#444444", 60, 2);
                        login();
                        break;
                }
                return true;
            }
        });
    }

    public void login(){
        id = et_id.getText().toString();
        pwd = et_pw.getText().toString();

        php = new PHPReader();
        php.addVariable("id", id);
        php.addVariable("password", pwd);
        php.addVariable("dbName", "h2ov2");
        php.execute("http://1.234.63.165/h2o/admin/login.php");

        try{
            if(php.get().trim().equalsIgnoreCase("No Such User Found")){
                Log.i("pddddd","dsfddf");
                Toast.makeText(context, "ID/PW를 확인해주세요", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "로그인 되었습니다", Toast.LENGTH_SHORT).show();

                JSONObject root = new JSONObject(php.get().trim());
                JSONArray ja = root.getJSONArray("results");

                for(int i = 0; i < ja.length(); i++){
                    JSONObject jo = ja.getJSONObject(i);
                    name = jo.getString("name");
                }
                Log.i("pddddd",name);
                Intent intent = new Intent(HA_login.this, HA_monitor.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.appear_from_right_300, R.anim.disappear_to_left_300);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                android.os.Process.killProcess(android.os.Process.myPid());
        }
        return false;
    }
    private void firstView(){
        loginInfo = getSharedPreferences("loginInfo", Activity.MODE_PRIVATE);

        pref_id = loginInfo.getString("et_id", "");
        pref_pw = loginInfo.getString("et_pw", "");
        isChecked = loginInfo.getBoolean("isChecked", isChecked);

        et_id.setText(pref_id);
        et_pw.setText(pref_pw);

        checkBox();
    }

    public void onStop(){
        super.onStop();
        loginInfo = getSharedPreferences("loginInfo", Activity.MODE_PRIVATE);
        editor = loginInfo.edit();
        if(isChecked == true) {
            editor.putString("et_id", et_id.getText().toString());
            editor.putString("et_pw", et_pw.getText().toString());
            editor.putBoolean("isChecked", true);
        }

        else if(isChecked == false){
            String none = "";
            editor.putString("et_id", none);
            editor.putString("et_pw", none);
            editor.putBoolean("isChecked", false);
        }
        editor.commit();
    }

    public void checkBox(){
        if(isChecked == false) checkbox.setImageResource(R.drawable.check);
        else if(isChecked == true) checkbox.setImageResource(R.drawable.check_1);
    }

}
