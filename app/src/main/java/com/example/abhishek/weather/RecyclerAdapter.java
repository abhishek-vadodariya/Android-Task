package com.example.abhishek.weather;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import GetterSetter.Pojo;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>
{
    private List<Pojo> pojolist;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txt_temp,txt_pressure,txt_humidity,txt_min_temp,txt_mex_temp;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            txt_temp =(TextView) itemView.findViewById(R.id.txt_temp);
            txt_pressure =(TextView)itemView.findViewById(R.id.txt_pressure);
            txt_humidity =(TextView)itemView.findViewById(R.id.txt_humidity);
            txt_min_temp=(TextView)itemView.findViewById(R.id.txt_min_temp);
            txt_mex_temp=(TextView)itemView.findViewById(R.id.txt_mex_temp);

        }
    }

    public RecyclerAdapter(List<Pojo> list)
    {
        this.pojolist = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_recycler_view,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position)
    {
        Pojo pojo = pojolist.get(position);
        myViewHolder.txt_temp.setText(pojo.getTxt_temp());
        myViewHolder.txt_pressure.setText(pojo.getTxt_pressure());
        myViewHolder.txt_humidity.setText(pojo.getTxt_humidity());
        myViewHolder.txt_min_temp.setText(pojo.getTxt_min_temp());
        myViewHolder.txt_mex_temp.setText(pojo.getTxt_mex_temp());

    }

    @Override
    public int getItemCount()
    {
        return pojolist.size();
    }


}
