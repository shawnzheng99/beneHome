package zhengc.bcit.ca.benehome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class House_detail extends Fragment {
    Place selectedHouse;
    View view;
    private MainActivity mainActivity;

    ImageSwitcher switcher;
    ImageView imageView;
    float initialX;
    private Cursor cursor;
    private  int columnIndex, position = 0;
    private int currentPosition = 0;
    private float downX;


    int [] images = {R.drawable.slide1,R.drawable.slide2,R.drawable.slide3};

    ArrayList<String> fillin = new ArrayList<String>();

    ListView lv;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }
    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_hose_detail,null);
        // set up

        //TODO: HashMap<String,String> hm = selectedHouse.getUrl();
        //      hm.get("a");.. b, c

        selectedHouse = (Place) getArguments().getSerializable("house");

        switcher = view.findViewById(R.id.imageSwitcher);

        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(mainActivity);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                imageView.setImageURI(Uri.parse(selectedHouse.getUrl().get("a")))

                return imageView;
            }
        });



//        switcher.setImageURI(Uri.parse(selectedHouse.getUrl().get("a")));//initialize the first view of the image switcher

        switcher.setImageResource(images[0]);



        switcher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        //finger press on

                        downX = event.getX();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        float lastX = event.getX();

                        //swipe right or left
                        if (lastX > downX) {
                            switcher.setInAnimation(AnimationUtils.loadAnimation(mainActivity, android.R.anim.slide_in_left));//set in animition
                            switcher.setOutAnimation(AnimationUtils.loadAnimation(mainActivity, android.R.anim.slide_out_right));//set out animition


                                if (currentPosition > 0) {
                                    currentPosition--;
//                                switcher.setImageURI(Uri.parse(selectedHouse.getUrl()));
                                switcher.setImageResource(images[currentPosition % images.length]);

                                } else {
                                    currentPosition = 2;
//                                switcher.setImageURI(Uri.parse(selectedHouse.getUrl()));
                                switcher.setImageResource(images[currentPosition % images.length]);
//                                Toast.makeText(mainActivity, "first page", Toast.LENGTH_SHORT).show();
                                }
                            }

                            if (lastX < downX) {
                                switcher.setInAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_right)); //reset in animition
                                switcher.setOutAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_out_left)); // reset out animition
                                if (currentPosition < images.length - 1) {

                                    currentPosition++;
                                    //                               switcher.setImageURI(Uri.parse(selectedHouse.getUrl()));
                                                                   switcher.setImageResource(images[currentPosition]);
                                } else {
                                    currentPosition = 0;
                                    //                               switcher.setImageURI(Uri.parse(selectedHouse.getUrl()));
                                                                   switcher.setImageResource(images[currentPosition % images.length]);
                                    //                               Toast.makeText(mainActivity, "last page", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        break;
                    }

                return true;
            }
        });
//        imageView = view.findViewById(R.id.img_house);

//        switcher.setFactory(this);
//        imageView.setOnTouchListener(this);


//        Picasso.get().load(selectedHouse.getUrl()).fit().centerCrop().into(imageView);







            int idx = selectedHouse.getDescription().indexOf(".");
            String des = selectedHouse.getDescription().substring(0, idx + 1).toLowerCase();
            fillin.add(des);

            int idy = selectedHouse.getDescription().indexOf("ousing for");
            String houseFor = selectedHouse.getDescription().substring(idy + 11);

            fillin.add(houseFor);

            fillin.add(selectedHouse.getName());

            fillin.add(selectedHouse.getLocation());

            int [] ii = {R.drawable.ic_menu_send, R.drawable.ic_menu_share, R.drawable.ic_drawer, R.drawable.ic_filter_list_black_24dp};

        lv = view.findViewById(R.id.androidList);

        lv.setAdapter(new DetailAdapter(mainActivity, fillin, ii));



        /*contact*/
//        callHouse();
//        sendEmail();
//        setApply();

        return view;
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



//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:{
//                //手指按下的X坐标
//                downX = event.getX();
//                break;
//            }
//            case MotionEvent.ACTION_UP:{
//                float lastX = event.getX();
//                //抬起的时候的X坐标大于按下的时候就显示上一张图片
//                if(lastX > downX){
//                    if(currentPosition > 0){
//                        //设置动画，这里的动画比较简单，不明白的去网上看看相关内容
//                        currentPosition --;
//                        imageView.setImageResource(images[currentPosition % images.length]);
//
//                    }else{
//                        Toast.makeText(mainActivity, "first page", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                if(lastX < downX){
//                    if(currentPosition < images.length - 1){
//
//                        currentPosition ++ ;
//                        imageView.setImageResource(images[currentPosition]);
//                    }else{
//                        Toast.makeText(mainActivity, "last page", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            break;
//        }
//
//        return true;
//
//    }
//
//    @Override
//    public View makeView() {
//        return imageView;
//    }
}
