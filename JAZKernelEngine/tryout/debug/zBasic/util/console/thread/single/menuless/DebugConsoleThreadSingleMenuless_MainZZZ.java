package debug.zBasic.util.console.thread.single.menuless;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.ResourceEasyZZZ;

/** Einfaches Ausführen der Scanner Eingabe,
 *  um einen Thread zu steuern
 *  
 *  https://stackoverflow.com/questions/30626591/java-scanner-input-in-separate-thread
 *  
 *  
 *  Das wird Grundlage für eine ConsoleZZZ-Klasse
 *  und für CyrptConsoleMainZZZ
 *   
 * @author Fritz Lindhauer, 16.10.2022, 07:34:48
 * 
 */
public class DebugConsoleThreadSingleMenuless_MainZZZ {
	static long rand = 10000;
	public static void main(String[] args) {
		 ExecutorService executor = Executors.newFixedThreadPool(5);

	        //File f = new File("C:\\fglkernel\\kernellog\\OpenVPNZZZ_ServerStarterLog.txt");		 
	        try {
	        	//1. Ermittle eine Testdatei im aktuellen Verzeichnis
	        	String sConfigFile = FileEasyZZZ.joinFilePathName("tryout\\debug\\zBasic\\util\\console\\thread\\menuless", "openvpn-status.log");	        	
	        	File f = ResourceEasyZZZ.searchFile(sConfigFile);
	        	

	            final Runnable keyPressThread = new ExampleKeyPressThread(rand);
	            Thread t = new Thread(keyPressThread);
	            t.start();

	            BufferedReader br = new BufferedReader(new FileReader(f));

	            String line;

	            while ((line = br.readLine()) != null)
	            {

	                try {
	                    final String copy = line;

	                    executor.execute(new Runnable() {
	                        @Override
	                        public void run() {
	                            try {
	                            	rand = ((ExampleKeyPressThread) keyPressThread).getRand();
	                                System.out.println(rand);
	                                Thread.sleep(rand);
	                                System.out.println(copy);
	                            } catch (InterruptedException e) {
	                                e.printStackTrace();
	                            }
	                        }
	                    });


	                } catch (Exception e)
	                {
	                    e.printStackTrace();
	                }

	            }

	        } catch (Exception e)
	        {
	            e.printStackTrace();
	        }


	}
	
	


}
