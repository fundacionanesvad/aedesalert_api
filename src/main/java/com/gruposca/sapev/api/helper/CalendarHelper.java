/*******************************************************************************
 * Aedes Alert, Support to collect data to combat dengue
 * Copyright (C) 2017 Fundación Anesvad
 *   
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *   
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *   
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.gruposca.sapev.api.helper;


import java.util.Calendar;

public class CalendarHelper {

	static String[] dayOfWeek = {"domingo","lunes","martes","miércoles","jueves","viernes","sábado"};
	static String[] months = {"enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre"};
	static String[] dayOfWeekAcronym = {"D","L","M","M","J","V","S"};

	
	//Formato ejemplo: martes, 27 de septiembre de 2016
	public static String getDateToPlanMap(Calendar cal) {
		String date = "";
		cal.get(Calendar.DAY_OF_WEEK);
		date = dayOfWeek[cal.get(Calendar.DAY_OF_WEEK)-1]+", ";
		date += cal.get(Calendar.DAY_OF_MONTH)+" de ";
		date += months[cal.get(Calendar.MONTH)]+" de ";		
		date += cal.get(Calendar.YEAR);
        return date;
    }
	
	
	public static String getMonth(Integer monthNumber) {
        return months[monthNumber];
    }
	
	public static String getDayOfWeekAcronym(Integer day) {
        return dayOfWeekAcronym[day-1];
    }
	
}