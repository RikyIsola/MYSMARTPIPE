package com.island.mysmartpipe;
import android.bluetooth.*;
import android.content.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.viewport.*;
public class MyGdxGame extends Game
{
	public Batch batch;
	public Context context;
	public BluetoothAdapter adapter;
	public BitmapFont font;
	public Viewport view;
	public AssetManager asset;
	public TextureAtlas atlas;
	public Skin skin;
	public static final String colori="pix";
	public static final boolean debug=true;
	public static final boolean suono=true;
	private FPSLogger fps=new FPSLogger();
	public MyGdxGame(Context c)
	{
		context=c;
		adapter=BluetoothAdapter.getDefaultAdapter();
	}
	@Override
	public void create()
	{
		asset=new AssetManager();
		asset.load("atlas.atlas",TextureAtlas.class);
		Pixmap pix=new Pixmap(1,1,Pixmap.Format.RGBA8888);
		pix.setColor(Color.WHITE);
		pix.fill();
		skin=new Skin();
		skin.add(colori,new Texture(pix));
		pix.dispose();
		Gdx.input.setCatchBackKey(true);
		batch=new SpriteBatch();
		font=new BitmapFont();
		view=new StretchViewport(1000,1000);
		adapter.enable();
		setScreen(new Caricamento());
	}
	@Override
	public void resize(int width,int height)
	{
		view.update(width,height);
		super.resize(width,height);
	}
	@Override
	public void render()
	{
		fps.log();
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}
	@Override
	public void dispose()
	{
		asset.dispose();
		adapter.disable();
		batch.dispose();
		font.dispose();
		skin.dispose();
		super.dispose();
	}
	public static MyGdxGame game()
	{
		return(MyGdxGame)Gdx.app.getApplicationListener();
	}
	public static Drawable colore(Color colore)
	{
		return game().skin.newDrawable(colori,colore);
	}
}
