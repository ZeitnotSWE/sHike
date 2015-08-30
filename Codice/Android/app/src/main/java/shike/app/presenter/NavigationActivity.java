package shike.app.presenter;

/**
 * Created by Luca on 11/05/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import shike.app.R;
import shike.app.view.session.CompassView;
import shike.app.view.session.DashboardView;
import shike.app.view.weather.WeatherSelectionView;
import shike.app.view.helpnumber.HelpNumberSelectionView;
import shike.app.view.poi.PoiSelectionView;


public class NavigationActivity extends FragmentActivity {
	private DashboardView dashboardView;
	private CompassView compassView;
	private WeatherSelectionView weatherView;
	private PoiSelectionView poiView;
	private HelpNumberSelectionView numberView;
	private DashboardPresenter dashboardPresenter;
	private CompassPresenter compassPresenter;
	private PoiPresenter poiPresenter;
	private WeatherPresenter weatherPresenter;

	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent mIntent = getIntent();
		id = mIntent.getIntExtra("id", 0);

		compassPresenter = new CompassPresenter(this, id);
		compassView = new CompassView(compassPresenter);

		dashboardView = new DashboardView();
		dashboardPresenter = new DashboardPresenter(this);
		dashboardView.setPresenter(dashboardPresenter);

		numberView = new HelpNumberSelectionView();
		HelpNumberPresenter helpNumberPresenter = new HelpNumberPresenter(this);
		numberView.setPresenter(helpNumberPresenter);

		poiPresenter = new PoiPresenter(this, compassPresenter);
		poiView = new PoiSelectionView();
		poiView.setPresenter(poiPresenter);
		poiPresenter.setView(poiView);


		ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

		if (id != 0) {
			weatherPresenter = new WeatherPresenter(this, id);
			weatherView = new WeatherSelectionView();
			weatherView.setPresenter(weatherPresenter);
			weatherPresenter.setView(weatherView);
		}
	}

	/**
	 * Invia una stringa alla dashboard
	 * @param s stringa da inviare al fragment della dashboard
	 */
	public void sendStringToDashFragment(String s) {
		dashboardView.returnStringFromDialog(s);
	}

	public DashboardView getDashboardView() {return dashboardView;}

	public DashboardPresenter getDashboardPresenter() {return dashboardPresenter;}

	public CompassPresenter getCompassPresenter() {return compassPresenter;}

	public WeatherSelectionView getWeatherView() {return weatherView;}

	public PoiSelectionView getPoiSelection() {return poiView;}

	public HelpNumberSelectionView getHelpNumber() {return numberView;}

	public PoiPresenter getPoiPresenter() {return poiPresenter;}

	public WeatherPresenter getWeatherPresenter() {return weatherPresenter;}

	private class MyPagerAdapter extends FragmentPagerAdapter {
		private int num = 4;

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			if (id != 0) {
				num = 5;
			}
		}

		@Override
		public Fragment getItem(int pos) {
			switch (pos) {
				case 0:
					return dashboardView;
				case 1:
					return compassView;
				case 2:
					return numberView;
				case 3:
					return poiView;
				case 4:
					return weatherView;
				default:
					return dashboardView;
			}
		}

		@Override
		public int getCount() {
			return num;
		}
		
	}


}
