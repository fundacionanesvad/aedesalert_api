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
 ******************************************************************************/
package com.gruposca.sapev.api.helper;

public class TracesHelper {
	/**
	 * Enumerado para definir los diferentes tipos y niveles de traza.
	 */
	public enum TraceVervose { DEBUG, INFO, ERROR }
	
	/**
	 * Usa este mÃ©todo para tirar por consola una traza.
	 * @param trace Traza a tirar por consola.
	 */
	public static void trace(String trace) { trace(TraceVervose.DEBUG, trace); }
	
	/**
	 * Usa este mÃ©todo para trazar una excepciÃ³n.
	 * @param e ExcepciÃ³n a trazar.
	 */
	public static void trace(Exception e) { 
		trace(TraceVervose.ERROR, e.toString());
		e.printStackTrace(System.out);
	}
	
	/**
	 * Usa este mÃ©todo para tirar por consola una traza.
	 * @param vervose Usa este argumento para establecer el nivel de la traza.
	 * @param trace Cadena de texto con la traza a tirar por consola.
	 */
	public static void trace(TraceVervose vervose, String trace) { System.out.println(composeTrace(vervose, trace)); }
	
	/**
	 * Usa este mÃ©todo para dar formato a todas las trazas de la aplicaciÃ³n.
	 * @param vervose
	 * @param trace
	 * @return
	 */
	private static String composeTrace(TraceVervose vervose, String trace) { return String.format("%s %s", vervose.name(), trace); }

}
