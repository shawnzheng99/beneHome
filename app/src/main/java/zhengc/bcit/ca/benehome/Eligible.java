package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Eligible extends AppCompatActivity {

    private ImageView btnBack;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligible);

        btnBack = (ImageView) findViewById(R.id.btn_back_eligi);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf(TAG, "enter btnStart onclick on main");
                Intent intent = new Intent();
                intent.setClass(Eligible.this, MainMenu.class);
                startActivity(intent);
                Log.wtf(TAG, "exit btnStart onClick on mian");
            }
        });


    }
}
