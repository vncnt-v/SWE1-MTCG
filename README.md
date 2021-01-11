# `SWE 1 - MTCG Protokol`

## `Git`
https://github.com/vncnt-v/SWE1-MTCG.git

## `Setup`
* DatabaseServer anpassen der Einträge: DB_URL, USER, PASS
* SQL Script in database.sql in einer PostgreSQL Database ausführen.
* Projekt mit InteliJ öffnen und TCPListener runnen.
* Server wartet auf Requests.

## `Designs`

Die Architektur setzt sich Grundsätzlich aus 3 verschiedenen Bereichen zusammen, welche verschiedene Klassen beinhalten. (Packages sind mit Punkten dargestellt)

`server`
* TcpListener
* Unwrapper
* ResponseHandler
* RequestContext
* ResponseContext

`database`
* DatabaseService

`mtcg`
* User
* Card
* Deck
* CardType - Enum
* ElemetType - Enum
* managers
* CardManager
* CombatManager
* TradeManager
* UserManager

Aufbau als basic Diagramm in Structure.png

### `Server`

#### `TcpListener`
Erstellt einen ServerSocket auf Port 10001 und startet für jede Anfrage einen neuen Thread.
Dieser Thread schickt einen Buffered Reader mit der Anfrage an den Unwrapper und bekommt einen RequestContext zurückgeben. Welcher weiter zur Bearbeitung an den ResponseHandler übermittelt wird. Die Antwort wird schlussendlich im ResponseHandler an den Client übermittelt.

#### `Unwrapper`
Bekommt einen Buffered Reader übergeben und versucht dessen Inhalt in den RequestContext zu speichern. Hierfür stehen das Http-Verb, Http-Version, URI,Header-Values und der Payload zur Verfügung.

#### `ResponseHandler`
Überprüft die Authentifikation des Clienten, über den UserManager, und führt entsprechend der URI verschiedene Funktionen in Klassen des Bereiches "mctg" auf.

#### `RequestContext`
Besitzt Variablen für den ankommenden Http Request und liest die ContentLength aus.

#### `ResponseContext`
Besitzt Variablen für die ausgehenden Http Response und setzt die ContentLength aus.

### `Database`

#### `DatabaseService`
Beinhaltet alle Informationen für die Datenbank, stellt eine Verbindung her und stellt diese anfragenden Klassen zur Verfügung.

### `MTCG`

#### `User`
Die User Klasse beinhaltet alle Informationen des Users und kann die Daten des aktuellen Users in der Datenbank Updaten. 

#### `Card`
Beinhaltet alle Informationen zu einer Karte: ID, Name, Damage, CardType, ElementType sowie zwei Konstruktorn, einer mit allen Variablen und eine abgeschwächte Form mit weniger Informationen.

#### `Deck`
Das Deck ist eine Sammlung an Karten mit welcher Interagiert werden kann. Es können Karten hinzugefügt, entfernt, zufällig gezogen werden und weiteres Abgefragt werden ob Karten aktuell in dem Deck verfügbar sind. 

#### `CardType`
Enum mit folgenden Attributen: Dragon, FireElf, Goblin, Knight, Kraken, Ork, Wizard, Spell.

#### `ElementType`
Enum mit folgenden Attributen: Water, Fire, Normal.

#### `CardManager`
Ist zuständig für die Registrierung von Karten,  die Erstellung der Karten aus der Datenbank, der Packages, einer Übersicht der Karten eines Users und des Deckes eines Users. Weiteres dem Zuordnen von Packages zu Usern und der Erstellung von Packages.

#### `CombatManager`
Besitzt eine Funktion in der sich Clients hinzufügen können, sobald zwei beigetreten sind startet die Battle Logic und überwacht den Kampf. Hier werden die Decks der beiden User genommen und die Karten gespielt. Der Gewinner einer Runde bekommt für die Dauer des Battles die Karte des Gegners übergeben. Dies wiederholt sich bis ein Deck leer ist oder 100 Runden überstanden sind.

#### `TradeManager`
Ist für Inhalte in der Marketplace Tabelle der Datenbank zuständig. Kann die Tabelle auslesen, Einträge hinzufügen, Einträge löschen und einen Trade abschließen.

#### `UserManager`
Der UserManager beinhaltet Funktionen für Registration, Login/Logout, Authentifikation sowie der Abfrage des Admins.


## `Integration Tests`

Die Integration Tests wurden mittels Postman automatisiert getestet und bestehen aus einer Collection von 78 Einträgen. Beim automatisierten Testen wurde der StatusCode der Antwort mit des Erwarteten verglichen. 
Die Integration Tests sollten identisch zu denen der Angabe sein. Die Postman Collection und das Standard-Curl-File sind dem GitRepository beigefügt.
Allerdings wurde der Curl "13) show configured deck different representation" nicht umgesetzt. Dies ist die einzige Abweichung zu den erwarteten Ergebnissen.

Die Automatisierung läuft soweit bis manuel leider ein zweiter Request in Postman gestartet werden muss. Postman wartet in einer Collection immer auf den Response.

In der Collection sind weitere Reqeust enthalten, welche evt Deaktiviert werden sollten beim automatisierten Testen.

Hint: zweiten Battle Curl deaktivieren oder zwei mal einen manuellen Request für den jeweils anderen User senden.

## `Database`

Die Datenbank besteht aus 4 Tabellen.
Users Tabelle mit allen Daten zu den Usern.
Cards Tabelle mit allen Daten zu den Cards sowie, falls vorhanden, dem Besitzer mit einem Foreign Key auf den Username von Users, und deren Collection wie "Stack" oder "Deck".
Packages Tabelle mit den Infos der Cards eines Packages mit den Foreign Keys auf die CardIDs von Cards.
Marketplace Tabelle mit den Infos eines Trades sowie dem Foreign Key auf die CardID der Cards.

SQL Statements zum Erstellen der Datenbank sind dem GitRepository beigefügt.
database.sql


## `Failures and Selected Solutions`

Die zwei größten Fehlerquellen waren das fehlende Wissen über den Aufbau eines Rest Servers sowie nicht gezieltes Programmieren auf eine Implementierung einer Datenbankanbindung.

Das Verständnis für die Struktur des Servers hat einige Zeit in Anspruch genommen und hat die meisten Fragen aufkommen lassen. Viele verschiedene Versuche haben schlussendlich zu einem funktionieren Ergebnis geführt. 

Die erste Version des MTCG beinhaltete eine sehr starke Aufteilung verschiedener Komponenten in unterschiedliche Klassen. Als Beispiel die Klasse "Package" konnte in die Klasse "Stack" übergeben werden und alle Karten aus dem "Package" wurden in den "Stack" inkludiert. 
Durch die persistente Speicherung in einer Datenbank wurde die alte Taktik, der Speicherung jedes Objektes zum Beispiel der Klasse "Package" in einer Datenstruktur wie z.B.: einer Liste überflüssig. Aus dem Grund habe ich mich entschieden die meisten SQL-Statements über Manager Klassen wie "CardManager", "UserManager", "TradeManager", "CombatManager" abwickeln zu lassen.

Der CombatManager wurde bis fast zum Schluss mit eine leere While Schleife gefangen um dem Thread zu halten. Dies wurde dann in ein synchronized Object geändert.

Weiteres wurden alle Unity Tests mit der Umgestalltung auf die Datenbankanbindung obsolet und wurden leider erst am Ende des Projektes erneut umgesetzt.


## `Unit Tests`

Es wurden mehrere Tests für die Berechnung des Schades der Karten erstellt (DamageCalculation). Es wird jede Fähigkeit der Monsterkarten sowie auch die Effekte der Zauberkarten überprüft.

Das Deck wird mit seinen Eigenschaften wie Karten hinzufügen und Karten entfernen sowie der richtigen Anzahl an Karten im Deck getestet.

Die Funktionalität des Unwrappers wurde mit Tests überprüft. Ihm wurde ein vorgefertigter BufferedReader übergeben und auf die Korrektheit des zurückgegebenen RequestContext geachtet.

Für den CombatManager wurden zwei User gemocked und überprüft ob Funktionen nach dem Battle am gemockten User aufgerufen werden.

Der ResponseHandler wurde mithilfe von gemocketen Static Funktionen getestet. Hier wurde überprüft ob Request die richtigen Funktionen in den jeweiligen Managern aufrufen.

Die Unit Tests wurden konfiguriert, dass sie automatisiert auf GitHub mit maven ausgeführt werden können.

## `Time Tracking`

Die Zeit die für dieses Projekt reingesteckt wurde war doch hoch. Allerdings zum großen Teil an doch sehr viel Verwirrung wie genau alles aufgebaut gehört und anfangs einer anderen Implementation als schlussendlich verwendet wurde. Die Anbindung an die Datenbank hat viel geändert. 


Für den MTCG Teil wurden ca. 55 Stunden gebraucht.
