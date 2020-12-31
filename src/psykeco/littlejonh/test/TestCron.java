package psykeco.littlejonh.test;

import psykeco.littlejonh.utility.LittleJonHUtils;

public class TestCron {
	public static void main(String[] args) {
		
		boolean minute[]=new boolean[60];
		
		System.out.println("every minute "+LittleJonHUtils.analyze("*",minute));
		System.out.println("every minute "+LittleJonHUtils.analyze("6",minute));
		System.out.println("every minute "+LittleJonHUtils.analyze("30-40",minute));
		System.out.println("every minute "+LittleJonHUtils.analyze("5/10",minute));
		System.out.println("every minute "+LittleJonHUtils.analyze("*/4",minute));
		System.out.println("every minute "+LittleJonHUtils.analyze("15-30/5",minute));

		
	}
}
