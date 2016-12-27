package healthcare.demand.H2O_Admin;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import etc.ViewMethod_l;
import etc.Views;

/**
 * Created by ㅇㅇ on 2016-12-27.
 */

public class HA_appCnt extends Activity {
    ViewMethod_l vml = new ViewMethod_l();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ha_app_cnt);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        init();

    }

    private void init() {
        TextView title[] = new TextView[7];
        TextView content[] = new TextView[7];

        vml.reformMultipleTextView(this, this.getApplicationContext(), "tv_title_cnt_", title, new int[]{1, 2, 3, 4, 5, 6}, 30, "bold", "linear", 270, 80);
        vml.reformMultipleTextView(this, this.getApplicationContext(), "tv_cnt_", content, new int[]{1, 2, 3, 4, 5, 6}, 35, "regular", "linear", 270, 150);
        vml.reformSingleTextBasedView(this.getApplicationContext(), (TextView)findViewById(R.id.tv_title_cnt), 65, "bold", "linear", 400, 130);


        TextView close = (TextView) findViewById(R.id.tv_cnt_close);
        Views vs = new Views();
        vs.customBox(close, "#caebda", "#ffffff", 70, 1);
        vml.reformSingleTextBasedView(this.getApplicationContext(), close,45, "regular", "linear", 660, 85);

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