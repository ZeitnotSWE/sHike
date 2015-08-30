package shike.app.view.weather;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import shike.app.R;
import shike.app.model.weather.Weather;
import shike.app.presenter.WeatherPresenter;


public class WeatherViewAdapter extends BaseAdapter {
	private Activity context;
	private List<Weather> weatherList;
	private WeatherPresenter presenter;

	public WeatherViewAdapter(Activity context, List<Weather> w, WeatherPresenter presenter) {
		super();
		weatherList = w;
		this.context = context;
		this.presenter = presenter;
	}

	public int getCount() {
		return weatherList.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater;

		if (convertView == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.weather_row_view, null);

			holder = new ViewHolder();
			holder.txtForecast = (TextView) convertView.findViewById(R.id.forecast);
			holder.txtHour = (TextView) convertView.findViewById(R.id.hour);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtForecast.setText(presenter.getForecast(position));
		Date d = weatherList.get(position).getDate();
		SimpleDateFormat df = new SimpleDateFormat("HH");
		String formattedDate = df.format(d.getTime());
		holder.txtHour.setText(String.valueOf(formattedDate) + ":00");

		return convertView;
	}

	private class ViewHolder {
		TextView txtForecast;
		TextView txtHour;
	}
}
