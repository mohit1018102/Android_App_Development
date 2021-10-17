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

public class PhrasesFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_phrases, container, false);
        ArrayList<Word> words=new ArrayList<>();
        words.add(new Word("Where are you going ?","minto wuksus",R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name ?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is ...","oyaaset ...  ",R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling ?","michәksәs ?",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming ?","әәnәs'aa ?",R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming","әәnәm",R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("Come here.","әnni'nem",R.raw.phrase_come_here));

        WordAdapter item=new WordAdapter(getContext(), words,R.color.cyan);
        ListView listView=rootView.findViewById(R.id.phrases_root_view);
        listView.setAdapter(item);

        MiwokMediaPlayerController miwokMediaPlayerController=MiwokMediaPlayerControllerSingleton.getmMiwokMediaPlayerController();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("PhrasesActivity: ",words.get(position).toString());

                miwokMediaPlayerController.stop();

                int requestAudioFocus = miwokMediaPlayerController.requestAudioFocus();

                if (requestAudioFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    miwokMediaPlayerController.createMediaPlayer(words.get(position).getmAudioMedia());
                    miwokMediaPlayerController.setOnCompletionListener();
                    miwokMediaPlayerController.start();

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
