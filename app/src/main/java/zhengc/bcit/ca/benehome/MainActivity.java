package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn_list, btn_start;
    private static final String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_list = (Button) findViewById(R.id.btn_list);
        btn_start = (Button) findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.wtf(TAG,"enter btnStart onclick on main");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MainMenu.class);
                startActivity(intent);
                Log.wtf(TAG,"exit btnStart onClick on main");
            }
        });

        btn_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.wtf(TAG,"enter btnList onclick on main");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,House_detail.class);
                startActivity(intent);
                Log.wtf(TAG,"exit btnList onClick on mian");
            }
        });
    }
}
