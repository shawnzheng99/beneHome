package zhengc.bcit.ca.benehome;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends Fragment {
    private MapView mMappview;
    private GoogleMap mMap;
    private View view;
    private MainActivity mainActivity;
    private ArrayList<Place> markers;
    private ArrayList<Place> formlist;
    private ArrayList<Place> filtered_house;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        markers = mainActivity.getMarkers();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_map,container,false);
        mMappview = view.findViewById(R.id.mapView);
        mMappview.onCreate(savedInstanceState);
        mMappview.onResume();
        filtered_house = (ArrayList<Place>)getArguments().getSerializable("data");
        formlist = (ArrayList<Place>)getArguments().getSerializable("all_house");
        try{
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }catch (Exception e){
            e.printStackTrace();
        }

        mMappview.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                mMap = googleMap;
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.clear();
                mainActivity.show_neighborhood(mMap);

                /*------------Marker-------------------*/
                for (int i = 0; i < markers.size(); ++i) {
                    LatLng temp = new LatLng(Double.parseDouble(markers.get(i).getY()), Double.parseDouble(markers.get(i).getX()));
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
                        mainActivity.show_slide(new House_detail(), selectHouse);
                    }
                });
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (!mainActivity.check_internet()) {
                            mainActivity.getSupportFragmentManager().beginTransaction().
                                    setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up, R.anim.pop_in, R.anim.pop_out).
                                    replace(R.id.container, new No_internet_Activity()).
                                    addToBackStack(null).
                                    commitAllowingStateLoss();
                            return false;
                        }
                        Place selectHouse = new Place();
                        // get selected house
                        for (int j = 0; j < formlist.size(); ++j) {
                            if (formlist.get(j).getName().equals(marker.getTitle())) {
                                selectHouse = formlist.get(j);
                            }
                        }
                        mainActivity.show_slide(new House_detail(), selectHouse);
                        return false;
                    }
                });
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        mainActivity.hide_slide();
                    }
                });
                mMap.getUiSettings().setZoomGesturesEnabled(true);
                zoomToMarker(markers);
            }
        });
        return view;
    }

    public void zoomToMarker(ArrayList<Place> markers) {
        if(markers.size() > 1 || markers.size()==0){
            LatLng newWest = new LatLng(49.21073429331534, -122.92282036503556);
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(newWest, 13);
            mMap.animateCamera(location);
        }else{
            LatLng temp = new LatLng(Double.parseDouble(markers.get(0).getY()),Double.parseDouble(markers.get(0).getX()));
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(temp, 17);
            mMap.animateCamera(location);
        }

    }
    public void onResume(){
        super.onResume();
        mMappview.onResume();
    }
    public void onPause(){
        super.onPause();
        mMappview.onPause();
    }
    public void onDestory(){
        super.onDestroy();
        mMappview.onDestroy();
    }
    public void onLowMemory(){
        super.onLowMemory();
        mMappview.onLowMemory();
    }
}
