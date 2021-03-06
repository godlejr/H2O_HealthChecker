package healthcare.demand.H2O_Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

public class HA_monitor extends Activity {
    TextView[] tv = new TextView[17];
    Context context;
//    ImageView iv_msg, iv_cnt;

    View monitor_content_view;
    ListView lv;
    Resources res;
    TextView company_name;

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
    String anvHrv;
    String sleepStress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ha_monitor);
        context = getApplicationContext();

        initReform(); // view size
        initInclude();

        company_name = (TextView)findViewById(R.id.company_name);

        ImageView logout = (ImageView) findViewById(R.id.iv_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), HA_login.class));
                finish();

                Toast.makeText(context, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView send_all = (ImageView) findViewById(R.id.iv_total_msg);
        send_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), HA_send_all.class);
                i.putExtra("admin", getIntent().getStringExtra("id"));
                startActivity(i);
            }
        });
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



   //     company_name.setText(getIntent().getStringExtra("company") + " / " + getIntent().getStringExtra("name") + "님");

//        title


//        vm.reformSingleTextBasedView(context, tv[0], 30, "bold", "linear", 138, 100); // 순번
//        vm.reformSingleTextBasedView(context, tv[1], 30, "bold", "linear", 310, 100); // ID
//        vm.reformSingleTextBasedView(context, tv[2], 30, "bold", "linear", 250, 100); // 이름
//        vm.reformSingleTextBasedView(context, tv[3], 30, "bold", "linear", 358, 100); // ANB/HRV
//        vm.reformSingleTextBasedView(context, tv[4], 30, "bold", "linear", 358, 100); // 수면/스트레스
//        vm.reformSingleTextBasedView(context, tv[5], 30, "bold", "linear", 250, 100); // 앱 사용 횟수
//        vm.reformSingleTextBasedView(context, tv[6], 30, "bold", "linear", 250, 100); // 메시지

    }

    private void initInclude() {

/***************************  테스트용 리스트   *******************************/
        ArrayList<HA_monitor_item> list = new ArrayList<>();

        php = new PHPReader();
        php.addVariable("level", getIntent().getStringExtra("level"));
        php.addVariable("dbName", "h2ov2");
        php.execute("http://1.234.63.165/h2o/admin/select_items.php");

        try {
            if (php.get().trim().equalsIgnoreCase("No Such User Found")) {
            } else {
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

                    if (!user_aa.equals("")) {
                        temp_aa = (int) Double.parseDouble(user_aa);
                        int_aa_abs = Integer.toString(100 - temp_aa);
                        user_aa = Integer.toString((int) Double.parseDouble(user_aa));
                    } else {
                        int_aa_abs = "";
                    }
                    if (!user_ppg_stress.equals("")) {
                        temp_ppg_stress = Integer.toString((int) Double.parseDouble(user_ppg_stress));
                    } else {
                        temp_ppg_stress = "";
                    }

                    if (!user_hrv.equals("")) {
                        user_hrv = Integer.toString((int) Double.parseDouble(user_hrv));
                    }

                    if (!temp_ppg_stress.equals("") && !user_aa.equals("") && !int_aa_abs.equals("") && !user_hrv.equals("")) {
                        anvHrv = temp_ppg_stress + "(" + user_aa + ":" + int_aa_abs + ") / " + user_hrv;
                    } else {
                        anvHrv = "-";
                    }

                    if (!user_sleep.equals("") && !user_stress.equals("")) {
                        sleepStress = user_sleep + " / " + user_stress;
                    } else {
                        sleepStress = "-";
                    }

                    list.add(new HA_monitor_item(String.valueOf(i + 1), user_id, user_name, anvHrv, sleepStress, user_app_count + "회"));
                }

                monitor_content_view = (View) findViewById(R.id.monitor_content);
                lv = (ListView) monitor_content_view.findViewById(R.id.lv_monitor_content);

                lv.setAdapter(new LvAdapter(monitor_content_view.getContext(), list));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class LvAdapter extends BaseAdapter {
        ArrayList<HA_monitor_item> list;
        LayoutInflater inflater;
        Context c;

        public LvAdapter(Context c, ArrayList<HA_monitor_item> list) {
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

            iv_cnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), HA_appCnt.class);
                    i.putExtra("userId", list.get(position).getId());
                    startActivity(i);
                }
            });

            iv_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, " 현재 전체메시지 전송만 가능합니다.", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }


    }

}