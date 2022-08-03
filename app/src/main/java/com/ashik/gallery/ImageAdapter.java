package com.ashik.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private ArrayList<Upload> mlist;
    private Context context;
    public ImageAdapter(Context context,ArrayList<Upload> mlist){
        this.context=context;
        this.mlist=mlist;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.image_container,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Upload uploadCurrent=mlist.get(position);
        holder.textViewName.setText(uploadCurrent.getDesc());
        //To load images in the recyclerview
        Glide.with(context).load(mlist.get(position).getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
      return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    public TextView textViewName;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewName=itemView.findViewById(R.id.textView2);
        imageView=itemView.findViewById(R.id.img);


    }

    }

}
