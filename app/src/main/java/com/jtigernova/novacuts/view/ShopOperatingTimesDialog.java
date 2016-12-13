package com.jtigernova.novacuts.view;

import jtiger.ui.android.base.BaseActivity;
import jtiger.ui.android.widget.BaseDialog;
import jtiger.webinterface.client.novacuts.model.BarberShop;
import jtiger.webinterface.model.DailyOperatingTime;
import jtiger.webinterface.model.OperatingTimeDay;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jtigernova.novacuts.R;
import com.jtigernova.novacuts.util.OperatingTimeUtil;

public class ShopOperatingTimesDialog extends BaseDialog {

    private final BarberShop shop;
    private final String timeZone;
    private String closed;
    private String hr24;
    private int red;

    public ShopOperatingTimesDialog(BarberShop shop) {
	if (shop == null) {
	    throw new IllegalArgumentException();
	}
	this.shop = shop;
	timeZone = shop.getBusiness().getTimezoneCode();
    }

    public void show(final BaseActivity activity) {
	if (activity == null) {
	    throw new IllegalArgumentException();
	}

	closed = activity.getString(R.string.closed_2);
	hr24 = activity.getString(R.string.hr24);
	red = activity.getResources().getColor(R.color.blue);
	_builder = new AlertDialog.Builder(activity);

	final LinearLayout tempLayout = new LinearLayout(activity);

	activity.getLayoutInflater().inflate(R.layout.dialog_operating_times,
		tempLayout);

	DailyOperatingTime dailyOperatingTime = shop.getDailyOperatingTimeRef();

	loadOperatingTimes(dailyOperatingTime, tempLayout);

	_builder.setTitle(activity.getString(R.string.business_hours))
		.setView(tempLayout)
		.setPositiveButton(activity.getString(R.string.close),
			new DialogInterface.OnClickListener() {

			    @Override
			    public void onClick(DialogInterface dialog,
				    int which) {
				dismiss();
			    }
			});

	createShow();
    }

    private void loadOperatingTimes(DailyOperatingTime dailyOperatingTime,
	    View view) {

	loadOperatingTime(dailyOperatingTime.getMonday(), view, R.id.monday);
	loadOperatingTime(dailyOperatingTime.getTuesday(), view, R.id.tuesday);
	loadOperatingTime(dailyOperatingTime.getWednesday(), view,
		R.id.wednesday);
	loadOperatingTime(dailyOperatingTime.getThursday(), view, R.id.thursday);
	loadOperatingTime(dailyOperatingTime.getFriday(), view, R.id.friday);
	loadOperatingTime(dailyOperatingTime.getSaturday(), view, R.id.saturday);
	loadOperatingTime(dailyOperatingTime.getSunday(), view, R.id.sunday);
    }

    private void loadOperatingTime(OperatingTimeDay day, View layout,
	    int textViewResID) {
	TextView textView = ((TextView) layout.findViewById(textViewResID));

	if (day.isIsOpen()) {
	    if (!day.isIs24_7()) {
		String timeDisplay = OperatingTimeUtil.getOperatingDisplay(day,
				timeZone);
		textView.setText(timeDisplay);
	    } else {
		textView.setText(hr24);
	    }
	} else {
	    textView.setText(closed);
	    textView.setTextColor(red);
	}
    }

}
