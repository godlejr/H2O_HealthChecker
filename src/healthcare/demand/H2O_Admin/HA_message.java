package healthcare.demand.H2O_Admin;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import etc.ViewMethod;
import etc.ViewMethod_l;
import etc.Views;

public class HA_message extends Activity implements View.OnClickListener{
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
    TextView title;
    //    ScrollView sv;
//    LinearLayout dynamic;
    LinearLayout bot;
    EditText message;
    TextView send;
    ViewMethod_l vml;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ha_message);

        adjustViews();
        defineEvents();
        init();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.send){
            message.setText("");


        }
    }

    private void adjustViews() {
        context = getApplicationContext();
        res = getResources();

        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
//        sv = (ScrollView)findViewById(R.id.sv);
//        dynamic = (LinearLayout)findViewById(R.id.dynamic);
        bot = (LinearLayout) findViewById(R.id.bot);
        message = (EditText) findViewById(R.id.message);
        send = (TextView) findViewById(R.id.send);

        vm.resizeSingleView(back, res, R.drawable.icon_back, "frame", 150, 150);
        vm.reformSingleTextBasedView(context, title, 60, "bold");
//        vm.resizeSingleView(sv, "frame", 0, 0, 0, 150, 0, 200);
        vm.resizeSingleView(bot, "linear", 1080, 200, 0, 0, 0, 0, 50, 30, 50, 30);
        vm.reformSingleTextBasedView(context, message, 30, "regular", "linear", 800, 140, 0, 0, 0, 0, 30, 20, 30, 20);
        vm.reformSingleTextBasedView(context, send, 45, "bold", "linear", 180, 140);
    }

    private void defineEvents() {


    }

    private void init() {
        vml = new ViewMethod_l();
        ListView lv = (ListView) findViewById(R.id.lv_msg);
        send.setOnClickListener(this);


        /******************** 테스트용 리스트 ******************/
        ArrayList<HA_message_item> list = new ArrayList<>();
        list.add(new HA_message_item("2016년 8월 9일 월요일", true));
        list.add(new HA_message_item("장기원", "아이디", "time", "스무디를 사용하면서~~~~~~~~~\n~~~~~~~~~~~~~~~~\n~~~~~~~~~~~~~~~\n~~~~~~~~~~~~~~", "R", false));
        list.add(new HA_message_item("장기원", "아이디", "time", "스무디에서 문의 버튼을~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", "R", false));
        list.add(new HA_message_item("2016년 10월 9일 월요일", true));
        list.add(new HA_message_item("name", "id", "time", "어떻게 하나요?", "S", false));

        lv.setAdapter(new Adapter(list));
        /*****************************************************/

    }

    class Adapter extends BaseAdapter {
        LayoutInflater inflater;
        ArrayList<HA_message_item> list = new ArrayList<>();

//
//        public Adapter(ArrayList<HA_message_item> list) {
//            this.list = list;
//            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        }


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
                if (list.get(position).getFlag().equals("R")) { // receive
                    convertView = inflater.inflate(R.layout.ha_message_box_receive, parent, false);
                    content = (TextView) convertView.findViewById(R.id.tv_msg_receive);
                    time = (TextView) convertView.findViewById(R.id.tv_msg_time_receive);
                    name = (TextView) convertView.findViewById(R.id.tv_msg_name);

                    String userName = list.get(position).getName() + "(" + list.get(position).getId() + ")";
                    name.setText(userName);
                    vml.reformSingleTextBasedView(convertView.getContext(), name, 43, "regular");

                } else if (list.get(position).getFlag().equals("S")) {// send
                    convertView = inflater.inflate(R.layout.ha_message_box_send, parent, false);

                    content = (TextView) convertView.findViewById(R.id.tv_msg_send);
                    time = (TextView) convertView.findViewById(R.id.tv_msg_time_send);
                }

                content.setText(list.get(position).getContent());
                time.setText(list.get(position).getTime());

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


    /********************** Message Item ********************/

    public class HA_message_item {
        String name, id, time, flag, content;
        boolean section;
        String title;


        public HA_message_item(String title, boolean section) {
            this.title = title;
            this.section = section;
        }

        public HA_message_item(String name, String id, String time, String content, String flag, boolean section) {
            this.flag = flag;
            this.name = name;
            this.id = id;
            this.time = time;
            this.content = content;
            this.section = section;
        }

        public String getContent() {
            return content;
        }

        public boolean isSection() {
            return section;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }


        public String getFlag() {
            return flag;
        }



        public String getTitle() {
            return title;
        }


    }

}
