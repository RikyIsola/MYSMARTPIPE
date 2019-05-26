package com.island.mysmartpipe;
import android.os.*;
import com.badlogic.gdx.backends.android.*;
import android.content.pm.*;
import android.*;
public class MainActivity extends Logcat
{
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_DENIED)requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},0);
        initialize(new MyGdxGame(this),MyGdxGame.debug,MyGdxGame.suono);
    }
}
