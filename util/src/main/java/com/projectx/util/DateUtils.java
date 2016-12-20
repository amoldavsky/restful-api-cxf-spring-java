package com.projectx.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtils {
	
	// TODO: add junit
	public static ZonedDateTime getCurrentUTCDateTime() {
        ZonedDateTime utc = ZonedDateTime.now( ZoneOffset.UTC );
        return utc;
    }
	
	// TODO: add junit
	public static Date getCurrentUTCDate() {
        ZonedDateTime utc = getCurrentUTCDateTime();
        return Date.from( utc.toInstant() );
    }
	
}
