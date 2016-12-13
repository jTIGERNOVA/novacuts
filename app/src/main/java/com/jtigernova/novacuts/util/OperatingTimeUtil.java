package com.jtigernova.novacuts.util;

import jtiger.webinterface.model.OperatingTimeDay;

public class OperatingTimeUtil {

    private OperatingTimeUtil() {
    }

    public static String getOperatingDisplay(OperatingTimeDay day,
	    String srcTimeZone) {
	String openTime = day.getOpeningTimeString(srcTimeZone);
	String closeTime = day.getClosingTimeString(srcTimeZone);

	return String.format("%s - %s", openTime, closeTime);
    }
}
