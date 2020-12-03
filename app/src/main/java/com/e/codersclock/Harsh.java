package com.e.codersclock;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.e.codersclock.Model.SliderModel;
import com.e.codersclock.MyAdapter.SliderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Harsh {

    private ViewPager bannerSliderViewPager;
    private List<SliderModel> sliderModelList;
    List<SliderModel> SliderList=new ArrayList<>();
    private int currentPage=2;
    private Timer timer;
    final private long DELAY_TIME=3000;
    final  private  long PERIOD_TIME=3000;


    void oncreate(ViewPager view, List<SliderModel> sliderModelListfromfucntion, Context context)
    {
        bannerSliderViewPager =view;
        sliderModelList=sliderModelListfromfucntion;
        Log.d("#testing",Integer.toString(sliderModelList.size()));
        SliderModel s1=sliderModelList.get(0);
        Log.d("#testing",s1.getEvent());
        SliderModel s2=sliderModelList.get(1);
        Log.d("#testing",s2.getEvent());
        SliderModel s3=sliderModelList.get(2);
        Log.d("#testing",s3.getEvent());


        SliderList.add(s2);
        SliderList.add(s3);
        SliderList.add(s1);
        SliderList.add(s2);
        SliderList.add(s3);
        SliderList.add(s1);
        SliderList.add(s2);

        Log.d("#testing",Integer.toString(SliderList.size()));


        SliderAdapter sliderAdapter= new SliderAdapter(SliderList,context);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state== ViewPager.SCROLL_STATE_IDLE)
                {
                    pageLooper();
                }
            }
        };
        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
        startBannerSlideShow();


    }

    private void pageLooper()
    {
        Log.d("#testing","currentpage = "+currentPage);
        Log.d("#testing",Integer.toString(SliderList.size()));
        if(currentPage==SliderList.size()-2)
        {
            Log.d("#testing","in first if");
            currentPage=2;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }


        if(currentPage==1)
        {
            Log.d("#testing","in second if");
            currentPage=SliderList.size()-3;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }
    }


    private void startBannerSlideShow()
    {
        final Handler handler=new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage>= SliderList.size())
                {
                    currentPage=1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++,true);
            }
        };

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);
    }

    private  void  stopBannerSlideShow()
    {
        timer.cancel();
    }
}
