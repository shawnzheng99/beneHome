package zhengc.bcit.ca.benehome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
    private ArrayList<Place> markers;
    SupportMapFragment mapFragment;
    private SlidingUpPanelLayout mLayout;
    private NavigationView navigationView;
    private ArrayList<Place> filtered_house;
    private ArrayList<Place> formlist;
    /*firebase*/
    private DatabaseReference databaseReference;
    private FirebaseDatabase db;
    private ImageButton imageButton;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*check if it is the first time run*/
        final String first_time = "if_first_time";

        SharedPreferences settings = getSharedPreferences(first_time, 0);

        if (settings.getBoolean("not_first", true)) {
            startActivity(new Intent(MainActivity.this, UserGuide.class));
            SharedPreferences.Editor ed = settings.edit();
            ed.putBoolean("not_first", false);
            ed.commit();
        }




        filtered_house = new ArrayList<>();
        formlist = new ArrayList<>();
        imageButton = findViewById(R.id.up_down_button);
        /*--------initilazing firebase-----------*/

        //"https://benehome-f1049.firebaseio.com/"
        db = FirebaseDatabase.getInstance("https://benehome-f1049.firebaseio.com/");
        databaseReference = db.getReference().child("features");
        storage = FirebaseStorage.getInstance();

        storageReference = storage.getReferenceFromUrl("gs://benehome-f1049.appspot.com/");
        loadFirebase();

        /*----------------------------------------*/
        /*slide up*/
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        hide_slide();
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
                if(slideOffset == 1){
                    imageButton.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                }
                if(slideOffset == 0){
                    imageButton.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "new state " + newState);
                Log.i(TAG, "previous state " + previousState);

            }
        });
        //------------------------nav oncreate-----------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,House_detail.class));
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(formlist.isEmpty())
            Log.wtf(TAG, "empty");
//-------------------------------map load and hide it----------------------------------------------------------
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
      //  getSupportFragmentManager().beginTransaction().show(mapFragment).commit();
        hidemap();
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
                show_pass(new House_list(),formlist,null);
            }

        }).start();
        set_item_check(0);
    }

    //-------------------------lording Firebase data------------------------------------
    public void loadFirebase() {
        databaseReference.keepSynced(true);
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


                    Place mPlace = new Place(Name, Description, Category,Hours
                            ,Location, PC, Email, Phone, X, Y, Website);

                    loadPic(Name,mPlace);

                    formlist.add(mPlace);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadPic(String houseName,final Place mPlace) {

        houseName = houseName.toLowerCase();
        houseName = houseName.replaceAll(" ", "");
        houseName = houseName.replaceAll("-", "");
        houseName = houseName.replaceAll("'", "");

        storageReference.child(houseName+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                mPlace.setPicUrl(uri);
            }
        });


    }
    //--------------------------nav method overload-----------------------------------

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment f = getSupportFragmentManager ().findFragmentById(R.id.container);
        FrameLayout container = findViewById(R.id.container);

        if(mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED ||
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED)){
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            Log.e("slide","back");
        }else if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
            Log.e("drawer","back");
            return;
        }
        else if(mapFragment.getUserVisibleHint()){
            hidemap();
            set_item_uncheck(1);
            Log.e("map","back");
        }else if(f instanceof House_detail){
            super.onBackPressed();
            Log.e("detail","back");
        }else if(f instanceof House_list){
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            Log.e("home","back");
        }else{
            super.onBackPressed();
            //overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            Log.e("super","back");
        }
        if(f instanceof House_list){
            set_item_check(0);
        }else if(f instanceof Eligible){
            set_item_check(2);
        }else if(f instanceof FAQ){
            set_item_check(3);
        }else if(f instanceof About){
            set_item_check(4);
        }else if(f instanceof Application){
            set_item_check(5);
            //navigationView.getMenu().getItem(6).getItem(0).setChecked(true);
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
        //if(id == R.id.homeAsUp)
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment f = getSupportFragmentManager ().findFragmentById(R.id.container);
        if (id == R.id.nav_houselist) {
            if(f instanceof House_list){
                getSupportFragmentManager().beginTransaction().detach(f).attach(f).commit();
            }else{
                //show_house_list();
                show_pass(new House_list(),formlist,null);
                hidemap();
                hide_slide();
            }
        } else if (id == R.id.nav_eligibility) {
            if(f instanceof Eligible){
                getSupportFragmentManager().beginTransaction().detach(f).attach(f).commit();
            }else{
                show_pass(new Eligible(), null,null);
                hidemap();
                hide_slide();
            }
        } else if (id == R.id.nav_faq) {
            if(f instanceof FAQ){
                getSupportFragmentManager().beginTransaction().detach(f).attach(f).commit();
            }else{
                show_pass(new FAQ(),null,null);
                hidemap();
                hide_slide();
            }
        } else if (id == R.id.nav_about) {
            if(f instanceof About){
                getSupportFragmentManager().beginTransaction().detach(f).attach(f).commit();
            }else{
                show_pass(new About(),null,null);
                hidemap();
                hide_slide();
            }
        } else if (id == R.id.nav_map) {
            mapFragment.getMapAsync(this);
            /*------------------markers---------------------------*/
            setMarkers(formlist);
            displaymap();
        } else if(id == R.id.nav_Application_guide){
            if(f instanceof Application){
                getSupportFragmentManager().beginTransaction().detach(f).attach(f).commit();
            }else{
                show_pass(new Application(),null,null);
            }
        }
        hide_slide();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void set_item_check(int i){
        navigationView.getMenu().getItem(i).setChecked(true);
    }
    public void set_item_uncheck(int i){
        navigationView.getMenu().getItem(i).setChecked(false);
    }
    //-------------------------------nav end----------------------------------
    public ArrayList<Place> getList() {
        return formlist;
    }
//------------------------------map method---------------------------------------------------
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();

        /*------------Marker-------------------*/
        for (int i = 0; i < markers.size(); ++i) {
            LatLng temp = new LatLng(Double.parseDouble(markers.get(i).getLat()),Double.parseDouble(markers.get(i).getLon()));
            mMap.addMarker(new MarkerOptions()
                    .position(temp)
                    .title(markers.get(i).getName())

            );

        }
        /*---------------marker listener---------*/
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Place selectHouse = new Place();

                // get selected house
                for (int j = 0; j < formlist.size(); ++j) {
                    if (formlist.get(j).getName().equals(marker.getTitle())) {
                        selectHouse = formlist.get(j);
                    }
                }

                show_slide(new House_detail(),selectHouse);
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) { 
                hide_slide();
            }
        });
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        zoomToMarker(markers);

    }
    public void zoomToMarker(ArrayList<Place> markers) {
        if(markers.size() > 1){
            LatLng newWest = new LatLng(49.21073429331534, -122.92282036503556);
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(newWest, 13);
            mMap.animateCamera(location);
        }else{
            LatLng temp = new LatLng(Double.parseDouble(markers.get(0).getLat()),Double.parseDouble(markers.get(0).getLon()));
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(temp, 15);
            mMap.animateCamera(location);
        }

    }
    /*change formlist to filtered_house later*/
    public void setMarkers(ArrayList<Place> list) {
        markers = new ArrayList<>();
        markers = list;
    }

    public void hidemap(){
        mapFragment.setUserVisibleHint(false);
        getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.slide_in_up,R.anim.pop_out,R.anim.pop_in,R.anim.pop_out).
                hide(mapFragment).commit();
    }
    public void displaymap(){
        mapFragment.setUserVisibleHint(true);
        getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.slide_in_up,R.anim.pop_out,R.anim.pop_in,R.anim.pop_out).
                show(mapFragment).commit();
    }
    public void pass_to_map(Place house){
        mapFragment.getMapAsync(this);
        ArrayList<Place> temp = new ArrayList<>();
        temp.add(house);
        setMarkers(temp);
        displaymap();
    }
//-------------------------------map method end---------------------------------------------------

    public void set_filtered_house(ArrayList<Place> list){
        filtered_house = list;
    }
    public void show_pass(Fragment fragment, ArrayList list, Place house){
        Bundle data = new Bundle();
        data.putSerializable("data",list);
        data.putSerializable("house", house);
        fragment.setArguments(data);
        getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up,R.anim.pop_in,R.anim.pop_out).
                replace(R.id.container, fragment).
                addToBackStack(null).
                commitAllowingStateLoss();
    }
    public void hide_slide(){
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    public void slide_expanded(Fragment fragment,Place house){
        TextView t =  findViewById(R.id.name);
        t.setText(house.getName());
        Bundle data = new Bundle();
        data.putSerializable("house",house);
        fragment.setArguments(data);
        getSupportFragmentManager().beginTransaction().replace(R.id.house_detail_container, fragment).addToBackStack(null).commitAllowingStateLoss();
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }
    public void show_slide(Fragment fragment, Place house){
        TextView t = findViewById(R.id.name);
        t.setText(house.getName());
        Bundle data = new Bundle();
        data.putSerializable("house",house);
        fragment.setArguments(data);
        getSupportFragmentManager().beginTransaction().replace(R.id.house_detail_container, fragment).addToBackStack(null).commitAllowingStateLoss();
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }
    public void up_down_button_click(View view){
        if(mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED){
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            imageButton.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
        }else if(mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            imageButton.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
        }
    }

}
