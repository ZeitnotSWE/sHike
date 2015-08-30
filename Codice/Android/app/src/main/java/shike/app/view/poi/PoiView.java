package shike.app.view.poi;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import shike.app.R;
import shike.app.presenter.PoiPresenter;

public class PoiView extends DialogFragment {

	private PoiPresenter presenter;

	public PoiView(PoiPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.poi_view, container, false);
		Button backButton = (Button) v.findViewById(R.id.backButton);
		Button startButton = (Button) v.findViewById(R.id.startButton);

		backButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * Crea un nuovo fragment TrackSelectionView e lo sostituisce alla view attuale
			 * quando il pulsante viene premuto
			 *
			 * @param v View sulla quale si svolge l'azione
			 */
			public void onClick(View v) {
				close();
			}
		});
		startButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * Crea un nuovo fragment TrackSelectionView e lo sostituisce alla view attuale
			 * quando il pulsante viene premuto
			 *
			 * @param v View sulla quale si svolge l'azione
			 */
			public void onClick(View v) {
				presenter.setPoi();
				close();
			}

		});
		getDialog().setTitle("Informazioni POI");
		return v;
	}

	public void close() {
		this.dismiss();
	}
}
