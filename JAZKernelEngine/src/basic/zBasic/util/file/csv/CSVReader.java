/*
 * Created on 24.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zBasic.util.file.csv;

/**

@author 0823 ,date 24.10.2004
*/
import java.io.*;
import java.util.*;

import basic.zBasic.util.datatype.string.StringZZZ;

/**
 * Eine Klasse, mit der mit einem Trennzeichen separierte Werte eingelesen
 * werden k�nnen. F�r jede Zeile werden die Werte in einem Hashtable abgelegt.
 * Eine Klasse, die die Daten weiterverarbeiten soll, kann dann unter dem
 * Namen der jeweiligen Spalte die Daten aus dem Hashtable auslesen.
 */
public class CSVReader {

	private char delimiter;
	private BufferedReader reader;
	private Vector header;
	private String nextLine;

	public CSVReader(Reader reader, char delimiter) {
		this.delimiter = delimiter;
		this.reader = new BufferedReader(reader);
		this.header = readHeader();
		nextLine = null;
	}

	public Vector getHeader() {
		return header;
	}

	public boolean hasMoreLines() {
		try {
			if (nextLine == null || nextLine.trim().equals(""))
				nextLine = reader.readLine();
		}
		catch (Exception ignore)
		{}

		if (nextLine == null || nextLine.trim().equals("")) {
			close();
			return false;
		}
		else
			return true;
	}

	public Hashtable getNextLine() {
		// Liest auf jeden Fall die neue Zeile, wenn es eine gibt.
		if (!hasMoreLines())
			return null;

		Hashtable hash = new Hashtable();

		// Aus der Zeile wird die Hashtable erzeugt.
		Vector dataFields = parseLine(nextLine.trim());
		for (int i=dataFields.size()-1; i>=0; i--)
			hash.put(header.elementAt(i), dataFields.elementAt(i));

		// L�scht die Zeile, damit hasMoreLines auf jeden Fall
		// eine neue Zeile einliest.
		nextLine = null;

		return hash;
	}

	public void close() {
		try {
			reader.close();
		}
		//kann ignoriert werden, da hasMoreLines den Reader bereits schliesst
		catch(IOException ignored)
		{}
	}

	private Vector readHeader() {
		Vector header = null;
		try {
			String line = reader.readLine();
			header = parseLine(line);
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
		}
		return header;
	}

	private Vector parseLine(String line) {
		Vector fields = new Vector();
		boolean quote = false;
		int start = 0, end = 0, index = 0, max = line.length()-1;
		try {
			// Alle Spalten durchlaufen
			while (index <= max) {
				start = index;
				quote = false;

				//Inhalt einer Spalte extrahieren
				while (index <= max) {
					char check = line.charAt(index);
					//Nun wird der n�chste Delimiter gesucht, der NICHT
					//innerhalb von Anf�hrungszeichen (") steht. Wenn ein
					//Anf�hrungszeichen gefunden wurde, dann muss der Merker
					//getoggled werden.
					if (check == '"')
						quote = !quote;
					//Es befindet sich auf jeden Fall eine gerade Anzahl von
					//"-Zeichen zwischen den Quotes, so dass nur die am Anfang
					//und Ende ber�cksichtigt werden.
					else if (check == delimiter && quote == false)
						break;
					index++;
				}
				end = index;

				// Anf�hrungszeichen am Anfang und am Ende geh�ren nicht zum
				// String und werden deshalb auch nicht beachtet.
				if (line.charAt(start) == '"' && line.charAt(end-1) == '"') {
					start++;
					end--;
				}

				// Der gefundene Text wird in dem Vector gespeichert, ohne jedoch zu
				// vergessen, dass zwei auf einander folgende Anf�hrungszeichen ("")
				// durch ein Einzelnes (") zu ersetzen sind.
				fields.addElement(StringZZZ.replace(line.substring(start, end), "\"\"", "\""));
				index++;
			}
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
		}
		return fields;
	}
}

