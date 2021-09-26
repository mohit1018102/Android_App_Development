package com.technomaniacs.android.miwokapp;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class MiwokMediaPlayerController {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private Context mContext;


    MiwokMediaPlayerController(Context context)
    {  mContext=context;
       mAudioManager= (AudioManager) mContext.getSystemService(mContext.AUDIO_SERVICE);
    }


    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
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
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            stop();
        }
    };


    public void createMediaPlayer(int rawId)
    {
        mMediaPlayer=MediaPlayer.create(mContext, rawId);
    }


    public void setOnCompletionListener()
    {
        mMediaPlayer.setOnCompletionListener(onCompletionListener);
    }

    public void start()
    {
        mMediaPlayer.start();
    }


    public int requestAudioFocus()
    {
        int requestAudioFocus;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            AudioAttributes mPlaybackAttributes = new AudioAttributes.Builder()
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build();
            AudioFocusRequest mAudioFocus = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                    .setAudioAttributes(mPlaybackAttributes)
                    .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                    .build();
            requestAudioFocus = mAudioManager.requestAudioFocus(mAudioFocus);
        } else {
            requestAudioFocus = mAudioManager.requestAudioFocus(onAudioFocusChangeListener,
                    AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }
        return  requestAudioFocus;

    }


    public void stop() {
        if (mMediaPlayer != null) {

            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}
