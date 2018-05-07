package zhengc.bcit.ca.benehome;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
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
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {

    private static final String TAG = MainActivity.class.getName();
    private GoogleMap mMap;
    private ArrayList<LatLng> markers;
    SupportMapFragment mapFragment;
    private SlidingUpPanelLayout mLayout;

    private ArrayList<HashMap<String, String>> filtered_house;
    private ArrayList<HashMap<String, String>> formlist;
    /*firebase*/
    private DatabaseReference databaseReference;
    //final private String FIREBASE_DB_ADD = "https://benehome-66efd.firebaseio.com/";
    private FirebaseDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filtered_house = new ArrayList<>();
        formlist = new ArrayList<>();
        /*--------initilazing db-----------*/
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference().child("features");

        /*loading firebase*/
        loadFirebase();
        /*slide up*/
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
            }
        });

        hide_slide();
        //------------------------nav oncreate-----------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,MapsActivity.class));
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(formlist.isEmpty())
            Log.wtf(TAG, "empty");
//-------------------------------map load and hide it----------------------------------------------------------
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();

        new Thread(new Runnable(){
            @Override
            public void run() {
                while(formlist.isEmpty()){
                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                show_pass(new House_list(),formlist);
            }

        }).start();
    }


    //-------------------------loding firebase data------------------------------------
    public void loadFirebase() {
        databaseReference.keepSynced(true);
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
            super.onBackPressed();
        } else {
            drawer.openDrawer(GravityCompat.START);
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
            //show_house_list();
            show_pass(new House_list(),formlist);
            hidemap();
            hide_slide();
        } else if (id == R.id.nav_eligibility) {
            show_pass(new Eligible(), null);
            hidemap();
            hide_slide();
        } else if (id == R.id.nav_faq) {
            show_pass(new FAQ(),null);
            hidemap();
            hide_slide();
        } else if (id == R.id.nav_about) {
            show_pass(new About(),null);
            hidemap();
            hide_slide();
        } else if (id == R.id.nav_map) {
            mapFragment.getMapAsync(this);
            /*------------------markers---------------------------*/
            setMarkers(formlist);
            showmap();
        }
        hide_slide();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //-----------------------------------JSON file method--------------------------------
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
    public ArrayList<HashMap<String, String>> getList() {
        return formlist;
    }
//------------------------------map method---------------------------------------------------
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        zoomToNewWest();

        /*------------Marker-------------------*/
        for (int i = 0; i < markers.size(); ++i) {
            mMap.addMarker(new MarkerOptions()
                    .position(markers.get(i))
                    .title(formlist.get(i).get("Name"))

            );

        }
        /*---------------marker listener---------*/
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //Intent intent = new Intent(MainActivity.this, House_detail.class);
                HashMap<String, String> selectHouse = new HashMap<>();

                // get selected house
                for (int j = 0; j < formlist.size(); ++j) {
                    if (formlist.get(j).get("Name").equals(marker.getTitle())) {
                        selectHouse = formlist.get(j);
                    }
                }
                //intent.putExtra("house", selectHouse);
                //startActivity(intent);
                show_slide(selectHouse);
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) { 
                hide_slide();
            }
        });
        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }
    public void zoomToNewWest() {
        LatLng newWest = new LatLng(49.21073429331534, -122.92282036503556);
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(newWest, 13);
        mMap.animateCamera((location));
    }
    /*change formlist to filtered_house later*/
    public void setMarkers(ArrayList<HashMap<String,String>> list) {
        markers = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            double y = Double.parseDouble(list.get(i).get("lon"));
            double x = Double.parseDouble(list.get(i).get("lat"));
            markers.add(new LatLng(x, y));
        }
    }

    public void hidemap(){
        getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();
    }
    public void showmap(){
        getSupportFragmentManager().beginTransaction().show(mapFragment).commit();
    }
//-------------------------------map method end---------------------------------------------------

    public void set_filtered_house(ArrayList<HashMap<String,String>> list){
        filtered_house = list;
    }
    public void show_pass(Fragment fragment, ArrayList list){
        Bundle data = new Bundle();
        data.putSerializable("data",list);
        fragment.setArguments(data);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss();
    }
//    public void show_house_list(){
//        Fragment house_list_fragment = new House_list();
//        Bundle data = new Bundle();
//        data.putSerializable("data",formlist);
//        house_list_fragment.setArguments(data);
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, house_list_fragment).commitAllowingStateLoss();
//    }
//    public void show_eligibility(){
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Eligible()).commitAllowingStateLoss();
//    }
//    public void show_faq(){
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FAQ()).commitAllowingStateLoss();
//    }
//    public void show_about(){
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, new About()).commitAllowingStateLoss();
//    }
    public void hide_slide(){
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }
    public void show_slide(final HashMap<String,String> house){
        TextView t = findViewById(R.id.name);
        t.setText(house.get("Name"));

        /*jump to house detail page*/
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, House_detail.class);
                intent.putExtra("house", house);
                startActivity(intent);
            }
        });
        //----------------------------------------------

        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);;
    }
}
