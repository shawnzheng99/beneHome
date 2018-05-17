package zhengc.bcit.ca.benehome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class House_detail extends Fragment {
    Place selectedHouse;
    View view;
    private MainActivity mainActivity;

//    ImageSwitcher switcher;
    ImageView imageView;
    float initialX;
    private Cursor cursor;
    private  int columnIndex, position = 0;
    private int currentPosition = 0;
    private float downX;

    private boolean isLastPage = false;
    private boolean isDragPage = false;
    private boolean canJumpPage = true;

    private LinearLayout linearLayout;
    private ArrayList<ImageView> pointList = new ArrayList<ImageView>();

    ViewPager sliderLayout;

    TextView tv;
//    int [] images = {R.drawable.slide1,R.drawable.slide2,R.drawable.slide3};

//    ArrayList<String> fillin = new ArrayList<String>();

//    ListView lv;

    ArrayList<String> al;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }
    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_hose_detail,null);

        String as;
        // set up

        //TODO: HashMap<String,String> hm = selectedHouse.getUrl();
        //      hm.get("a");.. b, c

        selectedHouse = (Place) getArguments().getSerializable("house");

//        switcher = view.findViewById(R.id.imageSwitcher);

        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout_points);

        sliderLayout = (ViewPager) view.findViewById(R.id.mygallery);

        tv = (TextView) view.findViewById(R.id.house_detail);

        al = new ArrayList<String>();

        for (String x : selectedHouse.getUrl().values()) {
            al.add(x);
        }

        DetailImageAdapter dia = new DetailImageAdapter(mainActivity, al);

        sliderLayout.setAdapter(dia);

        sliderLayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * swipe
             * @param position
             * @param positionOffset   move offset, 0/1
             * @param positionOffsetPixels   image move offset
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (isLastPage && isDragPage && positionOffsetPixels == 0){
                    if (canJumpPage){
                        canJumpPage = false;
                        Toast.makeText(mainActivity, "last page", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            /**
             * display effect between the three.
             * @param position    the current page index
             */
            @Override
            public void onPageSelected(int position) {
                isLastPage = position == al.size() -1 ;
                switchPoint(position);


            }

            /**
             * screen
             * @param state   0（END）,1(PRESS) , 2(UP) 。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

                isDragPage = state == 1;

            }
        });

        for (int i = 0; i < selectedHouse.getUrl().size(); i++) {
            ImageView imageView = new ImageView(mainActivity);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(35,35);
            llp.leftMargin = 20;
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(35, 35));
            imageView.setLayoutParams(llp);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // imageView.setImageResource(R.drawable.dot_1);
            imageView.setImageResource(R.drawable.circle_unselected);
            pointList.add(imageView);
            linearLayout.addView(imageView);
        }

        switchPoint(0);

        if (selectedHouse.getApply().equalsIgnoreCase("Apply to The Housing Registry")) {
            as = "<a href=\"http://students.bcitdev.com/A00982754/Lab2/LAP2.html\">Apply to the housing registry</a><br><br>\n";
        } else {
            as = "Apply to housing provider.";
        }

        tv.setText(Html.fromHtml("<h1>" + selectedHouse.getName() + "</h1>\n" +
                "<P><h2>Address</h2>\n" +
                selectedHouse.getLocation() +
                "<br>New Westerminster, BC   \n" +
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
//        switcher.setFactory(new ViewSwitcher.ViewFactory() {
//            @Override
//            public View makeView() {
//                ImageView imageView = new ImageView(mainActivity);
//                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
////                imageView.setImageURI(Uri.parse(selectedHouse.getUrl().get("a")))
//
//                return imageView;
//            }
//        });
//
////        Bitmap tmpBitmap = null;
////        try {
////            InputStream is = new java.net.URL(selectedHouse.getUrl().get("a")).openStream();
////            tmpBitmap = BitmapFactory.decodeStream(is);
////            is.close();
////        } catch (Exception e) {
////            e.printStackTrace();
////            Log.i("KK下载图片", e.getMessage());
////        }
//
//        switcher.setImageResource(images[0]);
//
////        switcher.setImageURI(Uri.parse(selectedHouse.getUrl().get("a")));//initialize the first view of the image switcher
//
////        switcher.setImageURL(selectedHouse.getUrl().get("a"));
//
//
//        switcher.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN: {
//
//                        //finger press on
//
//                        downX = event.getX();
//                        break;
//                    }
//                    case MotionEvent.ACTION_UP: {
//                        float lastX = event.getX();
//
//                        //swipe right or left
//                        if (lastX > downX) {
//                            switcher.setInAnimation(AnimationUtils.loadAnimation(mainActivity, android.R.anim.slide_in_left));//set in animition
//                            switcher.setOutAnimation(AnimationUtils.loadAnimation(mainActivity, android.R.anim.slide_out_right));//set out animition
//
//
//                                if (currentPosition > 0) {
//                                    currentPosition--;
////                                switcher.setImageURI(Uri.parse(selectedHouse.getUrl()));
//                                switcher.setImageResource(images[currentPosition % images.length]);
//
//                                } else {
//                                    currentPosition = 2;
////                                switcher.setImageURI(Uri.parse(selectedHouse.getUrl()));
//                                switcher.setImageResource(images[currentPosition % images.length]);
////                                Toast.makeText(mainActivity, "first page", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            if (lastX < downX) {
//                                switcher.setInAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_in_right)); //reset in animition
//                                switcher.setOutAnimation(AnimationUtils.loadAnimation(mainActivity, R.anim.slide_out_left)); // reset out animition
//                                if (currentPosition < images.length - 1) {
//
//                                    currentPosition++;
//                                    //                               switcher.setImageURI(Uri.parse(selectedHouse.getUrl()));
//                                                                   switcher.setImageResource(images[currentPosition]);
//                                } else {
//                                    currentPosition = 0;
//                                    //                               switcher.setImageURI(Uri.parse(selectedHouse.getUrl()));
//                                                                   switcher.setImageResource(images[currentPosition % images.length]);
//                                    //                               Toast.makeText(mainActivity, "last page", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//
//                        break;
//                    }
//
//                return true;
//            }
//        });
//        imageView = view.findViewById(R.id.img_house);

//        switcher.setFactory(this);
//        imageView.setOnTouchListener(this);


//        Picasso.get().load(selectedHouse.getUrl()).fit().centerCrop().into(imageView);







//        fillin.add(selectedHouse.getName());
//
//            int idx = selectedHouse.getDescription().indexOf(".");
//            String des = selectedHouse.getDescription().substring(0, idx + 1).toLowerCase();
//            fillin.add(des);
//
//            int idy = selectedHouse.getDescription().indexOf("ousing for");
//            String houseFor = selectedHouse.getDescription().substring(idy + 11);
//
//            fillin.add(houseFor);
//
//
//
//            fillin.add(selectedHouse.getLocation());
//
//            fillin.add(selectedHouse.getApply());
//
//            fillin.add(selectedHouse.getEmail());
//
//            fillin.add(selectedHouse.getMiddle());
//
//            fillin.add(selectedHouse.getPC());
//
//            fillin.add(selectedHouse.getPets());
//
//            fillin.add(selectedHouse.getWebsite());
//
//            fillin.add(selectedHouse.getElementary());
//
//            fillin.add(selectedHouse.getSecondary());
//
//            int [] ii = {R.drawable.ic_menu_send, R.drawable.ic_menu_share, R.drawable.ic_drawer, R.drawable.ic_filter_list_black_24dp};
//
//        lv = view.findViewById(R.id.androidList);
//
//        lv.setAdapter(new DetailAdapter(mainActivity, fillin, ii));


        /*contact*/
        callHouse();
        sendEmail();
//        setApply();

        return view;
    }




//    private void setApply() {
//        Button apply = view.findViewById(R.id.btn_applyNow);
//        final Uri uri = Uri.parse(selectedHouse.getWebsite());
//        apply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//            }
//        });
//    }


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
//    private ViewGroup.LayoutParams getListViewParams() {
//        ListView listView = view.findViewById(R.id.androidList);
//        ListAdapter listAdapter = listView.getAdapter();
//        int totalHeight = 0;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View item = listAdapter.getView(i, null, listView);
//            item.measure(0, 0);
//            totalHeight += item.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams lp = listView.getLayoutParams();
//        lp.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        return lp;
//    }



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

    private void switchPoint(int position) {
        ImageView iv;
        for (int i = 0; i < selectedHouse.getUrl().size(); i++) {
            iv = (ImageView) linearLayout.getChildAt(i);
            if (i == position) {
                iv.setImageResource(R.drawable.circle_unselected);
            } else {
                iv.setImageResource(R.drawable.circle_selected);
            }
        }

    }
}


