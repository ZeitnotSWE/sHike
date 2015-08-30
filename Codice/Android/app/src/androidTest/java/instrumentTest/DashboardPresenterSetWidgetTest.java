package instrumentTest;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.text.format.Time;
import android.widget.Button;

import shike.app.R;
import shike.app.presenter.DashboardPresenter;
import shike.app.presenter.NavigationActivity;
import shike.app.presenter.HomeActivity;
import shike.app.view.session.DashboardView;

/**
 * Created by Luca on 26/05/2015.
 */
public class DashboardPresenterSetWidgetTest extends ActivityInstrumentationTestCase2<HomeActivity> {

	HomeActivity mAct;
	NavigationActivity receiverActivity;
	DashboardView dash;
	DashboardPresenter controller;

	public DashboardPresenterSetWidgetTest(Class<HomeActivity> activityClass) {
		super(activityClass);
	}

	public DashboardPresenterSetWidgetTest() {
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

	public void testSetWidget() {
		Button b = (Button) receiverActivity.findViewById(R.id.buttonLarge);
		String expected, actual;

		controller.onLongClick(b);
		controller.changeWidget("Ora");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		expected = b.getText().toString();
		actual = today.format("%k:%M:%S");
		expected = expected.substring(0, expected.length() - 2);
		actual = actual.substring(0, actual.length() - 2);
		assertEquals(expected, actual);

		controller.onLongClick(b);
		controller.changeWidget("Data");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String data = today.monthDay + "/" + today.month + "/" + today.year;
		assertEquals(data, b.getText().toString());
	}
}
