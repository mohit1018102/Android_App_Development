package com.technomaniacs.android.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView numbers=findViewById(R.id.numbers);
        TextView colors=findViewById(R.id.colors);
        TextView familyMember=findViewById(R.id.family_members);
        TextView phrases=findViewById(R.id.phrases);


        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=NumbersActivity.makeNumbersActivityIntent(MainActivity.this);
                startActivity(intent);
            }
        });
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=ColorsActivity.makeColorsActivityIntent(MainActivity.this);
                startActivity(intent);

            }
        });
        familyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=FamilyMembersActivity.makeFamilyMembersActivityIntent(MainActivity.this);
                startActivity(intent);

            }
        });
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=PhrasesActivity.makePhrasesActivityIntent(MainActivity.this);
                startActivity(intent);

            }
        });
    }

}