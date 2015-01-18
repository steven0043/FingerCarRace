package com.fingerrace.car;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SimpleGame extends Game {
	public ActionResolver actionResolver;
	 
	  public SpriteBatch batch;
		public BitmapFont font;
	
		public SimpleGame(ActionResolver actionResolver){
			this.actionResolver = actionResolver;
		}
 
		public void create() {
			batch = new SpriteBatch();
			// Use LibGDX's default Arial font.
			font = new BitmapFont();
			font.setScale(1, 2);
			this.setScreen(new MainMenuScreen(this));
		}
	 
		public void render() {
			super.render(); // important!
		}
	 
		public void dispose() {
			batch.dispose();
			font.dispose();
		}
	 
	}