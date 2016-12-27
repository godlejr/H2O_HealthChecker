package healthcare.demand.H2O_Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import etc.ViewMethod_l;

/**
 * Created by ㅇㅇ on 2016-12-26.
 */

public class HA_monitor extends Activity {
    TextView[] tv = new TextView[17];
    LinearLayout layout_cnt;
    ViewMethod_l vm = new ViewMethod_l();
    Context context;
    ImageView iv_msg, iv_cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ha_monitor);

        context = getApplicationContext();
        init();
    }

    private void init() {
        Resources res = getResources();

        tv[0] = (TextView) findViewById(R.id.tv_title_num); // 순번
        tv[1] = (TextView) findViewById(R.id.tv_title_id);
        tv[2] = (TextView) findViewById(R.id.tv_title_name);
        tv[3] = (TextView) findViewById(R.id.tv_title_anb);
        tv[4] = (TextView) findViewById(R.id.tv_title_stress);
        tv[5] = (TextView) findViewById(R.id.tv_title_cnt);
        tv[6] = (TextView) findViewById(R.id.tv_title_msg); // 메시지

        tv[7] = (TextView) findViewById(R.id.tv_num);
        tv[8] = (TextView) findViewById(R.id.tv_id);
        tv[9] = (TextView) findViewById(R.id.tv_name);
        tv[10] = (TextView) findViewById(R.id.tv_anb);
        tv[11] = (TextView) findViewById(R.id.tv_stress);
        tv[12] = (TextView) findViewById(R.id.tv_cnt);

        iv_msg = (ImageView)findViewById(R.id.iv_msg);
        iv_cnt = (ImageView)findViewById(R.id.iv_cnt);
        iv_cnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), HA_appCnt.class);
                startActivity(i);
            }
        });

        layout_cnt = (LinearLayout) findViewById(R.id.layout_cnt);

//        title

        vm.reformSingleTextBasedView(context, tv[0], 30, "bold", "linear", 138, 100); // 순번
        vm.reformSingleTextBasedView(context, tv[1], 30, "bold", "linear", 310, 100); // ID
        vm.reformSingleTextBasedView(context, tv[2], 30, "bold", "linear", 250, 100); // 이름
        vm.reformSingleTextBasedView(context, tv[3], 30, "bold", "linear", 358, 100); // ANB/HRV
        vm.reformSingleTextBasedView(context, tv[4], 30, "bold", "linear", 358, 100); // 수면/스트레스
        vm.reformSingleTextBasedView(context, tv[5], 30, "bold", "linear", 250, 100); // 앱 사용 횟수
        vm.reformSingleTextBasedView(context, tv[6], 30, "bold", "linear", 250, 100); // 메시지

//       content
        vm.reformSingleTextBasedView(context, tv[7], 35, "regular", "linear", 138, 100);
        vm.reformSingleTextBasedView(context, tv[8], 35, "regular", "linear", 310, 100);
        vm.reformSingleTextBasedView(context, tv[9], 35, "regular", "linear", 250, 100);
        vm.reformSingleTextBasedView(context, tv[10], 35, "regular", "linear", 358, 100);
        vm.reformSingleTextBasedView(context, tv[11], 35, "regular", "linear", 358, 100);
        vm.reformSingleTextBasedView(context, tv[12], 35, "regular", "linear", 188, 100);

        vm.resizeSingleView(iv_cnt, res, R.drawable.icon_plus, "linear", 62, 100);
        vm.resizeSingleView(iv_msg, res, R.drawable.icon_message, "linear", 250, 100);




    }
}
