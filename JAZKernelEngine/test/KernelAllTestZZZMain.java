import basic.zBasic.util.file.jar.JarEasyZZZTest;

public class KernelAllTestZZZMain {

		/**
		 * Hiermit eine Swing-Gui starten.
		 * Das ist bei eclipse aber nicht notwendig, au�er man will alle hier eingebundenen Tests durchf�hren.
		 * @param args
		 */
		public static void main(String[] args) {
			//Ab Eclipse 4.4 ist junit.swingui sogar nicht mehr Bestandteil des Bundles
			//also auch nicht mehr unter der Eclipse Variablen JUNIT_HOME/junit.jar zu finden. 
		    //junit.swingui.TestRunner.run(KernelAllTestZZZ.class);
			
			//######################################
			//Löung: JUnit 4 Ohne GUI
			//Aber dafür ist es notwendig wg. Fehler: Exception in thread "main" java.lang.NoClassDefFoundError: org/hamcrest/SelfDescribing
			//https://stackoverflow.com/questions/14539072/java-lang-noclassdeffounderror-org-hamcrest-selfdescribing
			//
		    //1. Right click on the project.
		    //2. Choose Build Path Then from its menu choose Add Libraries.
		    //3. Choose JUnit then click Next.
		    //4. Choose JUnit4 then Finish.
			
//			JUnitCore junitCore = new JUnitCore(); 
//			junitCore.addListener(new TextListener(System.out)); 
//			junitCore.run(JarEasyZZZTest.class);
			
			//##########################################
			//Lösung Junit 3, Jar Dateien extra importiert als Alternative zu JUnit4, mit GUI
			//Aber, um die JUnit SwingGui zu nutzen muss man sich extra eine "alte" Jar Datei downloaden
			//http://junit.10954.n7.nabble.com/Why-should-I-continue-to-use-junit-swingui-TestRunner-with-JUnit-4-1-td4589.html		
			junit.swingui.TestRunner.run(KernelAllTestZZZ.class);

			
		}

	}