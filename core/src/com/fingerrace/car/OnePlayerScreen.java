package com.fingerrace.car;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

 
public class OnePlayerScreen implements Screen {
  final SimpleGame game;
	OrthographicCamera camera;
	Rectangle carRectangle, carTwoRectangle;
	Array<Rectangle> opponentArray;
	ArrayList<Texture> picArray = new ArrayList<Texture>();
	ArrayList<Texture> bgArray = new ArrayList<Texture>();
	long lastOpponentTime, lastImageTime;
	int score, scoreTwo, coord;
	int highScore, spawnTime, tap1, tap2, time, rndNum, levelNo, rndBG;
	Preferences prefs;
	Texture carTexture, carTwoTexture, opponentTwoTexture, opponentThreeTexture;
	Texture backgroundTexture, backgroundTexture2;
	boolean changeImage;
	String beaten;
	String carType = "";
	SpriteBatch spriteBatch;
	 
	public OnePlayerScreen(final SimpleGame gam) {
		this.game = gam;
		coord = 475;
		 prefs = Gdx.app.getPreferences("My Preferences");
		
		 if(getCarType().equals("redviper")){
			 carTexture = new Texture(Gdx.files.internal("redviper.png"));
		 }
		 else if(getCarType().equals("chevvy")){
			 carTexture = new Texture(Gdx.files.internal("chevvy.png"));
		 }
		 else if(getCarType().equals("mac")){
			 carTexture = new Texture(Gdx.files.internal("mac.png"));
		 }
		 else if(getCarType().equals("bike")){
			 carTexture = new Texture(Gdx.files.internal("bike.png"));
		 }
		 else if(getCarType().equals("plane")){
			 carTexture = new Texture(Gdx.files.internal("plane.png"));
		 }
		 else{
			 carTexture = new Texture(Gdx.files.internal("championpunch.png"));
		 }
		carTwoTexture = new Texture(Gdx.files.internal("challenger.png"));
		opponentTwoTexture = new Texture(Gdx.files.internal("dodge.png"));
		opponentThreeTexture = new Texture(Gdx.files.internal("viper2.png"));
		
		rndNum = MathUtils.random(0, 2);
		rndBG = MathUtils.random(0, 1);
		picArray.add(carTwoTexture);
		picArray.add(opponentTwoTexture);
		picArray.add(opponentThreeTexture);
		backgroundTexture = new Texture(Gdx.files.internal("road.png"));
		backgroundTexture2 = new Texture(Gdx.files.internal("sandroad.png"));
		bgArray.add(backgroundTexture);
		bgArray.add(backgroundTexture2);
		changeImage = false;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 360, 640);
		carRectangle = new Rectangle();
		carRectangle.x = 118; 
		carRectangle.y = 140; 
		carRectangle.width = 45;
		carRectangle.height = 80;
		carTwoRectangle = new Rectangle();
		carTwoRectangle.x = 195;
		carTwoRectangle.y = 140;
		carTwoRectangle.width = 45;
		carTwoRectangle.height = 80;
		spawnTime = 2000000000;
		
	}	
	
	public void moveCar(){
		carTwoRectangle.y = carTwoRectangle.y + 18;
		lastOpponentTime = TimeUtils.nanoTime();
	}
	@Override
	public void render(float delta) {
		// tell the camera to update its matrices.
		camera.update();
 
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);
 
		game.batch.begin();
		game.batch.draw(bgArray.get(rndBG), 0, 0);
		game.font.draw(game.batch, score + "-" + scoreTwo, 170, 531);
		game.batch.draw(carTexture, carRectangle.x, carRectangle.y);
		game.batch.draw(picArray.get(rndNum), carTwoRectangle.x, carTwoRectangle.y);
		game.batch.end();
		
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if(touchPos.y < coord){
				carRectangle.y = carRectangle.y + 18;
			}
		}
		
		if ((carRectangle.y > 483) && (carTwoRectangle.y > 483)){
			carRectangle.y = 141;
			carTwoRectangle.y = 141;
		}
		if (carRectangle.y > 483){
			score++;
			carRectangle.y = 141;
			carTwoRectangle.y = 141;
		}
		if (carTwoRectangle.y > 483){
			scoreTwo++;
			carRectangle.y = 141;
			carTwoRectangle.y = 141;
		}
		
		if(score == 3){
			setLevelNumber(getLevelNumber()+1);
			dispose();
			game.setScreen(new MainMenuScreen(game));
		}
		if(scoreTwo == 3){
			dispose();
			game.setScreen(new MainMenuScreen(game));
		}
		if (TimeUtils.nanoTime() - lastOpponentTime > getTime())
			moveCar();
	}
	
	public int getTime(){
		int getTime = 149000000-((this.getLevelNumber())*1375000);
		return getTime;
	}
	public void setLevelNumber(int level){
		if (game.actionResolver.getSignedInGPGS()){
			game.actionResolver.submitScoreGPGS(level); 
		}
		levelNo = level;
		prefs.putInteger("levelno", levelNo);
		prefs.flush();
	}
	
	public int getLevelNumber(){
		return prefs.getInteger("levelno");
	}
	public void setCarType(String x){
		carType = x;
		prefs.putString("cartype", carType);
		// bulk update your preferences
		prefs.flush();
	}
	
	public String getCarType(){
		return prefs.getString("cartype");
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
		//spriteBatch.dispose();
		carTexture.dispose();
		carTwoTexture.dispose();
		opponentTwoTexture.dispose();
		opponentThreeTexture.dispose();
		backgroundTexture.dispose();
		backgroundTexture2.dispose();
	}
 
}

