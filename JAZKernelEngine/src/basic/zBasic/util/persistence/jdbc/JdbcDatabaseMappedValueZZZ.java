package basic.zBasic.util.persistence.jdbc;

import java.io.Serializable;
import java.util.EnumSet;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.persistence.jdbc.JdbcDriverMappedValueZZZ.JdbcDriverClassTypeZZZ;

/*
Properties that are database specific are:

    hibernate.connection.driver_class: JDBC driver class
    hibernate.connection.url: JDBC URL
    hibernate.connection.username: database user
    hibernate.connection.password: database password
    hibernate.dialect: The class name of a Hibernate org.hibernate.dialect.Dialect which allows Hibernate to generate SQL optimized for a particular relational database.

To change the database, you must:

    Provide an appropriate JDBC driver for the database on the class path,
    Change the JDBC properties (driver, url, user, password)
    Change the Dialect used by Hibernate to talk to the database

There are two drivers to connect to SQL Server; the open source jTDS and the Microsoft one. The driver class and the JDBC URL depend on which one you use.
With the jTDS driver

The driver class name is net.sourceforge.jtds.jdbc.Driver.

The URL format for sqlserver is:

 jdbc:jtds:sqlserver://<server>[:<port>][/<database>][;<property>=<value>[;...]]

So the Hibernate configuration would look like (note that you can skip the hibernate. prefix in the properties):

<hibernate-configuration>
  <session-factory>
    <property name="connection.driver_class">net.sourceforge.jtds.jdbc.Driver</property>
    <property name="connection.url">jdbc:jtds:sqlserver://<server>[:<port>][/<database>]</property>
    <property name="connection.username">sa</property>
    <property name="connection.password">lal</property>

    <property name="dialect">org.hibernate.dialect.SQLServerDialect</property>

    ...
  </session-factory>
</hibernate-configuration>

With Microsoft SQL Server JDBC 3.0:

The driver class name is com.microsoft.sqlserver.jdbc.SQLServerDriver.

The URL format is:

jdbc:sqlserver://[serverName[\instanceName][:portNumber]][;property=value[;property=value]]

So the Hibernate configuration would look like:

<hibernate-configuration>
  <session-factory>
    <property name="connection.driver_class">com.microsoft.sqlserver.jdbc.SQLServerDriver</property>
    <property name="connection.url">jdbc:sqlserver://[serverName[\instanceName][:portNumber]];databaseName=<databaseName></property>
    <property name="connection.username">sa</property>
    <property name="connection.password">lal</property>

    <property name="dialect">org.hibernate.dialect.SQLServerDialect</property>

    ...
  </session-factory>
</hibernate-configuration>
 */

//#####################################################
//20191123: Um die Enumeration herum eine Klasse bauen.
//              Diese Struktur hat den Vorteil, das solche Werte auch in einer Datenbank per Hibernate persistiert werden können.
//              Verwendet wird solch eine Struktur z.B. in der Defaulttext - Klasse des TileHexMapTHM Projekts
public class JdbcDatabaseMappedValueZZZ  implements Serializable{
	private static final long serialVersionUID = 1340342888046470974L;
	
		//Entsprechend der internen Enumeration
		//Merke: Die Enumeration dient der Festlegung der Defaultwerte. In den Feldern des Entities werden die gespeicherten Werte gehalten.
		private String fullName, abbr;
		
		public JdbcDatabaseMappedValueZZZ(){		
		}
		
		public String getFullname(){
			return this.fullName;
		}
		public void setFullname(String sFullname){
			this.fullName = sFullname;
		}
		
		public String getAbbreviation(){
			return this.abbr;
		}
		public void setAbbreviation(String sAbbr){
			this.abbr = sAbbr;
		}

	//### Statische Methode (um einfacher darauf zugreifen zu können)
    public static Class getEnumClassStatic(){
    	try{
    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Diese Methode muss in den daraus erbenden Klassen überschrieben werden.");
    	}catch(ExceptionZZZ ez){
			String sError = "ExceptionZZZ: " + ez.getMessageLast() + "+\n ThreadID:" + Thread.currentThread().getId() +"\n";			
			System.out.println(sError);
		}
    	return JdbcDatabaseTypeZZZ.class;    	
    }
	
//#######################################################
//### Eingebettete Enum-Klasse mit den Defaultwerten, diese Werte werden auch per Konstruktor übergeben.
//### String fullName, String abbreviation
//#######################################################
			
//Merke: Obwohl fullName und abbr nicht direkt abgefragt werden, müssen Sie im Konstruktor sein, um die Enumeration so zu definieren.
//ALIAS("Beschreibung, wird nicht genutzt....","Abkürzung, also das, was im URL String steht. Meist gefolgt von einem  Doppelpunkt, der hinzugerchnet wird, wenn die Abkürzung nicht leer ist.")
public enum JdbcDatabaseTypeZZZ implements IEnumSetMappedZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
	SQLITE("sqlite","sqlite"),
	MYSQL("mysql","mysql"),
	SQLSERVER("sqlserver","sqlserver");
	
private String fullName, abbr;

//#############################################
//#### Konstruktoren
//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
//In der Util-Klasse habe ich aber einen Workaround gefunden.
JdbcDatabaseTypeZZZ(String fullName, String abbr) {
    this.fullName = fullName;
    this.abbr = abbr;
}


//the identifierMethod ---> Going in DB
public String getAbbreviation() {
 return this.abbr;
}

public String getFullname(){
	return this.fullName;
}



public EnumSet<?>getEnumSetUsed(){
	return JdbcDatabaseTypeZZZ.getEnumSet();
}

/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
@SuppressWarnings("rawtypes")
public static <E> EnumSet getEnumSet() {
	
 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
	//String sFilterName = "FLAGZ"; /
	//...
	//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
	
	//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
	Class<JdbcDatabaseTypeZZZ> enumClass = JdbcDatabaseTypeZZZ.class;
	EnumSet<JdbcDatabaseTypeZZZ> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
	
	 Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
	 for(Object obj : objaEnum){
		//System.out.println(obj + "; "+obj.getClass().getName());
		set.add((JdbcDatabaseTypeZZZ) obj);
	}
	return set;
	
}

//TODO: Mal ausprobieren was das bringt
//Convert Enumeration to a Set/List
private static <E extends Enum<E>>EnumSet<E> toEnumSet(Class<E> enumClass,long vector){
	  EnumSet<E> set=EnumSet.noneOf(enumClass);
	  long mask=1;
	  for (  E e : enumClass.getEnumConstants()) {
	    if ((mask & vector) == mask) {
	      set.add(e);
	    }
	    mask<<=1;
	  }
	  return set;
	}

//+++ Das könnte auch in einer Utility-Klasse sein.
//the valueOfMethod <--- Translating from DB
public static JdbcDatabaseTypeZZZ fromAbbreviation(String s) {
for (JdbcDatabaseTypeZZZ state : values()) {
   if (s.equals(state.getAbbreviation()))
       return state;
}
throw new IllegalArgumentException("Not a correct abbreviation: " + s);
}

//##################################################
//#### Folgende Methoden bring Enumeration von Hause aus mit. 
		//Merke: Diese Methoden können aber nicht in eine abstrakte Klasse verschoben werden, zum daraus Erben. Grund: Enum erweitert schon eine Klasse.
@Override
public String getName() {	
	return super.name();
}

@Override
public String toString() {
    return this.fullName+"="+this.abbr+"#";
}

@Override
public int getIndex() {
	return ordinal();
}

//### Folgende Methoden sind zum komfortablen Arbeiten gedacht.
@Override
public int getPosition() {
	return getIndex()+1; 
}

@Override
public String getDescription() {
	return this.fullName;
}
//+++++++++++++++++++++++++
}//End internal Class
	
}//End Class
