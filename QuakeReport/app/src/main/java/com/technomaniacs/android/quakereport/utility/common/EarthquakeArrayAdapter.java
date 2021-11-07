package com.technomaniacs.android.quakereport.utility.common;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.technomaniacs.android.quakereport.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeArrayAdapter extends ArrayAdapter<Earthquake> {

    private final String SEPERATOR="of";

    public EarthquakeArrayAdapter(Context context,ArrayList<Earthquake> earthquake)
    {
        super(context,0,earthquake);
    }


    private String getDate(long timeInMilliseconds)
    {
        Date dateObject = new Date(timeInMilliseconds);
        //SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD , yyyy");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd , yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        return dateToDisplay;

    }

    private String getTime(long timeInMilliseconds)
    {
        Date dateObject = new Date(timeInMilliseconds);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm aaa");
        String timeToDisplay=timeFormatter.format(timeInMilliseconds);
        return timeToDisplay;


    }


    private String getMagnitude(float mag)
    {
        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(mag);
        return output;
    }


    private int getMagnitudeColor(float mag)
    {
        int m=(int)mag;
        int colorId;
        switch (m)
        {

            case 1: colorId=R.color.magnitude1;
                    break;
            case 2: colorId= R.color.magnitude2;
                    break;
            case 3: colorId= R.color.magnitude3;
                    break;
            case 4: colorId= R.color.magnitude4;
                    break;
            case 5: colorId=R.color.magnitude5;
                    break;
            case 6: colorId= R.color.magnitude6;
                    break;
            case 7: colorId=R.color.magnitude7;
                    break;
            case 8: colorId=R.color.magnitude8;
                    break;
            case 9: colorId=R.color.magnitude9;
                    break;
            case 10: colorId=R.color.magnitude10plus;
                    break;
            default:  colorId=R.color.magnitude1;
        }

        return  ContextCompat.getColor(getContext(), colorId);
    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Earthquake earthquake=getItem(position);
        View listView=convertView;
        if(listView==null)
        {
            listView= LayoutInflater.from(getContext()).inflate(R.layout.list_earthquake,parent, false);
        }

        TextView mag=listView.findViewById(R.id.mag);
        TextView placeUpper=listView.findViewById(R.id.place_upper);
        TextView placelower=listView.findViewById(R.id.place_lower);
        TextView time=listView.findViewById(R.id.time);
        TextView date=listView.findViewById(R.id.date);

        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();
        int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        long timeInMilliseconds=earthquake.getTime();
        String loc=earthquake.getLocation();
        if(loc.contains(SEPERATOR))
        {
            String[] part=loc.split(SEPERATOR);
            placeUpper.setText(part[0]+SEPERATOR);
            placelower.setText(part[1]);

        }
        else
        {
            placeUpper.setText("~");
            placelower.setText(loc);
        }

        mag.setText(getMagnitude(earthquake.getMagnitude()));
        date.setText(getDate(timeInMilliseconds));
        time.setText(getTime(timeInMilliseconds));

        return  listView;

    }
}
