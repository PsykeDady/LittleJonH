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
	private boolean[] minutes=new boolean[60]; 
	
	/**  */
	private boolean[] hours=new boolean[24]; 
	
	/**  */
	private boolean[] daysOfMonth=new boolean[31]; 
	
	/**  */
	private boolean[] months=new boolean[12]; 
	
	/**  */
	private boolean[] daysOfWeek=new boolean[7]; 
	
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
			  hbuild.append(analyze(splitCron[i],daysOfMonth,DAY_OF_MONTH_STR));
			} else if(i==CronExprOrder.MONTH.ordinal()) {
			  hbuild.append(analyze(splitCron[i],months,MONTH_STR));
			} else if(i==CronExprOrder.DAY_OF_WEEK.ordinal()) {
			  hbuild.append(analyze(splitCron[i],daysOfWeek,DAY_OF_WEEK_STR));
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
		int dayOfMonth	= currentTime.getDayOfMonth()-1;
		int month		= currentTime.getMonthValue()-1;
		int dayOfWeek	= currentTime.getDayOfWeek().getValue()%7;
		int year		= currentTime.getYear();
		
		int tmp=0,tmp2=0;
		
		tmp=searchNextOccurence(minutes, minute); //update minute
		tmp2=tmp<=minute?hour+1:hour; // update hour
		minute=tmp; // update minute
		tmp=tmp2<hour?dayOfMonth+1:dayOfMonth; // update dM
		hour=tmp2; // update hour
		tmp2=tmp<dayOfMonth?month+1:month; // update month
		dayOfMonth=tmp; // update dM
		year=tmp2<month?year+1:year; // update year
		month=tmp2; //Update month
		
		while(true) {
			if( month>=months.length || ! months[month]) {
				tmp=searchNextOccurence(months, month);
				year=tmp<=month?year+1:year;
				dayOfMonth=hour=minute=0;
				month=tmp;
			} else if (dayOfMonth>=monthLength(month, year) || ! daysOfMonth[dayOfMonth]) { 
				tmp=searchNextOccurence(daysOfMonth, dayOfMonth); //update dM
				tmp2=tmp<=dayOfMonth?month+1:month; // update month
				dayOfMonth=tmp; // update dM
				year=tmp2<month?year+1:year; // update year
				month=tmp2; // update month
				hour=minute=0;
			} else if (hour>=hours.length || ! hours[hour]) {
				tmp=searchNextOccurence(hours, hour); //update Hour
				tmp2=tmp<=hour?dayOfMonth+1:dayOfMonth; // update dM
				hour=tmp; // update Hour
				tmp=tmp2<dayOfMonth?month+1:month; // update Month
				dayOfMonth=tmp2; // update dM
				year=tmp<month?year+1:year; // update year
				month=tmp; // update month
				minute=0;
			} else if (minute>=minutes.length || ! minutes[minute]) {
				tmp=searchNextOccurence(minutes, minute); //update minute
				tmp2=tmp<=minute?hour+1:hour; // update hour
				minute=tmp; // update minute
				tmp=tmp2<hour?dayOfMonth+1:dayOfMonth; // update dM
				hour=tmp2; // update hour
				tmp2=tmp<dayOfMonth?month+1:month; // update month
				dayOfMonth=tmp; // update dM
				year=tmp2<month?year+1:year; // update year
				month=tmp2; //Update month
			} else {
				break;
			}
		}
		
		currentTime=LocalDateTime.of(year,month+1, dayOfMonth+1, hour, minute);
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
