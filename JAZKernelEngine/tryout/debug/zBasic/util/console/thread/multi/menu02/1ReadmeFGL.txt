Beispiel: Zähle hoch.
Die Idee ist es trotz menue immer weiter zu zählen.
Also das Zählen selbst in einem Thread zu machen.
Daneben soll dann z.B. die Ascii - Tabelle angezeigt werden können.

Der laufende Thread soll auch beendet werden können.
Dann soll das Ergebnis angezeigt werden. 

Der laufende Thread wird auch beendet, wenn die Konsole beendet wird.

Besonderheit:
Verwende auf oberster Ebene eine "Composite" Objekt.
Es gibt darin dann 3 Objekte, die miteinander verknüpft sind:
- ConsoleController

- ExampleKeyPressThread

- ExampleConsoleService 

Probleme:
Man kann nicht von einem Menüpunkt (Sprich Zähler) zu einem anderen wechseln.

Auch die ASCII - Methode müsste extra implementiert werden (an einer anderen Stelle)
