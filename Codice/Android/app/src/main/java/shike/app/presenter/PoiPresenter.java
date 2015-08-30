package shike.app.presenter;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import shike.app.R;
import shike.app.model.service.PoiService;
import shike.app.model.service.VirtualTrackService;
import shike.app.model.session.track.Poi;
import shike.app.view.poi.PoiSelectionView;
import shike.app.view.poi.PoiView;

public class PoiPresenter {

	private CompassPresenter compass;
	private Activity context;
	private List<Poi> pois = new ArrayList<>();
	private PoiSelectionView view;
	private Location poiLocation;
	private PoiService poiService;
	Location currentPosition;

	public PoiPresenter(NavigationActivity context, CompassPresenter compass) {
		this.compass = compass;
		this.context = context;
		VirtualTrackService track = new VirtualTrackService(context.getApplicationContext());
		currentPosition = compass.getCurrentPosition();
		poiService = new PoiService(context);
		if(currentPosition != null) {
			pois = poiService.get(currentPosition, 3000.0);
		}
	}

	public void onClick(Location location) {
		PoiView poi = new PoiView(this);
		poiLocation = location;
		poi.setStyle(1, 0);
		poi.setTargetFragment(view.getParentFragment(), 0);
		poi.show(view.getFragmentManager(), "Dialog Fragment");
	}

	public void setPoi() {
		compass.setTargetLocation(poiLocation);
	}

	public void setPois(List<Poi> pois) {
		this.pois = pois;
	}


	public String getName(int position) {
		return pois.get(position).getName();
	}

	public String getType(int position) {
		return pois.get(position).getPoiType().getName();
	}

	public void setView(PoiSelectionView view) {
		this.view = view;
		view.setPois(pois);
	}

	public boolean check(){
		if(pois.size() == 0){
			Toast toast=Toast.makeText(context,context.getText(R.string.errorNoPoiFound),Toast
				.LENGTH_SHORT);
			toast.show();
			return true;
		}
		return false;
	}

	public void refresh(){
		currentPosition = compass.getCurrentPosition();
		if(currentPosition != null) {
			pois = poiService.get(currentPosition, 3000.0);
		}
	}

}
