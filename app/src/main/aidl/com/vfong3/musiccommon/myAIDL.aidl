// myAIDL.aidl
package com.vfong3.musiccommon;

import android.graphics.Bitmap;
import com.vfong3.musiccommon.Song;

interface myAIDL
{
    List<Song> retrieveAllInfo();

    Song retrieveSongInfo(int songNumber);

    String retrieveURL(int songNumber);
}