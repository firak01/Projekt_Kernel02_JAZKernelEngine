TODOGOON20260828:

Fortsetzung von Menue02
Beispiel: Zähle hoch.
Die Idee ist es trotz menue immer weiter zu zählen.
Also das Zählen selbst in einem Thread zu machen.
Daneben soll dann z.B. die Ascii - Tabelle angezeigt werden können.

Der laufende Thread soll auch beendet werden können.
Dann soll das Ergebnis angezeigt werden. 

Der laufende Thread wird auch beendet, wenn die Konsole beendet wird.


Diese Fortsetzung beinhaltet:
- Mache ein MenuePointObjekt, 
  das nun alles für eine Menüpunkt enthält. 
  Wenn dieses MenuePointObjekt an den ConsoleService übergeben wird,
  dann a) Ist keine Fallunterscheidung mehr darin notwendig
       b) Macht der ConsoleService einfach ein startit() und führt objMenuePoint.onStart() aus...
       
Ziel: Man soll über das Menü einfach einen anderen Menüpunkt auswählen können,
      der dann (ggfs. in seinem in onStart() selbst erzeugten Thread läuft.
      Oder halt etwas anderes selbst macht.

Ziel 2: Man soll wie im menue01 Package die zähler tauschen können.