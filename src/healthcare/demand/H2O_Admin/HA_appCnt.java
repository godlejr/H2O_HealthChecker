package healthcare.demand.H2O_Admin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import etc.ViewMethod_l;
import etc.Views;

/**
 * Created by ㅇㅇ on 2016-12-27.
 */

public class HA_appCnt extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ha_app_cnt);

        TextView close  = (TextView)findViewById(R.id.tv_cnt_close);
        Views vs = new Views();
        vs.customBox(close, "#caebda", "#ffffff", 70, 1);
    }
}

//vs.customBox(login, "#ffffff", "#ffffff", 43, 1);
//    public void customBox(View v, String bgColor, String lineColor, int radius, int lineWidth){
//        GradientDrawable gd = new GradientDrawable();
//        gd.setColor(Color.parseColor(bgColor));
//        gd.setStroke(lineWidth, Color.parseColor(lineColor));
//        gd.setCornerRadius((float) vm.adjustedValue(radius));
//        v.setBackgroundDrawable(gd);
//    }