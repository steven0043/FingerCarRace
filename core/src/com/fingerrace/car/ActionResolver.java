package com.fingerrace.car;

public interface ActionResolver {
	  public void showOrLoadInterstital();
	  public void tweetShare();
	  public boolean getSignedInGPGS();
	  public void loginGPGS();
	  public void submitScoreGPGS(int score);
	  public void unlockAchievementGPGS(String achievementId);
	  public void getLeaderboardGPGS();
	  public void getAchievementsGPGS();
	  public void gpOpen();
	}

