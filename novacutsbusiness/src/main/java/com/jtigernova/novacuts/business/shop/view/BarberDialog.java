package com.jtigernova.novacuts.business.shop.view;


import com.jtigernova.novacuts.business.R;
import jtiger.ui.android.base.BaseActivity;
import jtiger.ui.android.widget.BaseDialog;
import jtiger.webinterface.client.novacuts.model.Barber;
import android.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BarberDialog extends BaseDialog {

    private final Barber barber;

    public BarberDialog(Barber barber) {
	if (barber == null) {
	    throw new IllegalArgumentException();
	}
	this.barber = barber;
    }

    public void show(final BaseActivity activity) {
	if (activity == null) {
	    throw new IllegalArgumentException();
	}

	_builder = new AlertDialog.Builder(activity);

	final LinearLayout tempLayout = new LinearLayout(activity);

	activity.getLayoutInflater()
		.inflate(R.layout.dialog_barber, tempLayout);

	String name = barber.getBasicInfoRefModel().getBasicInfo()
		.getFirstName();
	String fullName = barber.getBasicInfoRefModel().getBasicInfo()
		.getFullName();
	String image = barber.getImagePath();
	String phone = barber.getPhoneNumber();
	String desp = barber.getSummary();

	View barberView = tempLayout.findViewById(R.id.barber);

	((TextView) tempLayout.findViewById(R.id.name)).setText(fullName);
	((TextView) tempLayout.findViewById(R.id.phone)).setText(phone);
	((TextView) tempLayout.findViewById(R.id.description)).setText(desp);

	barberView.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		dismiss();
	    }
	});

	ImageView imageView = (ImageView) barberView;

	activity.loadRemoteImage(imageView, image);

	_builder.setTitle(name).setView(tempLayout);

	createShow();
    }

}
