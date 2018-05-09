package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Filter extends Fragment implements View.OnClickListener {

    private Button btn_q1_1, btn_q1_2, btn_q1_3;
    private Button btn_q2_2, btn_q2_3, btn_q2_4;
    private Button btn_q3_1, btn_q3_2, btn_q3_3;
    private Button btn_q4_1, btn_q4_2, btn_q4_3;
    private Button btn_next;
    final private int color_unselected = 0xFF72C5CA;
    final private int color_selected = 0xFFAABD58;
    final private String keyword0 = "empty";
    final private String keyword_no = "no";
    final private String keyword1_1 = "families with children";
    final private String keyword2_1 = "seniors";
    final private String keyword2_2 = "(65+)";
    final private String keyword3_1 = "couples";
    final private String keyword3_2 = "singles";
    final private String keyword4_1 = "disabilities";
    private String keywords[] = new String[4];

    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_filter,null);

        init();

        return view;
    }

    private void init() {
        btn_q1_1 = view.findViewById(R.id.btn_q1_1);
        btn_q1_2 = view.findViewById(R.id.btn_q1_2);
        btn_q1_3 = view.findViewById(R.id.btn_q1_3);
        btn_q2_2 = view.findViewById(R.id.btn_q2_2);
        btn_q2_3 = view.findViewById(R.id.btn_q2_3);
        btn_q2_4 = view.findViewById(R.id.btn_q2_4);
        btn_q3_1 = view.findViewById(R.id.btn_q3_1);
        btn_q3_2 = view.findViewById(R.id.btn_q3_2);
        btn_q3_3 = view.findViewById(R.id.btn_q3_3);
        btn_q4_1 = view.findViewById(R.id.btn_q4_1);
        btn_q4_2 = view.findViewById(R.id.btn_q4_2);
        btn_q4_3 = view.findViewById(R.id.btn_q4_3);
        btn_next = view.findViewById(R.id.btn_next);
        for (int i = 0; i < 4; i++) {
            keywords[i] = keyword0;
        }
        btn_q1_1.setOnClickListener(this);
        btn_q1_2.setOnClickListener(this);
        btn_q1_3.setOnClickListener(this);
        btn_q2_2.setOnClickListener(this);
        btn_q2_3.setOnClickListener(this);
        btn_q2_4.setOnClickListener(this);
        btn_q3_1.setOnClickListener(this);
        btn_q3_2.setOnClickListener(this);
        btn_q3_3.setOnClickListener(this);
        btn_q4_1.setOnClickListener(this);
        btn_q4_2.setOnClickListener(this);
        btn_q4_3.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_q1_1:
                btn_q1_1.setBackgroundColor(color_selected);
                btn_q1_2.setBackgroundColor(color_unselected);
                btn_q1_3.setBackgroundColor(color_unselected);
                keywords[0] = keyword1_1;
                break;
            case R.id.btn_q1_2:
                btn_q1_1.setBackgroundColor(color_unselected);
                btn_q1_2.setBackgroundColor(color_selected);
                btn_q1_3.setBackgroundColor(color_unselected);
                keywords[0] = keyword0;
                break;
            case R.id.btn_q1_3:
                btn_q1_1.setBackgroundColor(color_unselected);
                btn_q1_2.setBackgroundColor(color_unselected);
                btn_q1_3.setBackgroundColor(color_selected);
                keywords[0] = keyword_no;
                break;
            case R.id.btn_q2_2:
                btn_q2_2.setBackgroundColor(color_selected);
                btn_q2_3.setBackgroundColor(color_unselected);
                btn_q2_4.setBackgroundColor(color_unselected);
                keywords[1] = keyword_no;
                break;
            case R.id.btn_q2_3:
                btn_q2_2.setBackgroundColor(color_unselected);
                btn_q2_3.setBackgroundColor(color_selected);
                btn_q2_4.setBackgroundColor(color_unselected);
                keywords[1] = keyword2_1;
                break;
            case R.id.btn_q2_4:
                btn_q2_2.setBackgroundColor(color_unselected);
                btn_q2_3.setBackgroundColor(color_unselected);
                btn_q2_4.setBackgroundColor(color_selected);
                keywords[1] = keyword2_2;
                break;
            case R.id.btn_q3_1:
                btn_q3_1.setBackgroundColor(color_selected);
                btn_q3_2.setBackgroundColor(color_unselected);
                btn_q3_3.setBackgroundColor(color_unselected);
                keywords[2] = keyword3_1;
                break;
            case R.id.btn_q3_2:
                btn_q3_1.setBackgroundColor(color_unselected);
                btn_q3_2.setBackgroundColor(color_selected);
                btn_q3_3.setBackgroundColor(color_unselected);
                keywords[2] = keyword0;
                break;
            case R.id.btn_q3_3:
                btn_q3_1.setBackgroundColor(color_unselected);
                btn_q3_2.setBackgroundColor(color_unselected);
                btn_q3_3.setBackgroundColor(color_selected);
                keywords[2] = keyword3_2;
                break;
            case R.id.btn_q4_1:
                btn_q4_1.setBackgroundColor(color_selected);
                btn_q4_2.setBackgroundColor(color_unselected);
                btn_q4_3.setBackgroundColor(color_unselected);
                keywords[3] = keyword4_1;
                break;
            case R.id.btn_q4_2:
                btn_q4_1.setBackgroundColor(color_unselected);
                btn_q4_2.setBackgroundColor(color_selected);
                btn_q4_3.setBackgroundColor(color_unselected);
                keywords[3] = keyword0;
                break;
            case R.id.btn_q4_3:
                btn_q4_1.setBackgroundColor(color_unselected);
                btn_q4_2.setBackgroundColor(color_unselected);
                btn_q4_3.setBackgroundColor(color_selected);
                keywords[3] = keyword_no;
                break;
            case R.id.btn_next:
                Fragment fragment = new House_list();
                Bundle data = new Bundle();
                data.putStringArray("keywords",keywords);
                fragment.setArguments(data);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss();
            default:
                break;
        }
    }
}
