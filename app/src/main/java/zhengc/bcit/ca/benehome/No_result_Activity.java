package zhengc.bcit.ca.benehome;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;

public class No_result_Activity extends Fragment {

    private MainActivity mainActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
    }
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_no_result_, container, false);
        mainActivity.set_all_item_uncheck();
        ImageView imageView = view.findViewById(R.id.no_result_image);
        final int random = new Random().nextInt(2);
        switch (random){
            case 0:
                imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.noresult));
                break;
            case 1:
                imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.noresult1));
                break;
            default:
                break;
        }

        return view;
    }
}
