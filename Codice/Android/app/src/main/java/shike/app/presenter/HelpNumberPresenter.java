package shike.app.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import shike.app.R;
import shike.app.model.service.HelpNumberService;
import shike.app.model.user.HelpNumber;


public class HelpNumberPresenter {
	private List<HelpNumber> numbers = new ArrayList<HelpNumber>();
	private Activity context;

	public HelpNumberPresenter(NavigationActivity context) {
		this.context = context;
		setNumbers(new HelpNumberService(context.getApplicationContext()).getAll());
	}

	public List<HelpNumber> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<HelpNumber> numbers) {
		this.numbers = numbers;
	}

	public String getName(int position) {
		return numbers.get(position).getName();
	}

	public String getNumber(int position) {
		return numbers.get(position).getNumber();
	}

	public boolean check(){
		if(numbers.size() == 0){
			Toast toast=Toast.makeText(context,context.getText(R.string.errorNoNumbersFound),Toast
				.LENGTH_SHORT);
			toast.show();
			return true;
		}
		return false;
	}

}
