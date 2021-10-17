package com.technomaniacs.android.miwokapp;

import android.content.Context;

public class MiwokMediaPlayerControllerSingleton {

    private static MiwokMediaPlayerController mMiwokMediaPlayerController=null;
    private static Context mContext;

    public static void init(Context context)
    {
        mContext=context.getApplicationContext();
    }

    public static MiwokMediaPlayerController getmMiwokMediaPlayerController()
    {
        if(mContext==null) return null;

        if(mMiwokMediaPlayerController==null)
        {
            mMiwokMediaPlayerController=new MiwokMediaPlayerController(mContext);
        }
        return mMiwokMediaPlayerController;
    }

    public static void removemMiwokMediaPlayerController()
    {
        mMiwokMediaPlayerController=null;
    }
}
