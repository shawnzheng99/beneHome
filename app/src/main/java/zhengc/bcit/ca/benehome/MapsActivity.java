package zhengc.bcit.ca.benehome;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<LatLng> markers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        /*------------------markers---------------------------*/
        setMarkers();
    }
    public void setMarkers(){
        double x = 49.2009387;
        double y = -122.93059118539031;
        markers = new ArrayList<>();
        for(int i =0; i < 10; ++i){
            LatLng house = new LatLng(x += 0.01,y-=0.001);
            markers.add(house);
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

        LatLng house = new LatLng( 49.2009387, -122.93059118539031);
        mMap.addMarker(new MarkerOptions()
                .position(house)
                .title("New Westminister")
        );
        zoomToNewWest();

        /*------------Marker-------------------*/
        for(int i =0; i < markers.size(); ++i){
            mMap.addMarker(new MarkerOptions()
                    .position(markers.get(i))
                    .title("markers array list " + i)

            );
        }
        /*---------------marker listener---------*/
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapsActivity.this, House_detail.class);
                startActivity(intent);
            }
        });
    }

    public void zoomToNewWest(){
        LatLng newWest = new LatLng(49.2009387,-122.9116244);
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(newWest,13);
        mMap.animateCamera((location));
    }


}//class ends
