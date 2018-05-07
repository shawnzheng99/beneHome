package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserGuide extends AppCompatActivity {

    ViewPager viewPager;

    private boolean isLastPage = false;
    private boolean isDragPage = false;
    private boolean canJumpPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        viewPager = (ViewPager) findViewById(R.id.ViewPager);
        ViewPagerAdpter viewPagerAdpter = new ViewPagerAdpter(this);

        viewPager.setAdapter(viewPagerAdpter);

        //        **********************************************************************************************************
        //监听ViewPager的跳转状态，当跳转到最后一页时，执行jumpToNext()方法
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * 在屏幕滚动过程中不断被调用
             * @param position
             * @param positionOffset   是当前页面滑动比例，如果页面向右翻动，这个值不断变大，最后在趋近1的情况后突变为0。如果页面向左翻动，这个值不断变小，最后变为0
             * @param positionOffsetPixels   是当前页面滑动像素，变化情况和positionOffset一致
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                Log.v("AAA",isLastPage+"   "+isDragPage+"   "+positionOffsetPixels);
                if (isLastPage && isDragPage && positionOffsetPixels == 0){   //当前页是最后一页，并且是拖动状态，并且像素偏移量为0
                    if (canJumpPage){
                        canJumpPage = false;
                        JumpToNext();
                    }
                }
            }

            /**
             * 这个方法有一个参数position，代表哪个页面被选中
             * @param position    当前页的索引
             */
            @Override
            public void onPageSelected(int position) {
                isLastPage = position == 2;

            }

            /**
             * 在手指操作屏幕的时候发生变化
             * @param state   有三个值：0（END）,1(PRESS) , 2(UP) 。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

                isDragPage = state == 1;

            }
        });
    }


    /**
     * viewpager滑动到最后一页做跳转逻辑
     */
    private void JumpToNext() {

        startActivity(new Intent(UserGuide.this, MainActivity.class));
    }

}
