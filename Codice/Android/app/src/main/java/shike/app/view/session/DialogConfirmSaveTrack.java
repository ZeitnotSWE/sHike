package shike.app.view.session;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import shike.app.R;
import shike.app.presenter.DashboardPresenter;

/**
 * Created by Luca on 04/06/2015.
 */

public class DialogConfirmSaveTrack extends DialogFragment {
	DashboardPresenter presenter;

	DialogConfirmSaveTrack(DashboardPresenter p) {
		presenter = p;
	}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity())
			.setMessage(getString(R.string.wantSaveSession))
			.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						presenter.stopSaveRun();
					}
				})
			.setNegativeButton("No", null).create();
	}
}