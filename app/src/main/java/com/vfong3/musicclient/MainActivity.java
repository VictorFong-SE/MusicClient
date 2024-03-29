package com.vfong3.musicclient;


import com.vfong3.musiccommon.myAIDL;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity
{
    private final static String TAG = "TEKn";

    private Button btnServiceBind;
    private Button btnServiceUnbind;
    private TextView txtStatus;
    boolean status;

    private RecyclerView recyclerView;

    private static myAIDL aidl = null;
    private static Context thisContext;

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
        thisContext = getApplicationContext();
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
                    Log.d(TAG, "onclick listener hit for bind.");
                    Intent intent = new Intent("com.vfong3.musiccentral.myAidl");
                    Intent explicitIntent = convertIntent(intent,getApplicationContext());
                    if (explicitIntent != null)
                        status = bindService(explicitIntent, serviceConnection, Context.BIND_AUTO_CREATE);

                    if (status)
                    {
                        Log.d(TAG, "bind completed and status true");
                        txtStatus.setText(R.string.status_bound);

                        List<Song> songs = Collections.synchronizedList(new ArrayList<Song>());
                        // populate recycler from service
                        try
                        {
                            Log.d(TAG, "trying aidl command");
                            songs = aidl.retrieveAllInfo();
                        } catch (RemoteException e)
                        {
                            e.printStackTrace();
                        }

                        initRecycler(songs);
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
    protected void onPause()
    {
        super.onPause();

        if (status)
        {
            unbindService(serviceConnection);
        }
    }


    public void initRecycler(List<Song> songs)
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, songs);
    }


    public static void playSong(int position) throws IOException, RemoteException
    {
        Uri myUri = Uri.parse( aidl.retrieveURL(position));
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(MainActivity.thisContext, myUri);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    //algorithm inspired by stack overflow to convert implicit intents to explicit for binding
    public static Intent convertIntent(Intent implicitIntent, Context context)
    {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfoList = pm.queryIntentServices(implicitIntent, 0);

        if (resolveInfoList.size() != 1)
        {
            return null;
        }
        ResolveInfo serviceInfo = resolveInfoList.get(0);
        ComponentName component = new ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name);
        Intent explicitIntent = new Intent(implicitIntent);
        explicitIntent.setComponent(component);
        return explicitIntent;
    }
}
