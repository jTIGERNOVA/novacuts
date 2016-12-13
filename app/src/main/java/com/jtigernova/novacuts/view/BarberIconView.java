package com.jtigernova.novacuts.view;

import com.jtigernova.novacuts.R;
import jtiger.ui.android.base.BaseActivity;
import jtiger.ui.android.component.InflatedView;
import jtiger.ui.android.component.RemoteImageView;
import jtiger.ui.android.util.UIUtil;
import jtiger.webinterface.client.novacuts.model.Barber;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BarberIconView extends InflatedView {

    private final Barber barber;

    public BarberIconView(BaseActivity activity, Barber barber) {
	super(activity);
	if (barber == null) {
	    throw new IllegalArgumentException("barber");
	}

	this.barber = barber;
    }

    protected View buildView(final BaseActivity activity,
	    LayoutInflater inflater) {
	LinearLayout view = new LinearLayout(activity);
	inflater.inflate(R.layout.widget_barber, view);

	RemoteImageView remoteImage = new RemoteImageView(
		(ImageView) view.findViewById(R.id.imageView1),
		barber.getImagePath(), false);

	String name = barber.getBasicInfoRefModel().getBasicInfo()
		.getFirstName();

	((TextView) view.findViewById(R.id.name)).setText(name);

	remoteImage.load();

	final View barberBackground = view.findViewById(R.id.barberBackground);

	View.OnClickListener onClick = new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		BarberDialog d = new BarberDialog(barber);

		d.show(activity);
	    }
	};

	UIUtil.setViewTouch(view, barberBackground, R.drawable.barber1,
		R.drawable.barber1p, onClick);

	return view;
    }
}
