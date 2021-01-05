package psykeco.littlejonh.utility;

import static psykeco.littlejonh.constants.LittleJonHConstants.ERR_TEXT_WRONG_CRON;
import static psykeco.littlejonh.constants.LittleJonHConstants.DAY_OF_MONTH_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.MONTH_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.DAY_OF_WEEK_STR;
import static psykeco.littlejonh.constants.LittleJonHConstants.WORDLIST_DAY_OF_WEEK;
import static psykeco.littlejonh.constants.LittleJonHConstants.WORDLIST_MONTH;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;





public final class LittleJonHUtils {

	// private construtore. Static utils
	private LittleJonHUtils() {}
	
	public static String analyze(String cron, boolean [] output, String what) {
		//separate values
		int step=1, start=0, end = output.length-1;
		boolean isStep=false, isRange=false,globStar=false;
		StringBuilder sb= new StringBuilder(500),wordb=new StringBuilder(10);
		
		String wordlist= searchWordlist(what);
		
		/*
		 * forme dei valori
		 * 
		 * x-y : range
		 * x : numero singolo 
		 * * : tutti i valori
		 * 
		 * Ogni valore può avere indicato uno step ( con /<numero> ) 
		 * 
		 * Alcuni valori possono essere alfanumerici (vedi wordlist) 
		 */
		for (int i=0; i<cron.length(); i++) {
			char c=cron.charAt(i);
			if (Character.isAlphabetic(c)) {
				 wordb.append(c);
			} else if (Character.isDigit(c)) {
				if (isStep )step=step*10+(int)(c-'0');
				else if ( isRange && ! globStar ) end=end*10+(int)(c-'0');
				else if ( ! globStar ) start=(10*start)+(int)(c-'0');
				else new IllegalArgumentException(ERR_TEXT_WRONG_CRON);
			} else if (c=='/') {
				String word=wordb.toString();
				if( !word.equals("")) {
					int indW=indexOfWordlist(word, wordlist);
					if (isRange) end=indW;
					else start=indW;
				}
				wordb=new StringBuilder(10);
				isStep=true;
				step=0;
			}  else if ( c=='*') {
				if (!wordb.toString().equals("")|| start!=0) new IllegalArgumentException(ERR_TEXT_WRONG_CRON);
				if(what.equals(MONTH_STR) || what.equals(DAY_OF_MONTH_STR)) {
					start=1;
					end=output.length;
				}
				globStar=true;
			} else if (c=='-') {
				String word=wordb.toString();
				if( !word.equals("")) {
					start=indexOfWordlist(word, wordlist);
				}
				wordb=new StringBuilder(10);
				if(globStar) new IllegalArgumentException(ERR_TEXT_WRONG_CRON);
				isRange=true;
				end=0;
			}
			if (c==','||i==cron.length()-1){
				String word=wordb.toString();
				if( !word.equals("")) {
					int indW=indexOfWordlist(word, wordlist);
					if (isRange) end=indW;
					else start=indW;
				}
				if (isRange && end<start) throw new IllegalArgumentException(ERR_TEXT_WRONG_CRON);

				if(what.equals(MONTH_STR) || what.equals(DAY_OF_MONTH_STR)) {
					start--;
					end--;
				}
				
				if (! globStar && ! isStep && ! isRange) {end=start;}
				for (int j=start; j<=end;j+=step) 
					if (output [j]) throw new IllegalArgumentException(ERR_TEXT_WRONG_CRON);
					else output [j]=true;
				
				if(what.equals(MONTH_STR) || what.equals(DAY_OF_MONTH_STR)) {
					start++;
					end++;
				}
				
				if(!globStar && !isRange && !isStep) sb.append("at "+what+" "+start);
				else if(!globStar && !isRange) sb.append("every "+what+" from "+start+" ");
				else if(!globStar) sb.append("every "+what+" on ranges from "+start+" ");
				else if (globStar) sb.append("every "+what+" ");
				
				if(isRange) sb.append("to "+end+" ");
				
				if(isStep && step>1) sb.append("with "+step+" steps at a time");
				
				step=1; start=0; end = output.length;
				wordb=new StringBuilder(10); word="";
				isStep=false; isRange=false; globStar=false;
				if (i!=cron.length()-1) sb.append(" and ");
			}
		}
		return sb.toString().trim();
	}
	
	public static String searchWordlist(String what) {
		switch(what) {
			case MONTH_STR : return WORDLIST_MONTH;
			case DAY_OF_WEEK_STR : return WORDLIST_DAY_OF_WEEK;
		}
		return "";
	}
	
	public static int indexOfWordlist(String word, String wordlist) {
		word=word.toUpperCase();
		int ind=-1, mod=-1;
		
		ind=wordlist.indexOf(word);
		if(ind==-1)return ind;
		
		mod=ind%3;
		if(mod!=0) throw new IllegalArgumentException(ERR_TEXT_WRONG_CRON);
		
		return ind/3;
	}
	
	public static int searchNextOccurence(boolean[]values, int index) {
		if(index>values.length) index=0; // restart
		for (int tmp=index+1;tmp<values.length;tmp++) {
			if(values[tmp]) return tmp;
		}
		for (int tmp=0;tmp<index;tmp++) {
			if(values[tmp]) return tmp;
		}
		return index;
	}
	
	/**
	 * 
	 * @param month 0-11
	 * @param year
	 * @return
	 */
	public static int monthLength(int month, int year) {
		
		switch (month) {
		case 0:case 2:case 4:case 6:case 7:case 9:case 11: return 31;
		case 3:case 5:case 8:case 10: return 30;
		}
		
		return  year%4==0 && (year%400==0 || year%100!=0 )  ? 29 : 28;
	}
	
	public static Date localDateToDate(LocalDateTime d) {
		return Date.from(
				d.atZone(ZoneId.systemDefault()).toInstant()
		);
	}
}
