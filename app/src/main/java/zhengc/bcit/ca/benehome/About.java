package zhengc.bcit.ca.benehome;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class About extends Fragment {
    private View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_about,null);

        TextView about = view.findViewById(R.id.textView_about);
        about.setText(Html.fromHtml("<p>Subsidized housing is long-term housing for people who permanently reside in British Columbia. Rental fees are calculated on a rent geared to income basis (30% of household total gross income, subject to minimum rent based on # of people).</p>\n" +
                "        <p><strong>You may be eligible</strong> for subsidized housing if you can live independently and meet the household, residency, income and assets criteria.&nbsp;</p>\n" +
                "        <p>See our Glossary for help with definitions.</p>\n" +
                "        <p><strong>Downloads</strong></p>\n" +
                "        <ul><li>\n" +
                "            <a href=\"https://www.bchousing.org/publications/Housing-Registry-Application-Form.pdf\">The Housing Registry Application Form</a>\n" +
                "        </li><li>\n" +
                "            <a href=\"https://www.bchousing.org/publications/Housing-Registry-Supplemental-Application-Form.pdf\">The Housing Registry Supplemental Application Form</a>\n" +
                "        </li><li>\n" +
                "            <a href=\"https://www.bchousing.org/publications/Consent-for-Personal-Information.doc\">Authorization to Exchange Personal Information</a>\n" +
                "        </li><li>\n" +
                "            <a href=\"https://www.bchousing.org/publications/Applying-for-Subsidized-Housing-English.pdf\">How to Apply For Subsidized Housing</a>\n" +
                "            &nbsp;|&nbsp;\n" +
                "            <a href=\"https://www.bchousing.org/publications/Chinese-How-Apply-Subsidized.pdf\">Chinese</a>\n" +
                "            &nbsp;|&nbsp;\n" +
                "            <a href=\"https://www.bchousing.org/publications/French-How-To-Apply.pdf\">French</a>\n" +
                "            &nbsp;|&nbsp;\n" +
                "            <a href=\"https://www.bchousing.org/publications/Punjabi-How-Apply-Subsidized.pdf\">Punjabi</a>\n" +
                "            &nbsp;|&nbsp;\n" +
                "            <a href=\"https://www.bchousing.org/publications/Spanish-How-Apply-Subsidized.pdf\">Spanish</a>\n" +
                "            &nbsp;|&nbsp;\n" +
                "            <a href=\"https://www.bchousing.org/publications/Vietnamese-How-Apply-Subsidized.pdf\">Vietnamese</a>\n" +
                "        </li></ul>"));
        about.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }
}
