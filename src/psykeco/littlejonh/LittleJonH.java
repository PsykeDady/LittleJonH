package psykeco.littlejonh;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import psykeco.littlejonh.constants.CronExprOrder;
import static psykeco.littlejonh.constants.LittleJonHConstants.ERR_TEXT_WRONG_CRON;
import static psykeco.littlejonh.constants.LittleJonHConstants.MINUTE_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.HOURS_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.DAY_OF_MONTH_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.MONTH_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.DAY_OF_WEEK_STR;
import static psykeco.littlejonh.utility.LittleJonHUtils.analyze;
import static psykeco.littlejonh.utility.LittleJonHUtils.searchNextOccurence;
import static psykeco.littlejonh.utility.LittleJonHUtils.monthLength;


public final class LittleJonH {
	
	/**  */
	private boolean[] minutes=new boolean[59]; 
	
	/**  */
	private boolean[] hours=new boolean[24]; 
	
	/**  */
	private boolean[] dayOfMonth=new boolean[31]; 
	
	/**  */
	private boolean[] month=new boolean[12]; 
	
	/**  */
	private boolean[] dayOfWeek=new boolean[7]; 
	
	private LocalDateTime currentTime;
	
	private String cronExpr;
	
	private String human="";
	
	public LittleJonH (String cronExpr) {
		if ( cronExpr==null || cronExpr.equals("") ) {
			throw new IllegalArgumentException(ERR_TEXT_WRONG_CRON);
		}
		this.cronExpr=cronExpr.trim();
		
		String [] splitCron = this.cronExpr.split(" ");
		
		if (splitCron.length != CronExprOrder.values().length) {
			throw new IllegalArgumentException(ERR_TEXT_WRONG_CRON);
		}
		
		StringBuilder hbuild=new StringBuilder(2000);
		for (int i=0;i<splitCron.length;i++) {
			if(i==CronExprOrder.MINUTE.ordinal()) {
			  hbuild.append(analyze(splitCron[i],minutes,MINUTE_STR));
			} else if(i==CronExprOrder.HOURS.ordinal()) {
			  hbuild.append(analyze(splitCron[i],hours,HOURS_STR));
			} else if(i==CronExprOrder.DAY_OF_MONTH.ordinal()) {
			  hbuild.append(analyze(splitCron[i],dayOfMonth,DAY_OF_MONTH_STR));
			} else if(i==CronExprOrder.MONTH.ordinal()) {
			  hbuild.append(analyze(splitCron[i],month,MONTH_STR));
			} else if(i==CronExprOrder.DAY_OF_WEEK.ordinal()) {
			  hbuild.append(analyze(splitCron[i],dayOfWeek,DAY_OF_WEEK_STR));
			}
			hbuild.append('\n');
		}
		hbuild.deleteCharAt(hbuild.length()-1);
		human=hbuild.toString();
		setNow();
	}
	
	public LocalDateTime getCurrent() {
		return currentTime;
	}
	
	public LocalDateTime nextT() {
		
		int minute		= currentTime.getMinute();
		int hour		= currentTime.getHour();
		int dayOfMonth	= currentTime.getDayOfMonth();
		int month		= currentTime.getMonthValue()-1;
		int dayOfWeek	= currentTime.getDayOfWeek().getValue()%7;
		int year		= currentTime.getYear();
		
		minute			= searchNextOccurence(this.minutes, minute);
		
		hour			= (minute<currentTime.getMinute())? searchNextOccurence(hours, hour)%24 :hour;
		
		if(hour<currentTime.getHour()) { 
			dayOfMonth	=searchNextOccurence(this.dayOfMonth, dayOfMonth)%monthLength(month,year); 
			dayOfWeek	=searchNextOccurence(this.dayOfWeek, dayOfWeek)%7; //TODO non sfruttato
		}
		
		month			= dayOfMonth<currentTime.getMonthValue()-1?searchNextOccurence(this.month, month)%12:month;
		
		currentTime=LocalDateTime.of(year, month+1, dayOfMonth, hour, minute);
		
		return currentTime;
	}
	
	public LocalDateTime prevT() {
		return null;
	}
	
	public void setCurrentTime(LocalDateTime currentTime ) {
		this.currentTime=currentTime;
	}
	
	public void setNow(){
		setCurrentTime(LocalDateTime.now());
		setCurrentTime(LocalDateTime.of(
				currentTime.getYear(), 
				currentTime.getMonth(), 
				currentTime.getDayOfMonth(), 
				currentTime.getHour(), 
				currentTime.getMinute()
		));
	}
	
	public String getCronExpr() {
		return cronExpr;
	}
	
	public String getHuman() {
		return human;
	}

}
