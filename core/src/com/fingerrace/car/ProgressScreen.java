package com.fingerrace.car;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
 
public class ProgressScreen implements Screen {
 
  final SimpleGame game;
	OrthographicCamera camera;
	int highScore, cary, picType;
	Texture backgroundTextureProgress, tick, cross;
	OnePlayerScreen medium;
	TimeTrial timet;
	BitmapFont font;
	ArrayList<Texture> state;
 
	public ProgressScreen(final SimpleGame gam) {
		game = gam;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 360, 640);
		font = new BitmapFont(Gdx.files.internal("whitetext.fnt"),
		         Gdx.files.internal("whitetext.png"), false);
		backgroundTextureProgress = new Texture(Gdx.files.internal("progress.png"));
		tick = new Texture(Gdx.files.internal("tick.png"));
		cross = new Texture(Gdx.files.internal("no.png"));
		cary = 800;
		state = new ArrayList<Texture>();
		state.add(cross);
		state.add(tick);
		medium = new OnePlayerScreen(game);
		timet = new TimeTrial(game); 
	}
 
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0 / 255.0f, 191 / 255.0f, 255 / 255.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
 
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
 
		game.batch.begin();
		game.batch.draw(backgroundTextureProgress, 0, 0);
		font.draw(game.batch, "" + timet.getBestScoreTime() , 226, 622);
		game.batch.draw(state.get(picType), 169, cary);
		font.draw(game.batch, "=10" , 209, 511);
		font.draw(game.batch, "=30" , 209, 420);
		font.draw(game.batch, "=60" , 209, 322);
		font.draw(game.batch, "=90" , 209, 234);
		font.draw(game.batch, "=120" , 209, 149);
		game.batch.end();
 
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if((touchPos.x < 270) && (touchPos.x > 160) && (touchPos.y < 623) && (touchPos.y > 550) ){
				timet.setBestScoreTime(BigDecimal.ZERO);
			}
			else if((touchPos.x < 129) && (touchPos.x > 39) && (touchPos.y < 628) && (touchPos.y > 553)){
				dispose();
				game.setScreen(new MainMenuScreen(game));
			}
			else if((touchPos.x < 310) && (touchPos.x > 31) && (touchPos.y < 529) && (touchPos.y > 458) ){
				cary = 506;
				if(medium.getLevelNumber() >= 10){
					medium.setCarType("redviper");
					picType = 1;
				}
				else{
					picType = 0;
				}
			}
			else if((touchPos.x < 310) && (touchPos.x > 31) && (touchPos.y < 428) && (touchPos.y > 364) ){
				cary = 411;
				if(medium.getLevelNumber() >= 30){
					medium.setCarType("chevvy");
					picType = 1;
				}
				else{
					picType = 0;
				}
			}
			else if((touchPos.x < 310) && (touchPos.x > 31) && (touchPos.y < 343) && (touchPos.y > 266) ){
				cary = 314;
				if(medium.getLevelNumber() >= 60){
					medium.setCarType("mac");
					picType = 1;
				}
				else{
					picType = 0;
				}
			}
			else if((touchPos.x < 310) && (touchPos.x > 31) && (touchPos.y < 244) && (touchPos.y > 176) ){
				cary = 223;
				if(medium.getLevelNumber() >= 90){
					medium.setCarType("bike");
					picType = 1;
				}
				else{
					picType = 0;
				}
			}
			else if((touchPos.x < 310) && (touchPos.x > 31) && (touchPos.y < 158) && (touchPos.y > 88) ){
				cary = 137;
				if(medium.getLevelNumber() >= 120){
					medium.setCarType("plane");
					picType = 1;
				}
				else{
					picType = 0;
				}
			}
		}
	}
	
	@Override
	public void resize(int width, int height) {
	}
 
	@Override
	public void show() {
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
		backgroundTextureProgress.dispose();
		tick.dispose();
		cross.dispose();
		font.dispose();
	}
}

