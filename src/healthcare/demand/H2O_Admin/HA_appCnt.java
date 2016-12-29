package healthcare.demand.H2O_Admin;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import etc.PHPReader;
import etc.ViewMethod_l;
import etc.Views;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ㅇㅇ on 2016-12-27.
 */

public class HA_appCnt extends Activity {
    ViewMethod_l vml = new ViewMethod_l();

    TextView tv_cnt_1;
    TextView tv_cnt_2;
    TextView tv_cnt_3;
    TextView tv_cnt_4;
    TextView tv_cnt_5;
    TextView tv_cnt_6;

    PHPReader php;

    int healing_sum=0;
    int jeju_sum=0;
    int breath_sum=0;
    int meditation_sum=0;
    int rethink_sum=0;
    int smoody_sum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ha_app_cnt);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        tv_cnt_1 = (TextView) findViewById(R.id.tv_cnt_1);
        tv_cnt_2 = (TextView) findViewById(R.id.tv_cnt_2);
        tv_cnt_3 = (TextView) findViewById(R.id.tv_cnt_3);
        tv_cnt_4 = (TextView) findViewById(R.id.tv_cnt_4);
        tv_cnt_5 = (TextView) findViewById(R.id.tv_cnt_5);
        tv_cnt_6 = (TextView) findViewById(R.id.tv_cnt_6);

        init();
        showItem();

    }

    private void showItem() {
        php = new PHPReader();
        php.addVariable("id", getIntent().getStringExtra("userId"));
        php.addVariable("dbName", "h2ov2");
        php.execute("http://1.234.63.165/h2o/admin/search_app_count.php");

        try {
            if (php.get().trim().equalsIgnoreCase("No Such User Found")) {
            } else {
                JSONObject root = new JSONObject(php.get().trim());
                JSONArray ja = root.getJSONArray("results");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    if(jo.getString("name").equals("healing")){
                        healing_sum += Integer.parseInt(jo.getString("count"));
                        Log.i("dsdfdsfds","jo.getString(\"name\")");
                    }else  if(jo.getString("name").equals("jeju")){
                        jeju_sum += Integer.parseInt(jo.getString("count"));
                    }else  if(jo.getString("name").equals("breath")){
                        breath_sum += Integer.parseInt(jo.getString("count"));
                    }else  if(jo.getString("name").equals("meditation")){
                        meditation_sum += Integer.parseInt(jo.getString("count"));
                    }else  if(jo.getString("name").equals("rethink")){
                        rethink_sum += Integer.parseInt(jo.getString("count"));
                    }else  if(jo.getString("name").equals("smoody")){
                        smoody_sum += Integer.parseInt(jo.getString("count"));
                    }
                }

                 tv_cnt_1.setText(String.valueOf(healing_sum)+"회");
                 tv_cnt_2.setText(String.valueOf(jeju_sum)+"회");
                 tv_cnt_3.setText(String.valueOf(breath_sum)+"회");
                 tv_cnt_4.setText(String.valueOf(meditation_sum)+"회");
                 tv_cnt_5.setText(String.valueOf(rethink_sum)+"회");
                 tv_cnt_6.setText(String.valueOf(smoody_sum)+"회");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {


        TextView title[] = new TextView[7];
        TextView content[] = new TextView[7];

        vml.reformMultipleTextView(this, this.getApplicationContext(), "tv_title_cnt_", title, new int[]{1, 2, 3, 4, 5, 6}, 30, "bold", "linear", 270, 80);
        vml.reformMultipleTextView(this, this.getApplicationContext(), "tv_cnt_", content, new int[]{1, 2, 3, 4, 5, 6}, 35, "regular", "linear", 270, 150);
        vml.reformSingleTextBasedView(this.getApplicationContext(), (TextView) findViewById(R.id.tv_title_cnt), 65, "bold", "linear", 400, 130);


        TextView close = (TextView) findViewById(R.id.tv_cnt_close);
        Views vs = new Views();
        vs.customBox(close, "#caebda", "#ffffff", 70, 1);
        vml.reformSingleTextBasedView(this.getApplicationContext(), close, 45, "regular", "linear", 660, 85);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



}

//vm.reformMultipleTextView(this, context, "tv_device_", tv_device, new int[]{0,1}, 48, "medium", "frame", 0, 0, 370, 70, 0, 0);
//    public void reformMultipleTextView(Activity activity, Context c, String frontId, TextView[] tv, int[] index, int fontSize, String font,
//                                       String layoutType, int width, int height){


//vs.customBox(login, "#ffffff", "#ffffff", 43, 1);
//    public void customBox(View v, String bgColor, String lineColor, int radius, int lineWidth){
//        GradientDrawable gd = new GradientDrawable();
//        gd.setColor(Color.parseColor(bgColor));
//        gd.setStroke(lineWidth, Color.parseColor(lineColor));
//        gd.setCornerRadius((float) vm.adjustedValue(radius));
//        v.setBackgroundDrawable(gd);
//    }

//    public void reformSingleTextBasedView(Context c, TextView tv, int fontSize, String font,
//                                          String layoutType, int width, int height){