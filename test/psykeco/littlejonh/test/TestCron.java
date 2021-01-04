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
		
		boolean minute		[]=new boolean[60];
		boolean dayOfWeek	[]=new boolean[ 7];
		boolean month		[]=new boolean[31];
		
		System.out.println(LittleJonHUtils.analyze("*",minute,MINUTE_STR)+"\n"); //every minute
		System.out.println(LittleJonHUtils.analyze("6",new boolean[60],MINUTE_STR)+"\n"); // at minute 6
		System.out.println(LittleJonHUtils.analyze("30-40",new boolean[60],MINUTE_STR)+"\n"); // every minute on ranges from 30 to 40
		System.out.println(LittleJonHUtils.analyze("14,18",new boolean[60],MINUTE_STR)+"\n"); // at minute 14 and at minute 18
		System.out.println(LittleJonHUtils.analyze("5/10",new boolean[60],MINUTE_STR)+"\n"); // every minute from 5 with 10 steps at a time
		System.out.println(LittleJonHUtils.analyze("*/4",new boolean[60],MINUTE_STR)+"\n"); // every minute 4 steps at a tmie
		System.out.println(LittleJonHUtils.analyze("15-30/5",new boolean[60],MINUTE_STR)+"\n"); // every minute on ranges from 15 to 30 with 5 steps at a time
		System.out.println(LittleJonHUtils.analyze("SUN",dayOfWeek,DAY_OF_WEEK_STR)+"\n"); // at day of week 0
		System.out.println(LittleJonHUtils.analyze("JAN-JUL/2",month,MONTH_STR)+"\n"); // every month on ranges from 1 to 7 with 2 steps at a time
		
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
		
		System.out.println("SUNDAY IS "+LittleJonHUtils.indexOfWordlist("SUN",WORDLIST_DAY_OF_WEEK ));
		System.out.println("JUNE IS "+LittleJonHUtils.indexOfWordlist("JUN",WORDLIST_MONTH ));
	}
	
}
