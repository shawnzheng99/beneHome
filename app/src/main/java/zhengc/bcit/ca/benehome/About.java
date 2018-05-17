package zhengc.bcit.ca.benehome;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class About extends Fragment {

    private static final String TAG = House_detail.class.getName();
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_about,null);

        WebView webview = view.findViewById(R.id.textView2);
        webview.loadUrl("file:///android_asset/about.html");

        return view;
    }
}
