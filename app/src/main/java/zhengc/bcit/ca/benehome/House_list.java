package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class House_list extends Fragment {

    ArrayList<HashMap<String, String>> formlist;
    ArrayList<HashMap<String, String>> filtered_house = new ArrayList<>();
    private static final String TAG = House_list.class.getName();
    private String[] keywords;
    private int keywords_size = 0;
    View view;
    private MainActivity mainActivity;

    // recycleView
    private RecyclerView recyclerView;
    private List<Place> placeList = new ArrayList<>();
    private RVAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set up house arrayList from josn
        mainActivity = (MainActivity) getActivity();





//        ActionBar bar =  getSupportActionBar();
//        bar.setDisplayShowCustomEnabled(true);
//        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_toolbar, null);
//        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
//        ImageButton mapBtn = (ImageButton)findViewById(R.id.mapbtn);
//        mapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, MapsActivity.class);
//                startActivity(i);
//            }
//        });

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_house_list,null);
        // set house list
        formlist = (ArrayList<HashMap<String,String>>)getArguments().getSerializable("data");
        // filter by keywords
//        if (get_keywords()) {
//            for (HashMap<String, String> h : formlist) {
//                if (cheak_have_keyword(h)) {
//                    filtered_house.add(h);
//                }
//            }
//        }
//        mainActivity.set_filtered_house(filtered_house);
//        setList();


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        initPlaceData();
        adapter = new RVAdapter(placeList, mainActivity,formlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
//            inputStream = mainActivity.getAssets().open("file:///android_asset/data.json");
            inputStream = getResources().openRawResource(R.raw.park);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }

    private void initPlaceData() {
//        try {
//            String jsonDataString = readJsonDataFromFile();
//            JSONArray menuItemsJsonArray = new JSONArray(jsonDataString);
//            for (int i = 0; i < menuItemsJsonArray.length(); ++i) {
//                JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(i);
//                String locationName = menuItemObject.getString("name");
//                String locationImg = menuItemObject.getString("imgId");
//                int locationImgId = getResources().getIdentifier(locationImg,"mipmap",mainActivity.getPackageName());
//                String desc = menuItemObject.getString("address");
//
//                Place p = new Place(locationName,locationImgId, desc);
//                placeList.add(p);

            for (int i = 0; i < formlist.size(); ++i) {

                String locationName = formlist.get(i).get("Name");
//                String locationImg = menuItemObject.getString("imgId");
//                int locationImgId = getResources().getIdentifier(locationImg,"mipmap",mainActivity.getPackageName());
                String desc = formlist.get(i).get("Description");
//
                Place p = new Place("",R.drawable.slide3, locationName);
                placeList.add(p);
            }

//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    public ArrayList<HashMap<String, String>> getHouseList() {
//        return filtered_house;
//    }

//    private boolean cheak_have_keyword(HashMap<String, String> ahouse) {
//        for (int i = 0; i < keywords_size; i++) {
//            if (ahouse.get("Description").contains(keywords[i])) {
//                return true;
//            }
//        }
//        return ahouse.get("Description").contains("all household types");
//    }

//    private void setList() {
//        final ArrayList<String> houseName = new ArrayList<>();
//        for (int i = 0; i < formlist.size(); ++i) {
//            houseName.add(formlist.get(i).get("Name"));
//        }

//        ListAdapter lst = new ArrayAdapter<>(
//                getActivity(),
//                android.R.layout.simple_list_item_1,//list styles
//                houseName);
//        ListView lstView = view.findViewById(R.id.lst_result_listing);
//        lstView.setAdapter(lst);
//        lstView.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Intent intent = new Intent(getActivity(), House_detail.class);
//                        HashMap<String, String> selectHouse = new HashMap<>();

                        // get selected house

//                        for (int j = 0; j < houseName.size(); ++j) {
//                            if (formlist.get(i).get("Name").equals(adapterView.getItemAtPosition(i))) {
//                                selectHouse = formlist.get(i);
//                            }
//                        }
//                        intent.putExtra("house", selectHouse);
//                        //startActivity(intent);
//                        mainActivity.show_slide(selectHouse);
//                    }
//                }
//        );
//    }


    private boolean get_keywords() {
        keywords = getActivity().getIntent().getStringArrayExtra("keys");
        keywords_size = getActivity().getIntent().getIntExtra("key_size", 0);
        return keywords_size != 0;
    }
}
