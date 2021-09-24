package com.technomaniacs.android.miwokapp;



import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MiwokFragmentPagerAdapter extends FragmentPagerAdapter {

    Context mContext;
    public MiwokFragmentPagerAdapter(Context context,@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        mContext=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:return new NumbersFragment();
            case 1:return new FamilyMembersFragment();
            case 2:return new ColorsFragment();
            default:return new PhrasesFragment();
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       switch (position)
       {
           case 0: return  mContext.getString(R.string.numbers);
           case 1: return  mContext.getString(R.string.family_Member);
           case 2: return  mContext.getString(R.string.colors);
           default: return  mContext.getString(R.string.phrases);
       }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
