package zhengc.bcit.ca.benehome;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class HomeActivity extends Fragment implements View.OnClickListener {

    private MainActivity mainActivity;
    private static final String TAG = House_detail.class.getName();

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, null);
        mainActivity = (MainActivity) getActivity();
        ImageView image_logo = view.findViewById(R.id.imageView_logo);
        ImageButton btn_about = view.findViewById(R.id.btn_about);
        ImageButton btn_eligible = view.findViewById(R.id.btn_eligible);
        ImageButton btn_find_house = view.findViewById(R.id.btn_find_house);
        ImageButton btn_application = view.findViewById(R.id.btn_application);
        ImageButton btn_faq = view.findViewById(R.id.btn_faq);
        btn_about.setOnClickListener(this);
        btn_eligible.setOnClickListener(this);
        btn_find_house.setOnClickListener(this);
        btn_application.setOnClickListener(this);
        btn_faq.setOnClickListener(this);

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

        RelativeLayout.LayoutParams lp;
        if ((double) height / (double) width <= 13.0 / 9) {
            image_logo.setVisibility(View.INVISIBLE);
        } else if ((double) height / (double) width <= 14.0 / 9) {
            lp = new RelativeLayout.LayoutParams(width / 9 * 2, width / 9);
            lp.setMargins(width / 18 * 7, 20, width / 18 * 7, 0);
            image_logo.setLayoutParams(lp);
        } else if ((double) height / (double) width <= 15.0 / 9) {
            lp = new RelativeLayout.LayoutParams(width / 9 * 4, width / 9 * 2);
            lp.setMargins(width / 18 * 5, 50, width / 18 * 5, 0);
            image_logo.setLayoutParams(lp);
        } else if ((double) height / (double) width <= 16.0 / 9) {
            lp = new RelativeLayout.LayoutParams(width / 3 * 2, width / 3);
            lp.setMargins(width / 6, 80, width / 6, 0);
            image_logo.setLayoutParams(lp);
        } else {
            lp = new RelativeLayout.LayoutParams(width / 9 * 8, width / 9 * 4);
            lp.setMargins(width / 18, 100, width / 18, 0);
            image_logo.setLayoutParams(lp);
        }


        LinearLayout.LayoutParams lp1;
        LinearLayout.LayoutParams lp2;
        LinearLayout.LayoutParams lp3;
        LinearLayout.LayoutParams lp4;

        lp1 = new LinearLayout.LayoutParams(width / 3 - 30, width / 3 - 20);
        lp1.setMargins(20, 10, 10, 10);
        btn_about.setLayoutParams(lp1);
        btn_eligible.setLayoutParams(lp1);

        lp2 = new LinearLayout.LayoutParams(width / 3 * 2 - 30, width / 3 * 2 - 20);
        lp2.setMargins(10, 10, 20, 10);
        btn_find_house.setLayoutParams(lp2);

        lp3 = new LinearLayout.LayoutParams(width / 2 - 30, width / 2 - 30);
        lp3.setMargins(20, 10, 10, 20);
        btn_application.setLayoutParams(lp3);

        lp4 = new LinearLayout.LayoutParams(width / 2 - 30, width / 2 - 30);
        lp4.setMargins(10, 10, 20, 20);
        btn_faq.setLayoutParams(lp4);

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_about:
                mainActivity.show_pass(new About(), null, null);
                break;
            case R.id.btn_eligible:
                mainActivity.show_pass(new Eligible(), null, null);
                break;
            case R.id.btn_find_house:
                mainActivity.show_pass(new Filter(), null, null);
                break;
            case R.id.btn_application:
                mainActivity.show_pass(new Application(), null, null);
                break;
            case R.id.btn_faq:
                mainActivity.show_pass(new FAQ(), null, null);
                break;
            default:
                break;
        }
    }
}
