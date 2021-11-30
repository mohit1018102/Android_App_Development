package home.streamer.notflix;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.HorizontalListViewHolder> {

   int[] img;
    public HorizontalListAdapter(int [] img)
    { this.img=img;

    }
    @NonNull
    @Override
    public HorizontalListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.card_layout,parent,false);

        return new HorizontalListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HorizontalListViewHolder holder, int position) {
       int id=img[position];
       holder.button.setImageResource(id);
    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    //view holder class
    public class HorizontalListViewHolder extends RecyclerView.ViewHolder{
        ImageButton button;
        public HorizontalListViewHolder(@NonNull View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.leftview);
        }
    }
}
