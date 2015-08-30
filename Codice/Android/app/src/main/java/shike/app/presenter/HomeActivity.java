package shike.app.presenter;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import shike.app.R;
import shike.app.presenter.HomePresenter;
import shike.app.presenter.SyncPresenter;
import shike.app.view.home.HomeView;

/**
 * Classe che fa da activity madre per i fragment HomeView, ListAdapterView, TrackSelectionView,
 * TrackView e ConfigView
 */
public class HomeActivity extends FragmentActivity {

	private HomeView firstFragment;
	private SyncPresenter syncPresenter;
	private HomePresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		if (findViewById(R.id.fragment_container) != null) {

			if (savedInstanceState != null) {
				return;
			}

			firstFragment = new HomeView();
			presenter = new HomePresenter(this);
			firstFragment.setPresenter(presenter);

			firstFragment.setArguments(getIntent().getExtras());

			syncPresenter = new SyncPresenter(this);
			firstFragment.setSyncPresenter(syncPresenter);

			getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, firstFragment).commit();
		}
	}

	public SyncPresenter getSyncPresenter() {
		return syncPresenter;
	}
	public HomePresenter getHomePresenter() {
		return presenter;
	}

	public HomeView getHomeView() {return firstFragment;}

}
