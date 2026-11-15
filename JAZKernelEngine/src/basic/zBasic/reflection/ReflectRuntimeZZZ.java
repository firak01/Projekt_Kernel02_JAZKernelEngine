package basic.zBasic.reflection;

import java.text.DecimalFormat;

public class ReflectRuntimeZZZ {
	static final DecimalFormat DF_2 = new DecimalFormat("#,##0.00");
	
	
	private ReflectRuntimeZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	
	static String ermittleDauer(long startZeitNanoSek) {
		long dauerMs = (System.nanoTime() - startZeitNanoSek) / 1000 / 1000;
		if (dauerMs < 1000)
			return "" + dauerMs + " ms";
		return DF_2.format(dauerMs / 1000.) + " s";
	}

	static long ermittleSpeicherverbrauch() {
		System.gc();
		System.gc();
		return Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
	}

	static String formatiereSpeichergroesse(long bytes) {
		if (bytes < 0)
			return "0 Byte";
		if (bytes < 1024)
			return "" + bytes + " Byte";
		double b = bytes / 1024.;
		if (b < 1024.)
			return DF_2.format(b) + " KByte";
		return DF_2.format(b / 1024.) + " MByte";
	}
}
