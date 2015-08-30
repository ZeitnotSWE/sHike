package shike.app.view.config;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import shike.app.presenter.SyncPresenter;

/**
 * Created by Luca on 13/06/2015.
 */
public class SyncNumberDialog extends DialogFragment {
	private SyncPresenter presenter;
	private String result = "";

	public SyncNumberDialog(SyncPresenter p, String s) {
		presenter = p;
		result = s;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity())
			.setMessage(result)
			.setPositiveButton("Fai primo sync",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						presenter.sync();
					}
				})
			.setNegativeButton("Annulla",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						presenter.unLinkAccount();
					}
				})
			.create();
	}
}


