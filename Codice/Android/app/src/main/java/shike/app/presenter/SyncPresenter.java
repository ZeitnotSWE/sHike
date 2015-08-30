package shike.app.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;

import shike.app.R;
import shike.app.helper.WebConnectionManager;
import shike.app.model.service.AccountService;
import shike.app.view.config.ConfigView;

/**
 * Created by Luca on 12/06/2015.
 */
public class SyncPresenter {
	private HomeActivity activity;
	private String result = "";
	private Button button = null;
	private int resultId;

	public SyncPresenter(HomeActivity c) {
		activity = c;
	}

	public void linkAccount(final ConfigView fragment) {
		final ProgressDialog[] progressDialog = new ProgressDialog[1];
		resultId = -1;

		AsyncTask<Void, Void, String> connect = new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				result = WebConnectionManager.linkAccount(activity);
				if (result.matches("/\\d{5}/")) {
					fragment.openDialog(result);
				} else {
					resultId = Integer.parseInt(result.substring(0, 1));
				}
				return result;
			}			@Override
			protected void onPreExecute() {
				progressDialog[0] = ProgressDialog.show(activity, "", "Attendi...", true);
				progressDialog[0].setCancelable(false);
			}

			@Override
			protected void onPostExecute(String result) {
				progressDialog[0].dismiss();
				if (resultId == 2) {
					unLinkAccount();
				}
				if (!result.matches("/\\d{5}/") && resultId != 2) {
					createAlert(result);
				}
			}
		};
		connect.execute();
	}

	public void unLinkAccount() {
		boolean resultBool = WebConnectionManager.unlink(activity);
		if (resultBool) {
			result = activity.getText(R.string.unLink).toString();
		} else {
			result = activity.getText(R.string.unLinkFalse).toString();
		}
		createAlert(result);
		setTextButton();
	}

	public void createAlert(String text) {
		AlertDialog.Builder miaAlert = new AlertDialog.Builder(activity);
		miaAlert.setMessage(text);
		miaAlert.setPositiveButton("Ok", null);
		AlertDialog alert = miaAlert.create();
		alert.show();
	}

	public void setTextButton() {
		if (button != null) {
			AccountService service = new AccountService(activity);
			if (service.get() == null) {
				button.setText("Collega Account");
			} else {
				button.setText("Scollega Account");
			}
		}
	}

	public void sync() {
		final ProgressDialog[] progressDialog = new ProgressDialog[1];
		AsyncTask<Void, Void, String> connect = new AsyncTask<Void, Void, String>() {
			@Override
			protected void onPreExecute() {
				progressDialog[0] =
					ProgressDialog.show(activity, "", "Attendi...", true);
				progressDialog[0].setCancelable(false);
			}

			@Override
			protected String doInBackground(Void... params) {
				return result = WebConnectionManager.sync(activity).toString();
			}

			@Override
			protected void onPostExecute(String result) {
				progressDialog[0].dismiss();
				createAlert(result);
				setTextButton();
				activity.getHomePresenter().refreshTracks(activity);
			}

		};
		connect.execute();
	}

	public void setButton(Button b) {button = b;}
}
