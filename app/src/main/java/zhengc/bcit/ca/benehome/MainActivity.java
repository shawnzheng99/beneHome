package zhengc.bcit.ca.benehome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

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
        implements NavigationView.OnNavigationItemSelectedListener, Serializable {

    private static final String TAG = MainActivity.class.getName();
    private GoogleMap mMap;
    private SlidingUpPanelLayout mLayout;
    private NavigationView navigationView;
    private ArrayList<Place> filtered_house;
    private ArrayList<Place> formlist;
    Place selectHouse;
    /*firebase*/
    private DatabaseReference databaseReference;
    private ImageButton imageButton;
    //auth
    private FirebaseAuth mAuth;
    private DrawerLayout drawer;
    private Fragment f;
    private boolean filter_on_map = false;
    private int on_back_press_twice_to_exit = 0;

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
            ed.apply();
            //ed.apply();   Compared to commit(), apply() won't change the file synchronously.

        }

        //firebase auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            signInAnonymously();
        }

        filtered_house = new ArrayList<>();
        formlist = new ArrayList<>();
        imageButton = findViewById(R.id.up_down_button);
        drawer = findViewById(R.id.drawer_layout);
        f = get_current_fragment();
        /*--------initilazing firebase-----------*/

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
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        start_creat();
    }


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


    @Override
    public void onBackPressed() {
        f = get_current_fragment();
        if(mLayout.getPanelState() != SlidingUpPanelLayout.PanelState.HIDDEN){
            hide_slide();
            return;
        }
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if(f instanceof House_list || f instanceof MapsActivity){
            if(drawer.isDrawerOpen(GravityCompat.START)){
                drawer.closeDrawer(GravityCompat.START);
            }else{
                drawer.openDrawer(GravityCompat.START);
            }
            return;
        }
        if(!(f instanceof HomeActivity)){
            super.onBackPressed();
            set_title(get_current_fragment());
        }else{
            on_back_press_twice_to_exit++;
            if(on_back_press_twice_to_exit == 2){
                on_back_press_twice_to_exit = 0;
                moveTaskToBack(true);
            }
            Toast.makeText(this,"Press again to exit",Toast.LENGTH_LONG).show();
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
            this.setTitle("House List");
            return;
        }
        if(frag instanceof MapsActivity){
            set_item_check(2);
            this.setTitle("Map");
            return;
        }
        if(frag instanceof About){
            set_item_check(3);
            this.setTitle("About");
            return;
        }
        if(frag instanceof Eligible){
            set_item_check(4);
            this.setTitle("Eligibility");
            return;
        }
        if(frag instanceof Application){
            set_item_check(5);
            this.setTitle("Application Guide");
            return;
        }
        if(frag instanceof FAQ){
            set_item_check(6);
            this.setTitle("FAQ");
            return;
        }

        if(frag instanceof Filter){
            set_all_item_uncheck();
            this.setTitle("Filter");
            return;
        }
        if(frag instanceof Document){
            this.setTitle("Document Checklist");
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
        hide_slide();

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
            show_fragment(new HomeActivity());
        } else if (id == R.id.nav_houselist) {
            if(filtered_house.size() == 0){
                show_fragment(new No_result_Activity());
            }else{
                show_fragment(new House_list());
            }

        } else if (id == R.id.nav_eligibility) {
            show_fragment(new Eligible());
        } else if (id == R.id.nav_faq) {
            show_fragment(new FAQ());
        } else if (id == R.id.nav_about) {
            show_fragment(new About());
        } else if (id == R.id.nav_map) {
           show_fragment(new MapsActivity());
        } else if(id == R.id.nav_Application_guide){
           show_fragment(new Application());
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void set_item_check(int i){
        navigationView.getMenu().getItem(i).setChecked(true);
    }
    public void set_all_item_uncheck(){
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    public ArrayList<Place> getList() {
        return formlist;
    }

    public void pass_to_map(Place house){
        hide_slide();
        ArrayList<Place> temp = new ArrayList<>();
        temp.add(house);
        set_filtered_house(temp);
        show_fragment(new MapsActivity());
    }

    public void set_filtered_house(ArrayList<Place> list){
        filtered_house = list;
    }
    public ArrayList<Place> get_filtered_house(){
        return filtered_house;
    }

    public void show_fragment(Fragment fragment){
        on_back_press_twice_to_exit = 0;
        set_title(fragment);
        if(check_internet() && (fragment instanceof House_list || fragment instanceof House_detail)){
            getSupportFragmentManager().beginTransaction().
                    setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up,R.anim.pop_in,R.anim.pop_out).
                    replace(R.id.container, new No_internet_Activity()).
                    addToBackStack(null).
                    commitAllowingStateLoss();
            return;
        }

        filter_on_map = fragment instanceof MapsActivity;
        getSupportFragmentManager().beginTransaction().
                setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up,R.anim.pop_in,R.anim.pop_out).
                replace(R.id.container, fragment).
                addToBackStack(null).
                commitAllowingStateLoss();    
        
    }

    public void hide_slide(){
        if( mMap != null){
            mMap.setPadding(0,0,0,0);
        }
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }
    public void show_slide(Fragment fragment){
        if(mMap != null){
            mMap.setPadding(0,0,0,200);
        }
        TextView t = findViewById(R.id.name);
        t.setTypeface(t.getTypeface(), Typeface.BOLD);
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
        if (!(f instanceof Filter)) {
            this.setTitle("Filter");
            show_fragment(new Filter());
            filter_on_map = f instanceof MapsActivity;
        }
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
            show_fragment(new No_internet_Activity());
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
        return activeNetwork == null ||
                !activeNetwork.isConnectedOrConnecting();
    }

    public Fragment get_current_fragment(){
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }

    public void show_neighborhood(GoogleMap googleMap){
        HashMap<String,ArrayList<LatLng>> area = new HashMap<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(this));
            JSONArray jsonarray = obj.getJSONArray("features");
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
            if(is.read(buffer)==-1){
                is.close();
            }
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public Place getSelectHouse() {
        return selectHouse;
    }

    public void setSelectHouse(Place selectHouse) {
        this.selectHouse = selectHouse;
    }

    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }
}
