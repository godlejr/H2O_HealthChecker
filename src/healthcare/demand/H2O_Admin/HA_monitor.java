package healthcare.demand.H2O_Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import etc.PHPReader;
import etc.ViewMethod_l;

/**
 * Created by ㅇㅇ on 2016-12-26.
 */

public class HA_monitor extends Activity implements View.OnClickListener {
    TextView[] tv = new TextView[17];
    ViewMethod_l vm = new ViewMethod_l();
    Context context;
//    ImageView iv_msg, iv_cnt;

    View monitor_content_view;
    ListView lv;
    Resources res;

    private PHPReader php;
    String user_id;
    String user_name;
    String user_ppg_stress;
    String user_aa;
    String user_hrv;
    String user_sleep;
    String user_stress;
    String user_app_count;

    int temp_aa;
    String temp_ppg_stress;
    String int_aa_abs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ha_monitor);

        context = getApplicationContext();
        initReform(); // view size
        initInclude();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_msg) {
            Intent i = new Intent(v.getContext(), HA_message.class);
            startActivity(i);
        }
    }

    private void initReform() {
        res = getResources();

        tv[0] = (TextView) findViewById(R.id.tv_title_num); // 순번
        tv[1] = (TextView) findViewById(R.id.tv_title_id);
        tv[2] = (TextView) findViewById(R.id.tv_title_name);
        tv[3] = (TextView) findViewById(R.id.tv_title_anb);
        tv[4] = (TextView) findViewById(R.id.tv_title_stress);
        tv[5] = (TextView) findViewById(R.id.tv_title_cnt);
        tv[6] = (TextView) findViewById(R.id.tv_title_msg); // 메시지


//        title
        vm.reformSingleTextBasedView(context, tv[0], 30, "bold", "linear", 138, 100); // 순번
        vm.reformSingleTextBasedView(context, tv[1], 30, "bold", "linear", 310, 100); // ID
        vm.reformSingleTextBasedView(context, tv[2], 30, "bold", "linear", 250, 100); // 이름
        vm.reformSingleTextBasedView(context, tv[3], 30, "bold", "linear", 358, 100); // ANB/HRV
        vm.reformSingleTextBasedView(context, tv[4], 30, "bold", "linear", 358, 100); // 수면/스트레스
        vm.reformSingleTextBasedView(context, tv[5], 30, "bold", "linear", 250, 100); // 앱 사용 횟수
        vm.reformSingleTextBasedView(context, tv[6], 30, "bold", "linear", 250, 100); // 메시지

    }

    private void initInclude() {
        monitor_content_view = (View) findViewById(R.id.monitor_content);
        lv = (ListView) monitor_content_view.findViewById(R.id.lv_monitor_content);

/***************************  테스트용 리스트   *******************************/
        ArrayList<HA_monitor_item> list = new ArrayList<>();

        php = new PHPReader();
        php.addVariable("dbName", "h2ov2");
        php.execute("http://1.234.63.165/h2o/admin/select_items.php");

        try {
            if (php.get().trim().equalsIgnoreCase("No Such User Found")) {
            } else {
                Toast.makeText(context, "test 되었습니다", Toast.LENGTH_SHORT).show();

                JSONObject root = new JSONObject(php.get().trim());
                JSONArray ja = root.getJSONArray("results");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    user_id = jo.getString("id");
                    user_name = jo.getString("name");
                    user_ppg_stress = jo.getString("st");
                    user_aa = jo.getString("aa");
                    user_hrv = jo.getString("hrv");
                    user_sleep = jo.getString("sleep");
                    user_stress = jo.getString("stress");
                    user_app_count = jo.getString("count");

                    if(!user_aa.equals("")) {
                        temp_aa = (int) Double.parseDouble(user_aa);
                        int_aa_abs = Integer.toString(100 - temp_aa);
                    }else {
                        int_aa_abs ="";
                    }
                    if (!user_ppg_stress.equals("")) {
                        temp_ppg_stress = Integer.toString((int) Double.parseDouble(user_ppg_stress));
                    }else{
                        temp_ppg_stress ="";
                    }

                    list.add(new HA_monitor_item(String.valueOf(i+1), user_id, user_name, temp_ppg_stress + "(" + user_aa + ":" + int_aa_abs + ") / "+ user_hrv, user_sleep + " / " + user_stress, user_app_count + "회"));
                }
                lv.setAdapter(new LvAdaper(monitor_content_view.getContext(), list));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class LvAdaper extends BaseAdapter implements View.OnClickListener {
        ArrayList<HA_monitor_item> list;
        LayoutInflater inflater;
        Context c;

        public LvAdaper(Context c, ArrayList<HA_monitor_item> list) {
            this.c = c;
            this.list = list;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = inflater.inflate(R.layout.ha_monitor_content_item, parent, false);

//            TextView num = (TextView)convertView.findViewById(R.id.tv_num);
//            TextView id = (TextView)convertView.findViewById(R.id.tv_id);
//            TextView name = (TextView)convertView.findViewById(R.id.tv_name);
//            TextView anb = (TextView)convertView.findViewById(R.id.tv_anb);
//            TextView stress = (TextView)convertView.findViewById(R.id.tv_stress);
//            TextView cnt = (TextView)convertView.findViewById(R.id.tv_cnt);
//

//       content
            ImageView iv_cnt = (ImageView) convertView.findViewById(R.id.iv_cnt);
            ImageView iv_msg = (ImageView) convertView.findViewById(R.id.iv_msg);

            tv[7] = (TextView) convertView.findViewById(R.id.tv_num); // 순번
            tv[8] = (TextView) convertView.findViewById(R.id.tv_id);
            tv[9] = (TextView) convertView.findViewById(R.id.tv_name);
            tv[10] = (TextView) convertView.findViewById(R.id.tv_anb);
            tv[11] = (TextView) convertView.findViewById(R.id.tv_stress);
            tv[12] = (TextView) convertView.findViewById(R.id.tv_cnt);


            tv[7].setText(list.get(position).getNum());
            tv[8].setText(list.get(position).getId());
            tv[9].setText(list.get(position).getName());
            tv[10].setText(list.get(position).getAnb());
            tv[11].setText(list.get(position).getStress());
            tv[12].setText(list.get(position).getCnt());

            iv_cnt.setOnClickListener(this);
            iv_msg.setOnClickListener(this);


            vm.reformSingleTextBasedView(context, tv[7], 35, "regular", "linear", 138, 100);
            vm.reformSingleTextBasedView(context, tv[8], 35, "regular", "linear", 310, 100);
            vm.reformSingleTextBasedView(context, tv[9], 35, "regular", "linear", 250, 100);
            vm.reformSingleTextBasedView(context, tv[10], 35, "regular", "linear", 358, 100);
            vm.reformSingleTextBasedView(context, tv[11], 35, "regular", "linear", 358, 100);
            vm.reformSingleTextBasedView(context, tv[12], 35, "regular", "linear", 188, 100);

            vm.resizeSingleView(iv_cnt, res, R.drawable.icon_plus, "linear", 62, 100);
            vm.resizeSingleView(iv_msg, res, R.drawable.icon_message, "linear", 250, 100);

            return convertView;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.iv_cnt) {
                Intent i = new Intent(v.getContext(), HA_appCnt.class);
                startActivity(i);
            } else if (v.getId() == R.id.iv_msg) {
                Intent i = new Intent(v.getContext(), HA_message.class);
                startActivity(i);
            }

        }
    }

}
