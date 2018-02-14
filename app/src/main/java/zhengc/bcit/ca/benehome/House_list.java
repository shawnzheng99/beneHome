package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class House_list extends AppCompatActivity {

    private ImageView btnBack;

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_list);

        btnBack = (ImageView) findViewById(R.id.btn_back_listing);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.wtf(TAG,"enter btnBack onclick on house detail page");
                Intent intent = new Intent();
                intent.setClass(House_list.this,MainMenu.class);
                startActivity(intent);
                Log.wtf(TAG,"exit btnBack onClick on house detail page");
            }
        });

        String[] house = {"house obj 1", "house obj 2", "house obj 3"};
        ListAdapter lst = new ArrayAdapter<String>(
                this,
                 android.R.layout.simple_list_item_1,//list styles
                 house);
        ListView lstView = (ListView) findViewById(R.id.lst_result_listing);
        lstView.setAdapter(lst);
        lstView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String house = String.valueOf(adapterView.getItemAtPosition(i));
                        Toast.makeText(House_list.this,
                                house,
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setClass(House_list.this,House_detail.class);
                        startActivity(intent);
                    }
                }
        );

    }
}
