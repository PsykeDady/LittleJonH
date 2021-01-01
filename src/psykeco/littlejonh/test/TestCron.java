package psykeco.littlejonh.test;

import psykeco.littlejonh.LittleJonH;
import psykeco.littlejonh.constants.LittleJonHConstants;
import psykeco.littlejonh.utility.LittleJonHUtils;
import static psykeco.littlejonh.constants.LittleJonHConstants.MINUTE_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.HOURS_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.DAY_OF_MONTH_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.MONTH_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.DAY_OF_WEEK_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.WORDLIST_DAY_OF_WEEK;
import static psykeco.littlejonh.constants.LittleJonHConstants.WORDLIST_MONTH;


public class TestCron {
	public static void main(String[] args) {
		
		boolean minute[]=new boolean[60];
		
		System.out.println(LittleJonHUtils.analyze("*",minute,MINUTE_STR)+"\n");
		System.out.println(LittleJonHUtils.analyze("6",minute,MINUTE_STR)+"\n");
		System.out.println(LittleJonHUtils.analyze("30-40",minute,MINUTE_STR)+"\n");
		System.out.println(LittleJonHUtils.analyze("14,18",minute,MINUTE_STR)+"\n");
		System.out.println(LittleJonHUtils.analyze("5/10",minute,MINUTE_STR)+"\n");
		System.out.println(LittleJonHUtils.analyze("*/4",minute,MINUTE_STR)+"\n");
		System.out.println(LittleJonHUtils.analyze("15-30/5",minute,MINUTE_STR)+"\n");
		
		LittleJonH l=new LittleJonH("1 1/2 31 4,5,12 *");
		System.out.println(l.getHuman());
		
		
		System.out.println("SUNDAY IS "+LittleJonHUtils.indexOfWordlist("SUN",WORDLIST_DAY_OF_WEEK ));
		System.out.println("JUNE IS "+LittleJonHUtils.indexOfWordlist("JUN",WORDLIST_MONTH ));
	}
}
