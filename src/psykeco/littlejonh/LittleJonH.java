package psykeco.littlejonh;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import psykeco.littlejonh.constants.CronExprOrder;
import psykeco.littlejonh.constants.LittleJonHConstants;

public final class LittleJonH {
	
	/** valid range 0-59 or {@link LittleJonHConstants #EVERY_STAR} if "every" */
	private boolean[] minute=new boolean[59]; 
	
	/** valid range 0-23 or {@link LittleJonHConstants #EVERY_STAR} if "every" */
	private boolean[] hours=new boolean[24]; 
	
	/** valid range 1-31 or {@link LittleJonHConstants #EVERY_STAR} if "every" */
	private boolean[] dayOfMonth=new boolean[31]; 
	
	/** valid range 1-12 or {@link LittleJonHConstants #EVERY_STAR} if "every" */
	private boolean[] month=new boolean[12]; 
	/** valid range 0-6 or {@link LittleJonHConstants #EVERY_STAR} if "every" */
	private boolean[] dayOfWeek=new boolean[7]; 
	
	private LocalDateTime currentTime;
	
	private String cronExpr;
	
	
	public LittleJonH (String cronExpr) {
		if ( cronExpr==null || cronExpr.equals("") ) {
			throw new IllegalArgumentException(LittleJonHConstants.ERR_TEXT_WRONG_CRON);
		}
		this.cronExpr=cronExpr.trim();
		String [] splitCron = this.cronExpr.split(" ");
		
		if (splitCron.length != CronExprOrder.values().length) {
			throw new IllegalArgumentException(LittleJonHConstants.ERR_TEXT_WRONG_CRON);
		}
		
		for (int i=0;i<splitCron.length;i++) {
			if(i==CronExprOrder.MINUTE.ordinal()) {
				//minute=analyze(splitCron[i]);
			} else if(i==CronExprOrder.HOURS.ordinal()) {
				//hours=analyze(splitCron[i]);
			} else if(i==CronExprOrder.DAY_OF_MONTH.ordinal()) {
				//dayOfMonth=analyze(splitCron[i]);
			} else if(i==CronExprOrder.MONTH.ordinal()) {
				//month=analyze(splitCron[i]);
			} else if(i==CronExprOrder.DAY_OF_WEEK.ordinal()) {
				//dayOfWeek=analyze(splitCron[i]);
			}
		}
	}
	
	
	public LocalDateTime nextT() {
		return null;
	}
	
	public LocalDateTime prevT() {
		return null;
	}
	
	public void setCurrentTime(LocalDateTime currentTime ) {
		this.currentTime=currentTime;
	}
	
	public void setNow(){
		setCurrentTime(LocalDateTime.now());
	}
	
	public String getCronExpr() {
		return null;
	}

}
