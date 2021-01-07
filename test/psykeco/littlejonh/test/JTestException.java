package psykeco.littlejonh.test;

import static psykeco.littlejonh.constants.LittleJonHConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import psykeco.littlejonh.utility.LittleJonHUtils;

/**
 * this junit module tests exception
 * @author psykedady
 *
 */
class JTestException {

	@Test
	void AnalyzeExceptionWORD() {
		
		String cronExpression="CIAO";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	void AnalyzeExceptionRANGE() {
		
		String cronExpression="32-13";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	void AnalyzeExceptionNEGATIVE() {
		
		String cronExpression="-13";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	void AnalyzeExceptionMINUS() {
		
		String cronExpression="12-";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	void AnalyzeExceptionINDEXOUT() {
		
		String cronExpression="612";
		boolean minute[]=new boolean [60];
		assertThrows(IndexOutOfBoundsException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}

}
