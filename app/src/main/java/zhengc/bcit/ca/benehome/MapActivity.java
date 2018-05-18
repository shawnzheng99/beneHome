//package zhengc.bcit.ca.benehome;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.SearchView;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class MapActivity extends Fragment implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback {
//    ArrayList<HashMap<String,String>> formlist;
//    ArrayAdapter<String> adapter;
//    SearchView searchView;
//    RadioGroup rGroup;
//
//
//    //    GoogleMap map;
//    int id;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.activity_map);
//
//        //Intent intent = getIntent();
//        formlist = (ArrayList<HashMap<String,String>>)intent.getSerializableExtra("jsondata");
//        //id = intent.getIntExtra("id",2);
//
//        Fragment mapFragment =  getFragmentManager()
//                .findFragmentById(R.id.container);
//        mapFragment.getMapAsync(this);
//
//    }
//
//
//    @Override
//    public void onMapReady(GoogleMap map) {
//        final LatLng nw = new LatLng(49.2066015,-122.9105347);
////        map = map;
//        map.addMarker(new MarkerOptions()
//                .position(nw)
//                .title("New Westminster")
//                .snippet("Beautiful city.")
//                .alpha(0.7f)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//
//        map.getUiSettings().setZoomGesturesEnabled(true);
//
//        for (HashMap<String, String> i : formlist) {
//            if (Integer.parseInt(i.get("Category")) == id) {
//                LatLng nwI = new LatLng(Double.parseDouble(i.get("lat")), Double.parseDouble(i.get("lon")));
//                map.addMarker(new MarkerOptions()
//                        .position(nwI)
//                        .title(i.get("Name"))
//                        .snippet("")
//                        .alpha(0.7f)
//                        .icon(BitmapDescriptorFactory.defaultMarker((id == 1 ?  BitmapDescriptorFactory.HUE_AZURE : BitmapDescriptorFactory.HUE_RED ))));
//            }
//        }
//
//
//
//
//        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        map.setOnInfoWindowClickListener(this);
//
//        final GoogleMap mMap = map;
//
//
//
//
//
///*-------------------------------------search bar---------------------------------*/
//        final ListView lv = (ListView)findViewById(R.id.search_list);
//        searchView = (SearchView)findViewById(R.id.search_View);
//        searchView.bringToFront();
//        searchView.getBackground().setAlpha(155);
//
//        lv.setVisibility(View.INVISIBLE);
//        final ArrayList<String> arrayBuilds = new ArrayList<>();
//        //arrayBuilds.addAll(Arrays.asList(getResources().getStringArray(R.array.array_builds)));
//        for (HashMap<String, String> i : formlist) {
//            arrayBuilds.add(i.get("Name"));
//        }
//
//        adapter = new ArrayAdapter<String>(MapActivity.this, android.R.layout.simple_list_item_1,arrayBuilds);
//
//        lv.setAdapter(adapter);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                lv.setVisibility(View.VISIBLE);
//                lv.bringToFront();
//                return false;
//            }
//        });
//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchView.setIconified(false);
//            }
//        });
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                searchView.setQuery("",false);
//                lv.setVisibility(View.INVISIBLE);
//                searchView.clearFocus();
//                return false;
//            }
//        });
//        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean queryTextFocused) {
//                lv.setVisibility(View.INVISIBLE);
//            }
//        });
//
//
//        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                lv.setVisibility(View.INVISIBLE);
//                searchView.clearFocus();
//            }
//        });
//
//
//
//
//
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String result = adapter.getItem(position);
//                searchView.setQuery(result,false);
//                lv.setVisibility(View.INVISIBLE);
//                searchView.clearFocus();
//                mMap.clear();
//                for (HashMap<String, String> i : formlist) {
//                    if (i.get("Name").equalsIgnoreCase(result)) {
//                        LatLng nwI = new LatLng(Double.parseDouble(i.get("lat")), Double.parseDouble(i.get("lon")));
//                        int temp_id = Integer.parseInt(i.get("Category"));
//                        mMap.addMarker(new MarkerOptions()
//                                .position(nwI)
//                                .title(result)
//                                .snippet("")
//                                .alpha(0.7f)
//                                .icon(BitmapDescriptorFactory.defaultMarker((temp_id == 1 ? BitmapDescriptorFactory.HUE_AZURE : BitmapDescriptorFactory.HUE_RED))));
//
//
//                    }
//                }
//
//            }
//        });
///*----------------------------------------------search bar end--------------------------------------------*/
///*--------------------------RaridGroup-------------------------------------------------------*/
//        RadioGroup rGroup = (RadioGroup) findViewById(R.id.radioSex);
//        RadioButton radio1 = (RadioButton) findViewById(R.id.radioMale);
//        RadioButton radio2 = (RadioButton) findViewById(R.id.radioFemale);
//        if (id == 0) {
//            radio1.setChecked(true);
//        } else {
//            radio2.setChecked(true);
//        }
//        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                mMap.clear();
//                if (checkedId == R.id.radioFemale) {
//
//                    for (HashMap<String, String> i : formlist) {
//                        if (Integer.parseInt(i.get("Category")) == 1) {
//                            LatLng nwI = new LatLng(Double.parseDouble(i.get("lat")), Double.parseDouble(i.get("lon")));
//                            mMap.addMarker(new MarkerOptions()
//                                    .position(nwI)
//                                    .title(i.get("Name"))
//                                    .snippet("")
//                                    .alpha(0.7f)
//                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                        }
//                    }
//
//                } else  if (checkedId == R.id.radioMale) {
//
//                    for (HashMap<String, String> i : formlist) {
//                        if (Integer.parseInt(i.get("Category")) == 0) {
//                            LatLng nwI = new LatLng(Double.parseDouble(i.get("lat")), Double.parseDouble(i.get("lon")));
//                            mMap.addMarker(new MarkerOptions()
//                                    .position(nwI)
//                                    .title(i.get("Name"))
//                                    .snippet("")
//                                    .alpha(0.7f)
//                                    .icon(BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_RED)));
//                        }
//                    }
//                }
//
//            }
//        });
//
//    }
///*--------------------------RaridGroup----END-------------------------------------------*/
//
//    @Override
//    public void onInfoWindowClick(Marker marker) {
//        Intent i = new Intent(this, ScrollingActivity.class);
//        for (HashMap<String, String> j : formlist) {
//            if (marker.getTitle().equalsIgnoreCase(j.get("Name"))) {
//                i.putExtra("Name", j.get("Name"));
//                i.putExtra("Description", j.get("Description"));
//                i.putExtra("Category", j.get("Category"));
//                i.putExtra("Hours", j.get("Hours"));
//                i.putExtra("Location", j.get("Location"));
//                i.putExtra("PC", j.get("PC"));
//                i.putExtra("Phone", j.get("Phone"));
//                i.putExtra("Email", j.get("Email"));
//                i.putExtra("Website", j.get("Website"));
//            }
//        }
//        startActivity(i);
//    }
//
//
//}