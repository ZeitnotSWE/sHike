package shike.app.view.config;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import shike.app.R;
import shike.app.presenter.SyncPresenter;

/**
 * Classe che rappresenta la view che contiene le impostazioni modificabili dall'utente
 */
public class ConfigView extends Fragment {
	private Button disConnectButton, syncButton;
	private SyncPresenter presenter;

	public ConfigView(SyncPresenter p) {presenter = p;}

	/**
	 * Imposta il layout e le impostazioni di base al fragment
	 *
	 * @param inflater           utilizzato per aggiungere altre view al fragment
	 * @param container          se non nullo indica il fragment padre a cui il fragment può
	 *                           essere associato
	 * @param savedInstanceState se non nullo il Fragment viene ricostruito dal precedente stato
	 *                           salavto
	 * @return la vista che visualizza le impostazioni modificabili dal singolo utente
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
							 Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.config_view, container, false);

		disConnectButton = (Button) rootView.findViewById(R.id.disconnectsConnectButton);
		disConnectButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * Scollega o collega l'account dal portale web
			 *
			 * @param v View sulla quale si svolge l'azione
			 */
			@Override
			public void onClick(View v) {
				presenter.linkAccount(ConfigView.this);
			}
		});

		presenter.setButton(disConnectButton);
		presenter.setTextButton();

		syncButton = (Button) rootView.findViewById(R.id.syncButton);
		syncButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				presenter.sync();
			}
		});
		return rootView;
	}

	public void openDialog(String result) {
		SyncNumberDialog dialog = new SyncNumberDialog(presenter, result);
		dialog.setTargetFragment(getParentFragment(), 0);
		dialog.setCancelable(false);
		dialog.show(getFragmentManager(), "DialogConnectAccount");
	}
}
