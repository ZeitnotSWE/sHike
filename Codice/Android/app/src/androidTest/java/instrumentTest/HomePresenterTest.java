package instrumentTest;

import android.app.Instrumentation;
import android.content.Intent;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import shike.app.model.session.track.VirtualTrack;
import shike.app.presenter.HomePresenter;
import shike.app.presenter.NavigationActivity;
import shike.app.presenter.HomeActivity;
import shike.app.view.home.HomeView;

/**
 * Created by andrea on 13/06/2015.
 */
public class HomePresenterTest extends ActivityInstrumentationTestCase2<HomeActivity> {
	HomeActivity mAct;
	NavigationActivity receiverActivity;
	HomePresenter presenter;
	HomeView view;

	public HomePresenterTest(Class<HomeActivity> activityClass) {
		super(activityClass);
	}

	public HomePresenterTest() {
		super(HomeActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mAct = getActivity();
		Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(
			NavigationActivity.class.getName(), null, false);
		Intent i = new Intent(mAct.getApplicationContext(), NavigationActivity.class);
		mAct.startActivity(new Intent(mAct.getApplicationContext(), NavigationActivity.class));
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		view = mAct.getHomeView();
		presenter = view.getPresenter();
	}

	public void testPoi() {
		Date date = new Date();
		Location location = new Location("Punto");
		List<Location> lista = new ArrayList<Location>();
		lista.add(location);
		VirtualTrack track = new VirtualTrack(1, "Tracciato", 100000.0, lista, date, location);
		List<VirtualTrack> tracks = new ArrayList<VirtualTrack>();
		tracks.add(track);

		presenter.setTracks(tracks);

		String actualName, exceptedName;
		actualName = presenter.getName(0);
		exceptedName = "Tracciato";
		assertEquals(actualName, exceptedName);

		Double actualLength, expectedLength;
		actualLength = presenter.getLength(0);
		expectedLength = 100000.0;
		assertEquals(actualLength, expectedLength);

	}
}
