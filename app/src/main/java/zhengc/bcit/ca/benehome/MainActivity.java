package zhengc.bcit.ca.benehome;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    private static final String TAG = MainActivity.class.getName();
    static ArrayList<HashMap<String, String>> formlist;
    /*firebase*/
    private DatabaseReference databaseReference;
    private FirebaseDatabase db;
    final private String FIREBASE_DB_ADD = "https://benehome-66efd.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //------------------------nav oncreate-----------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /*--------initilazing db-----------*/
        db = FirebaseDatabase.getInstance(FIREBASE_DB_ADD);
        databaseReference = db.getReference().child("features");

        /*loading firebase*/

        loadFirebase();


        if(formlist.isEmpty())
            Log.wtf(TAG, "empty");

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new House_list()).commitAllowingStateLoss();
    }



    //-------------------------loding firebase data------------------------------------
    public void loadFirebase() {
        databaseReference.keepSynced(true);
        formlist = new ArrayList<>();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.wtf(TAG,"---------------onChange--------------");
                formlist.clear();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String Category = (String) messageSnapshot.child("properties")
                            .child("Category").getValue();
                    String Description = (String) messageSnapshot.child("properties")
                            .child("Description").getValue();
                    String Email = (String) messageSnapshot.child("properties")
                            .child("Email").getValue();
                    String Hours = (String) messageSnapshot.child("properties")
                            .child("Hours").getValue();
                    String Location = (String) messageSnapshot.child("properties")
                            .child("Location").getValue();
                    String Name = (String) messageSnapshot.child("properties")
                            .child("Name").getValue();
                    String PC = (String) messageSnapshot.child("properties")
                            .child("PC").getValue();
                    String Phone = (String) messageSnapshot.child("properties")
                            .child("Phone").getValue();
                    String Website = (String) messageSnapshot.child("properties")
                            .child("Website").getValue();
                    String X = (String) messageSnapshot.child("properties")
                            .child("X").getValue();
                    String Y = (String) messageSnapshot.child("properties")
                            .child("Y").getValue();

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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadFromJson() {
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
    }
    //--------------------------nav method overload-----------------------------------

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_houselist) {
           // vf.setDisplayedChild(0);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new House_list()).commitAllowingStateLoss();
        } else if (id == R.id.nav_eligibility) {
           // vf.setDisplayedChild(1);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Eligible()).commitAllowingStateLoss();
        } else if (id == R.id.nav_faq) {
           // vf.setDisplayedChild(2);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new FAQ()).commitAllowingStateLoss();
        } else if (id == R.id.nav_about) {
          //  vf.setDisplayedChild(3);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new About()).commitAllowingStateLoss();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //-------------------------nav method overload end--------------------------------
    //-----------------------------------JSON file method-----------------------------------------------------------------------------------
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

//    public ArrayList<HashMap<String, String>> getList() {
//        return formlist;
//    }
}
