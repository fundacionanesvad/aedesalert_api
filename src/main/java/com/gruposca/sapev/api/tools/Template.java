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
package com.gruposca.sapev.api.tools;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import cb.jdynamite.JDynamiTe;

public class Template {
	private Hashtable sustituir = new Hashtable();
	private String fichero = new String("");
	private JDynamiTe dinamita =new JDynamiTe();
	private String salida = new String("");
	
	public Template(){
	}
	
	public Template(Hashtable sustituir, String fichero){
	  this.sustituir = sustituir;
	  this.fichero = fichero;
	}
	
	public Template(Vector variables, Vector valores, String fichero){
	  String variable;
	  String valor;
	  if (variables.size() == valores.size()) {
		for (int i = 0; i < variables.size(); i++){
			variable = (String) variables.elementAt(i);
			valor = (String) valores.elementAt(i);
			sustituir.put(variable, valor);
		}
	  }
	}
	
	public void addVariable(){
	}
	
	public void setPlantilla(String plantilla ){
	}
	
	public String getPlantilla(){ 
		return null;
	}
	
	public String getSalida(){  
		return this.salida;
	}
	
	public void hacerSustitucion() throws IOException {
	  String variable = new String("");
	  String valor = new String("");
	  Enumeration variables = sustituir.keys();
	  dinamita.setInput( fichero );
	  while (variables.hasMoreElements()){ 
			variable = (String) variables.nextElement();
			valor = (String) sustituir.get( variable );
			//Asigno los valores a las variables
			dinamita.setVariable( variable , valor );
	  }
	   //Preparo el string de salida
	   dinamita.parse();
	   salida = dinamita.toString(); 
	}	
}
