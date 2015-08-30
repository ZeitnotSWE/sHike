package shike.app.view.session;

/**
 * Created by Luca on 18/05/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import shike.app.R;
import shike.app.presenter.CompassPresenter;

public class CompassView extends Fragment {


	private CompassPresenter presenter;

	public CompassView(CompassPresenter c) {
		presenter = c;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
		savedInstanceState) {
		View v = inflater.inflate(R.layout.compass, container, false);

		ImageView imageCompass = (ImageView) v.findViewById(R.id.imageViewRosaVenti);
		presenter.setCompassImage(imageCompass);
		ImageView imageArrow = (ImageView) v.findViewById(R.id.imageViewArrow);
		presenter.setArrowImage(imageArrow);

		return v;
	}
}
