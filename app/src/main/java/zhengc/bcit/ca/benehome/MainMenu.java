package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button btnEligi = findViewById(R.id.btn_eligible);
        Button btnAbout = findViewById(R.id.btn_about);
        Button btnSearch = findViewById(R.id.btn_search);
        Button btnFAQ = findViewById(R.id.btn_faq);
        /*btn linster to house list page*/
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf(TAG, "enter btnStart onclick on main");
                Intent intent = new Intent();
                intent.setClass(MainMenu.this, House_list.class);
                startActivity(intent);
                Log.wtf(TAG, "exit btnStart onClick on mian");
            }
        });

        /*btn linster to house list page*/
        btnEligi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf(TAG, "enter btnStart onclick on main");
                Intent intent = new Intent();
                intent.setClass(MainMenu.this, Eligible.class);
                startActivity(intent);
                Log.wtf(TAG, "exit btnStart onClick on mian");
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf(TAG, "enter btnStart onclick on main");
                Intent intent = new Intent();
                intent.setClass(MainMenu.this, About.class);
                startActivity(intent);
                Log.wtf(TAG, "exit btnStart onClick on mian");
            }
        });
        btnFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf(TAG, "enter btnStart onclick on main");
                Intent intent = new Intent();
                intent.setClass(MainMenu.this, FAQ.class);
                startActivity(intent);
                Log.wtf(TAG, "exit btnStart onClick on mian");
            }
        });
    }
}
