package psykeco.littlejonh.test;

import static org.junit.jupiter.api.Assertions.*;
import static psykeco.littlejonh.constants.LittleJonHConstants.DAY_OF_WEEK_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.MINUTE_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.MONTH_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.WORDLIST_DAY_OF_WEEK;
import static psykeco.littlejonh.constants.LittleJonHConstants.WORDLIST_MONTH;

import org.junit.jupiter.api.Test;

import psykeco.littlejonh.LittleJonH;
import psykeco.littlejonh.utility.LittleJonHUtils;

class JTestCron {

	@Test
	void analyzeEveryMinute() {
		// every minute test
		boolean minute		[]=new boolean[60];
		String analysis=LittleJonHUtils.analyze("*",minute,MINUTE_STR);
		assertEquals(analysis, "every minute");
		for (int i =0; i<minute.length;i++)
			assertTrue(minute[i]);
	}
	
	@Test 
	void analyzeAt() {
		//at minute 6 test
		boolean minute[]=new boolean[60];
		String analysis=LittleJonHUtils.analyze("6",minute,MINUTE_STR);
		assertEquals(analysis, "at minute 6");
		for (int i=0; i<minute.length;i++)
			if(i==6) 	assertTrue(minute[i]);
			else 		assertFalse(minute[i]);
	}
	
	@Test
	void analyzeRange() {
		//every 30<minute<40 
		boolean [] minute	=new boolean[60];
		String analysis=LittleJonHUtils.analyze("30-40",minute,MINUTE_STR);
		assertEquals(analysis, "every minute on ranges from 30 to 40");
		for (int i=0;i<minute.length;i++)
			if(30<=i && i <= 40) 	assertTrue(minute[i]);
			else 					assertFalse(minute[i]);
	}
	
	@Test
	void analyzeMultiValue() {
		boolean [] minute	=new boolean[60];
		String analysis=LittleJonHUtils.analyze("14,18",minute,MINUTE_STR);
		assertEquals(analysis, "at minute 14 and at minute 18");
		for (int i=0;i<minute.length;i++)
			if(i==14 || i==18) 		assertTrue(minute[i]);
			else 					assertFalse(minute[i]);
	}
	
	@Test
	void analyzeFromStep() {
		boolean [] minute	=new boolean[60];
		String analysis=LittleJonHUtils.analyze("5/10",minute,MINUTE_STR);
		assertEquals(analysis, "every minute from 5 with 10 steps at a time");
		for (int i=0;i<minute.length;i++)
			if(i>=5 && (i-5)%10==0) assertTrue(minute[i]);
			else 					assertFalse(minute[i]);
	}
	
	@Test
	void analyzeStepOnly() {
		boolean [] minute	=new boolean[60];
		String analysis=LittleJonHUtils.analyze("*/4",minute,MINUTE_STR);
		assertEquals(analysis, "every minute with 4 steps at a time");
		for (int i=0;i<minute.length;i++)
			if(i%4==0)				 assertTrue(minute[i]);
			else 					assertFalse(minute[i]);
	}
	
	@Test
	void analyzeStepRange() {
		boolean [] minute =new boolean[60];
		String analysis=LittleJonHUtils.analyze("15-30/5",minute,MINUTE_STR);
		assertEquals(analysis, "every minute on ranges from 15 to 30 with 5 steps at a time");
		for (int i=0;i<minute.length;i++)
			if(15<=i && i<=30 && (i-15)%5==0 )
									 assertTrue(minute[i]);
			else 					assertFalse(minute[i]);
	}
	
	@Test
	void analyzeWordList() {
		boolean dayOfWeek	[]=new boolean[ 7];
		String analysis=LittleJonHUtils.analyze("SUN",dayOfWeek,DAY_OF_WEEK_STR);
		assertEquals(analysis, "at day of week 0");
		for (int i=0; i< dayOfWeek.length;i++) 
			if(i==0) 	assertTrue(dayOfWeek[i]);
			else 		assertFalse(dayOfWeek[i]);
	}
	
	@Test
	void analyzeWordListStepRange() {
		boolean month[]=new boolean[31];
		String analysis=LittleJonHUtils.analyze("JAN-JUL/2",month,MONTH_STR);
		assertEquals(analysis, "every month on ranges from 1 to 7 with 2 steps at a time");
		for(int i=0;i<month.length;i++) 
			if(0<=i && i<=6 && i%2==0)	 assertTrue(month[i]);
			else 				assertFalse(month[i]);
		
	}
	
	@Test
	void indexOfWeekWordList() {
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
	void indexOfMonthWordList() {
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
	void nextTest() {
		assertTrue(true);
		LittleJonH l=new LittleJonH("1 1/2 31 4,5,12 6");
		System.out.println(l.getCronExpr());
		System.out.println(l.getHuman()+'\n');
		System.out.println("curr "+l.getCurrent());
		System.out.println("next "+l.nextT()+'\n'+'\n');
		
		l=new LittleJonH("* * * * 1");
		System.out.println(l.getCronExpr());
		System.out.println(l.getHuman()+'\n');
		System.out.println("curr "+l.getCurrent());
		System.out.println("next "+l.nextT()+'\n'+'\n');
		
		l=new LittleJonH("* * * * *");
		System.out.println(l.getCronExpr());
		System.out.println(l.getHuman()+'\n');
		System.out.println("curr "+l.getCurrent());
		System.out.println("next "+l.nextT()+'\n'+'\n');
		
		l=new LittleJonH("* 16/2 * * *");
		System.out.println(l.getCronExpr());
		System.out.println(l.getHuman()+'\n');
		System.out.println("curr "+l.getCurrent());
		System.out.println("next "+l.nextT()+'\n'+'\n');
		
		l=new LittleJonH("1 * * * *");
		System.out.println(l.getCronExpr());
		System.out.println(l.getHuman()+'\n');
		System.out.println("curr "+l.getCurrent());
		System.out.println("next "+l.nextT()+'\n'+'\n');
	}

}
