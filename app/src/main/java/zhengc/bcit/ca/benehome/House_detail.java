package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class House_detail extends AppCompatActivity {
    private static final String TAG = House_detail.class.getName();
    HashMap<String, String> selectedHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hose_detail);

        // set up
        setselectedHouse();
        setName();
        setLocation();
        setEligible();
        setHouseType();

        /*contact*/
        callHouse();
        sendEmail();
    }

    private void setselectedHouse() {
        selectedHouse = (HashMap<String, String>) getIntent().getSerializableExtra("house");
    }


    private void sendEmail() {
        Button email = findViewById(R.id.btn_email);

        final String address = selectedHouse.get("Email");
        final String subject = "Booking an appointment for visit house";
        final String body = "Hi there, I'm inserted "
                + selectedHouse.get("Name")
                + ". \nCan we have an appointment on \n\n\n"
                + " Thank you";
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedHouse.get("Email").equals("")) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(Intent.EXTRA_TEXT, body);
                    startActivity(Intent.createChooser(intent, ""));
                } else {
                    Toast.makeText(House_detail.this
                            , "Email address for this house is not provided."
                            , Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

    }

    private void callHouse() {
        Button call = findViewById(R.id.btn_call);
        final String phone = selectedHouse.get("Phone");
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedHouse.get("Phone").equals("")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel: " + phone));
                    startActivity(intent);
                } else {
                    Toast.makeText(House_detail.this
                            , "Phone number for this house is not provided."
                            , Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

    }

    private void setHouseType() {
        TextView houseType = findViewById(R.id.txt_HousingTypeContent);
        houseType.setText(selectedHouse.get("Description"));
    }

    private void setEligible() {
        TextView eliType = findViewById(R.id.txt_EligibleType);
        eliType.setText("family, older people");
    }

    private void setName() {
        TextView txtName = findViewById(R.id.txtTitle_HoseName);
        txtName.setText(selectedHouse.get("Name"));
    }

    private void setLocation() {
        TextView txtLocation = findViewById(R.id.txt_location_detail);
        txtLocation.setText(selectedHouse.get("Location"));
    }

}
