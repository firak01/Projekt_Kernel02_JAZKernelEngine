Die Dateien enthalten die Pfade zu den .jar Dateien.
Das ist 1 Zeile. Die Einträge sind mit Semikolon getrennt.

Wenn es ein Maven - Projekt ist, kann die Datei automatisiert aus pom.xml erstellt wird
"start Maven..."

und als Goals eingeben: 
generate-resources


Merke:
Es gilt für Dateien unterhalb des target - Verzeichnisses: 
Sie gehören nicht in git eingecheckt.
Die Pfade können auf jeder Maschine anders sein.

Ausnahmen:
Diese readme-Datei 
und die Datei classpathOfLibrariesWorkspaceZZZ.txt, diese gibt die Pfade relativ zum Workspace an.
Man findet den Pfad unter: 
Projekteigenschaften/Java Build Path. Reiter Libraries.
Hier die Library als Kategorie aufklappen. 
Leider kann man da nichts Rauskopieren.

#########################################################
Mit den Angaben in diesen classpath...ZZZ Dateien können die notwendigen .jar Dateien 
in anderen Tools geladen werden. Z.B. Meine Jython Lösung.

Dabei gilt: 
Normalerweise immer absolute Pfadangaben, nur beim classpathOfLibrariesWorkspaceZZZ.txt
bezieht sich die Angabe auf den Workspace, d.h. hier relative Pfadangaben
 
             

#########################################################
Hier die wichtigen Angaben der pom.xml

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>jazkernelzzz</groupId>
  <artifactId>JAZKernel</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <description>Verwendung des Kernels (ini-Konfiguration, etc. - Einbindung von Bibliotheken)</description>
   <properties>
     <maven.compiler.source>1.7</maven.compiler.source>
     <maven.compiler.target>1.7</maven.compiler.target>
   </properties>
  <build>
    <sourceDirectory>src</sourceDirectory>
    <testSourceDirectory>test</testSourceDirectory>
    <resources>
      <resource>
        <directory>src</directory>
        <excludes>
          <exclude>**/*.java</exclude>
        </excludes>
      </resource>
    </resources>
    <testResources>
      <testResource>
        <directory>test</directory>
        <excludes>
          <exclude>**/*.java</exclude>
        </excludes>
      </testResource>
    </testResources>
    <plugins>
      <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.7.0</version>
        <configuration>
          <source>1.7</source>
          <target>1.7</target>
        </configuration>
      </plugin>
      
      <!-- FGL 20261105: 
           Die Idee ist, mit dem maven-dependency-plugin immer eine Textdatei aller .jar Dateien zu erzeugen.
           Diese Textdatei kann dann aus anderen Stellen heraus genutzt werden, konkret: Jython-Scripte binden ZKernel-Klassen ein
           
           Wie startet man das:
           Rechtsclick auf das Projekt in Eclipse.
           "Run as..."   
           Maven Build.  In dem Dialog eingeben als goal: generate-resources   
           
           Merke: build-classpath ist kein gültiger lifycycle. Gibt man das an, gibt es den Fehler
           "Unknown lifecycle phase "build-classpath". You must specify a valid lifecycle phase or a goal in the format <plugin-prefix>:<goal> or <plugin-group-id>:<plugin-artifact-id>[:<plugin-version>]:<goal>. Available lifecycle phases are: validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy, pre-clean, clean, post-clean, pre-site, site, post-site, site-deploy. -> [Help 1]"
           
           
           Merke: Diese Startkonfiguration kann man dann auch als shared in launchZZZ speichern.
           
           Das Ergebnis ist eine Textdatei an der angegbenen Stelle, die den Classpath als String in einer Zeile enthält.
           Z.B.:
           C:\Dokumente und Einstellungen\lindhaueradmin\.m2\repository\com\google\guava\guava\20.0\guava-20.0.jar;
           
           und viel mehr.
            -->
      
      <plugin>
	    <groupId>org.apache.maven.plugins</groupId>
	    <artifactId>maven-dependency-plugin</artifactId>
	    <version>3.6.0</version>
	
	    <executions>
	        <!-- FGL20261105:
	             Unter Eclipse Oxygen wird der execution-Tag als Fehler angemerkt:
	             "Plugin execution not covered by lifecycle configuration"
	             
	             Die Eclipse-Erweiterung m2e (Maven Integration für Eclipse) kenn diese nicht.
	             Für das Bauen ist dieser Fehler nicht wichtig. Man kann ihn daher ignorieren.
	             Dies passiert im Abschnitt pluginManagement.... genau für dieses Plugin und den im goal-Tag angegebenen Wert.	             	             	            
			-->	             	        
	        <execution>
	            <id>create-maven-classpathzzz</id>
	            <phase>generate-resources</phase>
	            <goals>
	                <goal>build-classpath</goal>
	            </goals>
	            <configuration>
	                <outputFile>
	                    ${project.build.directory}/classpathZZZ/classpathByMavenZZZ.txt
	                </outputFile>
	                <outputAbsoluteArtifactFilename>
	                    true
	                </outputAbsoluteArtifactFilename>
	                <includeScope>runtime</includeScope>
	            </configuration>
	        </execution>
	    </executions>
	</plugin>
    </plugins>
    
    
    <pluginManagement>
        <plugins>
            <!-- FGL20261105:
                 Diese Konfiguration sagt Eclipse, dass es den Fehler ignorieren soll, hervorgerufend durch execution-Tag (s.o.):
                 "Plugin execution not covered by lifecycle configuration"                 
                  -->
            <plugin>
                <groupId>org.eclipse.m2e</groupId>
                <artifactId>lifecycle-mapping</artifactId>
                <version>1.0.0</version>
                <configuration>
                    <lifecycleMappingMetadata>
                        <pluginExecutions>
                            <pluginExecution>
                                <pluginExecutionFilter>
                                    <groupId>org.apache.maven.plugins</groupId>
                                    <artifactId>maven-dependency-plugin</artifactId>
                                    <versionRange>[0,)</versionRange>
                                    <goals>
                                        <!-- Tragen Sie hier das Goal ein, das den Fehler wirft, z.B. copy-dependencies -->
                                        <!-- <goal>copy-dependencies</goal> --> 
                                        <!-- <goal>unpack</goal> -->
                                        <goal>build-classpath</goal>
                                    </goals>
                                </pluginExecutionFilter>
                                <action>
                                    <ignore />
                                </action>
                            </pluginExecution>
                        </pluginExecutions>
                    </lifecycleMappingMetadata>
                </configuration>
            </plugin>
        </plugins>
    </pluginManagement>
    
  </build>
  
  <dependencies>
      ..............
  </dependencies>
  
</project>