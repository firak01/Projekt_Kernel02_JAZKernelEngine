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
   
Ziel1: Z.B. die Ascii - Methode ist über das MenuPoint-Objekt ausführbar und muss nicht erste noch woanders implementiert werden.
    
Ziel2: Man soll über das Menü einfach einen anderen Menüpunkt auswählen können,
      der dann (ggfs. in seinem in onStart() selbst erzeugten Thread läuft.
      Oder halt etwas anderes selbst macht.

Ziel3: Man soll wie im menue01 Package die zähler tauschen können.


TODOGOON 20260831: Problem:
Beendet man den laufenden "Menüpunkt 2" mit 'q', beendet man die Konsole, 
aber der Thread mit "Menüpunkt 2" läuft weiter.
Registriert am KonsolenThread ist der aber. Nur: Meiner Meinung nach wird gar kein Ereignis bei 'q' geworfen. 