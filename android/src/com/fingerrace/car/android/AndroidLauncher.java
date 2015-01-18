package com.fingerrace.car.android;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.example.games.basegameutils.BaseGameActivity;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.fingerrace.car.ActionResolver;
import com.fingerrace.car.SimpleGame;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;

public class AndroidLauncher extends AndroidApplication implements ActionResolver, GameHelperListener {
	 private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-6937520742043004/1872248372";
	 private static final String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=com.fingerrace.car.android";
	 protected AdView adView;
	 protected View gameView;
	 int i, j;
	 private InterstitialAd interstitialAd;
	 InterstitialAd interstitial;
	 private static final String AD_UNIT_ID = "ca-app-pub-6937520742043004/1872248372";
	 private InterstitialAd interstitialAdStart;
	 private GameHelper gameHelper;
	 
	 //OnCreate initializes the layout and the AdMob ads.
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		if (gameHelper == null) {
			gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
			gameHelper.enableDebugLog(true);
		}
		gameHelper.setup(this);
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
    	RelativeLayout layout = new RelativeLayout(this);
    	RelativeLayout.LayoutParams gameViewParams =
    	new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
    	RelativeLayout.LayoutParams.WRAP_CONTENT);
    	View gameView = initializeForView(new SimpleGame(this), config);
    	layout.addView(gameView, gameViewParams);

    	AdView adView = new AdView(this);
    	adView.setAdUnitId("ca-app-pub-6937520742043004/4957569579");
    	adView.setAdSize(AdSize.BANNER);
    	interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(AD_UNIT_ID_INTERSTITIAL);
        showOrLoadInterstital();

    	RelativeLayout.LayoutParams adParams = 
    	new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
    	RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

    	layout.addView(adView, adParams);
    	
    	interstitialAdStart = new InterstitialAd(this);

        interstitialAdStart.setAdUnitId(AD_UNIT_ID);
        AdRequest adRequestIntStart = new AdRequest.Builder().build();

        interstitialAdStart.loadAd(adRequestIntStart);
        
        interstitialAdStart.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                    if (interstitialAdStart.isLoaded()) {
                        interstitialAdStart.show();
                    }

            }

            @Override
            public void onAdOpened() {


            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }
        });

        
    	AdRequest adRequest = new AdRequest.Builder().build();
    	adView.loadAd(adRequest);
    	setContentView(layout);
    	
	}
	
	public void showOrLoadInterstital() {
		j++;
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					if (interstitialAd.isLoaded() && (i<12) && (j%4==0)) {
						interstitialAd.show();
						i++;
					}
					else if (!interstitialAd.isLoaded()) {
						AdRequest interstitialRequest = new AdRequest.Builder().build();
						interstitialAd.loadAd(interstitialRequest);
					}
				}
			});
		} catch (Exception e) {
		}
	}
	
	//Load buttons on back pressed
	public void onBackPressed() {
	    final Dialog dialog = new Dialog(this);
	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    LinearLayout ll = new LinearLayout(this);
	    ll.setOrientation(LinearLayout.VERTICAL);
	    Button b1 = new Button(this);
	    b1.setText("Quit");
	    b1.setOnClickListener(new OnClickListener() {
	      public void onClick(View v) {
	        finish();
	      }
	    });
	    ll.addView(b1);

	    Button b2 = new Button(this);
	    b2.setText("Rate on Google Play");
	    b2.setOnClickListener(new OnClickListener() {
	      public void onClick(View v) {
	        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_URL)));
	        dialog.dismiss();
	      }
	    });
	    ll.addView(b2);

	    dialog.setContentView(ll);
	    dialog.show();
	  }
	
	//Share on twiiter intent
	@Override
	public void tweetShare() {
		String tweetUrl = "https://twitter.com/intent/tweet?text=OMG, this car game is amazing! https://play.google.com/store/apps/details?id=com.fingerrace.car.android&url="
                + "&hashtags=FingerCarRace,Android";
		Uri uri = Uri.parse(tweetUrl);
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
		
	}
	
	@Override
	public void onStart(){
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	public void onStop(){
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}
	
	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		System.out.println("Logging in");
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}

	@Override
	public void submitScoreGPGS(int score) {
		System.out.println("Submitting Score");
		Games.Leaderboards.submitScore(gameHelper.getApiClient(), "CgkI26LY_KcKEAIQBg", score);
	}
	
	@Override
	public void unlockAchievementGPGS(String achievementId) {
	  Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}
	
	@Override
	public void getLeaderboardGPGS() {
	  if (gameHelper.isSignedIn()) {
		System.out.println("Getting Leaderboard");
	    startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), "CgkI26LY_KcKEAIQBg"), 100);
	  }
	  else if (!gameHelper.isConnecting()) {
	    loginGPGS();
	  }
	}

	@Override
	public void getAchievementsGPGS() {
	  if (gameHelper.isSignedIn()) {
	    startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
	    
	  }
	  else if (!gameHelper.isConnecting()) {
	    loginGPGS();
	  }
	}
	
	@Override
	public void onSignInFailed() {
	}

	@Override
	public void onSignInSucceeded() {
	}
}
