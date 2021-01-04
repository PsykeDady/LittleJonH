package psykeco.littlejonh.test;

import static org.junit.jupiter.api.Assertions.*;
import static psykeco.littlejonh.constants.LittleJonHConstants.DAY_OF_WEEK_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.MINUTE_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.MONTH_STR;

import org.junit.jupiter.api.Test;

import psykeco.littlejonh.utility.LittleJonHUtils;

class JTestCron {

	@Test
	void analyzeTest() {
		boolean minute		[]=new boolean[60];
		boolean dayOfWeek	[]=new boolean[ 7];
		boolean month		[]=new boolean[31];
		
		// every minute test
		assertEquals(LittleJonHUtils.analyze("*",minute,MINUTE_STR), "every minute");
		for (int i =0; i<minute.length;i++)
			assertTrue(minute[i]);
		
		
		//at minute 6 test
		minute	=new boolean[60];
		assertEquals(LittleJonHUtils.analyze("6",minute,MINUTE_STR), "at minute 6");
		for (int i=0; i<minute.length;i++)
			if(i==6) 	assertTrue(minute[i]);
			else 		assertFalse(minute[i]);
		
		
		//every 30<minute<40 
		minute	=new boolean[60];
		assertEquals(LittleJonHUtils.analyze("30-40",minute,MINUTE_STR), "every minute on ranges from 30 to 40");
		for (int i=0;i<minute.length;i++)
			if(30<=i && i <= 40) 	assertTrue(minute[i]);
			else 					assertFalse(minute[i]);
		
		minute	=new boolean[60];
		assertEquals(LittleJonHUtils.analyze("14,18",minute,MINUTE_STR), "at minute 14 and at minute 18");
		minute	=new boolean[60];
		assertEquals(LittleJonHUtils.analyze("5/10",minute,MINUTE_STR), "every minute from 5 with 10 steps at a time");
		minute	=new boolean[60];
		assertEquals(LittleJonHUtils.analyze("*/4",minute,MINUTE_STR), "every minute 4 steps at a time");
		minute	=new boolean[60];
		assertEquals(LittleJonHUtils.analyze("15-30/5",minute,MINUTE_STR), "every minute on ranges from 15 to 30 with 5 steps at a time");
		minute	=new boolean[60];
		assertEquals(LittleJonHUtils.analyze("SUN",dayOfWeek,DAY_OF_WEEK_STR), "at day of week 0");
		dayOfWeek	=new boolean[ 7];
		assertEquals(LittleJonHUtils.analyze("JAN-JUL/2",month,MONTH_STR), "every month on ranges from 1 to 7 with 2 steps at a time");
		month	=new boolean[31];
	}
	
	@Test
	void wordlistTest() {
		
	}
	
	@Test
	void nextTest() {
		
	}

}
