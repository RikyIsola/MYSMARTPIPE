package com.island.mysmartpipe;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import java.io.*;
public class Info extends Stage implements Screen
{
	private Stanza prev;
	@Override
	public Info(Stanza prev)
	{
		super(MyGdxGame.game().view,MyGdxGame.game().batch);
		this.prev=prev;
		if(MyGdxGame.debug)setDebugAll(true);
		Image img=new Image(MyGdxGame.game().atlas.findRegion(getClass().getSimpleName()));
		img.setBounds(0,0,1000,1000);
		addActor(img);
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
	@Override
	public boolean keyDown(int keyCode)
	{
		if(keyCode==com.badlogic.gdx.Input.Keys.BACK)MyGdxGame.game().setScreen(prev);
		return true;
	}
}
