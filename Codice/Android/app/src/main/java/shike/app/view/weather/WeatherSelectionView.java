package shike.app.view.weather;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import shike.app.R;
import shike.app.model.weather.Weather;
import shike.app.presenter.WeatherPresenter;

public class WeatherSelectionView extends ListFragment implements AdapterView.OnItemClickListener {
	private WeatherPresenter presenter;
	private ListView listView;
	private List<Weather> weatherList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.weather_selection_view, container, false);
		listView = (ListView) rootView.findViewById(android.R.id.list);
		weatherList = presenter.getWeathers();
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		WeatherViewAdapter adapter = new WeatherViewAdapter(getActivity(), weatherList, presenter);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			/**
			 * Imposta il cambio id fragment quando un item della lista viene premuto
			 *
			 * @param adapter
			 * @param view
			 * @param position
			 * @param arg
			 */
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

				presenter.onClick(position);
			}
		});
	}

	public WeatherPresenter getPresenter() {
		return presenter;
	}
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {}

	public void setPresenter(WeatherPresenter presenter) {
		this.presenter = presenter;
	}


	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (menuVisible) {
			presenter.check();
		}
	}
}
