package healthcare.demand.H2O_Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import etc.PHPReader;
import etc.URLConnector;
import etc.ViewMethod;
import etc.ViewMethod_l;
import etc.Views;

public class HA_message extends Activity implements View.OnClickListener {
    Context context;
    Resources res;

    ViewMethod vm = new ViewMethod();
    ViewMethod_l vml;
    Views vs = new Views();

    LinearLayout bot;
    ImageView back;
    TextView title;
    TextView send;
    EditText message;
    ListView lv;

    Adapter adapter;
    ArrayList<HA_message_item> list;
    String str;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.ha_message);

        adjustViews();
        init();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send) {
            insertMsg();
            Log.i("sssssss","sbsbsbsbbbbsbsssssssssss");
        }
    }

    private void adjustViews() {
        context = getApplicationContext();
        res = getResources();

        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);

        bot = (LinearLayout) findViewById(R.id.bot);
        message = (EditText) findViewById(R.id.message);
        send = (TextView) findViewById(R.id.send);

        vm.resizeSingleView(back, res, R.drawable.icon_back, "frame", 150, 150);
        vm.reformSingleTextBasedView(context, title, 60, "bold");

        vm.resizeSingleView(bot, "linear", 1080, 200, 0, 0, 0, 0, 50, 30, 50, 30);
        vm.reformSingleTextBasedView(context, message, 30, "regular", "linear", 800, 140, 0, 0, 0, 0, 30, 20, 30, 20);
        vm.reformSingleTextBasedView(context, send, 45, "bold", "linear", 180, 140);
    }


    private void init() {
        vml = new ViewMethod_l();
        lv = (ListView) findViewById(R.id.lv_msg);
        send.setOnClickListener(this);

        readMsg();

       // lv.requestFocusFromTouch();

    }

    class Adapter extends BaseAdapter {
        LayoutInflater inflater;
        ArrayList<HA_message_item> list = new ArrayList<>();

        public Adapter(ArrayList<HA_message_item> list) {
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

            TextView content = null, time = null, name = null;

            if (list.get(position).isSection()) {
                convertView = inflater.inflate(R.layout.ha_message_date_sector, parent, false);
                TextView sector = (TextView) convertView.findViewById(R.id.tv_msg_sector);
                sector.setText(list.get(position).getTitle());

                vml.reformSingleTextBasedView(convertView.getContext(), sector, 34, "regular", "linear", 380, 40);
            } else {
                Log.e("rc", list.get(position).getReceiverId());
                Log.e("ad", getIntent().getStringExtra("adminId"));

                if (list.get(position).getReceiverId().equals(getIntent().getStringExtra("adminId"))) { // admin이 받은거
                    convertView = inflater.inflate(R.layout.ha_message_box_receive, parent, false);
                    content = (TextView) convertView.findViewById(R.id.tv_msg_receive);
                    time = (TextView) convertView.findViewById(R.id.tv_msg_time_receive);
                    name = (TextView) convertView.findViewById(R.id.tv_msg_name);

                    String userName = list.get(position).getUser() + "(" + list.get(position).getSenderId() + ")";

                    name.setText(userName);
                    vml.reformSingleTextBasedView(convertView.getContext(), name, 43, "regular");

                } else {// 보낸거
                    convertView = inflater.inflate(R.layout.ha_message_box_send, parent, false);

                    content = (TextView) convertView.findViewById(R.id.tv_msg_send);
                    time = (TextView) convertView.findViewById(R.id.tv_msg_time_send);
                }

                content.setText(list.get(position).getMsg());

                String dateStr[] = list.get(position).getDate().split(" ");
                String d = dateStr[0]; /// 날짜
                String t = dateStr[1]; // 시간

                int hour = Integer.valueOf(t.split(":")[0]);
                int minute = Integer.valueOf(t.split(":")[1]);
                String am_pm = am_pm(hour); // 오후

                Log.e("hour", hour + " ");
                Log.e("time", dateStr[1]);

                if (hour > 12) {
                    t = am_pm + (hour - 12) + ":" + minute;
                } else {
                    t = am_pm + (hour) + ":" + minute;
                }
                time.setText(t);

                Log.e("t", t);

                content.setMaxWidth(300);

                vml.reformSingleTextBasedView(convertView.getContext(), content, 45, "regular");
                vml.reformSingleTextBasedView(convertView.getContext(), time, 35, "regular");
//            }
            } // end else

            convertView.setOnTouchListener(new View.OnTouchListener() { // 터치이벤트 X
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

            return convertView;

        }

    }

    private void readMsg() {
        list = new ArrayList<>();

        PHPReader php = new PHPReader();
        String url = "http://1.234.63.165/h2o/admin/select_msg.php";

        php.addVariable("senderId", getIntent().getStringExtra("adminId"));
        php.addVariable("receiverId", getIntent().getStringExtra("userId"));
        php.addVariable("dbName", "h2ov2");
        php.execute(url);

        Log.e("msg", "user: " + getIntent().getStringExtra("userId") + " / admin : " + getIntent().getStringExtra("adminId"));

        try {
            str = php.get().trim();

            Log.e("json", str);

            if (!str.equals("No Such User Found")) {

                JSONObject obj = new JSONObject(str);
                JSONArray arr = obj.getJSONArray("results");

                for (int i = 0; i < arr.length(); i++) {
                    Log.i("arr_length", arr.length()+"");

                    JSONObject jObj = arr.getJSONObject(i);
                    Log.e("jObj", jObj.getString("msg"));

                    String senderId = jObj.get("senderId").toString(); // receiverId
                    String receiverId = jObj.get("receiverId").toString();
                    String userName = jObj.get("name").toString();
                    String date = jObj.get("datetime").toString();
                    String msg = jObj.get("msg").toString();

                    HA_message_item item = null;

                    String date2 = date.split(" ")[0]; // 날짜

                    if (i == 0) {
                        String dt = date.split(" ")[0];
                        String dt2 = dt.split("-")[0] + "년 " + dt.split("-")[1] + "월 " + dt.split("-")[2] + "일";

                        item = new HA_message_item(dt2, true);
                        list.add(item);

                        item = new HA_message_item(senderId, receiverId, date, msg, userName, false);
                    } else {
                        String prevDate = arr.getJSONObject(i - 1).getString("datetime");
                        String date3 = prevDate.split(" ")[0]; // 이전 날짜

                        if (!date2.equals(date3)) {
                            String dt2 = date2.split("-")[0] + "년 " + date2.split("-")[1] + "월 " + date2.split("-")[2] + "일";

                            item = new HA_message_item(dt2, true);
                            list.add(item);

                            item = new HA_message_item(senderId, receiverId, date, msg, userName, false);
                        } else {
                            item = new HA_message_item(senderId, receiverId, date, msg, userName, false);
                        }

                    }

                    list.add(item);
                }

                adapter = new Adapter(list);
                lv.setAdapter(adapter);


                lv.setSelection(adapter.getCount() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private void insertMsg() {
        String senderId = getIntent().getStringExtra("adminId");
        String receiverId = getIntent().getStringExtra("userId");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String date = sdf.format(new Date(System.currentTimeMillis()));

        Log.e("날짜,. 시간 ", date);

        /***********************************************************************/

        PHPReader php = new PHPReader();
        String url = "http://1.234.63.165/h2o/admin/insert_msg.php";

        php.addVariable("senderId", senderId);
        php.addVariable("receiverId", receiverId);
        php.addVariable("datetime", date);
        php.addVariable("dbName", "h2ov2");
        php.addVariable("msg", message.getText().toString());
        php.execute(url);

        /***********************************************************************/
        readMsg();
        adapter = new Adapter(list);
        adapter.notifyDataSetChanged();


        message.setText("");
    }

    private String am_pm(int h) {
        if (h >= 0 && h < 12)
            return "오전";
        else
            return "오후";

    }


    /*********************
     * Message Item
     ********************/

    public class HA_message_item {
        String senderId, receiverId, date, msg, user;
        boolean section;
        String title;


        public HA_message_item(String title, boolean section) {
            this.title = title;
            this.section = section;
        }

        public HA_message_item(String senderId, String receiverId, String date, String msg, String user, boolean section) {
            this.senderId = senderId;
            this.receiverId = receiverId;
            this.date = date;
            this.msg = msg;
            this.section = section;
            this.user = user;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(String receiverId) {
            this.receiverId = receiverId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public boolean isSection() {
            return section;
        }

        public void setSection(boolean section) {
            this.section = section;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
