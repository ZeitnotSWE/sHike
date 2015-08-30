package instrumentTest;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import shike.app.model.weather.Weather;
import shike.app.presenter.NavigationActivity;
import shike.app.presenter.WeatherPresenter;
import shike.app.presenter.HomeActivity;
import shike.app.view.weather.WeatherSelectionView;

/**
 * Created by andrea on 12/06/2015.
 */
public class WeatherPresenterTest extends ActivityInstrumentationTestCase2<HomeActivity> {
	HomeActivity mAct;
	NavigationActivity receiverActivity;
	WeatherPresenter presenter;
	WeatherSelectionView view;

	public WeatherPresenterTest(Class<HomeActivity> activityClass) {
		super(activityClass);
	}

	public WeatherPresenterTest() {
		super(HomeActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mAct = getActivity();
		Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(
			NavigationActivity.class.getName(), null, false);
		Intent i = new Intent(mAct.getApplicationContext(), NavigationActivity.class);
		int id = 1;
		i.putExtra("id", id);
		mAct.startActivity(i);
		receiverActivity =
			(NavigationActivity) receiverActivityMonitor.waitForActivityWithTimeout(2000);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		view = receiverActivity.getWeatherView();
		presenter = view.getPresenter();

	}

	public void testWeatherPresenter() {
		Date date = new Date();
		Weather.ForecastType type;
		Weather.Wind wind = new Weather.Wind(30, 15.5, 18.40);
		Weather weather = new Weather(date, 20.0, 1000.0, 75.0, wind, Weather.ForecastType.CLEAR);

		List<Weather> listaW = new ArrayList<Weather>();
		listaW.add(weather);
		presenter.setWeather(listaW);

		presenter.setPosition(0);

		TextView forecast = new TextView(getActivity());
		presenter.setForecast(forecast);
		String actualForecast, expectedForecast;
		actualForecast = forecast.getText().toString();
		expectedForecast = "Sereno";
		assertEquals(actualForecast, expectedForecast);

		TextView temperature = new TextView(getActivity());
		presenter.setTemperature(temperature);
		String actualTemperature, expectedTemperature;
		actualTemperature = temperature.getText().toString();
		expectedTemperature = "20.0°";
		assertEquals(actualTemperature, expectedTemperature);

		TextView pressure = new TextView(getActivity());
		presenter.setPressure(pressure);
		String actualPressure, expectedPressure;
		actualPressure = pressure.getText().toString();
		expectedPressure = "Pressione: 1000 hPa";
		assertEquals(actualPressure, expectedPressure);

		TextView humidity = new TextView(getActivity());
		presenter.setHumiduty(humidity);
		String actualHumiduty, expectedHumiduty;
		actualHumiduty = humidity.getText().toString();
		expectedHumiduty = "Umidità: 75.0%";
		assertEquals(actualHumiduty, expectedHumiduty);

		TextView windDirection = new TextView(getActivity());
		presenter.setWindDirection(windDirection);
		String actualWindDirection, expectedWindDirection;
		actualWindDirection = windDirection.getText().toString();
		expectedWindDirection = "Vento: Nord-Est";
		assertEquals(actualWindDirection, expectedWindDirection);

		TextView windAvgSpeed = new TextView(getActivity());
		presenter.setWindAvgSpeed(windAvgSpeed);
		String actualWindAvgSpeed, expectedWindAvgSpeed;
		actualWindAvgSpeed = windAvgSpeed.getText().toString();
		expectedWindAvgSpeed = "15.5Km/h";
		assertEquals(actualWindAvgSpeed, expectedWindAvgSpeed);

		TextView windMaxSpeed = new TextView(getActivity());
		presenter.setWindMaxSpeed(windMaxSpeed);
		String actualWindMaxSpeed, expectedWindMaxSpeed;
		actualWindMaxSpeed = windMaxSpeed.getText().toString();
		expectedWindMaxSpeed = "18.4Km/h";
		assertEquals(actualWindMaxSpeed, expectedWindMaxSpeed);

	}
}