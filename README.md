# Tv Maze Client App

[TvMazeClient.apk](/TvMazeClient.apk)

Esta es una aplicación de muestra que consume los servicios web:
* http://api.tvmaze.com/schedule?country=US&date='today' 
* http://api.tvmaze.com/search/shows?q='query' 
* http://api.tvmaze.com/shows/'id' 
* http://api.tvmaze.com/shows/'id'/cast

Esta desarrollada con la arquitectura de software MVVM + Clean: se divide en 3 capas presentación,
dominio y data; que a su vez cuentan con las subcapas modelo, vista y modelo de vista. 
Se utilizan 4 casos de uso, uno para cada servicio.

El fundamento principal de Clean es que la comunicación es de un solo sentido, la capa de datos es
independiente de la implementación realizada en el domino que a su vez es independiente de la capa
de presentación, de modo que la modificación de las capas exteriores no alteren el comportamiento de
las interiores. Se apoya en MVVM para aprovechar las librerias proporcionadas por la 
[Guía de Arquitectura de Apps](https://developer.android.com/jetpack/guide?hl=es-419) de Google. 
En MVVM la Vista transmite las acciones del usuario al Modelo de Vista, pero el Modelo de Vista no debe responder de forma explícita (no debe instanciar la vista),
por ello se se usa un patrón de observador que es facilitado por las variables tipo ViewModel.
El Modelo de Vista solicita información del Modelo, el cual solo responde y no puede realizar ningún otro tipo de acción ni sobre el Modelo de Vista ni sobre la Vista.

En este proyecto se utilizan las siguientes librerías:

* Navigation Graph y SafeArgs: Para la navegación y transferencia de datos entre fragments.
* DataBinding: Para el enlace entre los elementos de diseño y el código ejecutable.
* ViewModel: Para la implementación del patrón observador.
* Retrofit: Para la comunicación con los servicios web de TvMaze.
* Gson: Para el formateo de los datos obtenidos del API.
* Hilt: Para la inyección de dependencias.
* JUnit 4, Mock Web Server, Google Truth y Mockito: Para pruebas unitarias.
