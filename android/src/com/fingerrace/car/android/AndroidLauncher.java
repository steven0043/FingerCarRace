package com.fingerrace.car.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.fingerrace.car.ActionResolver;
import com.fingerrace.car.SimpleGame;

public class AndroidLauncher extends AndroidApplication implements ActionResolver {
	 private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-6937520742043004/1872248372";
	 private static final String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=com.fingerrace.car.android";
	 protected View gameView;
	 int i, j;
	 
	 //OnCreate initializes the layout and the AdMob ads.
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
    	RelativeLayout layout = new RelativeLayout(this);
    	RelativeLayout.LayoutParams gameViewParams =
    	new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
    	RelativeLayout.LayoutParams.WRAP_CONTENT);
    	View gameView = initializeForView(new SimpleGame(this), config);
    	layout.addView(gameView, gameViewParams);

    	RelativeLayout.LayoutParams adParams = 
    	new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
    	RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

    	setContentView(layout);

	}

	public void showOrLoadInterstital() {

	}
	
	//Share on twiiter intent
	@Override
	public void tweetShare() {
		String tweetUrl = "https://twitter.com/intent/tweet?text=This car game is amazing! https://play.google.com/store/apps/details?id=com.fingerrace.car.android&url="
                + "&hashtags=FingerCarRace,Android";
		Uri uri = Uri.parse(tweetUrl);
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
		
	}

	@Override
	public boolean getSignedInGPGS() {
		return false;
	}

	@Override
	public void loginGPGS() {

	}

	@Override
	public void submitScoreGPGS(int score) {

	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {

	}

	@Override
	public void getLeaderboardGPGS() {

	}

	@Override
	public void getAchievementsGPGS() {

	}

	public void gpOpen() {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_URL)));
	}
	
	
	@Override
	public void onStart(){
		super.onStart();
	}

	@Override
	public void onStop(){
		super.onStop();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
	}

}
