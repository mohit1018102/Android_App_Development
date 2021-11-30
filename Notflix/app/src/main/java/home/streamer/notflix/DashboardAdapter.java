package home.streamer.notflix;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutData[] lData;

    int layout = 0;
    Context context;
    public DashboardAdapter(LayoutData[] lData, Context context) {
        this.lData = lData; this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if(layout==0) {
            view = inflater.inflate(R.layout.top_banner, parent, false);
            layout++;
            return new DashboardViewHolder(view);
        }
        else
        {
            view=inflater.inflate(R.layout.horizontal_list,parent,false);

            return new ListViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position!=0)
        { ListViewHolder hold=(ListViewHolder)holder;
           hold.title.setText(lData[position].name);
            hold.hxlist.setAdapter(new HorizontalListAdapter(lData[position].image));
        }

    }


    @Override
    public int getItemCount() {
        return lData.length;
    }

    //view holder class
    public class DashboardViewHolder extends RecyclerView.ViewHolder {

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }


    public class ListViewHolder extends RecyclerView.ViewHolder{
        RecyclerView hxlist;
        TextView title;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
           hxlist=itemView.findViewById(R.id.h_list);
           hxlist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
           title=itemView.findViewById(R.id.title);        }
    }
}

