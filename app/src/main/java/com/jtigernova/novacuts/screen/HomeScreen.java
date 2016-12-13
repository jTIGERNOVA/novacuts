package com.jtigernova.novacuts.screen;

import com.jtigernova.novacuts.R;
import jtiger.ui.android.base.BaseActivity;
import jtiger.ui.android.component.RemoteImageView;
import jtiger.webinterface.android.AndroidServiceClient;
import jtiger.webinterface.client.IWebInterfaceResultHandler;
import jtiger.webinterface.client.novacuts.NovacutsServiceClient;
import jtiger.webinterface.client.novacuts.model.Barber;
import jtiger.webinterface.client.novacuts.model.BarberShop;
import jtiger.webinterface.client.result.WebInterfaceResult;
import jtiger.webinterface.model.OperatingTimeDay;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.jtigernova.novacuts.app.NovacutsApplication;
import com.jtigernova.novacuts.util.OperatingTimeUtil;
import com.jtigernova.novacuts.view.BarberIconView;
import com.jtigernova.novacuts.view.ShopOperatingTimesDialog;

public class HomeScreen extends BaseActivity implements
        ActionBar.OnNavigationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.open);

        getSherlock().getActionBar().hide();

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
                    alertUI(result.getMessage(),
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
        setContentView(R.layout.home);
        getSherlock().getActionBar().show();

        String timeZone = shop.getBusiness().getTimezoneCode();

        OperatingTimeDay operatingTime = shop.getDailyOperatingTimeRef()
                .getOperatingDayByTimeZone(timeZone);

        String dailyOperatingTime = OperatingTimeUtil.getOperatingDisplay(
                operatingTime, timeZone);

        TextView title = (TextView) findViewById(R.id.title);
        TextView address = (TextView) findViewById(R.id.address);
        TextView timeOpen = (TextView) findViewById(R.id.operatingTime);
        TextView open = (TextView) findViewById(R.id.openText);

        title.setText(shop.getBusiness().getBusinessName());
        address.setText(shop.getBusiness().getAddress1());
        timeOpen.setText(dailyOperatingTime);

        findViewById(R.id.hours).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ShopOperatingTimesDialog dialog = new ShopOperatingTimesDialog(
                        shop);

                dialog.show(HomeScreen.this);
            }
        });

        findViewById(R.id.signup).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        pushActivity(SignUpScreen.class);
                    }
                });

        // android timezone conversion does not work with jodatime
        boolean manuallyOffset = true;

        if (operatingTime.isOpenNow(timeZone, manuallyOffset)) {

            open.setText(getString(R.string.open));
            open.setTextColor(getResources().getColor(R.color.dark_green));
            timeOpen.setVisibility(View.VISIBLE);
        } else {

            open.setText(getString(R.string.closed));
            open.setTextColor(getResources().getColor(R.color.blue));
            timeOpen.setVisibility(View.GONE);
        }

        ImageView logo = (ImageView) findViewById(R.id.logo);

        RemoteImageView remote = new RemoteImageView(logo, shop.getBusiness()
                .getBusinessLogoImagePath(), false);

        remote.load();

        loadBarbers(shop);
    }

    private void loadBarbers(BarberShop shop) {
        LinearLayout list = (LinearLayout) findViewById(R.id.barbers);

        for (Barber barber : shop.getBarbers()) {
            list.addView(new BarberIconView(this, barber).getView());
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

        if (itemPosition == 1) {
            finish();
        }
        return false;
    }

}
