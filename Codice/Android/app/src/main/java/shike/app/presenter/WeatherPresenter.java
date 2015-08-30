package shike.app.presenter;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import shike.app.R;
import shike.app.model.service.TrackWeatherService;
import shike.app.model.weather.Weather;
import shike.app.view.weather.WeatherSelectionView;
import shike.app.view.weather.WeatherView;

public class WeatherPresenter {

	private List<Weather> weather;
	private WeatherSelectionView view;
	private int position = 0;
	private Activity context;

	public WeatherPresenter(NavigationActivity context, int id) {
		Calendar calendar = Calendar.getInstance();
		this.context = context;
		setWeather(new TrackWeatherService(context.getApplicationContext()).get(id).getForecastByDay(calendar.getTime
			()));
	}

	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}

	public void setView(WeatherSelectionView view) {
		this.view = view;
	}

	public List<Weather> getWeathers() {
		return weather;
	}

	public String getForecast(int position) {
		return weather.get(position).getForecast().getName();
	}

	public void setForecast(TextView forecast) {
		forecast.setText(weather.get(position).getForecast().getName());
	}

	public void setTemperature(TextView temperature) {
		temperature.setText(String.valueOf(weather.get(position).getTemperature()) + "°");
	}

	public void setPressure(TextView pressure) {
		pressure.setText("Pressione: " + String.valueOf((int) weather.get(position).getPressure
			()) + " hPa");
	}

	public void setHumiduty(TextView humidity) {
		humidity.setText("Umidità: " + String.valueOf(weather.get(position).getHumidity()
		) + "%");
	}

	public void setWindDirection(TextView windDirection) {
		String w = "Vento: ";
		int value = weather.get(position).getWind().getDirection();
		String x = "Nord";

		if (value >= 23 && value <= 67) {
			x = "Nord-Est";
		}
		if (value >= 68 && value <= 112) {
			x = "Est";
		}
		if (value >= 113 && value <= 157) {
			x = "Sud-Est";
		}
		if (value >= 158 && value <= 202) {
			x = "Sud";
		}
		if (value >= 203 && value <= 247) {
			x = "Sud-Ovest";
		}
		if (value >= 248 && value <= 292) {
			x = "Ovest";
		}
		if (value >= 293 && value <= 337) {
			x = "Nord-Ovest";
		}
		w = w + x;

		windDirection.setText(w);
	}

	public void setWindAvgSpeed(TextView windAvgSpeed) {
		windAvgSpeed.setText(String.valueOf(weather.get(position).getWind().getAvgSpeed()
		) + "Km/h");
	}

	public void setWindMaxSpeed(TextView windMaxSpeed) {
		windMaxSpeed.setText(String.valueOf(weather.get(position).getWind().getMaxSpeed()
		) + "Km/h");
	}

	public void onClick(int position) {
		setPosition(position);
		WeatherView weather = new WeatherView(this);
		weather.setStyle(1, 0);
		weather.setTargetFragment(view.getParentFragment(), 0);
		weather.show(view.getFragmentManager(), "Dialog Fragment");
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean check(){
		if(weather.size() == 0){
			Toast toast=Toast.makeText(context,context.getText(R.string.errorNoMeteoFound),Toast
				.LENGTH_SHORT);
			toast.show();
			return true;
		}
		return false;
	}


}
