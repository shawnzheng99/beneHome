package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import java.io.File;
import java.io.IOException;

public class House_detail extends AppCompatActivity {
    Place selectedHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hose_detail);

        // set up
        setselectedHouse();
        setPic();
        setName();
        setLocation();
        setEligible();
        setHouseType();

        /*contact*/
        callHouse();
        sendEmail();
        setApply();
    }

    private void setApply() {
        Button apply = findViewById(R.id.btn_applyNow);
        final Uri uri = Uri.parse(selectedHouse.getWeb());
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void setPic() {
        ImageView imageView = findViewById(R.id.img_house);
        imageView.setImageBitmap(selectedHouse.getPic());

//        String houseName = selectedHouse.getName().toLowerCase();
//        houseName = houseName.replaceAll(" ", "");
//        houseName = houseName.replaceAll("-", "");
//        houseName = houseName.replaceAll("'", "");
//
//        Log.i("---------House name:", houseName);
//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//
//        StorageReference storageReference = storage
//                .getReferenceFromUrl("gs://benehome-f1049.appspot.com/")
//                .child(houseName + ".jpg");
//
//        try {
//            final File localFile = File.createTempFile("imag", "jpg");
//            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    ImageView imageView = findViewById(R.id.img_house);
//                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                    imageView.setImageBitmap(bitmap);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    Toast.makeText(House_detail.this
//                            , "Please check your internet connection."
//                            , Toast.LENGTH_LONG
//                    ).show();
//                }
//            });
//        } catch (IOException e ) {
//            Log.e("Bao Cuo!", "can not find pic");
//        }

    }

    private void setselectedHouse() {
        selectedHouse = (Place) getIntent().getSerializableExtra("house");
    }

    private void sendEmail() {
        Button email = findViewById(R.id.btn_email);

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
        final String phone = selectedHouse.getPhone();
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedHouse.getPhone().equals("")) {
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
        int idx = selectedHouse.getDesc().indexOf(".");
        String des = selectedHouse.getDesc().substring(0, idx + 1).toLowerCase();
        houseType.setText(des);
    }

    private void setEligible() {
        TextView eliType = findViewById(R.id.txt_EligibleType);
        int idx = selectedHouse.getDesc().indexOf("ousing for");
        String houseFor = selectedHouse.getDesc().substring(idx + 11);

        eliType.setText(houseFor);
    }

    private void setName() {
        TextView txtName = findViewById(R.id.txtTitle_HoseName);
        txtName.setText(selectedHouse.getName());
    }

    private void setLocation() {
        TextView txtLocation = findViewById(R.id.txt_location_detail);
        txtLocation.setText(selectedHouse.getLocation());
    }

}
