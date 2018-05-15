package zhengc.bcit.ca.benehome;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EmptyActivity extends Fragment {
    ArrayList<Place> filtered_house = new ArrayList<>();
    View view;
    MainActivity mainActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_empty,null);
        TextView error = view.findViewById(R.id.error);
        filtered_house = (ArrayList<Place>)getArguments().getSerializable("data");
        error.setText("Please check you internet connection");
        if(filtered_house == null || filtered_house.size() == 0){
            error.setText("No Result Found\nPlease Change your filter options");
        }
        mainActivity.setTitle("Error");
        mainActivity.set_item_uncheck(0);
        return view;
    }
}
