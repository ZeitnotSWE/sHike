package shike.app.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import shike.app.R;
import shike.app.model.session.track.VirtualTrack;
import shike.app.presenter.NavigationActivity;

/**
 * Classe utilizzata per la visualizzazione dei dati di un singolo percorso
 */
public class TrackView extends Fragment {
	private List<VirtualTrack> tracks;
	private int id;

	public TrackView(int i, List<VirtualTrack> tracks) {
		id = i;
		this.tracks = tracks;
	}

	/**
	 * Imposta il layout e le impostazioni di base al fragment
	 *
	 * @param inflater           utilizzato per aggiungere altre view al fragment
	 * @param container          se non nullo indica il fragment padre a cui il fragment può
	 *                              essere associato
	 * @param savedInstanceState se non nullo il Fragment viene ricostruito dal precedente stato
	 *                           salavto
	 * @return la vista che visualizza le informazioni di un singolo percorso
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
		savedInstanceState) {
		View rootView = inflater.inflate(R.layout.track_view, container, false);
		TextView name = (TextView) rootView.findViewById(R.id.trackName);
		TextView length = (TextView) rootView.findViewById(R.id.trackLength);

		VirtualTrack thisTrack = null;
		for (VirtualTrack track : tracks) {
			if (track.get_id() == id) {
				thisTrack = track;
				break;
			}
		}

		if (thisTrack != null) {
			name.setText(thisTrack.getName());
			length.setText(String.valueOf(thisTrack.getLength() / 1000) + " Km");
		}
		Button startTrackButton = (Button) rootView.findViewById(R.id.startTrackButton);
		startTrackButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * Definisce l'activity NavigationActivity che deve rimpiazzare la view attuale quando
			 * il pulsante
			 * viene premuto
			 *
			 * @param v View sulla quale si svolge l'azione
			 */
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NavigationActivity.class);
				i.putExtra("id", id);
				startActivity(i);
			}
		});

		Button backTrackButton = (Button) rootView.findViewById(R.id.backTrackButton);
		backTrackButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * Crea un nuovo fragment TrackSelectionView e lo sostituisce alla view attuale
			 * quando il pulsante viene premuto
			 *
			 * @param v View sulla quale si svolge l'azione
			 */
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		return rootView;
	}
}
