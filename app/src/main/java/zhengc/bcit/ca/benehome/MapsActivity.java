package zhengc.bcit.ca.benehome;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<LatLng> markers;
    ArrayList<HashMap<String,String>> house;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*--------------set up house list---------*/
        setHouse();
        /*------------------markers---------------------------*/
        setMarkers();
    }

    private void setHouse() {
        house = MainActivity.getList();
    }

    public void setMarkers(){
        markers = new ArrayList<>();
        for(int i = 0; i < house.size(); ++i){
            double y = Double.parseDouble(house.get(i).get("lon"));
            double x = Double.parseDouble(house.get(i).get("lat"));
            markers.add(new LatLng(x ,y));
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        zoomToNewWest();

        /*------------Marker-------------------*/
        for(int i = 0; i < markers.size(); ++i){
            mMap.addMarker(new MarkerOptions()
                    .position(markers.get(i))
                    .title(house.get(i).get("Name"))

            );

        }
        /*---------------marker listener---------*/
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapsActivity.this, House_detail.class);
                HashMap<String,String> selectHouse = new HashMap<>();

                // get selected house
                for(int j = 0; j < house.size();++j) {
                    if(house.get(j).get("Name").equals(marker.getTitle())){
                        selectHouse = house.get(j);
                    }
                }
                intent.putExtra("house", selectHouse);
                startActivity(intent);
            }
        });

    }

    public void zoomToNewWest(){
        LatLng newWest = new LatLng(49.21073429331534,-122.92282036503556);
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(newWest,13);
        mMap.animateCamera((location));
    }


}//class ends
