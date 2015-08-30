package shike.app.view.helpnumber;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import shike.app.R;
import shike.app.model.user.HelpNumber;
import shike.app.presenter.HelpNumberPresenter;

public class HelpNumberSelectionView extends ListFragment {
	private HelpNumberPresenter presenter;
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.help_number_selection_view, container, false);
		listView = (ListView) rootView.findViewById(android.R.id.list);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<HelpNumber> numbers = presenter.getNumbers();
		HelpNumberViewAdapter adapter =
			new HelpNumberViewAdapter(getActivity(), numbers, presenter);
		listView.setAdapter(adapter);
	}

	public HelpNumberPresenter getPresenter() {return presenter;}

	public void setPresenter(HelpNumberPresenter presenter) {
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