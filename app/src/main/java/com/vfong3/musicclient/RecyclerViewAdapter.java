package com.vfong3.musicclient;

import android.content.Context;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private List<Song> songs;

    private Context context;

    public RecyclerViewAdapter(Context context, List<Song> songs)
    {
        this.songs = songs;
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
        holder.img.setImageBitmap(songs.get(position).getCover());
        holder.title.setText(songs.get(position).getTitle());
        holder.artist.setText(songs.get(position).getArtist());

        holder.parentLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    MainActivity.playSong(position);
                } catch (IOException | RemoteException e)
                {
                    e.printStackTrace();
                }
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
