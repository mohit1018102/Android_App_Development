package home.streamer.notflix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AccountActivity extends AppCompatActivity {

    public static Intent makeIntent(Context context)
    {
        return new Intent(context,AccountActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        RecyclerView accountList=(RecyclerView) findViewById(R.id.account);
       accountList.setLayoutManager(new GridLayoutManager(this,2));
       int[] list={R.drawable.smile,R.drawable.flash,R.drawable.kids,R.drawable.ic_add_2};

       String[] name={"Rohit","Mohit","Kids","Add Profile"};
       accountList.setAdapter(new AccountAdapter(list,name,this));




    }

}
