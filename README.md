# ISW-622 · Laboratorio 2 · Pruebas automatizadas con POM

Sitio de práctica: **https://the-internet.herokuapp.com** (público, necesita internet).
Usuario válido: `tomsmith` · Clave válida: `SuperSecretPassword!`

## Qué trae este proyecto
```
src/test/java/cr/ac/utn/lab2/
├── base/   BaseTest (abre/cierra Chrome) y Visual (pausa para ver el navegador)
├── pages/  Páginas YA HECHAS con Page Factory: LoginPage, SecureAreaPage, CheckboxesPage,
│           DropdownPage, AddRemovePage, TablePage
└── tests/  Lab2Test.java  <- AQUÍ trabajan: 8 pruebas + 1 de bono, todas empiezan en rojo
```

## Qué deben hacer
Construir las pruebas de `Lab2Test.java` **usando las páginas existentes** (patrón POM):
- Ningún `By` ni `@FindBy` dentro de las pruebas.
- Las aserciones van en la prueba, no en la página.
- Cada prueba es independiente.

## Cómo ejecutar (desde la carpeta que contiene pom.xml)
```
mvn test                     # las 9 pruebas (al inicio todas fallan: es lo esperado)
mvn test -Dtest=Lab2Test#t1_loginValidoLlevaALaZonaSegura    # una sola prueba
mvn test -Dritmo=0           # sin pausas visuales
mvn test -Dheadless=true     # sin ventana del navegador
```
