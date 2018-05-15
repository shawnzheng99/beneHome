package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class House_detail extends Fragment {
    Place selectedHouse;
    View view;
    private MainActivity mainActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_hose_detail,null);
        // set up
        selectedHouse = (Place) getArguments().getSerializable("house");
        ImageView imageView = view.findViewById(R.id.img_house);

        //TODO: add to loop in the image switcher, image index a,b,c
        Picasso.get().load(selectedHouse.getUrl().get("c")).fit().centerCrop().into(imageView);

        setName();
        setLocation();
        setEligible();
        setHouseType();
        setTotalUnit();
        setUnitType();
        setPet();
        setSchool();
        setBoundary();

        /*contact*/
        callHouse();
        sendEmail();
        setApply();

        return view;
    }

    private void setBoundary() {
        TextView boundary = view.findViewById(R.id.dis_boundary);
        boundary.setText(selectedHouse.getBoundaries());
    }

    private void setSchool() {
        TextView ele = view.findViewById(R.id.dis_school_ele);
        TextView sec = view.findViewById(R.id.dis_school_sec);
        TextView mid = view.findViewById(R.id.dis_school_mid);

        ele.setText("Elementary school : " + selectedHouse.getElementary());
        mid.setText("Middle school: " + selectedHouse.getMiddle());
        sec.setText("Secondary School: " + selectedHouse.getSecondary());
    }

    private void setPet() {
        TextView pet = view.findViewById(R.id.dis_pet);
        pet.setText(selectedHouse.getPets());
    }

    private void setUnitType() {
        TextView unitType = view.findViewById(R.id.dis_unitType);
        unitType.setText(selectedHouse.getTypeUnits());
    }

    private void setTotalUnit() {
        TextView totalUnit = view.findViewById(R.id.dis_totalUnit);
        totalUnit.setText(selectedHouse.getTotalUnit());
    }

    private void setApply() {
        Button apply = view.findViewById(R.id.btn_applyNow);
        final Uri uri = Uri.parse(selectedHouse.getWebsite());
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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

    private void setHouseType() {
        TextView houseType = view.findViewById(R.id.txt_HousingTypeContent);
        int idx = selectedHouse.getDescription().indexOf(".");
        String des = selectedHouse.getDescription().substring(0, idx + 1).toLowerCase();
        houseType.setText(des);
    }

    private void setEligible() {
        TextView eliType = view.findViewById(R.id.txt_EligibleType);
        String houseFor = selectedHouse.getEligible();

        eliType.setText(houseFor);
    }

    private void setName() {
        TextView txtName = view.findViewById(R.id.txtTitle_HoseName);
        txtName.setText(selectedHouse.getName());
    }

    private void setLocation() {
        TextView txtLocation = view.findViewById(R.id.txt_location_detail);
        txtLocation.setText(selectedHouse.getLocation());
    }

}
