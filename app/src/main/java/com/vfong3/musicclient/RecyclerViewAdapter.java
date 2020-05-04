package com.vfong3.musicclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{

    private ArrayList<String> titles;
    private ArrayList<String> artists;
    private ArrayList<Bitmap> images;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<String> titles, ArrayList<String> artists, ArrayList<Bitmap> images)
    {
        this.titles = titles;
        this.artists = artists;
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        holder.img.setImageBitmap(images.get(position));
        holder.title.setText(titles.get(position));
        holder.artist.setText(artists.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.playSong(position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        RelativeLayout parentLayout;
        ImageView img;
        TextView title;
        TextView artist;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.txtTitle);
            artist = itemView.findViewById(R.id.txtArtist);
        }
    }
}
