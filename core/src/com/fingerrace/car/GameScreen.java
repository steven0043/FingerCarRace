package com.fingerrace.car;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

 
public class GameScreen implements Screen {
	  final SimpleGame game;
	    Random random = new Random();
	    int range;
	    int previous;
		OrthographicCamera camera;
		Rectangle carCord, touchCord;
		Array<Rectangle> opponentArray, lineArray;
		Array<Integer> twoNumbers = new Array<Integer>();;
		ArrayList<Texture> picArray = new ArrayList<Texture>();
		long lastOpponentTime, lastlineTime, scoreTime;
		int score, i, j;
		int highScore, spawnTime, lineTime;
		Preferences prefs;
		OnePlayerScreen one;
		Texture userCar, opponentTexture, opponentTwoTexture,opponentThreeTexture,opponentFourTexture, opponentFiveTexture, lines, star, touch;
		SpriteBatch spriteBatch;
	 
		public GameScreen(final SimpleGame gam) {
			this.game = gam;
			 prefs = Gdx.app.getPreferences("My Preferences");
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
			lines = new Texture(Gdx.files.internal("lines.png"));
			opponentTexture = new Texture(Gdx.files.internal("redviper.png"));
			opponentTwoTexture = new Texture(Gdx.files.internal("challenger.png"));
			opponentThreeTexture = new Texture(Gdx.files.internal("dodge.png"));
			opponentFourTexture = new Texture(Gdx.files.internal("championpunch.png"));
			opponentFiveTexture = new Texture(Gdx.files.internal("viper2.png"));
			star = new Texture(Gdx.files.internal("star.png"));
			touch = new Texture(Gdx.files.internal("touch.png"));
			range = 4;
			camera = new OrthographicCamera();
			camera.setToOrtho(false, 360, 640);
			picArray.add(opponentTexture);
			picArray.add(opponentTwoTexture);
			picArray.add(opponentThreeTexture);
			picArray.add(opponentFourTexture);
			picArray.add(opponentFiveTexture);
			carCord = new Rectangle();
			carCord.x = 118; 
			carCord.y = 290; 
			carCord.width = 45;
			carCord.height = 80;
			touchCord = new Rectangle();
			touchCord.x = 118; 
			touchCord.y = 195; 
			touchCord.width = 50;
			touchCord.height = 50;
			twoNumbers.add(29);
			twoNumbers.add(99);
			twoNumbers.add(188);
			twoNumbers.add(285);
			spawnTime = 899500000;
			lineTime = 700000000;
			opponentArray = new Array<Rectangle>();
			lineArray = new Array<Rectangle>();
			i = 200;
			j = 275;
			spawnLine();
		}
 
		private void spawnCar() {
			Rectangle opponent = new Rectangle();
		    opponent.x = twoNumbers.get(nextRnd()-1);
			opponent.y = 640;
			opponent.width = 50;
			opponent.height = 80;
			opponentArray.add(opponent);
			lastOpponentTime = TimeUtils.nanoTime();
			
		}
		
		private void spawnLine() {
			Rectangle lineRec = new Rectangle();
			lineRec.x = 0;
			lineRec.y = 640;
			lineArray.add(lineRec);
			lastlineTime = TimeUtils.nanoTime();
			
		}
		
		private void scoreAdd() {
			score++;
			if(score<65){
				spawnTime = spawnTime - 7350000;
			}
			if(score <88){
				i=i+4;
				j = j+4;
			}
			if (score%5 == 0){
				opponentTexture = picArray.get(MathUtils.random(0,4));
			}
			scoreTime = TimeUtils.nanoTime();
			
		}
		
		@Override
		public void render(float delta) {
			Gdx.gl.glClearColor(95 / 255.0f, 96 / 255.0f, 97 / 255.0f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			// tell the camera to update its matrices.
			camera.update();
	 
			// tell the SpriteBatch to render in the
			// coordinate system specified by the camera.
			game.batch.setProjectionMatrix(camera.combined);
	 
			game.batch.begin();
			game.font.draw(game.batch, "" + score, 7, 637);
			game.font.draw(game.batch, "" + prefs.getInteger("highscore"), 326, 638);
			game.batch.draw(star, 302, 615);
		
			for (Rectangle line : lineArray) {
				game.batch.draw(lines, line.x, line.y);
			}
			for (Rectangle opp : opponentArray) {
				game.batch.draw(opponentTexture, opp.x, opp.y);
			}
			game.batch.draw(userCar, carCord.x, carCord.y);
			game.batch.draw(touch, touchCord.x, touchCord.y);
			game.batch.end();
	 
			// process user input
			if (Gdx.input.isTouched()) {
				Vector3 touchPos = new Vector3();
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);
				if(touchPos.x <= 0 ){
					carCord.x = carCord.x;
					touchCord.x = touchCord.x;
				}
				else if(touchPos.x >= (360 - 45) ){
					carCord.x = carCord.x;
					touchCord.x = touchCord.x;
				}
				else if (touchPos.y < 475){
					//touchCord.x = touchPos.x;
					carCord.x = touchPos.x;
					touchCord.x = touchPos.x;
					touchCord.y = touchPos.y;
				}
				
			}
			// check if we need to create a new car
			if (TimeUtils.nanoTime() - lastOpponentTime > spawnTime)
				spawnCar();
			
			if (TimeUtils.nanoTime() - lastlineTime > lineTime)
				spawnLine();
			
			if (TimeUtils.nanoTime() - scoreTime > 1000000000 )
				scoreAdd();
			
			Iterator<Rectangle> iter = lineArray.iterator();
			while (iter.hasNext()) {
				Rectangle line = iter.next();
				line.y -= j * Gdx.graphics.getDeltaTime();
				if (line.x + 50 < 0)
					iter.remove();
			}
		
			Iterator<Rectangle> iter2 = opponentArray.iterator();
			while (iter2.hasNext()) {
				Rectangle opponent = iter2.next();
				opponent.y -= i * Gdx.graphics.getDeltaTime();
				if (opponent.x + 50 < 0)
					iter2.remove();
				if (opponent.overlaps(carCord)) {
					if(score > prefs.getInteger("highscore")){
						setBestScore(score);
					}
					dispose();
					one.dispose();
					game.setScreen(new MainMenuScreen(game));
					iter2.remove();
				}
			}
		}
		
		public void setBestScore(int x){
			highScore = x;
			prefs.putInteger("highscore", highScore);
			// bulk update your preferences
			prefs.flush();
		}
		int nextRnd() {
		    if (previous == 0) return previous = random.nextInt(range) + 1;
		    final int rnd = random.nextInt(range-1) + 1;
		    return previous = (rnd < previous? rnd : rnd + 1);
		  }
		public int getBestScore(){
			return highScore;
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
			userCar.dispose();
			lines.dispose();
			opponentTexture.dispose();
			opponentTwoTexture.dispose();
			opponentThreeTexture.dispose();
			opponentFourTexture.dispose();
			opponentFiveTexture.dispose();
			star.dispose();
			touch.dispose();
		}
	 
	}
