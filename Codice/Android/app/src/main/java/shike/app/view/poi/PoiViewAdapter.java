package shike.app.view.poi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import shike.app.R;
import shike.app.model.session.track.Poi;
import shike.app.presenter.PoiPresenter;


public class PoiViewAdapter extends BaseAdapter {
	private Activity context;
	private List<Poi> pois;
	private PoiPresenter presenter;

	public PoiViewAdapter(Activity context, List<Poi> p, PoiPresenter presenter) {
		super();
		pois = p;
		this.context = context;
		this.presenter = presenter;
	}

	public int getCount() {
		return pois.size();
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
			convertView = inflater.inflate(R.layout.poi_row_view, null);

			holder = new ViewHolder();
			holder.txtPoiName = (TextView) convertView.findViewById(R.id.poiName);
			holder.txtType = (TextView) convertView.findViewById(R.id.type);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtPoiName.setText(presenter.getName(position));
		holder.txtType.setText(presenter.getType(position));

		return convertView;
	}

	private class ViewHolder {
		TextView txtPoiName;
		TextView txtType;
	}

}
