package psykeco.littlejonh.constants;

import psykeco.littlejonh.LittleJonH;

public final class LittleJonHConstants {
	
	private LittleJonHConstants() {}
	
	/** error message for bad initialization of {@link LittleJonH} */
	public final static String ERR_TEXT_WRONG_CRON="wrong cron expression";
	
	public final static String MINUTE_STR="minute";
	public final static String HOURS_STR="hour";
	public final static String DAY_OF_MONTH_STR="day of month";
	public final static String MONTH_STR="month";
	public final static String DAY_OF_WEEK_STR="day of week";
	
	public final static String WORDLIST_MONTH="\0\0\0JANFEBMARAPRMAYJUNJULAUGSEPOCTNOVDEC"; // \0\0\0 is added to match range 1-12
	public final static String WORDLIST_DAY_OF_WEEK="SUNMONTUEWEDTHUFRISAT";
	
	/** max year increment of {@link LittleJonH #nextT() nextT} and {@link LittleJonH #prevT() prevT} for searching a next possible value*/
	public final static int MAX_YEAR_TRY = 4;

}
