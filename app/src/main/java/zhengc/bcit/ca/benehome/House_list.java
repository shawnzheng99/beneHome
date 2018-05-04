package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;

public class House_list extends Fragment {

    static ArrayList<HashMap<String, String>> house;
    String[] houseName;
    private static final String TAG = House_list.class.getName();
    private String[] keywords;
    private int keywords_size = 0;
    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_house_list,null);
        // set up house arrayList from josn
        setHouse();

        // filter by keywords
        if (get_keywords()) {
            ArrayList<HashMap<String, String>> filtered_house = new ArrayList<>();
            for (HashMap<String, String> h : house) {
                if (cheak_have_keyword(h)) {
                    filtered_house.add(h);
                }
            }
            house = filtered_house;
            houseName = new String[house.size()];
        }
        // set house list
        setList();
        return view;
    }

    public static ArrayList<HashMap<String, String>> getHouseList() {
        return house;
    }

    private boolean cheak_have_keyword(HashMap<String, String> ahouse) {
        for (int i = 0; i < keywords_size; i++) {
            if (ahouse.get("Description").contains(keywords[i])) {
                return true;
            }
        }
        return ahouse.get("Description").contains("all household types");
    }

    private void setList() {
        for (int i = 0; i < house.size(); ++i) {
            houseName[i] = house.get(i).get("Name");
        }

        ListAdapter lst = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,//list styles
                houseName);
        ListView lstView = view.findViewById(R.id.lst_result_listing);
        lstView.setAdapter(lst);
        lstView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getActivity(), House_detail.class);
                        HashMap<String, String> selectHouse = new HashMap<>();

                        // get selected house
                        for (int j = 0; j < house.size(); ++j) {
                            if (house.get(i).get("Name").equals(adapterView.getItemAtPosition(i))) {
                                selectHouse = house.get(i);
                            }
                        }
                        intent.putExtra("house", selectHouse);
                        startActivity(intent);
                    }
                }
        );
    }

    private void setHouse() {
        house = MainActivity.formlist;
        //house = MainActivity.getList();
        houseName = new String[house.size()];
    }

    private boolean get_keywords() {
        keywords = getActivity().getIntent().getStringArrayExtra("keys");
        keywords_size = getActivity().getIntent().getIntExtra("key_size", 0);
        return keywords_size != 0;
    }
}
