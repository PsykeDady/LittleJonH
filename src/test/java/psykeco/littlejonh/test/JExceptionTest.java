package psykeco.littlejonh.test;

import static psykeco.littlejonh.constants.LittleJonHConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import psykeco.littlejonh.LittleJonH;
import psykeco.littlejonh.utility.LittleJonHUtils;

/**
 * this junit module tests exception
 * @author psykedady
 *
 */
class JExceptionTest {

	@Test
	void AnalyzeExceptionWORD() {
		
		String cronExpression="CIAO";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	void AnalyzeExceptionMONTHWORD() {
		
		String cronExpression="MON";
		boolean month[]=new boolean [12];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, month, MONTH_STR);}
		);
		
	}
	
	@Test
	void AnalyzeExceptionWEEKWORD() {
		
		String cronExpression="JAN";
		boolean week[]=new boolean [7];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, week, DAY_OF_WEEK_STR);}
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
	void AnalyzeExceptionGLOBNUMBER() {
		
		String cronExpression="*12";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	void AnalyzeExceptionNUMBERGLOB() {
		
		String cronExpression="12*";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	void AnalyzeExceptionONLYSTEP() {
		
		String cronExpression="/12";
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
	
	
	@Test
	void AnalyzeExceptionWOUTVALUE() {
		
		String cronExpression="-/";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	void littleJonHExceptionVUOTO() {
		
		String cronExpression="";
		assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			LittleJonH l=new LittleJonH(cronExpression);
		});
	}
	
	@Test
	void littleJonHExceptionSIZE4() {
		
		String cronExpression="* * * *";
		assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			LittleJonH l=new LittleJonH(cronExpression);
		});
	}
	
	
	@Test
	void littleJonHExceptionSIZE6() {
		
		String cronExpression="* * * * * *";
		assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			LittleJonH l=new LittleJonH(cronExpression);
		});
	}
	
	@Test
	void littleJonHExceptionOVERFLOWHOUR() {
		
		String cronExpression="* 24 * * *";
		assertThrows(IndexOutOfBoundsException.class, () -> {
			@SuppressWarnings("unused")
			LittleJonH l=new LittleJonH(cronExpression);
		});
	}
	
	@Test
	void nextException31FEB() {
		
		String cronExpression="0 0 31 FEB *";
		LittleJonH l=new LittleJonH(cronExpression);
		assertThrows(IllegalArgumentException.class, () -> {
			l.nextT();
		});
	}

}
