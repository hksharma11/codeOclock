package com.e.codersclock.MyAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.e.codersclock.Model.SliderModel;
import com.e.codersclock.R;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private List<SliderModel> sliderModelList;
    Context context;



    public SliderAdapter(List<SliderModel> sliderModelList, Context context) {
        this.sliderModelList = sliderModelList;
        this.context=context;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.cardview,container,false);
       // ImageView banner= view.findViewById(R.id.banner_slider);
       // banner.setImageResource(sliderModelList.get(position).getBanner());

        TextView event=view.findViewById(R.id.cardview_textview_event);
        TextView name=view.findViewById(R.id.cardview_textview_name);
        TextView status=view.findViewById(R.id.cardview_textview_status);
        TextView dateandtime=view.findViewById(R.id.cardview_textview_dateandtime);
        View logo=view.findViewById(R.id.cardview_view_logo);
        RelativeLayout background=view.findViewById(R.id.cardview_layout_background);

        //
        event.setText(sliderModelList.get(position).getEvent().toString());
        name.setText(sliderModelList.get(position).getName().toString());

        long start_sortingID=sliderModelList.get(position).getStart_sortingID();
        long end_sortingID=sliderModelList.get(position).getEnd_sortingID();
        long rightnow= Calendar.getInstance().getTimeInMillis();
        long time_remain=start_sortingID-rightnow-19800000;




        String hour_remain=getDate(time_remain,"HH");
        String min_remain=getDate(time_remain,"mm");
        String day=getDate(time_remain,"dd");
        int intday=Integer.parseInt(day)-1;
        if (intday>0)
        {
            status.setText(Integer.toString(intday)+" Days "+hour_remain+" hours and "+min_remain+" to go");
        }else {
            status.setText(hour_remain+" hours and "+min_remain+" minutes to go");
        }


        String start_time=getDate(start_sortingID,"hh:mm a");
        String start_date=getDate(start_sortingID,"dd");
        String start_month=getDate(start_sortingID,"MMM");

        String end_time=getDate(end_sortingID,"hh:mm a");
        String end_date=getDate(end_sortingID,"dd");
        String end_month=getDate(end_sortingID,"MMM");

        String datetime=fixdate(Integer.parseInt(start_date))+" "+start_month+" "+"<b>"+start_time+"<b>"+" - "+fixdate(Integer.parseInt(end_date))+" "+end_month+" "+"<b>"+end_time+"<b>";
        dateandtime.setText(Html.fromHtml(datetime));

        if(sliderModelList.get(position).getName().toString().equals("codechef"))
        {
            logo.setBackground(ContextCompat.getDrawable(context, R.drawable.codechef));
        }else if(sliderModelList.get(position).getName().toString().equals("codeforces"))
        {
            logo.setBackground(ContextCompat.getDrawable(context, R.drawable.codeforces));
        }else if(sliderModelList.get(position).getName().toString().equals("hackerearth"))
        {
            logo.setBackground(ContextCompat.getDrawable(context, R.drawable.hackerearth));
        }else if(sliderModelList.get(position).getName().toString().equals("topcoder"))
        {
            logo.setBackground(ContextCompat.getDrawable(context, R.drawable.topcoder));
        }else if(sliderModelList.get(position).getName().toString().equals("atcoder"))
        {
            logo.setBackground(ContextCompat.getDrawable(context, R.drawable.atcoder));
        }else if(sliderModelList.get(position).getName().toString().equals("leetcode"))
        {
            logo.setBackground(ContextCompat.getDrawable(context, R.drawable.leetcode));
        }else if(sliderModelList.get(position).getName().toString().equals("codingcompetitions"))
        {
            logo.setBackground(ContextCompat.getDrawable(context, R.drawable.google));
        }

        if (position==2)
        {
            background.setBackground(ContextCompat.getDrawable(context,R.drawable.gradient1));
        }else if(position==3)
        {
            background.setBackground(ContextCompat.getDrawable(context,R.drawable.gradient2));
        }else if (position==4)
        {
            background.setBackground(ContextCompat.getDrawable(context,R.drawable.gradient3));
        }else if (position==5)
        {
            background.setBackground(ContextCompat.getDrawable(context,R.drawable.gradient1));
        }


        container.addView(view,0);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return sliderModelList.size();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    String fixdate(int n)
    {
        String result="th";

        if(n%10==1)
        {
            result="st";
        }else if(n%10==2)
        {
            result="nd";
        }else if(n%10==3)
        {
            result="rd";
        }else{
            result="th";
        }

        return  n+result;
    }
}


