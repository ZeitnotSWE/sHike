package shike.app.presenter;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import shike.app.R;
import shike.app.model.service.VirtualTrackService;
import shike.app.model.session.track.VirtualTrack;
import shike.app.view.home.TrackSelectionView;

/**
 * Created by andrea on 09/06/2015.
 */
public class HomePresenter {

	private List<VirtualTrack> tracks = new ArrayList<VirtualTrack>();
	private Activity context;

	public HomePresenter(HomeActivity context) {
		this.context = context;
		setTracks(new VirtualTrackService(context.getApplicationContext()).getAll());

	}

	public void setTracks(List<VirtualTrack> tracks) {
		this.tracks = tracks;
	}

	public void setSelectionView(TrackSelectionView view) {
		view.setTracks(tracks);
	}

	public String getName(int position) {
		return tracks.get(position).getName();
	}

	public double getLength(int position) {
		return tracks.get(position).getLength();
	}

	public void refreshTracks(Context context){
		setTracks(new VirtualTrackService(context).getAll());
	}

	public List<VirtualTrack> getTracks(){
		return tracks;
	}

	public boolean check(){
		if(tracks.size() == 0){
			Toast toast=Toast.makeText(context,context.getText(R.string.errorNoTracksFound),Toast
				.LENGTH_SHORT);
			toast.show();
			return true;
		}
		return false;
	}
}
