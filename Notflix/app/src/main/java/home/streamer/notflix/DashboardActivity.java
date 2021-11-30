package home.streamer.notflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


class LayoutData
{
    public String name;
    public int[] image;

}

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        RecyclerView dashList=(RecyclerView) findViewById(R.id.dashboard_main);
        dashList.setHasFixedSize(true);
        dashList.setLayoutManager(new LinearLayoutManager(this));
        dashList.setItemViewCacheSize(10);
        int[] list={R.drawable.r11,R.drawable.r12,R.drawable.r13,R.drawable.r14,R.drawable.r15,R.drawable.r16};
        LayoutData[] lData=new LayoutData[6];
        for(int i=0;i<6;i++)
        {
            lData[i]=new LayoutData();
            lData[i].name="Best Movies";
            lData[i].image=list;

        }

        dashList.setAdapter(new DashboardAdapter(lData,this));
    }
}