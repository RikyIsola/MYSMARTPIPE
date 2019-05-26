package com.island.mysmartpipe;
import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.android.*;
import java.io.*;
public class Logcat extends AndroidApplication
{
	private PrintStream log;
	public void initialize(ApplicationListener listener,boolean debug,boolean audio)
	{
		if(debug)
		{
			Thread.setDefaultUncaughtExceptionHandler(new UnchaughtException());
			if(log!=null)log.close();
			System.out.close();
			System.err.close();
			try
			{
				log = new PrintStream(new FileOutputStream(getExternalFilesDir(null).getPath()+"/log.txt"));
				System.setOut(new PrintStream(new FileOutputStream(getExternalFilesDir(null).getPath()+"/debug.txt")));
				System.setErr(new PrintStream(new FileOutputStream(getExternalFilesDir(null).getPath()+"/error.txt")));
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
        AndroidApplicationConfiguration cfg=new AndroidApplicationConfiguration();
        cfg.disableAudio=debug&&!audio;
		cfg.useAccelerometer=false;
		cfg.useCompass=false;
        initialize(listener,cfg);
	}
	@Override
	public void log(String tag,String message,Throwable exception)
	{
		if(log!=null)
		{
			log.println(tag+" "+message);
			exception.printStackTrace(log);
		}
		else debug(tag,message,exception);
	}
	@Override
	public void log(String tag,String message)
	{
		if(log!=null)log.println(tag+" "+message);
		else debug(tag,message);
	}
	@Override
	public void debug(String tag,String message,Throwable exception)
	{
		System.out.println(tag+" "+message);
		exception.printStackTrace(System.out);
	}
	@Override
	public void debug(String tag,String message)
	{
		System.out.println(tag+" "+message);
	}
	@Override
	public void error(String tag,String message,Throwable exception)
	{
		System.err.println(tag+" "+message);
		exception.printStackTrace();
	}
	@Override
	public void error(String tag,String message)
	{
		System.err.println(tag+" "+message);
	}
	public class UnchaughtException implements Thread.UncaughtExceptionHandler
	{
		@Override
		public void uncaughtException(Thread p1,Throwable p2)
		{
			p2.printStackTrace();
			finish();
		}
	}
}
