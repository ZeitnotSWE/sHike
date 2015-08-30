package shike.app.view.session;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SlidingDrawer;

import shike.app.R;
import shike.app.presenter.DashboardPresenter;

/**
 * Created by Luca on 11/05/2015.
 */
public class DashboardView extends Fragment {

	private Button buttonLarge;
	private Button buttonCenterLeft;
	private Button buttonCenterRight;
	private Button buttonUnderLeft;
	private Button buttonUnderRight;
	private Button stopButton;
	private Button startPauseButton;
	private Button handleButton;
	private DashboardPresenter presenter;
	SlidingDrawer slidingDrawer;

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dashboard_frag, container, false);

		startPauseButton = (Button) v.findViewById(R.id.buttonHandleCenter);
		stopButton = (Button) v.findViewById(R.id.buttonHandleLeft);
		handleButton = (Button) v.findViewById(R.id.buttonHandle);
		slidingDrawer = (SlidingDrawer) v.findViewById(R.id.slidingDrawer);

		buttonLarge = (Button) v.findViewById(R.id.buttonLarge);
		buttonCenterLeft = (Button) v.findViewById(R.id.buttonCenterLeft);
		buttonCenterRight = (Button) v.findViewById(R.id.buttonCenterRight);
		buttonUnderLeft = (Button) v.findViewById(R.id.buttonUnderLeft);
		buttonUnderRight = (Button) v.findViewById(R.id.buttonUnderRight);

		presenter.setWidget(buttonLarge, getActivity().getString(R.string.dashboardItemTimer));
		presenter.setWidget(buttonCenterLeft, getActivity().getString(R.string
			.dashboardItemStepCounter));
		presenter.setWidget(buttonCenterRight, getActivity().getString(R.string
			.dashboardItemDistanceTotal));
		presenter.setWidget(buttonUnderRight, getActivity().getString(R.string.dashboardItemAltitude));
		presenter.setWidget(buttonUnderLeft, getActivity().getString(R.string.dashboardItemSpeed));

		buttonLarge.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				presenter.onLongClick(buttonLarge);
				return true;
			}
		});

		buttonCenterLeft.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				presenter.onLongClick(buttonCenterLeft);
				return true;
			}
		});

		buttonCenterRight.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				presenter.onLongClick(buttonCenterRight);
				return true;
			}
		});

		buttonUnderRight.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				presenter.onLongClick(buttonUnderRight);
				return true;
			}
		});

		buttonUnderLeft.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				presenter.onLongClick(buttonUnderLeft);
				return true;
			}
		});


		startPauseButton.setOnClickListener(
			new View.OnClickListener() {
				public void onClick(View view) {
					startPauseButton.setText(presenter.startPauseRun());
				}
			});

		stopButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				DialogConfirmSaveTrack dialog = new DialogConfirmSaveTrack(presenter);
				presenter.onStop(dialog, startPauseButton);
			}
		});

		slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				handleButton.setMaxHeight(30);
				handleButton.setMinHeight(30);
				handleButton.setMinWidth(60);
				handleButton.setMaxWidth(60);
				handleButton.setText("Chiudi");
			}
		});

		slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				handleButton.setMaxHeight(5);
				handleButton.setMinHeight(5);
				handleButton.setMinWidth(240);
				handleButton.setMaxWidth(240);
				handleButton.setText("");
			}
		});

		if (!presenter.isActivated()) {
			startPauseButton.callOnClick();
			presenter.setActivated(true);
		}
		return v;
	}

	public void returnStringFromDialog(String s) {presenter.changeWidget(s);}

	public DashboardPresenter getPresenter() {return presenter;}

	public void setPresenter(DashboardPresenter presenter) {this.presenter = presenter;}
}
