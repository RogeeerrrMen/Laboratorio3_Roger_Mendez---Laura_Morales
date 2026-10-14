# Laboratorio 3 — Herencia y Polimorfismo en Java

**Universidad del Valle de Guatemala**  
**Curso:** Programación Orientada a Objetos  
**Ciclo:** 2, 2026

## Integrantes

- Roger Yorkaef Méndez García — 26786
- Laura Suseth Morales Murga — 26281

## Descripción

EnvíaGT es un sistema de gestión de envíos desarrollado en Java como parte del Laboratorio 3 de Programación Orientada a Objetos.

El programa simula las operaciones de una empresa de mensajería, permitiendo administrar diferentes categorías de envíos, vehículos, despachos, entregas e ingresos.

Su implementación utiliza los principios de **herencia, abstracción, encapsulamiento y polimorfismo**, distribuyendo las responsabilidades entre diferentes clases.

## Funcionalidades

- Cotizar y registrar envíos.
- Gestionar sobres, paquetes, paquetes frágiles y cargas refrigeradas.
- Calcular tarifas y tiempos estimados de entrega según la categoría.
- Registrar vehículos con distintas capacidades y disponibilidad de refrigeración.
- Crear despachos, asignar y retirar envíos.
- Registrar salidas y resultados de entrega.
- Gestionar intentos de entrega y devoluciones.
- Consultar envíos y vehículos.
- Generar reportes de envíos, ingresos y despachos activos.
- Validar operaciones y manejar errores mediante excepciones.


## Instrucciones de compilación y ejecución

**1. Compilar los archivos Java**

Desde la carpeta principal del proyecto, ejecutar:

```bash
javac -d bin src/*.java
```

**2. Ejecutar el programa**

```bash
java -cp bin Main
```

El programa mostrará un menú interactivo desde el cual se pueden realizar las operaciones disponibles.

## Datos iniciales

El sistema inicia con ocho envíos precargados, dos por cada categoría, y tres vehículos con diferentes capacidades.

Estos datos permiten realizar pruebas desde el inicio y no generan ingresos por registro.


## Consideraciones

- El programa funciona mediante una interfaz de consola.
- Los datos se conservan únicamente durante la ejecución.
- No se utilizan archivos externos ni bases de datos para almacenar información.
- Los costos se expresan en quetzales (Q).
- Los tiempos estimados de entrega se expresan en días hábiles.

## Análisis y diseño

El análisis y el diseño del sistema EnvíaGT se encuentran en la carpeta `docs/`.


## Propósito académico

Aplicar los conceptos de herencia y polimorfismo para desarrollar un sistema orientado a objetos, promoviendo la reutilización de código, la separación de responsabilidades y la organización modular del programa.