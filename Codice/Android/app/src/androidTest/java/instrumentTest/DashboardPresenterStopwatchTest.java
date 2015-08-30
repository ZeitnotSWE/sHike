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
public class DashboardPresenterStopwatchTest extends
	ActivityInstrumentationTestCase2<HomeActivity> {
	HomeActivity mAct;
	NavigationActivity receiverActivity;
	DashboardView dash;
	DashboardPresenter controller;

	public DashboardPresenterStopwatchTest(Class<HomeActivity> activityClass) {
		super(activityClass);
	}

	public DashboardPresenterStopwatchTest() {
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

	public void testCronometer() {
		Button b = (Button) receiverActivity.findViewById(R.id.buttonLarge);
		String actual, expected;

		controller.onLongClick(b);
		controller.changeWidget("Cronometro");
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		controller.startPauseRun();
		expected = "0:00:1";
		actual = b.getText().toString().substring(0, b.getText().length() - 1);
		assertEquals(expected, actual);
	}
}
