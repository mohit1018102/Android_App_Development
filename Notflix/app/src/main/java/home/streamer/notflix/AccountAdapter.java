package home.streamer.notflix;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {
    private int[] data;
    private String[] name;
    Context context;
    public AccountAdapter(int[] data, String[] name, Context context)
    {
        this.data=data;
        this.name=name;
        this.context=context;
    }
    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.account_list,parent,false);

        return new AccountViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
      int id=data[position];
      holder.button.setImageResource(id);
      holder.textView.setText(name[position]);
      if(position==data.length-1)
      {
          holder.button.setPadding(50,50,50,50);
      }
      else {

          holder.button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(context, DashboardActivity.class);
                  context.startActivity(intent);
              }
          });
      }

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    //view holder class
    public class AccountViewHolder extends RecyclerView.ViewHolder{
          ImageButton button;
          TextView textView;
        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.leftview);
            textView=itemView.findViewById(R.id.acc_name);



        }
    }
}
