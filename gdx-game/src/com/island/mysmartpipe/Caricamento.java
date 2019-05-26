package com.island.mysmartpipe;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
public class Caricamento extends Stage implements Screen
{
	private ProgressBar progresso;
	private Label bluetooth,asset;
	public Caricamento()
	{
		super(MyGdxGame.game().view,MyGdxGame.game().batch);
		if(MyGdxGame.debug)setDebugAll(true);
		Label label=new Label("CARICANDO...",new Label.LabelStyle(MyGdxGame.game().font,Color.WHITE));
		label.setBounds(0,800,1000,100);
		label.setAlignment(Align.center);
		label.setFontScale(2f);
		addActor(label);
		label=new Label("ASSETS:",new Label.LabelStyle(MyGdxGame.game().font,Color.WHITE));
		label.setBounds(200,400,300,100);
		label.setAlignment(Align.center);
		label.setFontScale(2f);
		addActor(label);
		label=new Label("BLUETOOTH:",new Label.LabelStyle(MyGdxGame.game().font,Color.WHITE));
		label.setBounds(200,200,300,100);
		label.setAlignment(Align.center);
		label.setFontScale(2f);
		addActor(label);
		label=new Label("OFF",new Label.LabelStyle(MyGdxGame.game().font,Color.WHITE));
		label.setBounds(500,200,300,100);
		label.setAlignment(Align.center);
		label.setFontScale(2f);
		addActor(label);
		bluetooth=label;
		label=new Label("0%",new Label.LabelStyle(MyGdxGame.game().font,Color.WHITE));
		label.setBounds(500,400,300,100);
		label.setAlignment(Align.center);
		label.setFontScale(2f);
		addActor(label);
		asset=label;
		progresso=new ProgressBar(0,2,0.0001f,false,new ProgressBar.ProgressBarStyle(MyGdxGame.colore(Color.RED),MyGdxGame.colore(Color.GREEN)));
		progresso.setBounds(200,600,600,100);
		progresso.getStyle().background.setMinHeight(progresso.getHeight());
		progresso.getStyle().knob.setMinHeight(progresso.getHeight());
		progresso.getStyle().knobBefore=progresso.getStyle().knob;
		progresso.setAnimateDuration(0.5f);
		addActor(progresso);
	}
	@Override
	public void render(float delta)
	{
		boolean attivo=MyGdxGame.game().adapter.isEnabled();
		float caricato=MyGdxGame.game().asset.getProgress();
		progresso.setValue(caricato+(attivo?1:0));
		asset.setText((caricato*100)+"%");
		bluetooth.setText((attivo?"ON":"OFF"));
		if(progresso.getVisualValue()==2)
		{
			MyGdxGame.game().atlas=MyGdxGame.game().asset.get("atlas.atlas");
			MyGdxGame.game().setScreen(new Titolo());
		}
		else MyGdxGame.game().asset.update();
		act(delta);
		draw();
	}
	@Override
	public void resize(int p1,int p2)
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
}
