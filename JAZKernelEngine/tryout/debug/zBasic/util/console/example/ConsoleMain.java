package debug.zBasic.util.console.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsoleMain {
	static long rand = 10000;
	public static void main(String[] args) {
		 ExecutorService executor = Executors.newFixedThreadPool(5);

	        File f = new File("C:\\fglkernel\\kernellog\\OpenVPNZZZ_ServerStarterLog.txt");
	        try {

	            final Runnable keyPressThread = new KeyPressThread(rand);
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
	                            	rand = ((KeyPressThread) keyPressThread).getRand();
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
