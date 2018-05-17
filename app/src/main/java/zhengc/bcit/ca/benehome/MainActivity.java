package zhengc.bcit.ca.benehome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback, Serializable {

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
    private ImageButton imageButton;
    //auth
    FirebaseAuth mAuth;
    FirebaseUser user;
    DrawerLayout drawer;
    Fragment f;
    boolean filter_on_map = false;
    int on_back_press_twice_to_exit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*check if it is the first time run this app*/
        final String first_time = "if_first_time";

        SharedPreferences settings = getSharedPreferences(first_time, 0);

        if (settings.getBoolean("first", true)) {
            startActivity(new Intent(MainActivity.this, UserGuide.class));
            SharedPreferences.Editor ed = settings.edit();

            ed.putBoolean("first", false);
            ed.commit();
            //ed.apply();   Compared to commit(), apply() won't change the file synchronously.

        }


        //firebase auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user == null) {
            signInAnonymously();
        }
//        } else {
//            Toast.makeText(this,"Loading",Toast.LENGTH_LONG).show();
//        }


        filtered_house = new ArrayList<>();
        formlist = new ArrayList<>();
        imageButton = findViewById(R.id.up_down_button);
        drawer = findViewById(R.id.drawer_layout);
        f = get_current_fragment();
        /*--------initilazing firebase-----------*/

        //"https://benehome-f1049.firebaseio.com/"
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://benehome-f1049.firebaseio.com/");
        databaseReference = db.getReference();

        /*----------------------------------------*/
        /*slide up*/
        mLayout = findViewById(R.id.sliding_layout);
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                Log.e("Drawer","open");
                super.onDrawerOpened(drawerView);
                if(mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    hide_slide();
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                on_back_press_twice_to_exit = 0;
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//-------------------------------map load and hide it----------------------------------------------------------
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //  getSupportFragmentManager().beginTransaction().show(mapFragment).commit();
        hidemap();
        start_creat();

    }



    /*-------------------------------------------------oncreate end-----------------------------------------------*/
    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override public void onSuccess(AuthResult authResult) {
                Log.e("TAG", "sinInOK");
            }
        }) .addOnFailureListener(this, new OnFailureListener() {
            @Override public void onFailure(@NonNull Exception exception) {
                Log.e("TAG", "signInAnonymously:FAILURE", exception);
            }
        });
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
                    Place mPlace = messageSnapshot.getValue(Place.class);
                    formlist.add(mPlace);
                    filtered_house = formlist;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase","Server side database error");
            }
        });

    }

    //--------------------------nav method overload-----------------------------------

    @Override
    public void onBackPressed() {
        f = get_current_fragment();
        if(mLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN){
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            return;
        }
        if(mapFragment.getUserVisibleHint()){
            hidemap();
            set_title(f);
            return;
        }
        if(f instanceof House_detail){
            super.onBackPressed();
            set_title(f);
            return;
        }
        if(drawer.isDrawerOpen(GravityCompat.START)){
            on_back_press_twice_to_exit++;
            if(on_back_press_twice_to_exit == 2){
                on_back_press_twice_to_exit = 0;
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
            Toast.makeText(this,"Press again to exit",Toast.LENGTH_LONG).show();
        }else{
            drawer.openDrawer(GravityCompat.START);
        }
    }
    public void set_title(Fragment frag){
        if(frag instanceof HomeActivity){
            set_item_check(0);
            this.setTitle("Home");
            return;
        }
        if(frag instanceof House_list){
            set_item_check(1);
            this.setTitle("House list");
            return;
        }
        if(frag instanceof About){
            set_item_check(3);
            this.setTitle("About");
            return;
        }
        if(frag instanceof Eligible){
            set_item_check(4);
            this.setTitle("Eligible");
            return;
        }
        if(frag instanceof Application){
            set_item_check(5);
            this.setTitle("Application");
            return;
        }
        if(frag instanceof FAQ){
            set_item_check(6);
            this.setTitle("FAQ");
            return;
        }

        if(frag instanceof Filter){
            set_item_check(0);
            this.setTitle("Filter");
            return;
        }

        this.setTitle("BeneHome");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        Log.e("menu","open");
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_filter) {
            go_filter_by_check_list_map();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_Application_home) {
            show_pass(new HomeActivity(),filtered_house,null);
            hidemap();
        } else if (id == R.id.nav_houselist) {
            show_pass(new House_list(),filtered_house,null);
            hidemap();
            //setTitle("House List");
        } else if (id == R.id.nav_eligibility) {
            show_pass(new Eligible(), null,null);
            hidemap();
            //setTitle("Eligibility");
        } else if (id == R.id.nav_faq) {
            show_pass(new FAQ(),null,null);
            hidemap();
            //setTitle("FAQ");
        } else if (id == R.id.nav_about) {
            show_pass(new About(),null,null);
            hidemap();
            //setTitle("About");
        } else if (id == R.id.nav_map) {
            //mapFragment.getMapAsync(this);
            /*------------------markers---------------------------*/
            f = getSupportFragmentManager().findFragmentById(R.id.container);
            displaymap(filtered_house);
            if(f instanceof No_internet_Activity){
                start_creat();
            }
        } else if(id == R.id.nav_Application_guide){
           show_pass(new Application(),null,null);
           //setTitle("Application Guide");
           hidemap();
        } else if (id == R.id.nav_Application_home) {
            show_pass(new HomeActivity(),null,null);
            //this.setTitle("Home");
            hidemap();
        }
        hide_slide();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void set_item_check(int i){
        navigationView.getMenu().getItem(i).setChecked(true);
    }
    public void set_item_uncheck(int i){
        navigationView.getMenu().getItem(i).setChecked(false);
    }
    public void set_all_item_uncheck(){
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }
    //-------------------------------nav end----------------------------------
    public ArrayList<Place> getList() {
        return formlist;
    }
//------------------------------map method---------------------------------------------------
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.clear();
        show_neighborhood(mMap);

        /*------------Marker-------------------*/
        for (int i = 0; i < markers.size(); ++i) {
            LatLng temp = new LatLng(Double.parseDouble(markers.get(i).getY()),Double.parseDouble(markers.get(i).getX()));
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
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(!check_internet()){
                    getSupportFragmentManager().beginTransaction().
                            setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up,R.anim.pop_in,R.anim.pop_out).
                            replace(R.id.container, new No_internet_Activity()).
                            addToBackStack(null).
                            commitAllowingStateLoss();
                    hidemap();
                    return false;
                }
                Place selectHouse = new Place();
                // get selected house
                for (int j = 0; j < formlist.size(); ++j) {
                    if (formlist.get(j).getName().equals(marker.getTitle())) {
                        selectHouse = formlist.get(j);
                    }
                }
                show_slide(new House_detail(),selectHouse);
                return false;
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
        if(markers.size() > 1 || markers.size()==0){
            LatLng newWest = new LatLng(49.21073429331534, -122.92282036503556);
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(newWest, 13);
            mMap.animateCamera(location);
        }else{
            LatLng temp = new LatLng(Double.parseDouble(markers.get(0).getY()),Double.parseDouble(markers.get(0).getX()));
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(temp, 20);
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
    public void displaymap(ArrayList<Place> list){
        if(!check_internet()){
            getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up,R.anim.pop_in,R.anim.pop_out).
                    replace(R.id.container, new No_internet_Activity()).
                    addToBackStack(null).
                    commitAllowingStateLoss();
            return;
        }
        mapFragment.getMapAsync(this);
        setMarkers(list);
        mapFragment.setUserVisibleHint(true);
        getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.slide_in_up,R.anim.pop_out,R.anim.pop_in,R.anim.pop_out).
                show(mapFragment).commit();
        set_item_check(2);
        this.setTitle("Map");
    }
    public void pass_to_map(Place house){
       // mapFragment.getMapAsync(this);
        ArrayList<Place> temp = new ArrayList<>();
        temp.add(house);
        displaymap(temp);
    }
//-------------------------------map method end---------------------------------------------------

    public void set_filtered_house(ArrayList<Place> list){
        filtered_house = list;
    }
    public ArrayList<Place> get_filtered_house(){
        return filtered_house;
    }
    public void show_pass(Fragment fragment, ArrayList list, Place house){
        set_title(fragment);
        if(!check_internet() && (fragment instanceof House_list || fragment instanceof House_detail)){
            getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up,R.anim.pop_in,R.anim.pop_out).
                    replace(R.id.container, new No_internet_Activity()).
                    addToBackStack(null).
                    commitAllowingStateLoss();
            return;
        }
        Bundle data = new Bundle();
        data.putSerializable("data",list);
        data.putSerializable("all_house",formlist);
        data.putSerializable("house", house);
        fragment.setArguments(data);
        if(mapFragment.getUserVisibleHint()){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.container, fragment).
                    addToBackStack(null).
                    commitAllowingStateLoss();
        }else{
            getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up,R.anim.pop_in,R.anim.pop_out).
                    replace(R.id.container, fragment).
                    addToBackStack(null).
                    commitAllowingStateLoss();
        }
    }
    public void hide_slide(){
        if(mapFragment!=null){
            if(mapFragment.getUserVisibleHint() && mMap != null){
                mMap.setPadding(0,0,0,0);
            }
        }

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
        if(mapFragment!=null){
            if(mapFragment.getUserVisibleHint() && mMap != null){
                mMap.setPadding(0,0,0,138);
            }
        }
        TextView t = findViewById(R.id.name);
        t.setTypeface(t.getTypeface(), Typeface.BOLD);
        t.setText("House Detail");
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
    public boolean check_map_is_display_background(){
           return filter_on_map;
    }
    public void go_filter_by_check_list_map(){
        f = get_current_fragment();
        if(f instanceof Filter){
            if(mapFragment.getUserVisibleHint()){
                hidemap();
                filter_on_map = true;
            }
        }else{
            if(!mapFragment.getUserVisibleHint()){
                filter_on_map = false;
                this.setTitle("Filter");
                show_pass(new Filter(),null,null);
            }else{
                show_pass(new Filter(),null,null);
                this.setTitle("Filter");
                hidemap();
                filter_on_map = true;
            }
        }
        this.setTitle("Filter");
    }

    public void show_neighborhood(GoogleMap googleMap){
        HashMap<String,ArrayList<LatLng>> area = new HashMap<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(this));
            JSONArray jsonarray = obj.getJSONArray("features");
            Log.e(TAG,"json array"+jsonarray.length());
            for(int i = 0; i <jsonarray.length();i++){
                JSONObject obj_inside = jsonarray.getJSONObject(i);

                ArrayList<LatLng> x_y = new ArrayList<>();
                String Name = obj_inside.getJSONObject("properties").getString("NEIGH_NAME");

                JSONObject obj_area = obj_inside.getJSONObject("geometry");
                JSONArray coordinate_array = obj_area.getJSONArray("coordinates").getJSONArray(0);
                for(int n = 0; n<coordinate_array.length();n++){
                    double x = coordinate_array.getJSONArray(n).getDouble(1);
                    double y = coordinate_array.getJSONArray(n).getDouble(0);
                   x_y.add(new LatLng(y,x));

                }
                area.put(Name,x_y);
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
        PolylineOptions myoption = new PolylineOptions();
        myoption.clickable(true);
        for(Map.Entry<String,ArrayList<LatLng>> entry: area.entrySet()){

            PolygonOptions polygonOptions = new PolygonOptions();
           for(LatLng coordinate : entry.getValue()){
               polygonOptions.add(coordinate);
           }
           Polygon polyline = googleMap.addPolygon(polygonOptions);
           polyline.setStrokeColor(Color.rgb(244, 65, 128));
        }
    }

    public String loadJSONFromAsset(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("NEIGHBOURHOOD_BOUNDARIES.json");
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
    public Fragment getF() {
        f = getSupportFragmentManager().findFragmentById(R.id.container);
        return f;
    }

    public void start_creat(){
        loadFirebase();
        if(formlist.isEmpty())
            Log.wtf(TAG, "empty");
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            show_pass(new No_internet_Activity(),null,null);
        }else{
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
                    getSupportFragmentManager().beginTransaction().
                            setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up,R.anim.pop_in,R.anim.pop_out).
                            replace(R.id.container, new HomeActivity()).
                            addToBackStack(null).
                            commitAllowingStateLoss();
                }

            }).start();
            set_item_check(0);
        }
        setTitle("Home");
    }

    public boolean check_internet(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public Fragment get_current_fragment(){
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }
}
