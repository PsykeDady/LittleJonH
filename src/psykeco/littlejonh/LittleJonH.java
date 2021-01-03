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
	
	private int[] nextMinute(int ... time) {
		int tmp=0,tmp2=0;
		tmp=searchNextOccurence(minutes, time[0]); //update minute
		tmp2=tmp<=time[0]?time[1]+1:time[1]; // update hour
		time[0]=tmp; // update minute
		tmp=tmp2<time[1]?time[3]+1:time[3]; // update dM
		time[1]=tmp2; // update hour
		tmp2=tmp<time[3]?time[4]+1:time[4]; // update month
		time[3]=tmp; // update dM
		time[5]=tmp2<time[4]?time[5]+1:time[5]; // update year
		time[4]=tmp2; //Update month
		
		return time;
	}
	
	private int[] nextHour(int...time) {
		int tmp=0,tmp2=0;
		tmp=searchNextOccurence(hours, time[1]); //update Hour
		tmp2=tmp<=time[1]?time[2]+1:time[2]; // update dM
		time[1]=tmp; // update Hour
		tmp=tmp2<time[2]?time[3]+1:time[3]; // update Month
		time[2]=tmp2; // update dM
		time[5]=tmp<time[3]?time[5]+1:time[5]; // update year
		time[3]=tmp; // update month
		time[0]=0;
		
		return time;
	}
	
	private int[] nextDayOfMonth(int [] time) {
		int tmp=0,tmp2=0;
		
		tmp=searchNextOccurence(daysOfMonth, time[2]); //update dM
		tmp2=tmp<=time[2]?time[3]+1:time[3]; // update month
		time[2]=tmp; // update dM
		time[5]=tmp2<time[3]?time[5]+1:time[5]; // update year
		time[3]=tmp2; // update month
		time[1]=time[0]=0;
		
		return time;
	}
	
	public int[] nextMonth(int ...time) {
		int tmp=0;
		
		tmp=searchNextOccurence(months, time[3]);
		time[5]=tmp<=time[3]?time[5]+1:time[5];
		time[3]=tmp;
		time[2]=time[1]=time[0]=0;
		
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
		minute=0;hour=1;dayOfMonth=2;month=3;dayOfWeek=4;year=5; // into index. TODO write down for clean		
		
		nextMinute(time);
		
		while(true) {
			if( time[3]>=months.length || ! months[time[3]]) {
				nextMonth(time);
			} else if (time[2]>=monthLength(time[3], time[5]) || ! daysOfMonth[time[2]]) { 
				nextDayOfMonth(time);
			} else if (time[1]>=hours.length || ! hours[time[1]]) {
				nextHour(time);
			} else if (time[0]>=minutes.length || ! minutes[time[0]]) {
				nextMinute(time);
			} else {
				currentTime=LocalDateTime.of(time[5],time[3]+1, time[2]+1, time[1], time[0]);
				time[4]=currentTime.getDayOfWeek().getValue()%7; // TODO waiting for better algorithm..
				if( daysOfWeek[time[4]] ) break;
				nextDayOfMonth(time);
			}
		}
		
		currentTime=LocalDateTime.of(time[5],time[3]+1, time[2]+1, time[1], time[0]);
		System.out.println(currentTime.getDayOfWeek());
		System.out.println(time[4]);
		
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
