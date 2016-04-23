package com.fingerrace.car;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
 
public class MainMenuScreen implements Screen {
 
  final SimpleGame game;
	OrthographicCamera camera;
	Texture backgroundTexture;
	OnePlayerScreen getLevel;
	BitmapFont font;
 
	public MainMenuScreen(final SimpleGame gam) {
		game = gam;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 360, 640);
		font = new BitmapFont(Gdx.files.internal("whitetext.fnt"),
		         Gdx.files.internal("whitetext.png"), false);
		backgroundTexture = new Texture(Gdx.files.internal("roadmain.png"));
		getLevel = new OnePlayerScreen(game);
	}
 
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0 / 255.0f, 191 / 255.0f, 255 / 255.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
 
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
 
		game.batch.begin();
		game.batch.draw(backgroundTexture, 0, 0);
		font.draw(game.batch, "" + getLevel.getLevelNumber() , 234, 627);
		game.batch.end();
 
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if((touchPos.x < 338) && (touchPos.x > 23) && (touchPos.y < 551) && (touchPos.y > 474) ){
				dispose();
				getLevel.dispose();
				game.setScreen(new OnePlayerScreen(game));
			}
			if((touchPos.x < 225) && (touchPos.x > 125) && (touchPos.y < 298) && (touchPos.y > 221) ){
				dispose();
				getLevel.dispose();
				game.setScreen(new GameScreen(game));
			}
			if(((touchPos.x < 338) && (touchPos.x > 23) && (touchPos.y < 425) && (touchPos.y > 347)) ){
				dispose();
				getLevel.dispose();
				game.setScreen(new TimeTrial(game));
			}
			if((touchPos.x < 125) && (touchPos.x > 60) && (touchPos.y < 625) && (touchPos.y > 564) ){
				dispose();
				getLevel.dispose();
				game.setScreen(new ProgressScreen(game));
			}
			if((touchPos.x < 316) && (touchPos.x > 206) && (touchPos.y < 196) && (touchPos.y > 159) ){
				game.actionResolver.tweetShare();
			}
			if((touchPos.x < 152) && (touchPos.x > 44) && (touchPos.y < 193) && (touchPos.y > 158) ){
				game.actionResolver.getLeaderboardGPGS();
			}
			if((touchPos.x < 232) && (touchPos.x > 122) && (touchPos.y < 149) && (touchPos.y > 107) ){
				game.actionResolver.gpOpen();
			}
		}
	}
	
	@Override
	public void resize(int width, int height) {
	}
 
	@Override
	public void show() {
		game.actionResolver.showOrLoadInterstital();
	}
 
	@Override
	public void hide() {
	}
 
	@Override
	public void pause() {
	}
 
	@Override
	public void resume() {
	}
 
	@Override
	public void dispose() {
		backgroundTexture.dispose();
		font.dispose();
	}
}

