package com.jtigernova.novacuts.business.shop.app;

import jtiger.util.android.locale.LocaleUtil;
import jtiger.webinterface.IWebInterfaceApplication;
import jtiger.webinterface.android.AndroidNetworkPreparer;
import jtiger.webinterface.util.EnvironmentType;

import android.app.Application;

/**
 * Application class. Remember to reference this class in manifest
 *
 * @author Antonio
 */
public class NovacutsApplication extends Application implements
        IWebInterfaceApplication {

    private final static NovacutsApplication instance = new NovacutsApplication();

    @Override
    public void onCreate() {
        super.onCreate();

        if (NovacutsConstants.ENVIRONMENT == EnvironmentType.PRODUCTION) {
            AndroidNetworkPreparer.updateNetworkPreparer(
                    NovacutsConstants.ENVIRONMENT, NovacutsConstants.DEBUG,
                    NovacutsConstants.DEBUG);
        } else {
            AndroidNetworkPreparer.updateNetworkPreparer(
                    NovacutsConstants.ENVIRONMENT, NovacutsConstants.DEBUG,
                    NovacutsConstants.DEBUG);
        }
    }

    @Override
    public String getApplicationTitle() {
        return "novacuts.business";
    }

    @Override
    public String getLocale() {
        return LocaleUtil.getLocale();
    }

    @Override
    public String getServerCallKey() {
        return "F3BE7606-A2F7-4B0B-89C5-11C2CD8AC214";
    }

    public static NovacutsApplication getInstance() {
        return instance;
    }

}
