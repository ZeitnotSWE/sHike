package shike.app.view.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import shike.app.R;
import shike.app.model.session.track.VirtualTrack;
import shike.app.presenter.HomePresenter;

/**
 * Classe utilizzata per la visualizzazione della lista di tutti i VirtualTrack presenti nel
 * dispositivo
 */
public class TrackSelectionView extends ListFragment implements AdapterView.OnItemClickListener {
	private List<VirtualTrack> tracks;
	private ListView listView;
	private HomePresenter presenter;


	public void setPresenter(HomePresenter presenter) {
		this.presenter = presenter;
	}

	/**
	 * Imposta il layout e le impostazioni di base al fragment
	 *
	 * @param inflater           utilizzato per aggiungere altre view al fragment
	 * @param container          se non nullo indica il fragment padre a cui il fragment può
	 *                           essere associato
	 * @param savedInstanceState se non nullo il Fragment viene ricostruito dal precedente stato
	 *                           salavto
	 * @return la vista con il layout assegnato
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.selection_track_view, container, false);
		listView = (ListView) rootView.findViewById(android.R.id.list);
		return rootView;
	}

	/**
	 * Imposta l'adapter alla lista in modo da visualizzare i percorsi e le relative informazioni
	 * nel modo corretto
	 *
	 * @param savedInstanceState se il fragment viene riscotruito da un precedente stato salvato
	 *                           esso viene indicato qui
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListViewAdapter adapter = new ListViewAdapter(getActivity(), tracks);
		adapter.setPresenter(presenter);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			/**
			 * Imposta il cambio id fragment quando un item della lista viene premuto
			 *
			 * @param adapter
			 * @param view
			 * @param position
			 * @param arg
			 */
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

				Fragment newFragment = new TrackView(tracks.get(position).get_id(), tracks);
				FragmentTransaction transaction = getFragmentManager().beginTransaction();

				transaction.replace(R.id.fragment_container, newFragment);
				transaction.addToBackStack(null);

				transaction.commit();
			}
		});
	}

	public void setTracks(List<VirtualTrack> tracks) {
		this.tracks = tracks;
	}	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {}


}
