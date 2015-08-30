package shike.app.view.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import shike.app.R;
import shike.app.model.session.track.VirtualTrack;
import shike.app.presenter.HomePresenter;

/**
 * Imposta il layout di un singolo elemento della lista
 */
public class ListViewAdapter extends BaseAdapter {
	Activity context;
	List<VirtualTrack> tracks;
	HomePresenter presenter;

	public ListViewAdapter(Activity context, List<VirtualTrack> t) {
		super();
		tracks = t;
		this.context = context;
	}

	public int getCount() {
		return tracks.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Imposta la visualizzione dell'elemento della lista
	 *
	 * @param position    posizione dell'oggetto da adattare
	 * @param convertView se non null indica la vecchia view riutilizzabile
	 * @param parent      la ViewGroup padre alla quale è possibile associarsi
	 * @return la view modificata in modo che rappresenti i dati in maniera corretta
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater;

		if (convertView == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listitem_row_view, null);

			holder = new ViewHolder();
			holder.txtViewTrackName = (TextView) convertView.findViewById(R.id.trackName);
			holder.txtViewDescription = (TextView) convertView.findViewById(R.id.trackLength);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtViewTrackName.setText(presenter.getName(position));
		holder.txtViewDescription.setText(String.valueOf(presenter.getLength(position) / 1000) +
			" Km");

		return convertView;
	}

	public void setPresenter(HomePresenter presenter) {
		this.presenter = presenter;
	}

	private class ViewHolder {
		TextView txtViewTrackName;
		TextView txtViewDescription;
	}


}
