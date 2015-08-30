package instrumentTest;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.List;

import shike.app.model.user.HelpNumber;
import shike.app.presenter.HelpNumberPresenter;
import shike.app.presenter.NavigationActivity;
import shike.app.view.helpnumber.HelpNumberSelectionView;
import shike.app.presenter.HomeActivity;

/**
 * Created by andrea on 10/06/2015.
 */
public class HelpNumberPresenterTest extends ActivityInstrumentationTestCase2<HomeActivity> {
	HomeActivity mAct;
	NavigationActivity receiverActivity;
	HelpNumberPresenter presenter;
	HelpNumberSelectionView view;

	public HelpNumberPresenterTest(Class<HomeActivity> activityClass) {
		super(activityClass);
	}

	public HelpNumberPresenterTest() {
		super(HomeActivity.class);
	}

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
		view = receiverActivity.getHelpNumber();
		presenter = view.getPresenter();

	}

	public void testPresenter() {
		HelpNumber number = new HelpNumber("123456", "Casa");
		List<HelpNumber> lista = new ArrayList<HelpNumber>();
		lista.add(number);
		presenter.setNumbers(lista);

		List<HelpNumber> actualList, expextedList;
		actualList = lista;
		expextedList = presenter.getNumbers();

		assertEquals(actualList, expextedList);


		String actualName, expectedName;
		actualName = presenter.getName(0);
		expectedName = "Casa";

		assertEquals(actualName, expectedName);


		String actualNumber, expectedNumber;
		actualNumber = presenter.getNumber(0);
		expectedNumber = "123456";

		assertEquals(actualNumber, expectedNumber);
	}


}
