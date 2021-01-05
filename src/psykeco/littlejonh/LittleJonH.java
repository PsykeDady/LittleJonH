package psykeco.littlejonh;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import psykeco.littlejonh.constants.CronExprOrder;
import psykeco.littlejonh.utility.LittleJonHUtils;
import sun.security.util.Length;

import static psykeco.littlejonh.constants.LittleJonHConstants.ERR_TEXT_WRONG_CRON;
import static psykeco.littlejonh.constants.LittleJonHConstants.MINUTE_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.HOURS_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.DAY_OF_MONTH_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.MONTH_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.DAY_OF_WEEK_STR;
import static psykeco.littlejonh.utility.LittleJonHUtils.analyze;
import static psykeco.littlejonh.utility.LittleJonHUtils.searchNextOccurence;
import static psykeco.littlejonh.utility.LittleJonHUtils.monthLength;
import static psykeco.littlejonh.constants.CronExprOrder.*;


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
		
		if (splitCron.length != CronExprOrder.values().length-1) {
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
	
	private int[] nextMinute(int [] time) {
		int tmp=0;
		tmp=searchNextOccurence(minutes, time[MINUTE.ordinal()]); //update minute
		time[HOURS.ordinal()]=tmp<=time[MINUTE.ordinal()]?time[HOURS.ordinal()]+1:time[HOURS.ordinal()]; // update hour
		time[MINUTE.ordinal()]=tmp; // update minute
		
		return time;
	}
	
	private int[] nextHour(int [] time) {
		int tmp=0;
		tmp=searchNextOccurence(hours, time[HOURS.ordinal()]); //update Hour
		time[DAY_OF_MONTH.ordinal()]=tmp<=time[HOURS.ordinal()]?time[DAY_OF_MONTH.ordinal()]+1:time[DAY_OF_MONTH.ordinal()]; // update dM
		time[HOURS.ordinal()]=tmp; // update Hour
		time[MINUTE.ordinal()]=0;
		
		return time;
	}
	
	private int[] nextDayOfMonth(int [] time) {
		int tmp=0;
		
		tmp=searchNextOccurence(daysOfMonth, time[DAY_OF_MONTH.ordinal()]); //update dM
		time[MONTH.ordinal()]=tmp<=time[DAY_OF_MONTH.ordinal()]?time[MONTH.ordinal()]+1:time[MONTH.ordinal()]; // update month
		time[DAY_OF_MONTH.ordinal()]=tmp; // update dM
		time[HOURS.ordinal()]=time[MINUTE.ordinal()]=0;
		
		return time;
	}
	
	public int[] nextMonth(int [] time) {
		int tmp=0;
		
		tmp=searchNextOccurence(months, time[MONTH.ordinal()]);
		time[YEAR.ordinal()]=tmp<=time[MONTH.ordinal()]?time[YEAR.ordinal()]+1:time[YEAR.ordinal()];
		time[MONTH.ordinal()]=tmp;
		time[DAY_OF_MONTH.ordinal()]=time[HOURS.ordinal()]=time[MINUTE.ordinal()]=0;
		
		return time;
	}
	
	public LocalDateTime nextT() {
		int minute		= currentTime.getMinute();
		int hour		= currentTime.getHour();
		int dayOfMonth	= currentTime.getDayOfMonth()-1;
		int month		= currentTime.getMonthValue()-1;
		int dayOfWeek	= currentTime.getDayOfWeek().getValue()%7;
		int year		= currentTime.getYear();
		
		int [] time= {minute,hour,dayOfMonth,month,dayOfWeek,year};
		
		nextMinute(time);
		
		while(true) {
			if( time[MONTH.ordinal()]>=months.length || ! months[time[MONTH.ordinal()]]) {
				nextMonth(time);
			} else if (time[DAY_OF_MONTH.ordinal()]>=monthLength(time[MONTH.ordinal()], time[YEAR.ordinal()]) || ! daysOfMonth[time[DAY_OF_MONTH.ordinal()]]) { 
				nextDayOfMonth(time);
			} else if (time[HOURS.ordinal()]>=hours.length || ! hours[time[HOURS.ordinal()]]) {
				nextHour(time);
			} else if (time[MINUTE.ordinal()]>=minutes.length || ! minutes[time[MINUTE.ordinal()]]) {
				nextMinute(time);
			} else {
				currentTime=LocalDateTime.of(time[YEAR.ordinal()],time[MONTH.ordinal()]+1, time[DAY_OF_MONTH.ordinal()]+1, time[HOURS.ordinal()], time[MINUTE.ordinal()]);
				time[DAY_OF_WEEK.ordinal()]=currentTime.getDayOfWeek().getValue()%7; // TODO waiting for better algorithm..
				if( daysOfWeek[time[DAY_OF_WEEK.ordinal()]] ) break;
				nextDayOfMonth(time);
			}
		}
		
		currentTime=LocalDateTime.of(time[YEAR.ordinal()],time[MONTH.ordinal()]+1, time[DAY_OF_MONTH.ordinal()]+1, time[HOURS.ordinal()], time[MINUTE.ordinal()]);
		
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
