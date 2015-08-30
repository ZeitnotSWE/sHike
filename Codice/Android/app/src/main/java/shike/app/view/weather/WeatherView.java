package shike.app.view.weather;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import shike.app.R;
import shike.app.presenter.WeatherPresenter;

public class WeatherView extends DialogFragment {
	private WeatherPresenter presenter;
	private Button backButton;

	private TextView forecast;
	private TextView temperature;
	private TextView pressure;
	private TextView humidity;
	private TextView windDirection;
	private TextView windAvgSpeed;
	private TextView windMaxSpeed;

	public WeatherView(WeatherPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
		savedInstanceState) {
		View v = inflater.inflate(R.layout.weather_view, container, false);
		backButton = (Button) v.findViewById(R.id.backButton);
		backButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * Crea un nuovo fragment TrackSelectionView e lo sostituisce alla view attuale
			 * quando il pulsante viene premuto
			 *
			 * @param v View sulla quale si svolge l'azione
			 */
			public void onClick(View v) {
				close();
			}
		});
		getDialog().setTitle("Informazioni meteo");

		forecast = (TextView) v.findViewById(R.id.forecast);
		temperature = (TextView) v.findViewById(R.id.temperature);
		pressure = (TextView) v.findViewById(R.id.pressure);
		humidity = (TextView) v.findViewById(R.id.humidity);
		windDirection = (TextView) v.findViewById(R.id.wind);
		windAvgSpeed = (TextView) v.findViewById(R.id.avgSpeed);
		windMaxSpeed = (TextView) v.findViewById(R.id.maxSpeed);

		presenter.setForecast(forecast);
		presenter.setTemperature(temperature);
		presenter.setPressure(pressure);
		presenter.setHumiduty(humidity);
		presenter.setWindDirection(windDirection);
		presenter.setWindAvgSpeed(windAvgSpeed);
		presenter.setWindMaxSpeed(windMaxSpeed);

		return v;
	}

	public void close() {
		this.dismiss();
	}

}
