package com.jtigernova.novacuts.app;

import jtiger.util.android.locale.LocaleUtil;
import jtiger.webinterface.IWebInterfaceApplication;
import jtiger.webinterface.android.AndroidNetworkPreparer;
import jtiger.webinterface.util.ClientUtil;
import jtiger.webinterface.util.EnvironmentType;
import android.app.Application;

public class NovacutsApplication extends Application implements IWebInterfaceApplication{

    private final static NovacutsApplication instance = new NovacutsApplication();
    
    @Override
    public void onCreate() {
	super.onCreate();

	if(NovacutsConstants.ENVIRONMENT == EnvironmentType.LOCAL){
	    ClientUtil.setLocalhostIP("192.168.0.1");
	}

	if (NovacutsConstants.ENVIRONMENT == EnvironmentType.PRODUCTION) {
	    AndroidNetworkPreparer.updateNetworkPreparer(
		    NovacutsConstants.ENVIRONMENT, false, false);
	} else {
	    AndroidNetworkPreparer.updateNetworkPreparer(
		    NovacutsConstants.ENVIRONMENT, true, true);
	}
    }

    @Override
    public String getApplicationTitle() {
	return "novacuts.customer";
    }

    @Override
    public String getLocale() {
	return LocaleUtil.getLocale();
    }

    @Override
    public String getServerCallKey() {
	return "6EE94BD8-BE37-4E17-A8D8-1D3C9028D6AD";
    }

    public static NovacutsApplication getInstance() {
	return instance;
    }

}
