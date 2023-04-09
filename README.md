Üdvözöllek a Programom leírásában.
A kötelező program Grafikus úton lett megvalósítva


program futtatásához szükséges információk:
Ha valamilyen programozói felületet (IDE) használsz pl idea , eclipse
a main osztály elérési útvonala : \Kötprog\src\main\java\org\example\app.java

Ha terminálból szeretnéd futtatni akkor a következő parancsal tudod futtatni:
javac /kötprog/src/main/java/org/example/*.java fordításhoz
java /kötprog/src/main/java/org/example/*.java futtatáshoz

-------------------------------------------------------------------------------------------------

Idea:

Open - funkcióval keressük ki a letöltött kötelező programot
!!!a kötprog nevű file-t nyissuk meg fő könyvtárnak
várjuk meg ameddig lebuildeli a program magát
próbáljuk meg futtatni a src/java/org.example/App ot


--------------------------------------------------------------------------------------------------

Ha nem sikerül futtatni a programot:
Kicsi András Videójában végigmegy több olyan előfeltételen ami aprogram futtatásához szükséges
https://www.youtube.com/watch?v=m2AFU3Tjeg4&list=PLGlj9VbLRrjA_i8dGwGWhNu17CXbFRUYD&index=2

https://gluonhq.com/products/javafx/ - legörgetve az operációs rendszernek megfelelő SDK file-t kell letölteni

ha ez után se fordul érdemes letölteni egy 17.0.1 es java verziót

ha ez után se fordul érdemes lehet letölteni egy maven-t is
https://maven.apache.org/download.cgi

---------------------------------------------------------------------------------------------------

A felhasználói segédlet:
Futtatást követően megjelenik egyablak. Itt 2 lehetőségünk van New Game feliratú táblára kattintani vagy
A quit feliratú hordóra kattintani

a new game-re ha rákattintunk megjelenik a nehézség választás
nehézség választás után a vásárlás rész következik
csak akkor tudunk harcba menni ha vásárolunk legalább 1 egységet

ha rányomtunk a Start feliratra a jobb alsó sarokban a csatamezőn találjuk magunkat
a képernyő bal alsó sarkában vannak a megvásárolt egységeink nevei.
Ha kiválasztunk közülük egyet akkor a térképen a baloldali első két oszlopba
kihelyezhetjük őket.

ha kihelyeztük az egységeket egy átmeneti fázisba érkezünk. Itt megjelennek új felületek
az első mozgást mozgást követően minden felület megtöltődik a megfelelő tartalommal.

innentől kezdve 4 lehetőségünkvan:
1: az adott egységgel várakozni
2: az adott egységgel mozogni
3: ad adott egységgel támadni egy másik egységet
4: varázsolni

1: ha az aktuális egység képére a saját körében rákattintasz az várakozásnak számít
2: ha rákattintasz egy üres mezőre akkor az egység ha elég gyors oda mozog ha nem nem történik semmi
3: ha az adott egység közvetlen az ellenfél egysége melett áll akkor az ellenséges egységre kattintva megtámadja azt
4: ha a játékos köre van (tehát amikor a saját egységgel tud mozogni) akkor lehetősége van a bal fenti varázslatok közül választani
ha nem vett varázslatot akkor csak ütni tud
ha kiválasztott egy varázslatot (vagy ütést) akkor a varázslat tipusán még változtathat de varázsolni onnantól kötelező

fix automaticly
