package shike.app.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import shike.app.R;
import shike.app.presenter.HomePresenter;
import shike.app.presenter.NavigationActivity;
import shike.app.presenter.SyncPresenter;
import shike.app.view.config.ConfigView;

/**
 * Classe che modella la visualizzazione dell'homepage dell'applicazione
 */
public class HomeView extends Fragment {

	private HomePresenter homePresenter;
	private SyncPresenter syncPresenter;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
		savedInstanceState) {
		View rootView = inflater.inflate(R.layout.home_view, container, false);

		Button selectionTrackButton = (Button) rootView.findViewById(R.id.selectionTrackButton);
		selectionTrackButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * Crea un nuovo fragment TrackSelectionView e lo sostituisce alla view attuale
			 * quando il pulsante viene premuto
			 *
			 * @param v View sulla quale si svolge l'azione
			 */
			public void onClick(View v) {
				if(!homePresenter.check()) {

					TrackSelectionView newFragment = new TrackSelectionView();

					homePresenter.setSelectionView(newFragment);
					newFragment.setPresenter(homePresenter);

					FragmentTransaction transaction = getFragmentManager().beginTransaction();

					transaction.replace(R.id.fragment_container, newFragment);
					transaction.addToBackStack(null);

					transaction.commit();
				}
			}
		});

		Button settingButton = (Button) rootView.findViewById(R.id.settingsButton);
		settingButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * Crea un nuovo fragment ConfigView e lo sostituisce alla view attuale
			 * quando il pulsante viene premuto
			 *
			 * @param v View sulla quale si svolge l'azione
			 */
			public void onClick(View v) {
				Fragment newFragment = new ConfigView(syncPresenter);
				FragmentTransaction transaction = getFragmentManager().beginTransaction();

				transaction.replace(R.id.fragment_container, newFragment);
				transaction.addToBackStack(null);

				transaction.commit();
			}
		});

		Button startButton = (Button) rootView.findViewById(R.id.startButton);
		startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NavigationActivity.class);
				startActivity(i);
			}
		});

		return rootView;
	}

	public void setSyncPresenter(SyncPresenter syncPresenter) {this.syncPresenter = syncPresenter;}

	public HomePresenter getPresenter() {
		return homePresenter;
	}

	public void setPresenter(HomePresenter presenter) {
		this.homePresenter = presenter;
	}
}
