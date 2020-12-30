package psykeco.littlejonh;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import psykeco.littlejonh.constants.CronExprOrder;
import psykeco.littlejonh.constants.LittleJonHConstants;

public final class LittleJonH {
	
	private int minute; // 0-59
	private int hours; // 0-23
	private int dayOfMonth; // 1-31
	private Month month; //1-12
	private DayOfWeek dayOfWeek; //0-6 
	
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
				
			} else if(i==CronExprOrder.HOURS.ordinal()) {
				
			} else if(i==CronExprOrder.DAY_OF_MONTH.ordinal()) {
				
			} else if(i==CronExprOrder.MONTH.ordinal()) {
				
			} else if(i==CronExprOrder.DAY_OF_WEEK.ordinal()) {
				
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
