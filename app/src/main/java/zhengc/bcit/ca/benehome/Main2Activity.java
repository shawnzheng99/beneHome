package zhengc.bcit.ca.benehome;


import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Main2Activity extends Fragment implements View.OnClickListener{

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//    }
    private MainActivity mainActivity;
    private static final String TAG = House_detail.class.getName();
    private ImageView image_logo;
    private ImageButton btn_about;
    private ImageButton btn_eligible;
    private ImageButton btn_find_house;
    private Button btn_application;
    private Button btn_faq;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_main2,null);
        mainActivity = (MainActivity)getActivity();
        image_logo = view.findViewById(R.id.imageView_logo);
        btn_about = view.findViewById(R.id.btn_about);
        btn_eligible = view.findViewById(R.id.btn_eligible);
        btn_find_house = view.findViewById(R.id.btn_find_house);
        btn_application = view.findViewById(R.id.btn_application);
        btn_faq = view.findViewById(R.id.btn_faq);
        btn_about.setOnClickListener(this);
        btn_eligible.setOnClickListener(this);
        btn_find_house.setOnClickListener(this);
        btn_application.setOnClickListener(this);
        btn_faq.setOnClickListener(this);

//        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
//        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
//
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());

//        LinearLayout layout = view.findViewById(R.id.page_layout);
//        layout.measure(0,0);
//        int width = layout.getMeasuredWidth();
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
//        int height = layout.getMeasuredHeight();


//        image_logo.setMaxHeight(height - width / 6 * 5);
//        btn_about.setMaxWidth(width/3);
//        btn_about.setMaxHeight(width/3);
//        btn_eligible.setMaxWidth(width/3);
//        btn_eligible.setMaxHeight(width/3);
//        btn_find_house.setMaxWidth(width / 3 * 2);
//        btn_find_house.setMaxHeight(width / 3 * 2);
//        btn_application.setMaxHeight(width / 3 / 2);
//        btn_faq.setMaxHeight(width / 3 / 2);
//
//        image_logo.setMinimumHeight(height - width / 6 * 5);
//        btn_about.setMinimumWidth(width/3);
//        btn_about.setMinimumHeight(width/3);
//        btn_eligible.setMinimumWidth(width/3);
//        btn_eligible.setMinimumHeight(width/3);
//        btn_find_house.setMinimumWidth(width / 3 * 2);
//        btn_find_house.setMinimumHeight(width / 3 * 2);
//        btn_application.setMinimumHeight(width / 3 / 2);
//        btn_faq.setMinimumHeight(width / 3 / 2);

//        image_logo.setLayoutParams(new LinearLayout.LayoutParams(width, height - width / 6 * 5 - (int)px));
        btn_about.setLayoutParams(new LinearLayout.LayoutParams(width / 3, width / 3));
        btn_eligible.setLayoutParams(new LinearLayout.LayoutParams(width / 3, width / 3));
        btn_find_house.setLayoutParams(new LinearLayout.LayoutParams(width / 3 * 2, width / 3 * 2));
        btn_application.getLayoutParams().height = width / 6;
        btn_application.setLayoutParams(btn_application.getLayoutParams());
        btn_faq.getLayoutParams().height = width / 6;
        btn_faq.setLayoutParams(btn_faq.getLayoutParams());

        return view;
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_about:
                mainActivity.show_pass(new About(),null,null);
                mainActivity.setTitle("About");
                mainActivity.set_item_check(1);
                break;
            case R.id.btn_eligible:
                mainActivity.show_pass(new Eligible(),null,null);
                mainActivity.setTitle("Eligibility");
                mainActivity.set_item_check(1);
                break;
            case R.id.btn_find_house:
                mainActivity.show_pass(new Filter(),null,null);
                mainActivity.setTitle("Filter");
                mainActivity.set_item_check(1);
                break;
            case R.id.btn_application:
                mainActivity.show_pass(new Application(),null,null);
                mainActivity.setTitle("Application");
                mainActivity.set_item_check(1);
                break;
            case R.id.btn_faq:
                mainActivity.show_pass(new FAQ(),null,null);
                mainActivity.setTitle("FAQ");
                mainActivity.set_item_check(1);
                break;
            default:
                break;
        }
    }
}
