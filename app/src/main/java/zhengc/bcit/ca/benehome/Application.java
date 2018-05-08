package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Application extends Fragment {
    Button btnOnlineApply;
    Button btnOtherApply;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_eligible,null);
        btnOnlineApply = view.findViewById(R.id.onlineApply);
        btnOtherApply = view.findViewById(R.id.otherApply);

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
               // Intent documentCheck = new Intent(ApplicationGuidePage.this, DocumentCheckList.class);
              //  startActivity(documentCheck);
            }
        });
        return view;
    }
}
