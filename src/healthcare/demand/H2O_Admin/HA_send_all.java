package healthcare.demand.H2O_Admin;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import etc.PHPReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dev-0 on 2016-12-30.
 */
public class HA_send_all extends Activity {
    EditText text;
    String send_text;
    PHPReader php;
    String admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ha_message_send_all);

        ImageView back = (ImageView) findViewById(R.id.iv_send_all_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        text = (EditText) findViewById(R.id.et_msg_send_all);

        Button send_btn = (Button) findViewById(R.id.send_btn);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_text = text.getText().toString();

                send_msg(send_text);

                finish();
            }
        });


    }

    private void send_msg(String msg) {
        php = new PHPReader();
        admin = getIntent().getStringExtra("admin");

        Date day = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String datetime = sdf.format(day);
      //  String dateStr[] = list.get(position).getDate().split(" ");
        php.addVariable("senderId", admin);
        php.addVariable("datetime", datetime);
        php.addVariable("msg", msg);
        php.addVariable("dbName", "h2ov2");
        php.execute("http://1.234.63.165/h2o/admin/insert_all_msg.php");

        finish();
    }
}
