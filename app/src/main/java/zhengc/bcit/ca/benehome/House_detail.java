package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class House_detail extends AppCompatActivity {
    private ImageView btnBack;
    private static final String TAG = House_detail.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hose_detail);

        btnBack = (ImageView) findViewById(R.id.btn_back_detail);

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.wtf(TAG,"enter btnBack onclick on house detail page");
                Intent intent = new Intent();
                intent.setClass(House_detail.this,House_list.class);
                startActivity(intent);
                Log.wtf(TAG,"exit btnBack onClick on house detail page");
            }
        });



    }
}
