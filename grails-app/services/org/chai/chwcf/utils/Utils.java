/**
 * Copyright (c) 2011, Clinton Health Access Initiative.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.chai.chwcf.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jean Kahigiso M.
 *
 */
public class Utils {
	public static Set<String> split(String string) {
		Set<String> result = new HashSet<String>();
		if (string != null) result.addAll(Arrays.asList(StringUtils.split(string.trim(), ',')));
		return result;
	}

	@SuppressWarnings("unchecked")
	public static String unsplit(Object list) {
		if (list == null) return "";
		if (list instanceof String) return (String) list;
		if (list instanceof Collection) return StringUtils.join(((Collection<String>)list).toArray(), ',');
		else return StringUtils.join((Object[]) list, ',');
	}
	
	@SuppressWarnings("unused")
	private static boolean matches(String text, String value) {
		if (value == null) return false;
		return value.matches("(?i).*"+text+".*");
	}
	
	public static String formatDate(Date date) {
		if (date == null) return null;
		return DATE_FORMAT.format(date);
	}
	
	public static String formatDateYear(Date date) {
		if (date == null) return null;
		return DATE_FORMAT_YEAR.format(date);
	}
	
	public static Date parseDate(String string){
		try {
			return DATE_FORMAT.parse(string);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Calendar getFirstOfMonthCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	
	public static Date getStartDate(Date date) {
		return getFirstOfMonthCalendar(date).getTime();
	}
	
	public static Date getEndDate(Date date) {
		Calendar calendar = getFirstOfMonthCalendar(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, 1);
		return calendar.getTime();
	}
	
	public static Date getDate( int year, int month, int day ) {
		final Calendar calendar = Calendar.getInstance();

		calendar.clear();
		calendar.set( Calendar.YEAR, year );
		calendar.set( Calendar.MONTH, month - 1 );
		calendar.set( Calendar.DAY_OF_MONTH, day );

		return calendar.getTime();
	}

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	
	private static DateFormat DATE_FORMAT_YEAR = new SimpleDateFormat("yyyy");
	
	public static DateFormat REPORT_DATE_FORMAT = new SimpleDateFormat("MM-yyyy");

	public static String stripHtml(String htmlString, Integer num) {
		String noHtmlString;
		Integer length = num;
	
		if (htmlString != null) noHtmlString = htmlString.replaceAll("\\<.*?\\>", "");
		else noHtmlString = htmlString;
	
		if (num == null || noHtmlString.length() <= num) return noHtmlString;
		return noHtmlString.substring(0, length);
	}

	public static String prepareStringForSQL(String string) {
		String cleanString;
		if (string != null) {
			cleanString = string.replace("\"", "");
			cleanString = cleanString.replace("\\", "'");
		} else
			cleanString = string;

		return cleanString;
	}
	public static Map<Integer,Integer> getCooperativeIdMap(){
		Map<Integer,Integer> mapList = new HashMap<Integer,Integer>();
		
		mapList.put(355,1);
		mapList.put(356,2);
		mapList.put(357,3);
		mapList.put(358,4);
		mapList.put(359,5);
		
		mapList.put(360,6);
		mapList.put(361,7);
		mapList.put(362,8);
		mapList.put(363,9);
		mapList.put(364,10);
		
		mapList.put(365,11);
		mapList.put(366,12);
		mapList.put(586,13);
		mapList.put(593,14);
		mapList.put(1047,15);
		
		mapList.put(411,17);
		mapList.put(430,18);
		mapList.put(433,19);
		
		return mapList;
		
	}
	
}
