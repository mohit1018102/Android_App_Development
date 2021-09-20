package com.technomaniacs.android.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
private MediaPlayer mMediaPlayer;
    public static Intent makePhrasesActivityIntent(Context context) {
        return new Intent(context,PhrasesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
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

        WordAdapter item=new WordAdapter(this, words,R.color.cyan);
        ListView listView=findViewById(R.id.phrases_root_view);
        listView.setAdapter(item);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stop();

                mMediaPlayer= MediaPlayer.create(PhrasesActivity.this,words.get(position).getmAudioMedia());
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stop();
                    }
                });
                mMediaPlayer.start();


            }
        });
    }
    public void stop() {
        if (mMediaPlayer != null) {

            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}