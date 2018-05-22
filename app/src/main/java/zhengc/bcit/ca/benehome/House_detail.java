package zhengc.bcit.ca.benehome;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class House_detail extends Fragment {
    private Place selectedHouse;
    private View view;
    private MainActivity mainActivity;
    private GoogleMap mMap;
    private LinearLayout linearLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }
    @SuppressLint({"ClickableViewAccessibility", "InflateParams"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_hose_detail,container,false);
        mMap = mainActivity.getmMap();
        String as;
        selectedHouse = mainActivity.getSelectHouse();
        linearLayout = view.findViewById(R.id.linearLayout_points);
        ViewPager sliderLayout = view.findViewById(R.id.mygallery);
        TextView tv = view.findViewById(R.id.house_detail);
        ArrayList<String> al = new ArrayList<>(selectedHouse.getUrl().values());

        DetailImageAdapter dia = new DetailImageAdapter(mainActivity, al);
        sliderLayout.setAdapter(dia);
        sliderLayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * display effect between the three.
             * @param position    the current page index
             */
            @Override
            public void onPageSelected(int position) {
                switchPoint(position);
            }
            /**
             * screen
             * @param state   0（END）,1(PRESS) , 2(UP) 。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < selectedHouse.getUrl().size(); i++) {
            ImageView imageView = new ImageView(mainActivity);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(35,35);
            llp.leftMargin = 20;
            imageView.setLayoutParams(llp);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(R.drawable.circle_unselected);
            linearLayout.addView(imageView);
        }
        switchPoint(0);
        if (selectedHouse.getApply().equalsIgnoreCase("Apply to The Housing Registry")) {
            as = "<a href=\"https://housingapplication.bchousing.org\">Apply to the housing registry</a><br><br>\n";
        } else {
            as = "Apply to housing provider.";
        }

        TextView houseName = view.findViewById(R.id.house_name);
        houseName.setText(Html.fromHtml("<h1>" + selectedHouse.getName() + "</h1>" ));

        TextView add = view.findViewById(R.id.address);
        add.setText(Html.fromHtml("<P><h2>Address</h2>\n" +
                selectedHouse.getLocation()+"\n" +
                "<br>New Westminster, BC"));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mainActivity.get_current_fragment() instanceof MapsActivity){
                    mMap.clear();
                    mainActivity.show_neighborhood(mMap);
                    mainActivity.hide_slide();
                    LatLng temp = new LatLng(Double.parseDouble(selectedHouse.getY()), Double.parseDouble(selectedHouse.getX()));
                    mMap.addMarker(new MarkerOptions()
                            .position(temp)
                            .title(selectedHouse.getName())
                    );
                    LatLng temp_location = new LatLng(Double.parseDouble(selectedHouse.getY()),Double.parseDouble(selectedHouse.getX()));
                    CameraUpdate location = CameraUpdateFactory.newLatLngZoom(temp_location, 15);
                    mMap.animateCamera(location);
                }else{
                    mainActivity.pass_to_map(selectedHouse);
                }
            }
        });

        tv.setText(Html.fromHtml(
                selectedHouse.getPC() +
                "<br><br><h2>Phone</h2>" +
                selectedHouse.getPhone() +
                "<br><br><h2>How to apply?</h2>\n" +
                as +
                "<br><h2>Eligible clents</h2>\n" +
                selectedHouse.getEligible() +
                "<br><br><h2>Nearby school</h2>\n" +
                selectedHouse.getElementary() +
                "<br>" +
                selectedHouse.getMiddle() +
                "<br>" +
                selectedHouse.getSecondary()+
                "<br><br><h2>Bedroom Type</h2>\n" +
                selectedHouse.getTypeUnits() +
                "<br> Total Units: " +
                selectedHouse.getTotalUnit()+
                "<br><br><h2>Neighbourhood</h2>\n" +
                selectedHouse.getBoundaries()+
                "<br><br><h2>Policy of Pets</h2>\n" +
                selectedHouse.getPets() +
                "<br></P>"));

        tv.setMovementMethod(LinkMovementMethod.getInstance());


        /*contact*/
        callHouse();
        sendEmail();
        setApply();

        return view;
    }

    private void setApply() {
        Button apply = view.findViewById(R.id.btn_applyNow);
        final Uri uri = Uri.parse("https://housingapplication.bchousing.org");
        final AlertDialog.Builder alertChk = new AlertDialog.Builder(getContext());
            apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedHouse.getApply().equalsIgnoreCase("Apply to The Housing Registry")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }else{

            alertChk.setTitle("This house cannot be applied online")
                    .setMessage("For more information, please contact house provider")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // do nothing
                }
            });
            AlertDialog alertDialog = alertChk.create();
            alertDialog.show();
                }

            }
        });
    }

    private void sendEmail() {
        Button email = view.findViewById(R.id.btn_email);

        final String address = selectedHouse.getEmail();
        final String subject = "Booking an appointment for visit house";
        final String body = "Hi there, I'm inserted "
                + selectedHouse.getName()
                + ". \nCan we have an appointment on \n\n\n"
                + " Thank you";
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedHouse.getEmail().equals("")) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(Intent.EXTRA_TEXT, body);
                    startActivity(Intent.createChooser(intent, ""));
                } else {
                    Toast.makeText(getActivity()
                            , "Email address for this house is not provided."
                            , Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

    }

    private void callHouse() {
        Button call = view.findViewById(R.id.btn_call);
        final String phone = selectedHouse.getPhone();
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedHouse.getPhone().equals("")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel: " + phone));
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity()
                            , "Phone number for this house is not provided."
                            , Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

    }

    private void switchPoint(int position) {
        ImageView iv;
        for (int i = 0; i < selectedHouse.getUrl().size(); i++) {
            iv = (ImageView) linearLayout.getChildAt(i);
            if (i == position) {
                iv.setImageResource(R.drawable.circle_selected);
            } else {
                iv.setImageResource(R.drawable.circle_unselected);
            }
        }

    }
}


