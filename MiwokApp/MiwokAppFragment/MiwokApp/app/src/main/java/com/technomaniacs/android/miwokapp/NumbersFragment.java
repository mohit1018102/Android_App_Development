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

public class NumbersFragment extends Fragment {
    public NumbersFragment() {
        super();
    }


    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;


    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener= new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause(); // pause audio
                mMediaPlayer.seekTo(0);//reset to beginning
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                stop();
            }
        }

    };

    private MediaPlayer.OnCompletionListener onCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            stop();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_numbers, container, false);

        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("One", "lutti", R.drawable.number_one,R.raw.number_one));
        words.add(new Word("Two", "otilko", R.drawable.number_two,R.raw.number_two));
        words.add(new Word("Three", "tolookosu", R.drawable.number_three,R.raw.number_three));
        words.add(new Word("Four", "oyyisa", R.drawable.number_four,R.raw.number_four));
        words.add(new Word("Five", "massokka", R.drawable.number_five,R.raw.number_five));
        words.add(new Word("Six", "temmokka", R.drawable.number_six,R.raw.number_six));
        words.add(new Word("Seven", "kenekaku", R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("Eight", "kawinta", R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("Nine", "wo'e", R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("Ten", "na'aacha", R.drawable.number_ten,R.raw.number_ten));


        WordAdapter item = new WordAdapter(getContext(), words, R.color.orange);
        ListView listView = rootView.findViewById(R.id.numbers_root_view);
        listView.setAdapter(item);



        mAudioManager= (AudioManager) getContext().getSystemService(getContext().AUDIO_SERVICE);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stop();
                Log.d("NumberActivity: ",words.get(position).toString());
                int requestAudioFocus;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    AudioAttributes mPlaybackAttributes= new AudioAttributes.Builder()
                            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                            .build();
                    AudioFocusRequest mAudioFocus=new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                            .setAudioAttributes(mPlaybackAttributes)
                            .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                            .build();
                    requestAudioFocus=mAudioManager.requestAudioFocus(mAudioFocus);
                }
                else {
                    requestAudioFocus= mAudioManager.requestAudioFocus(onAudioFocusChangeListener,
                            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                }
                if(requestAudioFocus==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getContext(), words.get(position).getmAudioMedia());
                    mMediaPlayer.setOnCompletionListener(onCompletionListener);
                    mMediaPlayer.start();
                }
                else
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
        stop();
    }

    public void stop() {
        if (mMediaPlayer != null) {

            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}
