package zhengc.bcit.ca.benehome;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;


public class House_list extends Fragment{
    private MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        RecyclerView recyclerView;
        RVAdapter adapter;
        View view = inflater.inflate(R.layout.activity_house_list, container, false);
        // set house list
        ArrayList<Place> filtered_house = mainActivity.get_filtered_house();
        recyclerView = view.findViewById(R.id.recyclerView);

        adapter = new RVAdapter(mainActivity, filtered_house);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;

    }
}
