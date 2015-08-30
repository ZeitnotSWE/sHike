package instrumentTest;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import shike.app.R;
import shike.app.presenter.DashboardPresenter;
import shike.app.presenter.NavigationActivity;
import shike.app.presenter.HomeActivity;
import shike.app.view.session.DashboardView;

/**
 * Created by Luca on 27/05/2015.
 */
public class DashboardPresenterStartPauseTest extends ActivityInstrumentationTestCase2<HomeActivity> {
	HomeActivity mAct;
	NavigationActivity receiverActivity;
	DashboardView dash;
	DashboardPresenter controller;

	public DashboardPresenterStartPauseTest(Class<HomeActivity> activityClass) {
		super(activityClass);
	}

	public DashboardPresenterStartPauseTest() {
		super(HomeActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mAct = getActivity();
		Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(
			NavigationActivity.class.getName(), null, false);
		mAct.startActivity(new Intent(mAct.getApplicationContext(), NavigationActivity.class));
		receiverActivity =
			(NavigationActivity) receiverActivityMonitor.waitForActivityWithTimeout(2000);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dash = receiverActivity.getDashboardView();
		controller = dash.getPresenter();
	}

	public void testStartStop() {
		Button b = (Button) receiverActivity.findViewById(R.id.buttonLarge);
		String actual, expected;

		actual = controller.startPauseRun();
		actual = controller.startPauseRun();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		expected = mAct.getString(R.string.pause);
		assertEquals(expected, actual);

		actual = controller.startPauseRun();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		expected = mAct.getString(R.string.start);
		assertEquals(expected, actual);
	}
}
