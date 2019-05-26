package com.island.mysmartpipe;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.*;
public class Titolo extends Stage implements Screen
{
	public Titolo()
	{
		super(MyGdxGame.game().view,MyGdxGame.game().batch);
		if(MyGdxGame.debug)setDebugAll(true);
		Image image=new Image(MyGdxGame.game().atlas.findRegion(getClass().getSimpleName()));
		image.setBounds(0,0,1000,1000);
		image.addListener(new Touch());
		addActor(image);
	}
	@Override
	public void render(float delta)
	{
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
	private class Touch extends InputListener
	{
		@Override
		public boolean touchDown(InputEvent event,float x,float y,int pointer,int button)
		{
			if(x>232&&x<508&&y>156&&y<314)MyGdxGame.game().setScreen(new Stanza(Titolo.this,"CUCINA"));
			return true;
		}
	}
	@Override
	public boolean keyDown(int keyCode)
	{
		if(keyCode==Input.Keys.BACK)
		{
			dispose();
			Gdx.app.exit();
		}
		return true;
	}
}
