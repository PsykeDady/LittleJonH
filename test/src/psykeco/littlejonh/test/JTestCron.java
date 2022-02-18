package psykeco.littlejonh.test;

import static org.junit.Assert.*;
import static psykeco.littlejonh.constants.LittleJonHConstants.DAY_OF_WEEK_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.MINUTE_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.MONTH_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.WORDLIST_DAY_OF_WEEK;
import static psykeco.littlejonh.constants.LittleJonHConstants.WORDLIST_MONTH;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import psykeco.littlejonh.LittleJonH;
import psykeco.littlejonh.utility.LittleJonHUtils;

public class JTestCron {

	@Test
	public void analyzeEveryMinute() {
		// every minute test
		boolean minute		[]=new boolean[60];
		String analysis=LittleJonHUtils.analyze("*",minute,MINUTE_STR);
		assertEquals(analysis, "every minute");
		for (int i =0; i<minute.length;i++)
			assertTrue(minute[i]);
	}
	
	@Test 
	public void analyzeAt() {
		//at minute 6 test
		boolean minute[]=new boolean[60];
		String analysis=LittleJonHUtils.analyze("6",minute,MINUTE_STR);
		assertEquals(analysis, "at minute 6");
		for (int i=0; i<minute.length;i++)
			if(i==6) 	assertTrue(minute[i]);
			else 		assertFalse(minute[i]);
	}
	
	@Test
	public void analyzeRange() {
		//every 30<minute<40 
		boolean [] minute	=new boolean[60];
		String analysis=LittleJonHUtils.analyze("30-40",minute,MINUTE_STR);
		assertEquals(analysis, "every minute on ranges from 30 to 40");
		for (int i=0;i<minute.length;i++)
			if(30<=i && i <= 40) 	assertTrue(minute[i]);
			else 					assertFalse(minute[i]);
	}
	
	@Test
	public void analyzeMultiValue() {
		boolean [] minute	=new boolean[60];
		String analysis=LittleJonHUtils.analyze("14,18",minute,MINUTE_STR);
		assertEquals(analysis, "at minute 14 and at minute 18");
		for (int i=0;i<minute.length;i++)
			if(i==14 || i==18) 		assertTrue(minute[i]);
			else 					assertFalse(minute[i]);
	}
	
	@Test
	public void analyzeFromStep() {
		boolean [] minute	=new boolean[60];
		String analysis=LittleJonHUtils.analyze("5/10",minute,MINUTE_STR);
		assertEquals(analysis, "every minute from 5 with 10 steps at a time");
		for (int i=0;i<minute.length;i++)
			if(i>=5 && (i-5)%10==0) assertTrue(minute[i]);
			else 					assertFalse(minute[i]);
	}
	
	@Test
	public void analyzeStepOnly() {
		boolean [] minute	=new boolean[60];
		String analysis=LittleJonHUtils.analyze("*/4",minute,MINUTE_STR);
		assertEquals(analysis, "every minute with 4 steps at a time");
		for (int i=0;i<minute.length;i++)
			if(i%4==0)				 assertTrue(minute[i]);
			else 					assertFalse(minute[i]);
	}
	
	@Test
	public void analyzeStepRange() {
		boolean [] minute =new boolean[60];
		String analysis=LittleJonHUtils.analyze("15-30/5",minute,MINUTE_STR);
		assertEquals(analysis, "every minute on ranges from 15 to 30 with 5 steps at a time");
		for (int i=0;i<minute.length;i++)
			if(15<=i && i<=30 && (i-15)%5==0 )
									 assertTrue(minute[i]);
			else 					assertFalse(minute[i]);
	}
	
	@Test
	public void analyzeWordList() {
		boolean dayOfWeek	[]=new boolean[ 7];
		String analysis=LittleJonHUtils.analyze("SUN",dayOfWeek,DAY_OF_WEEK_STR);
		assertEquals(analysis, "at day of week 0");
		for (int i=0; i< dayOfWeek.length;i++) 
			if(i==0) 	assertTrue(dayOfWeek[i]);
			else 		assertFalse(dayOfWeek[i]);
	}
	
	@Test
	public void analyzeWordListStepRange() {
		boolean month[]=new boolean[31];
		String analysis=LittleJonHUtils.analyze("JAN-JUL/2",month,MONTH_STR);
		assertEquals(analysis, "every month on ranges from 1 to 7 with 2 steps at a time");
		for(int i=0;i<month.length;i++) 
			if(0<=i && i<=6 && i%2==0)	 assertTrue(month[i]);
			else 				assertFalse(month[i]);
	}
	
	@Test
	public void indexOfWeekWordList() {
		int i=0;
		assertEquals(LittleJonHUtils.indexOfWordlist("SUN",WORDLIST_DAY_OF_WEEK ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("MON",WORDLIST_DAY_OF_WEEK ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("TUE",WORDLIST_DAY_OF_WEEK ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("WED",WORDLIST_DAY_OF_WEEK ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("THU",WORDLIST_DAY_OF_WEEK ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("FRI",WORDLIST_DAY_OF_WEEK ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("SAT",WORDLIST_DAY_OF_WEEK ), i  );
	}
	
	@Test
	public void indexOfMonthWordList() {
		int i=1;
		assertEquals(LittleJonHUtils.indexOfWordlist("JAN",WORDLIST_MONTH ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("FEB",WORDLIST_MONTH ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("MAR",WORDLIST_MONTH ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("APR",WORDLIST_MONTH ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("MAY",WORDLIST_MONTH ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("JUN",WORDLIST_MONTH ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("JUL",WORDLIST_MONTH ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("AUG",WORDLIST_MONTH ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("SEP",WORDLIST_MONTH ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("OCT",WORDLIST_MONTH ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("NOV",WORDLIST_MONTH ), i++);
		assertEquals(LittleJonHUtils.indexOfWordlist("DEC",WORDLIST_MONTH ), i  );
	}
	
	@Test
	public void nextComplete() {
		String cronExpr="1 1/2 31 4,5,12 6";
		String human=
			"at minute 1"+ '\n'+
			"every hour from 1 with 2 steps at a time"+ '\n'+
			"at day of month 31"+ '\n'+
			"at month 4 and at month 5 and at month 12"+ '\n'+
			"at day of week 6"
		;
		
		LittleJonH l=new LittleJonH(cronExpr);
		assertEquals(l.getCronExpr(),cronExpr);
		assertEquals(l.getHuman(),human);
		LocalDateTime after=LocalDateTime.of(2022, Month.DECEMBER, 31, 01, 01);
		l.setCurrentTime(LocalDateTime.of(2020, Month.JANUARY, 05, 18, 00));
		assertEquals(l.nextT(),after);
	}
		
	@Test
	public void nextMinute() {
		String cronExpr="* * * * *";
		String human=
			"every minute"+ '\n'+
			"every hour"+ '\n'+
			"every day of month"+ '\n'+
			"every month"+ '\n'+
			"every day of week"
		;
		
		LittleJonH l=new LittleJonH(cronExpr);
		assertEquals(l.getCronExpr(),cronExpr);
		assertEquals(l.getHuman(),human);
		LocalDateTime n=LocalDateTime.now();
		l.setCurrentTime(n);
		assertEquals((n.getMinute()+1)%60,l.nextT().getMinute());
		
	}
	
	@Test
	public void nextMinuteChangeAll() {
		String cronExpr="* * * * *";
		String human=
			"every minute"+ '\n'+
			"every hour"+ '\n'+
			"every day of month"+ '\n'+
			"every month"+ '\n'+
			"every day of week"
		;
		
		LittleJonH l=new LittleJonH( cronExpr);
		assertEquals(l.getCronExpr(),cronExpr);
		assertEquals(l.getHuman()   ,human);
		LocalDateTime after=LocalDateTime.of(2019, Month.JANUARY, 1, 00, 00);
		l.setCurrentTime(LocalDateTime.of(2018, Month.DECEMBER, 31, 23, 59));
		LocalDateTime n=l.nextT();
		assertEquals(n,after);
	}
	
	@Test
	public void nextMinuteChangeFEB29() {
		String cronExpr="* * * * *";
		String human=
			"every minute"+ '\n'+
			"every hour"+ '\n'+
			"every day of month"+ '\n'+
			"every month"+ '\n'+
			"every day of week"
		;
		
		LittleJonH l=new LittleJonH( cronExpr);
		assertEquals(l.getCronExpr(),cronExpr);
		assertEquals(l.getHuman()   ,human);
		LocalDateTime after=LocalDateTime.of(2020, Month.MARCH, 1, 00, 00);
		l.setCurrentTime(LocalDateTime.of(2020, Month.FEBRUARY, 29, 23, 59));
		LocalDateTime n=l.nextT();
		assertEquals(n,after);
	}
	
	@Test
	public void nextMinuteChangeFEB28() {
		String cronExpr="* * * * *";
		String human=
			"every minute"+ '\n'+
			"every hour"+ '\n'+
			"every day of month"+ '\n'+
			"every month"+ '\n'+
			"every day of week"
		;
		
		LittleJonH l=new LittleJonH( cronExpr);
		assertEquals(l.getCronExpr(),cronExpr);
		assertEquals(l.getHuman()   ,human);
		LocalDateTime after=LocalDateTime.of(2021, Month.MARCH, 1, 00, 00);
		l.setCurrentTime(LocalDateTime.of(2021, Month.FEBRUARY, 28, 23, 59));
		LocalDateTime n=l.nextT();
		assertEquals(n,after);
	}
	
	@Test
	public void nextMinuteChangeAPR() {
		String cronExpr="* * * * *";
		String human=
			"every minute"+ '\n'+
			"every hour"+ '\n'+
			"every day of month"+ '\n'+
			"every month"+ '\n'+
			"every day of week"
		;
		
		LittleJonH l=new LittleJonH( cronExpr);
		assertEquals(l.getCronExpr(),cronExpr);
		assertEquals(l.getHuman()   ,human);
		LocalDateTime after=LocalDateTime.of(2021, Month.MAY, 1, 00, 00);
		l.setCurrentTime(LocalDateTime.of(2021, Month.APRIL, 30, 23, 59));
		LocalDateTime n=l.nextT();
		assertEquals(n,after);
	}
	
	@Test
	public void nextAtDayWeek() {
		String cronExpr="1 20 * * MON";
		String human=
			"at minute 1"+ '\n'+
			"at hour 20"+ '\n'+
			"every day of month"+ '\n'+
			"every month"+ '\n'+
			"at day of week 1"
		;
		
		LittleJonH l=new LittleJonH(cronExpr);
		assertEquals(l.getCronExpr(),cronExpr);
		assertEquals(l.getHuman(),human);
		LocalDateTime after=LocalDateTime.of(2018, Month.JUNE, 11, 20, 01);
		l.setCurrentTime(LocalDateTime.of(2018, Month.JUNE, 04, 20, 01));
		LocalDateTime n=l.nextT();
		assertEquals(n,after);
	}
	
	@Test
	public void nextAtMonths() {
		String cronExpr="0 0 1 FEB,JUN *";
		String human=
			"at minute 0"+ '\n'+
			"at hour 0"+ '\n'+
			"at day of month 1"+ '\n'+
			"at month 2 and at month 6"+ '\n'+
			"every day of week"
		;
		
		LittleJonH l=new LittleJonH(cronExpr);
		assertEquals(l.getCronExpr(),cronExpr);
		assertEquals(l.getHuman(),human);
		LocalDateTime after=LocalDateTime.of(2020, Month.FEBRUARY, 01, 00, 00);
		l.setCurrentTime(LocalDateTime.of(2020, Month.JANUARY, 11, 20, 01));
		LocalDateTime n=l.nextT();
		assertEquals(n,after);
		after=LocalDateTime.of(2020, Month.JUNE, 01, 00, 00);
		n=l.nextT();
		assertEquals(after,n);
	}
	
	@Test 
	public void nextPreciseDayWithStep(){
		String cronExpr="* 16/2 * * *";
		String human=
			"every minute"+ '\n'+
			"every hour from 16 with 2 steps at a time"+ '\n'+
			"every day of month"+ '\n'+
			"every month"+ '\n'+
			"every day of week"
		;
		
		LittleJonH l=new LittleJonH(cronExpr);
		assertEquals(l.getCronExpr(),cronExpr);
		assertEquals(l.getHuman(),human);
		LocalDateTime after=LocalDateTime.of(2019, Month.JULY, 1, 18, 00);
		l.setCurrentTime(LocalDateTime.of(2019, Month.JULY, 1, 17, 43));
		assertEquals(l.nextT(),after);
	}
	
	@Test
	public void nextMinuteAt() {
		String cronExpr="1 * * * *";
		String human=
			"at minute 1"+ '\n'+
			"every hour"+ '\n'+
			"every day of month"+ '\n'+
			"every month"+ '\n'+
			"every day of week"
		;
		
		LittleJonH l=new LittleJonH(cronExpr);
		assertEquals(l.getCronExpr(),cronExpr);
		assertEquals(l.getHuman(),human);
		LocalDateTime after=LocalDateTime.of(2019, Month.APRIL, 15, 16, 01);
		l.setCurrentTime(LocalDateTime.of(2019, Month.APRIL, 15, 15, 15));
		assertEquals(l.nextT(),after);
	}

}
