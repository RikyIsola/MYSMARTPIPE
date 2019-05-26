package com.island.mysmartpipe;
import android.bluetooth.*;
import android.content.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.*;
public class Ricerca extends Stage implements Screen
{
	private Titolo prevprev;
	private Stanza prev;
	private Attivazione a;
	private int index;
	private Label testo;
	public Ricerca(Stanza prev,Titolo prevprev)
	{
		super(MyGdxGame.game().view,MyGdxGame.game().batch);
		this.prev=prev;
		this.prevprev=prevprev;
		if(MyGdxGame.debug)setDebugAll(true);
		Image img=new Image(MyGdxGame.game().atlas.findRegion(getClass().getSimpleName()));
		img.setBounds(0,0,1000,1000);
		addActor(img);
		testo=new Label("CERCANDO...",new Label.LabelStyle(MyGdxGame.game().font,Color.BLACK));
		testo.setBounds(317,904,571,72);
		testo.setFontScale(2f);
		addActor(testo);
	}
	@Override
	public void render(float delta)
	{
		testo.setVisible(MyGdxGame.game().adapter.isDiscovering());
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
		MyGdxGame.game().adapter.cancelDiscovery();
		Gdx.input.setInputProcessor(this);
		a=new Attivazione();
		MyGdxGame.game().context.registerReceiver(a,new IntentFilter(BluetoothDevice.ACTION_FOUND));
		MyGdxGame.game().adapter.startDiscovery();
	}
	@Override
	public void hide()
	{
		MyGdxGame.game().context.unregisterReceiver(a);
		dispose();
	}
	@Override
	public void pause()
	{
	}
	@Override
	public void resume()
	{
	}
	private class Touch extends InputListener
	{
		private BluetoothDevice bd;
		public Touch(BluetoothDevice bd)
		{
			this.bd=bd;
		}
		public boolean touchDown(InputEvent event,float x,float y,int pointer,int button)
		{
			prev.pref.putString(Stanza.dove,bd.getAddress());
			prev.pref.flush();
			MyGdxGame.game().setScreen(prev);
			return true;
		}
	}
	private class Attivazione extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context c,Intent i)
		{
			if(i.getAction().equals(BluetoothDevice.ACTION_FOUND)&&index!=3)
			{
				BluetoothDevice bd=i.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				String s=bd.getName();
				if(s==null)s=bd.getAddress();
				Label label=new Label(s,new Label.LabelStyle(MyGdxGame.game().font,Color.BLUE));
				label.setBounds(441,68+((195+23+22)*index),403,195);
				label.addListener(new Touch(bd));
				label.setFontScale(2f);
				addActor(label);
				index++;
			}
		}
	}
	@Override
	public boolean keyDown(int keyCode)
	{
		if(keyCode==Input.Keys.BACK)
		{
			prev.dispose();
			MyGdxGame.game().setScreen(prevprev);
		}
		return true;
	}
}
