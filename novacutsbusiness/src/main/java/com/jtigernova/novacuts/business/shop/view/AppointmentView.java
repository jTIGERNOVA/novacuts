package com.jtigernova.novacuts.business.shop.view;

import com.jtigernova.novacuts.business.R;
import jtiger.ui.android.base.BaseActivity;
import jtiger.ui.android.component.InflatedView;
import jtiger.ui.android.util.UIUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppointmentView extends InflatedView {

    private Appointment appointment;

    public AppointmentView(BaseActivity activity, Appointment appointment) {
	super(activity);

	this.appointment = appointment;
    }

    protected View buildView(final BaseActivity activity,
	    LayoutInflater inflater) {
	LinearLayout view = new LinearLayout(activity);
	inflater.inflate(R.layout.widget_appointment, view);

	((ImageView) view.findViewById(R.id.imageView1))
		.setImageResource(R.drawable.antonio);

	((TextView) view.findViewById(R.id.name)).setText(appointment.name);
	((TextView) view.findViewById(R.id.appTime)).setText(appointment.time);

	final View barberBackground = view.findViewById(R.id.appBackground);

	View.OnClickListener onClick = new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
	    }
	};

	UIUtil.setViewTouch(view, barberBackground, R.drawable.barber1,
		R.drawable.barber1p, onClick);

	return view;
    }
}
