package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;

public class House_list extends AppCompatActivity {

    private ImageView btnBack;
    private Button btn_map;
    ArrayList<HashMap<String,String>> house;
    String[] houseName;
    private static final String TAG = House_list.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_list);

        // set up btns
        setBtns();
        // set up house arrayList from josn
        setHouse();
        // set house list
        setList();

    }

    private void setList() {
        for(int i = 0; i < house.size();++i){
            houseName[i] = house.get(i).get("Name");
        }

        ListAdapter lst = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,//list styles
                houseName);
        ListView lstView = (ListView) findViewById(R.id.lst_result_listing);
        lstView.setAdapter(lst);
        lstView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(House_list.this,House_detail.class);
                        HashMap<String,String> selectHouse = new HashMap<>();

                        // get selected house
                        for(int j = 0; j < house.size();++j) {
                            if(house.get(i).get("Name").equals(adapterView.getItemAtPosition(i))){
                                selectHouse = house.get(i);
                            }
                        }
                        intent.putExtra("house", selectHouse);
                        startActivity(intent);
                    }
                }
        );
    }

    private void setHouse() {
        house = MainActivity.getList();
        houseName = new String[house.size()];
    }

    private void setBtns() {
        btnBack = findViewById(R.id.btn_back_listing);
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
        btn_map = findViewById(R.id.btn_map_list);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf(TAG,"map view in");
                Intent intent = new Intent(House_list.this, MapsActivity.class);
                startActivity(intent);
                Log.wtf(TAG,"map view go");
            }
        });
    }

}
