package instrumentTest;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import shike.app.presenter.NavigationActivity;
import shike.app.presenter.HomeActivity;

/**
 * Created by Luca on 26/05/2015.
 */
public class ActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {

	HomeActivity mAct;
	NavigationActivity receiverActivity;

	public ActivityTest(Class<HomeActivity> activityClass) {
		super(activityClass);
	}

	public ActivityTest() {
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
	}

	public void testActivityLaunch() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(NavigationActivity.class, receiverActivity.getClass());
	}
}
