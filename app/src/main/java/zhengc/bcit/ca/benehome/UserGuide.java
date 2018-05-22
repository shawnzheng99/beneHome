package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Three pages user guide for whom run this app first time.
 */
public class UserGuide extends AppCompatActivity {

    private ImageView circle1;
    private ImageView circle2;
    private ImageView circle3;

    private boolean isLastPage = false;
    private boolean isDragPage = false;
    private boolean canJumpPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);
        circle3 = findViewById(R.id.circle3);

        ViewPager viewPager = findViewById(R.id.ViewPager);
        ViewPagerAdpter viewPagerAdpter = new ViewPagerAdpter(this);

        viewPager.setAdapter(viewPagerAdpter);

        //**********************************************************************************************************
        //when swipe left on the last page, execute JumpToNext()
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * swipe
             * @param position position
             * @param positionOffset   move offset, 0/1
             * @param positionOffsetPixels   image move offset
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (isLastPage && isDragPage && positionOffsetPixels == 0){
                    if (canJumpPage){
                        canJumpPage = false;
                        JumpToNext();
                    }
                }
            }

            /**
             * display effect between the three.
             * @param position    the current page index
             */
            @Override
            public void onPageSelected(int position) {
                isLastPage = position == 2;

                if (position == 0) {
                    circle1.setImageResource(R.drawable.circle_selected);
                    circle2.setImageResource(R.drawable.circle_unselected);
                    circle3.setImageResource(R.drawable.circle_unselected);
                }

                if (position == 1) {
                    circle1.setImageResource(R.drawable.circle_unselected);
                    circle2.setImageResource(R.drawable.circle_selected);
                    circle3.setImageResource(R.drawable.circle_unselected);
                }

                if (position == 2) {
                    circle1.setImageResource(R.drawable.circle_unselected);
                    circle2.setImageResource(R.drawable.circle_unselected);
                    circle3.setImageResource(R.drawable.circle_selected);
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
     * On the last user guide page, keeping swiping left will make the screen jump to next activity.
     */
    private void JumpToNext() {

        startActivity(new Intent(UserGuide.this, MainActivity.class));
    }

}
