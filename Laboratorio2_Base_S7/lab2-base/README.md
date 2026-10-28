# ISW-622 · Laboratorio 2 · Pruebas automatizadas con POM

Pruebas automatizadas con **Selenium** y **JUnit 5**, usando el patrón **Page Object Model (POM)**.

Sitio de práctica: **https://the-internet.herokuapp.com** (público, necesita internet).
Usuario válido: `tomsmith` · Clave válida: `SuperSecretPassword!`

## Estructura del proyecto
```
src/test/java/cr/ac/utn/lab2/
├── base/   BaseTest (abre y cierra Chrome en cada prueba) y Visual (pausa para ver el navegador)
├── pages/  Páginas hechas con Page Factory: LoginPage, SecureAreaPage, CheckboxesPage,
│           DropdownPage, AddRemovePage y TablePage
└── tests/  Lab2Test.java  <- aquí están las 8 pruebas obligatorias y la del bono
```

## Qué se hizo
Se completaron las pruebas de `Lab2Test.java` usando solo los métodos de las páginas.
Las pruebas no tienen `By`, `@FindBy` ni `driver.findElement`, y las aserciones están en la prueba.
Cada prueba abre su propio Chrome, así que no dependen unas de otras, y siguen el orden
Arrange (preparar), Act (hacer la acción) y Assert (comprobar).

| Prueba | Qué comprueba |
|--------|---------------|
| T1 Login válido | Título "Secure Area", mensaje de sesión iniciada y URL que termina en `/secure` |
| T2 Usuario inválido | El mensaje dice que el usuario es inválido |
| T3 Clave inválida | El mensaje dice que la contraseña es inválida |
| T4 Cerrar sesión | Después de salir aparece el mensaje de cierre de sesión |
| T5 Casillas | La 1.ª empieza sin marcar y la 2.ª marcada; al marcar la 1.ª quedan las dos marcadas |
| T6 Lista desplegable | Al elegir "Option 2", esa es la opción seleccionada |
| T7 Agregar y quitar | Se agregan 3 elementos, se elimina 1 y quedan 2 botones Delete |
| T8 Ordenar tabla | Al ordenar por Last Name, la primera fila es Bach y la última es Smith |
| T9 Bono | El correo de Doe es `jdoe@hotmail.com` |

## Cambio en las páginas (bono)
Solo se modificó `TablePage` para el bono: se agregó el método `correoPorApellido(String apellido)`.
Recorre las filas de la tabla, busca el apellido en la columna 0 y devuelve el correo de la columna 2.
Las demás páginas quedaron como se recibieron.

## Cómo ejecutar (desde la carpeta que contiene pom.xml)
```
mvn test                     # las 9 pruebas
mvn test -Dtest=Lab2Test#t1_loginValidoLlevaALaZonaSegura    # una sola prueba
mvn test -Dritmo=0           # sin pausas visuales
mvn test -Dheadless=true     # sin ventana del navegador
```
Se necesita Java 17, Maven y Chrome instalados.

## Resultado
Las 9 pruebas pasan en verde (`Tests run: 9, Failures: 0, Errors: 0`).

## Nota sobre las advertencias
Al correr las pruebas puede salir `WARNING: Unable to find CDP implementation matching ...`.
Es solo un aviso porque la versión de Chrome es más nueva que la de Selenium (4.27.0).
No afecta las pruebas, porque no usan las funciones de DevTools.