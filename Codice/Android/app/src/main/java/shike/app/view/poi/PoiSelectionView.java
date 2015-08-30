package shike.app.view.poi;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import shike.app.R;
import shike.app.model.session.track.Poi;
import shike.app.presenter.PoiPresenter;

public class PoiSelectionView extends ListFragment implements AdapterView.OnItemClickListener {
	private PoiPresenter presenter;
	private ListView listView;
	private List<Poi> pois;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.poi_selection_view, container, false);
		listView = (ListView) rootView.findViewById(android.R.id.list);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		PoiViewAdapter adapter = new PoiViewAdapter(getActivity(), pois, presenter);
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

				presenter.onClick(pois.get(position).getLocation());
			}
		});
	}

	public void setPois(List<Poi> p) {
		pois = p;
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {}

	public PoiPresenter getPresenter() {
		return presenter;
	}

	public void setPresenter(PoiPresenter presenter) {
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
