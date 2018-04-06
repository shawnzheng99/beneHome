package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class House_detail extends AppCompatActivity {
    private static final String TAG = House_detail.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hose_detail);

        setName();
        setLocation();
        setEligible();
        setHouseType();

        /*contact*/
        callHouse();
        sendEmail();

    }

    private void sendEmail() {
        Button email = findViewById(R.id.btn_email);
        final String address = "example@gmail.com";
        final String subject = "Booking an appointment for visit house";
        final String body = "Hi there, I'm inserted in this house. Can we have an appointment on \n"
                + " Thank you";
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {address});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intent, ""));
            }
        });
    }

    private void callHouse() {
        Button call = findViewById(R.id.btn_call);
        final String phone = "6046046022";
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: " + phone));
                startActivity(intent);
            }
        });
    }

    private void setHouseType() {
        TextView houseType = findViewById(R.id.txt_HousingTypeContent);
        houseType.setText("This is house type content, 3 bedroom etc.");
    }

    private void setEligible() {
        TextView eliType = findViewById(R.id.txt_EligibleType);
        eliType.setText("family, older people");
    }

    private void setName() {
        TextView txtName = findViewById(R.id.txtTitle_HoseName);
        txtName.setText("viewHeight");
    }
    private void setLocation(){
        TextView txtLocation = findViewById(R.id.txt_location_detail);
        txtLocation.setText("123, new westminister");
    }

}
