package shike.app.view.session;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import shike.app.R;
import shike.app.presenter.NavigationActivity;

public class WidgetSelectionView extends DialogFragment implements AdapterView.OnItemClickListener {

	ListView listView;
	boolean haveTrack;
	String[] nomi;

	public WidgetSelectionView(boolean b) {
		haveTrack = b;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.selection_view, container, false);

		if (haveTrack) {
			nomi = getResources().getStringArray(R.array.dashboardTrack);
		} else {
			nomi = getResources().getStringArray(R.array.dashboardNormal);
		}

		listView = (ListView) v.findViewById(R.id.listViewGeneral);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout
			.simple_list_item_1,
			nomi);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);

		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, final View view, int pos,
							long id) {
		NavigationActivity activity = (NavigationActivity) getActivity();
		activity.sendStringToDashFragment(nomi[pos]);
		this.dismiss();
	}
}