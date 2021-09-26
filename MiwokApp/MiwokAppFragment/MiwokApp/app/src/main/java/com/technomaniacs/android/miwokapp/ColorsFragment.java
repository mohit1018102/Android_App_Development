package com.technomaniacs.android.miwokapp;

import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ColorsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_colors, container, false);

        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        WordAdapter item = new WordAdapter(getContext(), words, R.color.pink);
        ListView list = rootView.findViewById(R.id.colors_root_view);

        list.setAdapter(item);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ColorsActivity: ", words.get(position).toString());
                MainActivity.miwokMediaPlayerController.stop();

                int requestAudioFocus = MainActivity.miwokMediaPlayerController.requestAudioFocus();

                if (requestAudioFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    MainActivity.miwokMediaPlayerController.createMediaPlayer(words.get(position).getmAudioMedia());
                    MainActivity.miwokMediaPlayerController.setOnCompletionListener();
                    MainActivity.miwokMediaPlayerController.start();

                } else
                    Toast.makeText(getContext(),
                            "failed focus request",
                            Toast.LENGTH_LONG).show();


            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
