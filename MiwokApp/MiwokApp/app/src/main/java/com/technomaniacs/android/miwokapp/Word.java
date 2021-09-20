package com.technomaniacs.android.miwokapp;

public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageId;
    private  static  final int NO_IMAGE_PROVIDED=-1;
    private int mAudioMedia;

    public Word(String defaultTranslation,String miwokTranslation,int audioMedia)
    {
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
        mImageId=NO_IMAGE_PROVIDED;
        mAudioMedia=audioMedia;

    }
    public Word(String defaultTranslation,String miwokTranslation,int imageId,int audioMedia)
    {
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
        mImageId=imageId;
        mAudioMedia=audioMedia;
    }

    public String getmDefaultTranslation()
    {
        return mDefaultTranslation;
    }

    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }
    public int getmImageId(){return mImageId;}
    public int getmAudioMedia(){return mAudioMedia;}

    public boolean hasImage()
    {
        return (mImageId!=NO_IMAGE_PROVIDED)?true:false;
    }
}
