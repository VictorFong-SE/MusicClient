package com.vfong3.musicclient;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private Button btnServiceBind;
    private Button btnServiceUnbind;
    private TextView txtStatus;

    private ArrayList<String> titles;
    private ArrayList<String> artists;
    private ArrayList<Bitmap> covers;
    private ArrayList<String> urls;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStatus = findViewById(R.id.txtBindStatus);

        btnServiceBind = findViewById(R.id.btnServiceBind);
        btnServiceBind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //todo: bind

                txtStatus.setText("Status: Bound");
            }
        });

        btnServiceUnbind = findViewById(R.id.btnServiceUnbind);
        btnServiceUnbind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //todo: unbind

                txtStatus.setText("Status: Unbound");
            }
        });
    }


    public void initRecycler()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, titles, artists, covers);

    }


    public static void playSong(int position)
    {

    }
}
