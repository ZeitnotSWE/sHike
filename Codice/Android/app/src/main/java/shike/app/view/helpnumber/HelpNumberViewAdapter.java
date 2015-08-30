package shike.app.view.helpnumber;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import shike.app.R;
import shike.app.model.user.HelpNumber;
import shike.app.presenter.HelpNumberPresenter;

public class HelpNumberViewAdapter extends BaseAdapter {
	private Activity context;
	private List<HelpNumber> numbers;
	private HelpNumberPresenter presenter;

	public HelpNumberViewAdapter(Activity context, List<HelpNumber> n, HelpNumberPresenter
		presenter) {
		super();
		numbers = n;
		this.context = context;
		this.presenter = presenter;
	}

	public int getCount() {
		return numbers.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater;

		if (convertView == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.number_row_view, null);

			holder = new ViewHolder();
			holder.txtNameNumber = (TextView) convertView.findViewById(R.id.nameNumber);
			holder.txtNumber = (TextView) convertView.findViewById(R.id.number);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtNameNumber.setText(presenter.getName(position));
		holder.txtNumber.setText(presenter.getNumber(position));

		return convertView;
	}

	private class ViewHolder {
		TextView txtNameNumber;
		TextView txtNumber;
	}

}