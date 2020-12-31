package psykeco.littlejonh.utility;

import static psykeco.littlejonh.constants.LittleJonHConstants.ERR_TEXT_WRONG_CRON;
import static psykeco.littlejonh.constants.LittleJonHConstants.EVERY_STAR;

import psykeco.littlejonh.constants.CronExprOrder;;

public final class LittleJonHUtils {

	// private construtore. Static utils
	private LittleJonHUtils() {}
	
	public static String analyze(String cron, boolean [] output) {
		//separate values
		int step=1, tmp=0, end = output.length-1;
		boolean isStep=false, isRange=false,globStar=false;
		StringBuilder sb= new StringBuilder(500);
		
		/*
		 * forme dei valori
		 * 
		 * x-y : range
		 * x : numero singolo 
		 * * : tutti i valori
		 * 
		 * Ogni valore può avere indicato uno step ( con /<numero> ) 
		 */
		for (int i=0; i<cron.length(); i++) {
			char c=cron.charAt(i);
			if (Character.isDigit(c)) {
				if (isStep )step=step*10+(int)(c-'0');
				else if ( isRange && ! globStar ) end=end*10+(int)(c-'0');
				else if ( ! globStar ) tmp=(10*tmp)+(int)(c-'0');
				else new IllegalArgumentException(ERR_TEXT_WRONG_CRON);
			} else if (c=='/') {
				isStep=true;
				step=0;
			}  else if ( c=='*') {
				if (tmp!=0) new IllegalArgumentException(ERR_TEXT_WRONG_CRON);
				globStar=true;
			} else if (c=='-') {
				if(globStar) new IllegalArgumentException(ERR_TEXT_WRONG_CRON);
				isRange=true;
				end=0;
			}
			if (c==','||i==cron.length()-1){
				if (! globStar && ! isStep && ! isRange) {end=tmp;}
				for (int j=tmp; j<=end;j+=step) output [j]=true;
				sb.append("start from "+tmp+" to "+end+" every "+step+"\n");
				step=1; tmp=0; end = output.length;
				isStep=false; isRange=false; globStar=false;
			}
		}
		return sb.toString();
	}
}
