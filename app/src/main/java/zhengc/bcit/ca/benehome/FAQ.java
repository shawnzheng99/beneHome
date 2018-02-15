package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;

public class FAQ extends AppCompatActivity {

    private ImageView btnBack;
    private static final String TAG = House_detail.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        btnBack = (ImageView) findViewById(R.id.btn_back_faq);

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.wtf(TAG,"enter btnBack onclick on house detail page");
                Intent intent = new Intent();
                intent.setClass(FAQ.this,MainMenu.class);
                startActivity(intent);
                Log.wtf(TAG,"exit btnBack onClick on house detail page");
            }
        });
        WebView webview = (WebView) findViewById(R.id.webview_about);
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl("https://www.bchousing.org/housing-assistance/rental-housing/subsidized-housing");
    }
}
