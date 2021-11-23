package com.technomaniacs.android.mypet.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.technomaniacs.android.mypet.R;
import com.technomaniacs.android.mypet.db.PetContract.PetEntry;

public class PetCursorAdapter extends CursorAdapter {
    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.pet_list_view_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name=view.findViewById(R.id.text_name);
        TextView breed=view.findViewById(R.id.text_breed);
        TextView gender=view.findViewById(R.id.text_gender);
        TextView weight=view.findViewById(R.id.text_weight);
        String petName= cursor.getString(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_PET_NAME));
        String petBreed=cursor.getString(cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED));
        int petgender=cursor.getInt(cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER));
        int petweight=cursor.getInt(cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT));

        name.setText(petName);
        if(petBreed.equals("")){
            breed.setText("~unknown breed");
        }
        else
        breed.setText(petBreed);
        switch (petgender)
        {
            case PetEntry.GENDER_FEMALE: gender.setText("Female");break;
            case PetEntry.GENDER_MALE: gender.setText("Male"); break;
            default:gender.setText("Unknown");

        }
        weight.setText(String.valueOf(petweight)+" kg");
    }
}
