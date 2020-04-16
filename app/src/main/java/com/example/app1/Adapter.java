package com.example.app1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.VnExpressViewHolder> {

    private ArrayList<VnExpress> mArrayVnExpress;

    public Adapter(ArrayList<VnExpress> mArrayVnExpress) {
        this.mArrayVnExpress = mArrayVnExpress;
    }

    @NonNull
    @Override
    public VnExpressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item,parent,false);
        return new VnExpressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VnExpressViewHolder holder, int position) {
        VnExpress vnExpress = mArrayVnExpress.get(position);

        holder.mTvTitle.setText(vnExpress.getTitle());
        holder.mTvMota.setText(vnExpress.getLink());
        VnExpress vnExpress1 = mArrayVnExpress.get(position);
        Picasso.get().load(vnExpress1.image)
                .into(holder.mImg); // ook quang
    }

    @Override
    public int getItemCount() {
        return mArrayVnExpress!= null ? mArrayVnExpress.size() : 0;
    }

    class VnExpressViewHolder extends RecyclerView.ViewHolder {
        ImageView mImg ;
        TextView mTvTitle, mTvMota;
        public VnExpressViewHolder(@NonNull View itemView) {
            super(itemView);

            mImg = itemView.findViewById(R.id.imgview);
            mTvMota = itemView.findViewById(R.id.tv_link);
            mTvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

}
