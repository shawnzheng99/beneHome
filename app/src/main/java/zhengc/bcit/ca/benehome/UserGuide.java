package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class UserGuide extends AppCompatActivity {

    ViewPager viewPager;
    ImageView cycle1;
    ImageView cycle2;
    ImageView cycle3;

    private ImageView[] tips;

    private boolean isLastPage = false;
    private boolean isDragPage = false;
    private boolean canJumpPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        cycle1 = findViewById(R.id.cycle1);
        cycle2 = findViewById(R.id.cycle2);
        cycle3 = findViewById(R.id.cycle3);

        viewPager = (ViewPager) findViewById(R.id.ViewPager);
        ViewPagerAdpter viewPagerAdpter = new ViewPagerAdpter(this);

        viewPager.setAdapter(viewPagerAdpter);

        //        **********************************************************************************************************
        //when swipe left on the last page, execute JumpToNext()
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * swipe
             * @param position
             * @param positionOffset   move offset, 0/1
             * @param positionOffsetPixels   image move offset
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                Log.v("AAA",isLastPage+"   "+isDragPage+"   "+positionOffsetPixels);
                if (isLastPage && isDragPage && positionOffsetPixels == 0){
                    if (canJumpPage){
                        canJumpPage = false;
                        JumpToNext();
                    }
                }
            }

            /**
             *
             * @param position    the current page index
             */
            @Override
            public void onPageSelected(int position) {
                isLastPage = position == 2;

                if (position == 0) {
                    cycle1.setImageResource(R.drawable.circle_selected);
                    cycle2.setImageResource(R.drawable.circle_unselected);
                    cycle3.setImageResource(R.drawable.circle_unselected);
                }

                if (position == 1) {
                    cycle1.setImageResource(R.drawable.circle_unselected);
                    cycle2.setImageResource(R.drawable.circle_selected);
                    cycle3.setImageResource(R.drawable.circle_unselected);
                }

                if (position == 2) {
                    cycle1.setImageResource(R.drawable.circle_unselected);
                    cycle2.setImageResource(R.drawable.circle_unselected);
                    cycle3.setImageResource(R.drawable.circle_selected);
                }

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
    }


    /**
     * Jump to next activity
     */
    private void JumpToNext() {

        startActivity(new Intent(UserGuide.this, MainActivity.class));
    }

}
