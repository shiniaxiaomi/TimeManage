package model;

import gui.MainFrm;

import java.util.Timer;
import java.util.TimerTask;

import util.ChangeUtil;

public class Time {

	public MainFrm mainFrm=null;
	public Timer timer;
	
	public int time=0;
	
	public String timeString;
	private boolean isStarted=false;
	
	
	
	public Time(MainFrm mainFrm) {
		super();
		this.mainFrm = mainFrm;
	}


	public void star() {//一秒中计数一次
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				time++;
				mainFrm.todayAllTime++;
				timeString=ChangeUtil.changeTime(time);
				mainFrm.timeTxt.setText(timeString);
				mainFrm.todayAllTimeTxt.setText(ChangeUtil.changeTime(mainFrm.todayAllTime));
			}
		}, 0, 1000);
		
		setStarted(true);
		mainFrm.startButton.setText("停止计时");
		
	}

	
	public void stop() {//停止计数
		timer.cancel();

		setStarted(false);
		mainFrm.startButton.setText("开始计时");
	}


	public int getTime() {
		return time;
	}


	public void setTime(int time) {
		this.time = time;
	}


	public boolean isStarted() {
		return isStarted;
	}


	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}
	
	
	
	
	
	
	
}
