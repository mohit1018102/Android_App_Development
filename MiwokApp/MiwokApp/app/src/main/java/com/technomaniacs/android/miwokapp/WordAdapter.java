package com.technomaniacs.android.miwokapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {


   int color;
   Context context;
    public WordAdapter(Context context, ArrayList<Word> words,int color)
    {
        super(context,0,words);
        this.context=context;
        this.color=color;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Word word=getItem(position);

        // to reuse view
        View listView=convertView;
        //sometimes it is null, so we have to create new one
        if(listView==null)
        {
            listView= LayoutInflater.from(getContext()).inflate(R.layout.list_view,parent,false);
        }
        LinearLayout child=listView.findViewById(R.id.child_linear_layout);


        child.setBackgroundColor(this.getContext().getResources().getColor(color));

        TextView miwok=listView.findViewById(R.id.miwok_word);
        TextView deft=listView.findViewById(R.id.default_word);
        ImageView img=listView.findViewById(R.id.icon);

        if(word.hasImage()) {
            img.setImageResource(word.getmImageId());
        }
        else
        {
            img.setVisibility(View.GONE);
        }
        miwok.setText(word.getmMiwokTranslation());
        deft.setText(word.getmDefaultTranslation());

        return listView;
    }
}
