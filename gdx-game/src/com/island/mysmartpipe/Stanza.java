package com.island.mysmartpipe;
import android.bluetooth.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.*;
import java.io.*;
import java.util.*;
import com.badlogic.gdx.utils.StringBuilder;
public class Stanza extends Stage implements Screen
{
	private BluetoothSocket bs;
	private String nome;
	public Preferences pref;
	private Label acqua,attuale,testo;
	private TextField limite;
	private float tempo;
	private int comando;
	private OutputStream out;
	private InputStream in;
	private StringBuilder sb=new StringBuilder();
	public Titolo prev;
	private boolean disposed,help;
	public static final String dove="INDIRIZZO";
	@Override
	public Stanza(Titolo prev,String nome)
	{
		super(MyGdxGame.game().view,MyGdxGame.game().batch);
		addListener(new Touch());
		pref=Gdx.app.getPreferences(nome);
		this.nome=nome;
		this.prev=prev;
		Image img=new Image(MyGdxGame.game().atlas.findRegion(getClass().getSimpleName()));
		img.setBounds(0,0,1000,1000);
		addActor(img);
		TextField f=new TextField("0.1",new TextField.TextFieldStyle(MyGdxGame.game().font,Color.BLUE,null,null,null));
		f.setTextFieldListener(new Keyboard());
		f.setBounds(553,404,257,140);
		f.getStyle().font.scale(2);
		addActor(f);
		limite=f;
		Label l=new Label("OL",new Label.LabelStyle(MyGdxGame.game().font,Color.BLUE));
		l.setBounds(553,124,257,140);
		l.setAlignment(Align.center);
		l.setFontScale(2f);
		addActor(l);
		attuale=l;
		l=new Label("OL",new Label.LabelStyle(MyGdxGame.game().font,Color.BLUE));
		l.setBounds(553,264,257,140);
		l.setAlignment(Align.center);
		l.setFontScale(2f);
		addActor(l);
		acqua=l;
		testo=new Label("CONNETTENDO...",new Label.LabelStyle(MyGdxGame.game().font,Color.BLACK));
		testo.setBounds(317,904,571,72);
		testo.setFontScale(2f);
		addActor(testo);
	}
	@Override
	public void render(float delta)
	{
		testo.setVisible(in==null);
		if(in!=null)
		{
			tempo-=delta;
			if(tempo<=0&&comando==0)
			{
				tempo=1;
				try
				{
					out.write(97);
					out.write(98);
					comando=2;
				}
				catch(IOException e)
				{
					e.printStackTrace(System.out);
				}
			}
			if(comando>0)
			{
				try
				{
					int avaible=in.available();
					for(int a=0;a<avaible;a++)
					{
						int letto=in.read();
						if(letto==10)
						{
							if(comando==2)acqua.setText(sb.append("L").toString());
							else if(comando==1)attuale.setText(sb.append("L/h").toString());
							else if(comando==3)
							{
								limite.setText(sb.toString());
								comando=1;
							}
							sb.setLength(0);
							comando--;
						}
						else sb.append((char)letto);
					}
				}
				catch(IOException e)
				{
					e.printStackTrace(System.out);
				}
			}
		}
		act(delta);
		draw();
	}
	@Override
	public void resize(int w,int h)
	{
	}
	@Override
	public void show()
	{
		Gdx.input.setInputProcessor(this);
		if(MyGdxGame.debug)setDebugAll(true);
		String indirizzo=pref.getString(dove,null);
		if(indirizzo==null)MyGdxGame.game().setScreen(new Ricerca(this,prev));
		else if(!help)connetti(MyGdxGame.game().adapter.getRemoteDevice(indirizzo));
		else help=false;
	}
	@Override
	public void hide()
	{
	}
	@Override
	public void pause()
	{
	}
	@Override
	public void resume()
	{
	}
	@Override
	public void dispose()
	{
		try
		{
			System.out.println("dispose");
			MyGdxGame.game().font.setScale(1);
			disposed=true;
			bs.close();
		}
		catch(IOException e)
		{
			e.printStackTrace(System.out);
		}
		super.dispose();
	}
	public void connetti(BluetoothDevice device)
	{
		new Thread(new Connetti(device)).start();
	}
	@Override
	public boolean keyDown(int keyCode)
	{
		if(keyCode==com.badlogic.gdx.Input.Keys.BACK)
		{
			dispose();
			MyGdxGame.game().setScreen(prev);
		}
		return true;
	}
	private class Touch extends InputListener
	{
		@Override
		public boolean touchDown(InputEvent event,float x,float y,int pointer,int button)
		{
			if(x>115&&x<237&&y>638&&y<708)
			{
				MyGdxGame.game().setScreen(new Info(Stanza.this));
				help=true;
			}
			return super.touchDown(event,x,y,pointer,button);
		}
	}
	private class Keyboard implements TextField.TextFieldListener
	{
		@Override
		public void keyTyped(TextField t,char c)
		{
			System.out.println(c+t.getText());
			if(c=='\n'&&out!=null)
			{
				try
				{
					Double.valueOf(t.getText());
					out.write(100);
					out.write(t.getText().getBytes());
					out.write(' ');
				}
				catch(IOException e)
				{
					e.printStackTrace(System.out);
				}
				catch(NumberFormatException e)
				{
					e.printStackTrace(System.out);
				}
			}
		}
	}
	private class Connetti implements Runnable
	{
		public Connetti(BluetoothDevice device)
		{
			this.device=device;
		}
		public BluetoothDevice device;
		public void run()
		{
			try
			{
				Thread.sleep(1);
				bs=device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
				MyGdxGame.game().adapter.cancelDiscovery();
				bs.connect();
				OutputStream o=bs.getOutputStream();
				o.write(99);
				comando=3;
				out=o;
				in=bs.getInputStream();
			}
			catch(IOException e)
			{
				e.printStackTrace(System.out);
				if(!disposed)Gdx.app.postRunnable(new Eccezione());
			}
			catch(InterruptedException e)
			{
				e.printStackTrace(System.out);
			}
		}
	}
	private class Eccezione implements Runnable
	{
		public void run()
		{
			MyGdxGame.game().setScreen(new Ricerca(Stanza.this,prev));
		}
	}
}
