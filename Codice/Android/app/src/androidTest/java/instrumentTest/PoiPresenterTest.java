package instrumentTest;

import android.app.Instrumentation;
import android.content.Intent;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.List;

import shike.app.model.session.track.Poi;
import shike.app.presenter.NavigationActivity;
import shike.app.presenter.PoiPresenter;
import shike.app.presenter.HomeActivity;
import shike.app.view.poi.PoiSelectionView;

/**
 * Created by andrea on 12/06/2015.
 */
public class PoiPresenterTest extends ActivityInstrumentationTestCase2<HomeActivity> {
	HomeActivity mAct;
	NavigationActivity receiverActivity;
	PoiPresenter presenter;
	PoiSelectionView view;

	public PoiPresenterTest(Class<HomeActivity> activityClass) {
		super(activityClass);
	}

	public PoiPresenterTest() {
		super(HomeActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mAct = getActivity();
		Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(
			NavigationActivity.class.getName(), null, false);
		Intent i = new Intent(mAct.getApplicationContext(), NavigationActivity.class);
		int id = 1;
		i.putExtra("id", id);
		mAct.startActivity(i);
		receiverActivity =
			(NavigationActivity) receiverActivityMonitor.waitForActivityWithTimeout(2000);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		view = receiverActivity.getPoiSelection();
		presenter = view.getPresenter();

	}

	public void testPoi() {
		Location location = new Location("Campeggio");
		Poi poi = new Poi(1, location, "Campeggio", Poi.PoiType.CAMPING);
		List<Poi> poiList = new ArrayList<Poi>();

		poiList.add(poi);
		presenter.setPois(poiList);

		String actualName, expectedName;
		actualName = presenter.getName(0);
		expectedName = "Campeggio";
		assertEquals(actualName, expectedName);

		String actualType, expectedType;
		actualType = presenter.getType(0);
		expectedType = "bivacco";
		assertEquals(actualType, expectedType);
	}
}
