package basic.zBasic.util.datatype.string;

import java.util.Arrays;
import java.util.Comparator;

import basic.zBasic.ExceptionZZZ;

/** s. ChatGPT vom 2026-01-24
 *   * 
 * @author Fritz Lindhauer, 24.01.2026, 16:31:33
 * 
 */
public class StringAnalyseUtilZZZ {
	private StringAnalyseUtilZZZ(){
		//Zum Verstecken des Konstruktors, sind halt nur static Methoden
	}
	
	/**
     * Prüft, ob target ausschließlich aus den Bausteinen in parts besteht.
     */
    public static boolean consistsOnlyOf(String target, String[] parts) throws ExceptionZZZ {
        if (target == null || parts == null) return false;

        // Kopie + nach Länge absteigend sortieren
        String[] sorted = Arrays.copyOf(parts, parts.length);
        Arrays.sort(sorted, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s2.length(), s1.length());
            }
        });

        String rest = target;

        boolean replaced;
        do {
            replaced = false;
            for (String part : sorted) {
                if (part == null || part.isEmpty()) continue;

                if (rest.contains(part)) {
                    rest = rest.replace(part, "");
                    replaced = true;
                }
            }
        } while (replaced);

        return rest.isEmpty();
    }
}
