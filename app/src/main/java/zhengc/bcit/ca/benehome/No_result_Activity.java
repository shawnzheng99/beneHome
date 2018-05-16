package zhengc.bcit.ca.benehome;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class No_result_Activity extends Fragment {

    View view;
    MainActivity mainActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        /*--images retrieved from
            https://dribbble.com/shots/2936116-No-Result-Found
            https://www.illustrationui.com/portfolio/items/dropbox-empty-state/
        */
        view = inflater.inflate(R.layout.activity_no_result_, null);
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
