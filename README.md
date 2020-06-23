<img src="img/readme0.gif" width=385px />

<img src="img/readme1.jpg" width=500px />

<img src="img/readme2.png" width=366px />

Scopul proiectului

Descrierea  con?inutului vizual de pe terminalele mobile cu ajutorul unui model de re?ea neuronala

<img src="img/readme3.png" width=201px />

Obiectivele propuse

Implementarea ?i antrenarea unei re?ele neuronale care va genera o descrierea textuala pe baza unei poze

Implementarea unui server care sa permita utilizarea modelului print REST API

Dezvoltarea unei aplica?ii mobile care sa comunice cu serverul

<img src="img/readme4.gif" width=385px />

<img src="img/readme5.jpg" width=500px />

__Diagrama bloc a__  __aplicatiei__

<img src="img/readme6.jpg" width=500px />

<img src="img/readme7.gif" width=385px />

<img src="img/readme8.jpg" width=500px />

Modelul de generare a

descrierii textuale

<img src="img/readme9.png" width=500px />

<img src="img/readme10.gif" width=385px />

<img src="img/readme11.jpg" width=500px />

Structura re?elei recurente

<img src="img/readme12.jpg" width=500px />

Re?eaua de decodare este conectata la penultimul strat al VGG\-ului

Primul strat reduce vectorul de intrare de la 4096 la 512\,egalcunumarul de stari interne ale straturilor GRU

Ultimul strat va con?ine 10000 de elemente=dimensiunea vocabularului re?elei

Vectorul de ie?ire reprezinta o codare de tipone\-hot ?i\,folosind harta detokenizare\, determinamcuvântulpe baza indexului valorii maxime

<img src="img/readme13.gif" width=385px />

<img src="img/readme14.jpg" width=500px />

Antrenarea re?elei

recurente

Pentru antrenarea modelului recurent s\-au utilizat:

Imaginile sub forma codata de penultimul strat al VGG16 \(un vector 4096 valori\)

OptimizatorRMSpropcu o rata de înva?are variabila

20 de epoci\, cu un o dimensiune a lotului de înva?are de 3000 de imagini\.

Oepocaadurataproximativ6 ore pe unprocesorgraficNvidiaGTX 1060

__Evolu?ia func?iei cost__  __pe__  __setul__  __de__  __validare__

<img src="img/readme15.png" width=500px />

<img src="img/readme16.gif" width=385px />

<img src="img/readme17.jpg" width=500px />

Rezultatele modelului

<img src="img/readme18.jpg" width=500px />

<img src="img/readme19.jpg" width=500px />

<img src="img/readme20.jpg" width=500px />

<img src="img/readme21.jpg" width=500px />

Setulde validare

<img src="img/readme22.gif" width=385px />

<img src="img/readme23.jpg" width=500px />

Implementare serverului

Autentificarefirebase

<img src="img/readme24.png" width=500px />

Pentru implementarea serverului ce ruleaza modelul de descriere aimagnilors\-au utilizat urmatoarele tehnologii:

Flask\(REST API\)

HTML/CSS\(Interfa?a

WEB\)

Docker\(Scalabilitate\)

Firebase\(Autentificare\,Scalabilitate\)

Google Cloud\(Hosting\)

Scalabilitate?iloadbalancing

<img src="img/readme25.jpg" width=500px />

Dockerinstances

<img src="img/readme26.gif" width=385px />

<img src="img/readme27.jpg" width=500px />

Aplica?ia web software

Serverul are ?i o aplica?ie web ce permite utilizatorului accesul la serviciul de generare a descrierilor\.

<img src="img/readme28.png" width=500px />

Serverul dispune si de un serviciu de REST API catre acest serviciu

POSThttp://127\.0\.0\.1:5000/api/predict?i poza a carei descriere ne intereseaza

<img src="img/readme29.gif" width=385px />

<img src="img/readme30.jpg" width=500px />

Aplica?ia Android

<img src="img/readme31.png" width=367px />

<img src="img/readme32.png" width=366px />

Aplica?ia android are urmatoarea structura:

<img src="img/readme33.png" width=372px />

<img src="img/readme34.png" width=365px />

<img src="img/readme35.png" width=363px />

<img src="img/readme36.png" width=361px />

Pagina de introducere

2\.  Paginile de autentificare/înregistrare

3\.  Pagina de selectare a pozei

4\.  Generarea descrierii

<img src="img/readme37.gif" width=385px />

<img src="img/readme38.jpg" width=500px />

Proiectul de fa?a a reu?it sa atinga urmatoarele puncte:

Crearea unui model capabil sa genereze o descriere textuala pe baza unei imagini

Antrenarea ?i testarea modelului de re?ea neuronala

Implementarea modelului într\-un back\-endscalabil\,capabil sa faca fa?a cererii utilizatorilor

Crearea unei aplica?ii web care sa permita utilizarea aplica?iei prin intermediul unui browser web

Implementarea unei aplica?ii android cu un serviciu de autentificare ?i gestiune al utilizatorilor ce comunica cu serverul web pentru a genera descrierile imaginilor

Testarea aplica?iei in diferite scenarii

