package com.vfong3.musicclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.vfong3.MusicCommon.myAIDL;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private Button btnServiceBind;
    private Button btnServiceUnbind;
    private TextView txtStatus;
    boolean status;

    private ArrayList<Song> songs;
    private RecyclerView recyclerView;

    private myAIDL aidl;

    private final ServiceConnection serviceConnection = new ServiceConnection()
    {
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            aidl = myAIDL.Stub.asInterface(service);
            status = true;
        }

        public void onServiceDisconnected(ComponentName name)
        {
            aidl = null;
            status = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStatus = findViewById(R.id.txtBindStatus);
        btnServiceBind = findViewById(R.id.btnServiceBind);
        btnServiceUnbind = findViewById(R.id.btnServiceUnbind);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        btnServiceBind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!status)
                {
                    Intent intent = new Intent(myAIDL.class.getName());
                    ResolveInfo info = getPackageManager().resolveService(intent, 0);
                    assert info != null;
                    intent.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

                    status = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                    if (status)
                    {
                        txtStatus.setText(R.string.status_bound);
                        Bundle bundle = new Bundle();
                        // populate recycler from service
                        try
                        {
                            bundle = aidl.retrieveAllInfo();
                        } catch (RemoteException e)
                        {
                            e.printStackTrace();
                        }

                        songs = bundle.getParcelableArrayList("songs");
                        initRecycler();
                    }
                    else
                    {
                        txtStatus.setText(R.string.status_unbound);
                    }
                }
            }
        });

        btnServiceUnbind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //todo: unbind
                unbindService(serviceConnection);
                status = false;
                txtStatus.setText(R.string.status_unbound);
            }
        });
    }


    @Override
    protected  void onPause()
    {
        super.onPause();

        if (status)
        {
            unbindService(serviceConnection);
        }
    }


    public void initRecycler()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, songs);
    }


    public static void playSong(int position)
    {
        //todo
    }
}
