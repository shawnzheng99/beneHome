package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.FragmentTransaction;


public class Application extends Fragment {
    Button btnOnlineApply;
    Button btnOtherApply;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_application,null);
        btnOnlineApply = view.findViewById(R.id.online_Apply_button);
        btnOtherApply = view.findViewById(R.id.other_Apply_button);

        btnOnlineApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url ="https://housingapplication.bchousing.org";
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browser);
            }
        });


        btnOtherApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new DocumentList()).commitAllowingStateLoss();
            }
        });
        return view;
    }
}
