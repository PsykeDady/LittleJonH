package psykeco.littlejonh.test;

import static psykeco.littlejonh.constants.LittleJonHConstants.*;
import static org.junit.Assert.*;

import org.junit.Test;

import psykeco.littlejonh.LittleJonH;
import psykeco.littlejonh.utility.LittleJonHUtils;

/**
 * this junit module tests exception
 * @author psykedady
 *
 */
public class JTestException {

	@Test
	public void AnalyzeExceptionWORD() {
		
		String cronExpression="CIAO";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	public void AnalyzeExceptionMONTHWORD() {
		
		String cronExpression="MON";
		boolean month[]=new boolean [12];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, month, MONTH_STR);}
		);
		
	}
	
	@Test
	public void AnalyzeExceptionWEEKWORD() {
		
		String cronExpression="JAN";
		boolean week[]=new boolean [7];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, week, DAY_OF_WEEK_STR);}
		);
		
	}
	
	@Test
	public void AnalyzeExceptionRANGE() {
		
		String cronExpression="32-13";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	public void AnalyzeExceptionNEGATIVE() {
		
		String cronExpression="-13";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	public void AnalyzeExceptionMINUS() {
		
		String cronExpression="12-";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	public void AnalyzeExceptionGLOBNUMBER() {
		
		String cronExpression="*12";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	public void AnalyzeExceptionNUMBERGLOB() {
		
		String cronExpression="12*";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	public void AnalyzeExceptionONLYSTEP() {
		
		String cronExpression="/12";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	public void AnalyzeExceptionINDEXOUT() {
		
		String cronExpression="612";
		boolean minute[]=new boolean [60];
		assertThrows(IndexOutOfBoundsException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	
	@Test
	public void AnalyzeExceptionWOUTVALUE() {
		
		String cronExpression="-/";
		boolean minute[]=new boolean [60];
		assertThrows(IllegalArgumentException.class, () -> { 
			LittleJonHUtils.analyze(cronExpression, minute, MINUTE_STR);}
		);
		
	}
	
	@Test
	public void littleJonHExceptionVUOTO() {
		
		String cronExpression="";
		assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			LittleJonH l=new LittleJonH(cronExpression);
		});
	}
	
	@Test
	public void littleJonHExceptionSIZE4() {
		
		String cronExpression="* * * *";
		assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			LittleJonH l=new LittleJonH(cronExpression);
		});
	}
	
	
	@Test
	public void littleJonHExceptionSIZE6() {
		
		String cronExpression="* * * * * *";
		assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			LittleJonH l=new LittleJonH(cronExpression);
		});
	}
	
	@Test
	public void littleJonHExceptionOVERFLOWHOUR() {
		
		String cronExpression="* 24 * * *";
		assertThrows(IndexOutOfBoundsException.class, () -> {
			@SuppressWarnings("unused")
			LittleJonH l=new LittleJonH(cronExpression);
		});
	}
	
	@Test
	public void nextException31FEB() {
		
		String cronExpression="0 0 31 FEB *";
		LittleJonH l=new LittleJonH(cronExpression);
		assertThrows(IllegalArgumentException.class, () -> {
			l.nextT();
		});
	}

}
