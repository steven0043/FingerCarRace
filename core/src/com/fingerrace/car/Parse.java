package com.fingerrace.car;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Preferences;
	
public class Parse implements HttpResponseListener {
	
	private URL url = null;
	private URLConnection conn = null;
	private String app_id;
	private String app_key;
	Preferences gotInfo;
	public Parse(){	
		try {
			gotInfo = Gdx.app.getPreferences("My Info");
			url = new URL("https://api.parse.com/1/classes/Users/");
			app_id = "l8XrEeZIZIZXRPgmdA3ET0vGWvjjDej4j6KSABko";
			app_key = "ZljwZGuLWNQwOZoi0B3PBMNrdY7ppYgbpZN8Ki3a";		
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void addInfo(String email, String number){
		
		// LibGDX NET CLASS
		HttpRequest httpPost = new HttpRequest(HttpMethods.POST);
		httpPost.setUrl("https://api.parse.com/1/classes/Users/");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("X-Parse-Application-Id", app_id);
		httpPost.setHeader("X-Parse-REST-API-Key", app_key);
		httpPost.setContent("{\"email\": \""+ email +"\" , \"number\": \""+ number +"\"}");
		this.setInfo(true);
		Gdx.net.sendHttpRequest(httpPost,Parse.this);
		//this.get_net_score();
	}
	
	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
     
	}

	@Override
	public void failed(Throwable t) {
		 System.out.println("FAILED MATE " + t.getMessage());
	}

	@Override
	public void cancelled() {
		// TODO Auto-generated method stub
		
	}
	
	public void setInfo(boolean info){
		gotInfo.putBoolean("info", info);
		gotInfo.flush();
	}
	
	public boolean getInfo(){
		return gotInfo.getBoolean("info");
	}
	
}


