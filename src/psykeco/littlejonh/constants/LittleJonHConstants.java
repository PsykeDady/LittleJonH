package psykeco.littlejonh.constants;

import psykeco.littlejonh.LittleJonH;

public final class LittleJonHConstants {
	
	private LittleJonHConstants() {}
	
	/** error message for bad initialization of {@link LittleJonH} */
	public final static String ERR_TEXT_WRONG_CRON="wrong cron expression";
	
	public final static String CHAR_EVERY="*";
	public final static String CHAR_SEPARATOR=",";
	public final static String CHAR_STEP="/";
	
	/** in cron expression, * sign stand for "every", on variabiles we have this constant */
	public final static int EVERY_STAR = -1;

}
