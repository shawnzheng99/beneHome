package zhengc.bcit.ca.benehome;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    public static ArrayList<HashMap<String, String>> formlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formlist = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(this));
            JSONArray jsonarray = obj.getJSONArray("features");

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject obj_inside = jsonarray.getJSONObject(i);

                String Name = obj_inside.getJSONObject("properties").getString("Name");
                String Description = obj_inside.getJSONObject("properties").getString("Description");
                String Category = obj_inside.getJSONObject("properties").getString("Category");
                String Hours = obj_inside.getJSONObject("properties").getString("Hours");
                String Location = obj_inside.getJSONObject("properties").getString("Location");
                String PC = obj_inside.getJSONObject("properties").getString("PC");
                String Phone = obj_inside.getJSONObject("properties").getString("Phone");
                String Email = obj_inside.getJSONObject("properties").getString("Email");
                String Website = obj_inside.getJSONObject("properties").getString("Website");
                String X = obj_inside.getJSONObject("properties").getString("X");
                String Y = obj_inside.getJSONObject("properties").getString("Y");

                HashMap<String, String> mylist = new HashMap<>();

                mylist.put("Name", Name);
                mylist.put("Description", Description);
                mylist.put("Category", Category);
                mylist.put("Hours", Hours);
                mylist.put("Location", Location);
                mylist.put("PC", PC);
                mylist.put("Phone", Phone);
                mylist.put("Email", Email);
                mylist.put("Website", Website);
                mylist.put("lon", X);
                mylist.put("lat", Y);

                formlist.add(mylist);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button btn_list = findViewById(R.id.btn_list);
        Button btn_start = findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf(TAG, "enter btnStart onclick on main");
                Intent intent = new Intent(MainActivity.this, MainMenu.class);
                startActivity(intent);
                Log.wtf(TAG, "exit btnStart onClick on main");
            }
        });

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf(TAG, "enter btnList onclick on main");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, House_list.class);
                startActivity(intent);
                Log.wtf(TAG, "exit btnList onClick on mian");
            }
        });
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static ArrayList<HashMap<String, String>> getList() {
        return formlist;
    }
}
