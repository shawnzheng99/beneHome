package zhengc.bcit.ca.benehome;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class No_internet_Activity extends Fragment {

    private MainActivity mainActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
    }
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        /*--images retrieved from
            https://www.pinterest.ca/timoa/mobile-ui-errors
            https://dribbble.com/shots/2758771-No-Internet-Connection
            https://www.picmog.com/user/pavle_svilicic/1424227787/1246651768250274165_1424227787
        */
        View view = inflater.inflate(R.layout.activity_no_internet, container, false);
        mainActivity.setTitle("No Internet");
        mainActivity.set_all_item_uncheck();
        Button button = view.findViewById(R.id.reload);
        ImageView imageView = view.findViewById(R.id.reload_image);
        final int random = new Random().nextInt(3);
        switch (random){
            case 0:
                imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.nointernet0));
                break;
            case 1:
                imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.nointernet1));
                break;
            case 2:
                imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.nointernet2));
                break;
                default:
                    break;
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.start_creat();
            }
        });
        return view;
    }
}
