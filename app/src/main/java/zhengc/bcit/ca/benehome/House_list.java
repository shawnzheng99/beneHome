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

    ArrayList<HashMap<String, String>> formlist;
    ArrayList<HashMap<String, String>> filtered_house = new ArrayList<>();
    private static final String TAG = House_list.class.getName();
    private String[] keywords;
    private int keywords_size = 0;
    View view;
    private MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set up house arrayList from josn
        mainActivity = (MainActivity) getActivity();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_house_list,null);
        // set house list
        formlist = (ArrayList<HashMap<String,String>>)getArguments().getSerializable("data");
        // filter by keywords
        if (get_keywords()) {
            for (HashMap<String, String> h : formlist) {
                if (cheak_have_keyword(h)) {
                    filtered_house.add(h);
                }
            }
        }
        mainActivity.set_filtered_house(filtered_house);
        setList();
        return view;
    }

//    public ArrayList<HashMap<String, String>> getHouseList() {
//        return filtered_house;
//    }

    private boolean cheak_have_keyword(HashMap<String, String> ahouse) {
        for (int i = 0; i < keywords_size; i++) {
            if (ahouse.get("Description").contains(keywords[i])) {
                return true;
            }
        }
        return ahouse.get("Description").contains("all household types");
    }

    private void setList() {
        final ArrayList<String> houseName = new ArrayList<>();
        for (int i = 0; i < formlist.size(); ++i) {
            houseName.add(formlist.get(i).get("Name"));
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
                        for (int j = 0; j < houseName.size(); ++j) {
                            if (formlist.get(i).get("Name").equals(adapterView.getItemAtPosition(i))) {
                                selectHouse = formlist.get(i);
                            }
                        }
                        intent.putExtra("house", selectHouse);
                        //startActivity(intent);
                        mainActivity.show_slide(selectHouse);
                    }
                }
        );
    }

    private boolean get_keywords() {
        keywords = getActivity().getIntent().getStringArrayExtra("keys");
        keywords_size = getActivity().getIntent().getIntExtra("key_size", 0);
        return keywords_size != 0;
    }
}
