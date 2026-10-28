# Pruebas Automatizadas con POM — Laboratorio 2 (Semana 7)

**Curso:** ISW-622 Pruebas de Software — III Cuatrimestre 2026  
**Institución:** Universidad Técnica Nacional (UTN)  
**Docente:** Jose David Carvajal Jiménez  
**Sitio de prueba:** https://the-internet.herokuapp.com

## Integrantes del Grupo

- Josue Vargas
- Cristohper León
- Stephanie Zamora

---

## Estructura del Proyecto

```
.
├── .gitignore
├── pom.xml
├── README.md
└── src/
    └── test/
        └── java/
            └── cr/ac/utn/lab2/
                ├── base/
                │   ├── BaseTest.java
                │   └── Visual.java
                ├── pages/
                │   ├── AddRemovePage.java
                │   ├── CheckboxesPage.java
                │   ├── DropdownPage.java
                │   ├── LoginPage.java
                │   ├── SecureAreaPage.java
                │   └── TablePage.java
                └── tests/
                    └── Lab2Test.java
```

---

## Ejecución de Pruebas Automatizadas

Para ejecutar la suite completa de 9 pruebas (8 obligatorias + 1 de bono) con Maven:

```bash
mvn test
```

---

## Pruebas Cubiertas (Patrón Page Object Model)

- **T1:** `t1_loginValidoLlevaALaZonaSegura` (Login válido y verificación de zona segura) — _Josue Vargas_
- **T2:** `t2_usuarioInvalidoMuestraError` (Validación de mensaje de usuario inválido) — _Josue Vargas_
- **T3:** `t3_claveInvalidaMuestraError` (Validación de mensaje de contraseña inválida) — _Cristohper León_
- **T4:** `t4_cerrarSesionVuelveAlLogin` (Cierre de sesión y verificación de mensaje de salida) — _Cristohper León_
- **T5:** `t5_casillasDeVerificacion` (Estado inicial y marcado de casillas de verificación) — _Stephanie Zamora_
- **T6:** `t6_listaDesplegable` (Selección y verificación de "Option 2" en lista desplegable) — _Stephanie Zamora_
- **T7:** `t7_agregarYQuitarElementos` (Agregar 3 elementos, eliminar 1 y verificar 2 restantes) — _Cristohper León_
- **T8:** `t8_ordenarTablaPorApellido` (Ordenamiento por Last Name: primera fila Bach y última Smith) — _Stephanie Zamora_
- **T9 (Bono):** `t9_bono_correoDeUnaPersona` + `TablePage.correoPorApellido` (Búsqueda de correo de Doe: `jdoe@hotmail.com`) — _Colaborativo_
