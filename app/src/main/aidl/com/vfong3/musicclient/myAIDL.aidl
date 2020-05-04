// myAIDL.aidl
package com.vfong3.musicclient;

import android.os.Bundle;

// Declare any non-default types here with import statements

interface myAIDL
{
    Bundle retrieveAllInfo();

    Bundle retrieveSongInfo(int songNumber);

    String retrieveURL(int songNumber);
}
