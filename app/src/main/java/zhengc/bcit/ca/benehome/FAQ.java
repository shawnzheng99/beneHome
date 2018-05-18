package zhengc.bcit.ca.benehome;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FAQ extends Fragment {

    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_faq,container,false);

        final ExpandableListView listView = view.findViewById(R.id.lvExp);
        initData();
        ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listHash);
        listView.setAdapter(listAdapter);

        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    listView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        return view;
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add(getString(R.string.faq_list_1));
        listDataHeader.add(getString(R.string.faq_list_2));
        listDataHeader.add(getString(R.string.faq_list_3));
        listDataHeader.add(getString(R.string.faq_list_4));
        listDataHeader.add(getString(R.string.faq_list_5));
        listDataHeader.add(getString(R.string.faq_list_6));
        listDataHeader.add(getString(R.string.faq_list_7));


        List<String> faq1 = new ArrayList<>();
        faq1.add(getString(R.string.faq_list_1_1));
        List<String> faq2 = new ArrayList<>();
        faq2.add(getString(R.string.faq_list_2_1));

        List<String> faq3 = new ArrayList<>();
        faq3.add(getString(R.string.faq_list_3_1));

        List<String> faq4 = new ArrayList<>();
        faq4.add(getString(R.string.faq_list_4_1));

        List<String> faq5 = new ArrayList<>();
        faq5.add(getString(R.string.faq_list_5_1));

        List<String> faq6 = new ArrayList<>();
        faq6.add(getString(R.string.faq_list_6_1));

        List<String> faq7 = new ArrayList<>();
        faq7.add(getString(R.string.faq_list_7_1));

        listHash.put(listDataHeader.get(0), faq1);
        listHash.put(listDataHeader.get(1), faq2);
        listHash.put(listDataHeader.get(2), faq3);
        listHash.put(listDataHeader.get(3), faq4);
        listHash.put(listDataHeader.get(4), faq5);
        listHash.put(listDataHeader.get(5), faq6);
        listHash.put(listDataHeader.get(6), faq7);

    }
}
