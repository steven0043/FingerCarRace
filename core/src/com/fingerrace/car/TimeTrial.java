package com.fingerrace.car;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

 
public class TimeTrial implements Screen {
  final SimpleGame game;
	MainMenuScreen gameOver;
	OrthographicCamera camera;
	Rectangle carRectangle, carTwoRectangle;
	Array<Integer> twoNumbers = new Array<Integer>();;
	ArrayList<Texture> picArray = new ArrayList<Texture>();
	long lastOpponentTime, lastImageTime;
	int score, scoreTwo;
	int highScore, spawnTime, tap1, tap2, time;
	Preferences prefs;
	Texture userCar, carTwoTexture, opponentTwoTexture;
	float currentTime, bestTime, initCheck;
	Texture backgroundTexture;
	boolean changeImage;
	Sprite backgroundSprite;
	String beaten, difficulty;
	OnePlayerScreen one;
	SpriteBatch spriteBatch;
	BigDecimal test, anotherbd;
	 
	public TimeTrial(final SimpleGame gam) {
		this.game = gam;
		prefs = Gdx.app.getPreferences("My Preferences");
		one = new OnePlayerScreen(game);
		one = new OnePlayerScreen(game);
		if(one.getCarType().equals("redviper")){
			userCar = new Texture(Gdx.files.internal("redviper.png"));
		}
		else if(one.getCarType().equals("chevvy")){
			userCar = new Texture(Gdx.files.internal("chevvy.png"));
		}
		else if(one.getCarType().equals("mac")){
			userCar = new Texture(Gdx.files.internal("mac.png"));
		}
		else if(one.getCarType().equals("bike")){
			userCar = new Texture(Gdx.files.internal("bike.png"));
		}
		else if(one.getCarType().equals("plane")){
			userCar = new Texture(Gdx.files.internal("plane.png"));
		}
		else{
			userCar = new Texture(Gdx.files.internal("championpunch.png"));
		}
		carTwoTexture = new Texture(Gdx.files.internal("challenger.png"));
		picArray.add(carTwoTexture);
		picArray.add(opponentTwoTexture);
		backgroundTexture = new Texture(Gdx.files.internal("road.png"));
		changeImage = false;
		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 360, 640);

		test = new BigDecimal("0.1");
		carRectangle = new Rectangle();
		carRectangle.x = 118; 
		carRectangle.y = 140; 
		carRectangle.width = 45;
		carRectangle.height = 80;
		twoNumbers.add(118);
		twoNumbers.add(195);
		spawnTime = (1000000000/10);
	}	
	
	public void moveCar(){
		test = test.add(new BigDecimal("0.1"));
		currentTime = (float) (currentTime + 0.1);
		lastOpponentTime = TimeUtils.nanoTime();
	}
	
	public void checkTime(){
		if((getBestScoreTime() == initCheck)){
			setBestScoreTime(test);
		}
		if(test.floatValue() < getBestScoreTime() && !(getBestScoreTime() == initCheck)){
			setBestScoreTime(test);
		}
		score++;
	}
	
	public void newBD(BigDecimal test) {
	    BigDecimal zero = BigDecimal.ZERO;
	    if (test.compareTo(zero) > 0)
	        test = zero;
	}
	
	@Override
	public void render(float delta) {
 
		// tell the camera to update its matrices.
		camera.update();
 
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);
 
		game.batch.begin();
		game.batch.draw(backgroundTexture, 0, 0);
		game.font.draw(game.batch, test + "", 170, 531);
		game.batch.draw(userCar, carRectangle.x, carRectangle.y);
		game.batch.end();
		
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if(touchPos.y < 580){
				carRectangle.y = carRectangle.y + 18;
			}
		}
		
		if (carRectangle.y > 483){
			carRectangle.y = 141;
			checkTime();
			test = BigDecimal.ZERO;
		}
		
		if(score == 3){
			dispose();
			one.dispose();
			game.setScreen(new MainMenuScreen(game));
		}
		
		if (TimeUtils.nanoTime() - lastOpponentTime > spawnTime)
			moveCar();
	}
	
	public void setBestScoreTime(BigDecimal x){
		anotherbd = x;
		bestTime = anotherbd.floatValue();
		prefs.putFloat("timetrial", bestTime);
		// bulk update your preferences
		prefs.flush();
	}
	public Float getBestScoreTime(){
		return prefs.getFloat("timetrial");
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
		backgroundTexture.dispose();
		userCar.dispose();
		carTwoTexture.dispose();
		//spriteBatch.dispose();
	}
 
}

