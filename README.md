# Caption this

<img src="img/readme2.png" width=366px />

Scopul proiectului

Descrierea  continutului vizual de pe terminalele mobile cu ajutorul unui model de retea neuronala

<img src="img/readme3.png" width=201px />

Obiectivele propuse

Implementarea si antrenarea unei retele neuronale care va genera o descrierea textuala pe baza unei poze

Implementarea unui server care sa permita utilizarea modelului print REST API

Dezvoltarea unei aplicatii mobile care sa comunice cu serverul



__Diagrama bloc a__  __aplicatiei__

<img src="img/readme6.jpg" width=500px />



Modelul de generare a

descrierii textuale

<img src="img/readme9.png" width=500px />



Structura retelei recurente

<img src="img/readme12.jpg" width=500px />

Reteaua de decodare este conectata la penultimul strat al VGG\-ului

Primul strat reduce vectorul de intrare de la 4096 la 512\,egalcunumarul de stari interne ale straturilor GRU

Ultimul strat va contine 10000 de elemente=dimensiunea vocabularului retelei

Vectorul de iesire reprezinta o codare de tipone\-hot si\,folosind harta detokenizare\, determinamcuvântulpe baza indexului valorii maxime



Antrenarea retelei

recurente

Pentru antrenarea modelului recurent s\-au utilizat:

Imaginile sub forma codata de penultimul strat al VGG16 \(un vector 4096 valori\)

OptimizatorRMSpropcu o rata de învatare variabila

20 de epoci\, cu un o dimensiune a lotului de înva?are de 3000 de imagini\.

Oepocaadurataproximativ6 ore pe unprocesorgraficNvidiaGTX 1060

__Evolu?ia func?iei cost__  __pe__  __setul__  __de__  __validare__

<img src="img/readme15.png" width=500px />



Rezultatele modelului

<img src="img/readme18.jpg" width=500px />

<img src="img/readme19.jpg" width=500px />

<img src="img/readme20.jpg" width=500px />

<img src="img/readme21.jpg" width=500px />

Setulde validare



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



Aplica?ia web software

Serverul are si o aplicatie web ce permite utilizatorului accesul la serviciul de generare a descrierilor\.

<img src="img/readme28.png" width=500px />

Serverul dispune si de un serviciu de REST API catre acest serviciu

POSThttp://127\.0\.0\.1:5000/api/predict si poza a carei descriere ne intereseaza



Aplicatia Android

Aplicatia android are urmatoarea structura:

<img src="img/readme31.png" width=367px />

Pagina de introducere

<img src="img/readme32.png" width=366px />

Paginile de autentificare/înregistrare


<img src="img/readme33.png" width=372px />

<img src="img/readme34.png" width=365px />

Pagina de selectare a pozei

<img src="img/readme35.png" width=363px />

Generarea descrierii

<img src="img/readme36.png" width=361px />







# Concluzii

Proiectul de fata a reusit sa atinga urmatoarele puncte:

Crearea unui model capabil sa genereze o descriere textuala pe baza unei imagini

Antrenarea si testarea modelului de retea neuronala

Implementarea modelului într\-un back\-endscalabil\,capabil sa faca fata cererii utilizatorilor

Crearea unei aplicatii web care sa permita utilizarea aplicatiei prin intermediul unui browser web

Implementarea unei aplicatii android cu un serviciu de autentificare si gestiune al utilizatorilor ce comunica cu serverul web pentru a genera descrierile imaginilor

Testarea aplicatiei in diferite scenarii

