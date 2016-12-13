package com.jtigernova.novacuts.business.shop.screen;

import com.jtigernova.novacuts.business.R;
import com.jtigernova.novacuts.business.shop.app.NovacutsApplication;
import com.jtigernova.novacuts.business.shop.view.Appointment;
import com.jtigernova.novacuts.business.shop.view.AppointmentView;
import com.jtigernova.novacuts.business.shop.view.BarberIconView;
import jtiger.ui.android.base.BaseActivity;
import jtiger.webinterface.android.AndroidServiceClient;
import jtiger.webinterface.client.IWebInterfaceResultHandler;
import jtiger.webinterface.client.novacuts.NovacutsServiceClient;
import jtiger.webinterface.client.novacuts.model.Barber;
import jtiger.webinterface.client.novacuts.model.BarberShop;
import jtiger.webinterface.client.result.WebInterfaceResult;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;

public class HomeScreen extends BaseActivity implements
	ActionBar.OnNavigationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.open);

	final TextView msg = (TextView) findViewById(R.id.msg);

	NovacutsServiceClient client = AndroidServiceClient
		.getNovacutsServiceClient(HomeScreen.this,
			NovacutsApplication.getInstance());

	msg.setText(String.format("Loading %s",
		getString(R.string.app_shop_name)));

	String shop = getString(R.string.barber_business_code);

	client.getShop(shop, new IWebInterfaceResultHandler() {

	    @Override
	    public void onSuccess(WebInterfaceResult result) {
		loadShop(result);
	    }

	    @Override
	    public void onFailure(WebInterfaceResult result) {
		String msg = getString(jtiger.ui.android.R.string.general_jtiger_network_error);

		alertUI(msg, new DialogInterface.OnClickListener() {

		    @Override
		    public void onClick(DialogInterface arg0, int arg1) {
			finish();
		    }

		});
	    }

	});
    }

    private void loadShop(final WebInterfaceResult result) {
	runOnUiThread(new Runnable() {
	    public void run() {

		if (result.getIsSuccess()) {
		    goHome((BarberShop) result.getData());
		} else {
		    alert(result.getMessage(),
			    new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0,
					int arg1) {
				    finish();
				}

			    });
		}
	    }
	});
    }

    private void goHome(final BarberShop shop) {
	boolean isAdmin = false;

	if (!isAdmin) {
	    goBarber(shop);
	} else {
	    goAdmin(shop);
	}
    }

    private void goBarber(final BarberShop shop) {
	setContentView(R.layout.barber_home);

	loadAppointments(shop);

	findViewById(R.id.button1).setOnClickListener(
		new View.OnClickListener() {

		    @Override
		    public void onClick(View v) {
			goAdmin(shop);
		    }
		});
    }

    private void goAdmin(final BarberShop shop) {
	setContentView(R.layout.admin_home);

	loadBarbers(shop);

	findViewById(R.id.button1).setOnClickListener(
		new View.OnClickListener() {

		    @Override
		    public void onClick(View v) {
			goBarber(shop);
		    }
		});
    }

    private void loadBarbers(BarberShop shop) {
	LinearLayout list = (LinearLayout) findViewById(R.id.barbers);

	for (Barber barber : shop.getBarbers()) {
	    list.addView(new BarberIconView(this, barber).getView());
	}

    }

    private void loadAppointments(BarberShop shop) {
	LinearLayout list = (LinearLayout) findViewById(R.id.apps);

	String[] names = new String[] { "Antonio", "John", "Timmy" };
	String[] times = new String[] { "2:30 PM", "3:00 PM", "4:15 PM" };

	for (int c = 0; c < 3; c++) {
	    Appointment app = new Appointment();
	    app.name = names[c];
	    app.time = times[c];

	    list.addView(new AppointmentView(this, app).getView());
	}

    }

    @Override
    protected void attachEvents() {

    }

    @Override
    public void onClick(int viewID) {

    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

	return false;
    }

}
