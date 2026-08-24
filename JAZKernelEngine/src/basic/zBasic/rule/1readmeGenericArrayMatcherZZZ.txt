ChatJPT vom 3.1.2026
2.1 Leichtgewichtige, saubere Lösung ohne externes Framework
a) Eigenes Regel-Interface (empfohlen)
public interface MatchRule<K, V> {
    boolean matches(K key, V value);
}


b) Generische Methode
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GenericArrayMatcher {

    public static <K, V> V[] filter(
            K[] keys,
            V[] values,
            MatchRule<K, V> rule,
            Class<V> valueType) {

        List<V> result = new ArrayList<V>();

        for (K key : keys) {
            for (V value : values) {
                if (rule.matches(key, value)) {
                    if (!result.contains(value)) {
                        result.add(value);
                    }
                }
            }
        }

        @SuppressWarnings("unchecked")
        V[] array = (V[]) Array.newInstance(valueType, result.size());
        return result.toArray(array);
    }
}


c) Konkrete Regel für String.contains(...)
MatchRule<String, String> containsRule = new MatchRule<String, String>() {
    @Override
    public boolean matches(String key, String value) {
        return key != null && value != null && value.contains(key);
    }
};


d) Aufruf
String[] result = GenericArrayMatcher.filter(
        keywords,
        values,
        containsRule,
        String.class
);


Vorteile dieser Lösung

✔ kein externes Framework
✔ Java 1.7-tauglich
✔ extrem flexibel
✔ klar regelbasiert
✔ funktioniert für beliebige Objekte

Beispiele für andere Regeln:

ID-Vergleich

Regex-Match

numerische Bereiche

komplexe Objektattribute

2.2 „Leichte“ Frameworks – realistisch betrachtet
Apache Commons / Guava

helfen bei Collections

nicht regelbasiert

keine Cross-Array-Matching-Logik

➡️ nicht passend

Rule Engines (Drools, Easy Rules)

sehr mächtig

schwergewichtig

Overkill für dein Szenario

Java 1.7 zwar möglich, aber unschön

➡️ nicht empfehlenswert

3. Empfehlung (praxisnah)

Für dein Szenario ist die eigene kleine Regel-Abstraktion die beste Lösung:

exakt auf dein Problem zugeschnitten

kein zusätzlicher Ballast

sehr gut testbar

gut erweiterbar

Wenn du möchtest, kann ich dir:

eine performantere Variante (Set / Hashing) bauen

eine Variante mit Comparator-ähnlicher API

eine Java-8-Version zum Vergleich

oder eine Variante ohne Duplikatsprüfung
