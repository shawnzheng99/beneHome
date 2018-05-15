package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class Filter extends Fragment implements View.OnClickListener {

    private MainActivity mainActivity;
    private Button btn_1_1, btn_1_2, btn_1_3, btn_1_4, btn_1_5;
    private Button btn_2_1, btn_2_2, btn_2_3, btn_2_4;
    private Button btn_3_1, btn_3_2, btn_3_3, btn_3_4;
    private Button btn_next;
    private boolean filter1_flag[] = {false, false, false, false, false};
    private String filter1_keyword[] = {"Families", "Seniors", "disabilities", "Single", "Couples"};
    private boolean filter2_flag[] = {false, false, false, false};
    private String filter2_keyword[] = {"Studio", "1 bedroom", "2 bedrooms", "3 bedrooms", "4 bedrooms"};
    private boolean filter3_flag[] = {false, false, false, false};
//    private String filter3_keyword[] = {"", "1 bedroom", "2 bedrooms", "3 bedrooms", "4 bedrooms"};
    final private int color_unselected = 0xFF72C5CA;
    final private int color_selected = 0xFFAABD58;

    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_filter,null);
        mainActivity = (MainActivity)getActivity();
        init();

        return view;
    }

    private void init() {
        btn_1_1 = view.findViewById(R.id.btn_1_1);
        btn_1_2 = view.findViewById(R.id.btn_1_2);
        btn_1_3 = view.findViewById(R.id.btn_1_3);
        btn_1_4 = view.findViewById(R.id.btn_1_4);
        btn_1_5 = view.findViewById(R.id.btn_1_5);
        btn_2_1 = view.findViewById(R.id.btn_2_1);
        btn_2_2 = view.findViewById(R.id.btn_2_2);
        btn_2_3 = view.findViewById(R.id.btn_2_3);
        btn_2_4 = view.findViewById(R.id.btn_2_4);
        btn_3_1 = view.findViewById(R.id.btn_3_1);
        btn_3_2 = view.findViewById(R.id.btn_3_2);
        btn_3_3 = view.findViewById(R.id.btn_3_3);
        btn_3_4 = view.findViewById(R.id.btn_3_4);
        btn_next = view.findViewById(R.id.btn_next);
        btn_1_1.setOnClickListener(this);
        btn_1_2.setOnClickListener(this);
        btn_1_3.setOnClickListener(this);
        btn_1_4.setOnClickListener(this);
        btn_1_5.setOnClickListener(this);
        btn_2_1.setOnClickListener(this);
        btn_2_2.setOnClickListener(this);
        btn_2_3.setOnClickListener(this);
        btn_2_4.setOnClickListener(this);
        btn_3_1.setOnClickListener(this);
        btn_3_2.setOnClickListener(this);
        btn_3_3.setOnClickListener(this);
        btn_3_4.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1_1:
                if (filter1_flag[0]) {
                    btn_1_1.setBackgroundColor(color_unselected);
                    filter1_flag[0] = false;
                } else {
                    btn_1_1.setBackgroundColor(color_selected);
                    filter1_flag[0] = true;
                }
                break;
            case R.id.btn_1_2:
                if (filter1_flag[1]) {
                    btn_1_2.setBackgroundColor(color_unselected);
                    filter1_flag[1] = false;
                } else {
                    btn_1_2.setBackgroundColor(color_selected);
                    filter1_flag[1] = true;
                }
                break;
            case R.id.btn_1_3:
                if (filter1_flag[2]) {
                    btn_1_3.setBackgroundColor(color_unselected);
                    filter1_flag[2] = false;
                } else {
                    btn_1_3.setBackgroundColor(color_selected);
                    filter1_flag[2] = true;
                }
                break;
            case R.id.btn_1_4:
                if (filter1_flag[3]) {
                    btn_1_4.setBackgroundColor(color_unselected);
                    filter1_flag[3] = false;
                } else {
                    btn_1_4.setBackgroundColor(color_selected);
                    filter1_flag[3] = true;
                }
                break;
            case R.id.btn_1_5:
                if (filter1_flag[4]) {
                    btn_1_5.setBackgroundColor(color_unselected);
                    filter1_flag[4] = false;
                } else {
                    btn_1_5.setBackgroundColor(color_selected);
                    filter1_flag[4] = true;
                }
                break;
            case R.id.btn_2_1:
                if (filter2_flag[0]) {
                    btn_2_1.setBackgroundColor(color_unselected);
                    filter2_flag[0] = false;
                } else {
                    btn_2_1.setBackgroundColor(color_selected);
                    filter2_flag[0] = true;
                }
                break;
            case R.id.btn_2_2:
                if (filter2_flag[1]) {
                    btn_2_2.setBackgroundColor(color_unselected);
                    filter2_flag[1] = false;
                } else {
                    btn_2_2.setBackgroundColor(color_selected);
                    filter2_flag[1] = true;
                }
                break;
            case R.id.btn_2_3:
                if (filter2_flag[2]) {
                    btn_2_3.setBackgroundColor(color_unselected);
                    filter2_flag[2] = false;
                } else {
                    btn_2_3.setBackgroundColor(color_selected);
                    filter2_flag[2] = true;
                }
                break;
            case R.id.btn_2_4:
                if (filter2_flag[3]) {
                    btn_2_4.setBackgroundColor(color_unselected);
                    filter2_flag[3] = false;
                } else {
                    btn_2_4.setBackgroundColor(color_selected);
                    filter2_flag[3] = true;
                }
                break;
            case R.id.btn_3_1:
                if (filter3_flag[0]) {
                    btn_3_1.setBackgroundColor(color_unselected);
                    filter3_flag[0] = false;
                } else {
                    btn_3_1.setBackgroundColor(color_selected);
                    filter3_flag[0] = true;
                }
                break;
            case R.id.btn_3_2:
                if (filter3_flag[1]) {
                    btn_3_2.setBackgroundColor(color_unselected);
                    filter3_flag[1] = false;
                } else {
                    btn_3_2.setBackgroundColor(color_selected);
                    filter3_flag[1] = true;
                }
                break;
            case R.id.btn_3_3:
                if (filter3_flag[2]) {
                    btn_3_3.setBackgroundColor(color_unselected);
                    filter3_flag[2] = false;
                } else {
                    btn_3_3.setBackgroundColor(color_selected);
                    filter3_flag[2] = true;
                }
                break;
            case R.id.btn_3_4:
                if (filter3_flag[3]) {
                    btn_3_4.setBackgroundColor(color_unselected);
                    filter3_flag[3] = false;
                } else {
                    btn_3_4.setBackgroundColor(color_selected);
                    filter3_flag[3] = true;
                }
                break;
            case R.id.btn_next:
                ArrayList<Place> alllist = mainActivity.getList();
                ArrayList<Place> list = new ArrayList<Place>();
                for (Place place : alllist) {
                    boolean suitable1 = true;
                    boolean suitable2 = false;
                    for (int i = 0; i < filter1_flag.length; i++) {
                        if (filter1_flag[i]) {
                            if (! place.getEligible().contains(filter1_keyword[i])) {
                                suitable1 = false;
                                continue;
                            }
                        }
                    }
                    if (! (filter2_flag[0] || filter2_flag[0] || filter2_flag[0] || filter2_flag[0])) {
                        suitable2 = true;
                    }
                    for (int i = 0; i < filter2_flag.length; i++) {
                        if (filter2_flag[i]) {
                            if (place.getTypeUnits().contains(filter2_keyword[i])) {
                                suitable2 = true;
                                continue;
                            }
                        }
                    }
                    if (filter2_flag[3]) {
                        if (place.getTypeUnits().contains(filter2_keyword[4])) {
                            suitable2 = true;
                        }
                    }
                    if (suitable1 && suitable2) {
                        list.add(place);
                    }
                }
                mainActivity.set_filtered_house(list);
                if(mainActivity.check_map_is_display_background()){
                    mainActivity.displaymap(list);
                    mainActivity.set_item_check(2);
                }else{
                    mainActivity.show_pass(new House_list(),list,null);
                    mainActivity.setTitle("House list");
                    mainActivity.set_item_check(1);
                }
                break;
            default:
                break;
        }
    }
}
