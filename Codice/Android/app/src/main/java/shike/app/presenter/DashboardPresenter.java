package shike.app.presenter;

import android.os.Handler;
import android.os.SystemClock;
import android.text.format.Time;
import android.widget.Button;

import java.util.Vector;

import shike.app.R;
import shike.app.view.session.DashboardView;
import shike.app.view.session.DialogConfirmSaveTrack;
import shike.app.view.session.WidgetSelectionView;

/**
 * Created by Luca on 21/05/2015.
 */
public class DashboardPresenter {
	private Time today = new Time(Time.getCurrentTimezone());
	private NavigationActivity activity;
	private TimerThread timer;
	private Vector<ButtonThread> containerThread = new Vector<>();
	private Handler customHandler = new Handler();
	private Button selectedButton;
	private DashboardView dashboardView;
	private CompassPresenter compassPresenter;
	private boolean isRunning = false;
	private boolean isActivated = false;

	public DashboardPresenter(NavigationActivity act) {
		activity = act;
		dashboardView = activity.getDashboardView();
		compassPresenter = activity.getCompassPresenter();
		timer = new TimerThread();
	}

	public void setWidget(Button b, String name) {
		ButtonThread bt = new ButtonThread(b, name);
		customHandler.postDelayed(bt, 0);
		containerThread.add(bt);
	}

	public void changeWidget(String name) {
		for (int i = 0; i < containerThread.size(); i++) {
			ButtonThread thread = containerThread.get(i);
			if (thread.getFunction().equals(name)) {
				thread.setFunction("");
			}
			if (thread.getButton().equals(selectedButton)) {
				thread.setFunction(name);
			}
		}
	}

	public void onLongClick(Button b) {
		selectedButton = b;
		WidgetSelectionView selectionView =
			new WidgetSelectionView(compassPresenter.isNavigationEnabled());
		selectionView.setStyle(1, 0);
		selectionView.setTargetFragment(dashboardView.getParentFragment(), 0);
		selectionView.show(dashboardView.getFragmentManager(), "Dialog Fragment");
	}

	public void onStop(DialogConfirmSaveTrack dialog, Button button) {
		dialog.setStyle(1, 0);
		dialog.setTargetFragment(dashboardView.getParentFragment(), 0);
		dialog.show(dashboardView.getFragmentManager(), "DialogYesNo");
		if (isRunning()) {
			button.setText(startPauseRun());
		}
	}

	public String startPauseRun() {
		if (isRunning == false) {
			compassPresenter.setSaveEnabled(true);
			timer.startTime();
			customHandler.postDelayed(timer, 0);
			isRunning = true;
			return dashboardView.getActivity().getString(R.string.pause);
		}
		compassPresenter.setSaveEnabled(false);
		timer.reloadTimeSwapBuff();
		customHandler.removeCallbacks(timer);
		isRunning = false;
		return dashboardView.getActivity().getString(R.string.start);
	}

	public void stopSaveRun() {
		compassPresenter.finishTrack();
		customHandler.removeCallbacks(timer);
		isRunning = false;
	}

	private class TimerThread implements Runnable {
		private int secs;
		private int mins;
		private int hours;
		private String timer;
		private long startTime = 0L;
		private long timeInMilliseconds = 0L;
		private long timeSwapBuff = 0L;
		private long updatedTime = 0L;

		public void run() {
			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
			updatedTime = timeSwapBuff + timeInMilliseconds;
			secs = (int) (updatedTime / 1000);
			mins = secs / 60;
			hours = mins / 60;
			secs = secs % 60;
			mins = mins % 60;
			reloadValueTimer();
			customHandler.postDelayed(this, 0);
		}

		public void reloadValueTimer() {
			timer = hours + ":" + String.format("%02d", mins) + ":" + String.format("%02d", secs);
		}

		public void startTime() {startTime = SystemClock.uptimeMillis();}

		public void reloadTimeSwapBuff() {timeSwapBuff += timeInMilliseconds;}

		public long getUpdatedTime() {return updatedTime;}

		public String getTimer() {return timer;}
	}

	private class ButtonThread implements Runnable {
		private Button button;
		private String function;
		private String firstFunction;

		ButtonThread(Button b, String f) {
			button = b;
			function = f;
		}

		@Override
		public void run() {
			String text = "";
			if (function.equals(activity.getString(R.string.dashboardItemClose))) {
				function = firstFunction;
			}
			if (function.equals(activity.getString(R.string.dashboardItemTimer))) {
				text = timer.getTimer();
			}
			if (function.equals(activity.getString(R.string.dashboardItemClock))) {
				today.setToNow();
				text = today.format("%k:%M:%S");
			}
			if (function.equals(activity.getString(R.string.dashboardItemDate))) {
				today.setToNow();
				text = today.monthDay + "/" + today.month + "/" + today.year;
			}
			if (function.equals(activity.getString(R.string.dashboardItemSpeed))) {
				text = compassPresenter
					.returnStringNormalized(compassPresenter.getActualSpeed() * 3.6) + " km/h";
			}
			if (function.equals(activity.getString(R.string.dashboardItemDistanceNextpoint))) {
				text = compassPresenter
					.returnStringNormalized(compassPresenter.getDistancePoint() / 1000) + " km";
			}
			if (function.equals(activity.getString(R.string.dashboardItemDistanceRemain))) {
				text = compassPresenter.returnStringNormalized(compassPresenter
					.getRemainingTotalDistance() / 1000) + " km";
			}
			if (function.equals(activity.getString(R.string.dashboardItemDistanceTotal))) {
				text = compassPresenter.returnStringNormalized(compassPresenter.getTotalDistance
					() / 1000) + " km";
			}
			if (function.equals(activity.getString(R.string.dashboardItemAltitude))) {
				text = compassPresenter.returnStringNormalized(compassPresenter.getAltitude()) + " m";
			}
			if (function.equals(activity.getString(R.string.dashboardItemStepCounter))) {
				int passi = compassPresenter.getStepCount();
				String step = " passi";
				if(passi == 1) step = " passo";
				text = passi + step;
			}
			if (function.equals(activity.getString(R.string.dashboardEstimateTimeArrival))) {
				double value = compassPresenter.getRemainingTime();
				int mins = (int) value / 60;
				int hours = mins / 60;
				mins = mins % 60;
				text = "h:" + hours + " m:" + String.format("%02d", mins);
			}
			button.setText(text);
			customHandler.postDelayed(this, 500);
		}

		public void setFunction(String f) {
			firstFunction = function;
			function = f;
		}

		public String getFunction() {return function;}

		public Button getButton() {
			return button;
		}
	}

	public long getTimer() {return timer.getUpdatedTime();}

	public boolean isActivated() {return isActivated;}

	public void setActivated(boolean activated) {this.isActivated = activated;}

	public boolean isRunning() {return isRunning;}
}
