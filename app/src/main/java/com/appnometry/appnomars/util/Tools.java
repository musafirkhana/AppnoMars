package com.appnometry.appnomars.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tools {

	public static String DateFormator(String dateString, String fromFormate,
			String toFormate) {
		String result = "";
		SimpleDateFormat stringFormatter = new SimpleDateFormat(fromFormate);
		SimpleDateFormat desireFormarte = new SimpleDateFormat(toFormate);

		try {

			Date date = stringFormatter.parse(dateString);
			result = desireFormarte.format(date);

		} catch (ParseException e) {
			result = null;
			e.printStackTrace();
		}

		return result;

	}
	
	public static boolean isDateTimeGreater(String today, String compareDate, String dateFormat)
	{
		try{

            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date date1 = sdf.parse(today);
            Date date2 = sdf.parse(compareDate);

            if(date2.compareTo(date1)>=0){
            	
                //System.out.println("greater");
                return true;
            }else{
               // System.out.println("not greater");
                return false;
            }

        }catch(ParseException ex){
            ex.printStackTrace();
        }
		return false;		
	}
	
	public static String getDateTime( Calendar cal){
		
        return cal.get(Calendar.YEAR) +"-" +(cal.get(Calendar.MONTH)+1) + "-" +cal.get(Calendar.DATE)+" "
        		+cal.get(Calendar.HOUR_OF_DAY) +":" +(cal.get(Calendar.MINUTE)) + ":" + cal.get(Calendar.SECOND);
	}
	
	public static int stringToInteger(String str)
	{
		int result;
		try
		{
			result = Integer.parseInt(str);
		}
		catch(Exception e)
		{
			result = -1;
		}
		return result;
	}
	
	public static boolean isDouble( String str )
	{
	    try{
	        Double.parseDouble( str );
	        return true;
	    }
	    catch( Exception e ){
	        return false;
	    }
	}

	/**
	 * Calculate Day Difference from Current date to Desire Date
	 * @param dateStop
	 * @return
	 */
	public static String dateDifference(String dateStop){
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar c = Calendar.getInstance();
			String startDate = format.format(c.getTime());
			Date d1 = null;
			Date d2 = null;
			try {
				d1 = format.parse(startDate);
				d2 = format.parse(dateStop);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long diff = d2.getTime() - d1.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;

			int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
			System.out.println("difference between days: " + diffDays);
			return ""+diffDays;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
