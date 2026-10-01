# LegacyInvoiceProcessor

Repositorio de práctica para la **Clase 1** de la *Capacitación para migración Java 7 a Java 21 (UTN BA)*.

---

## 🎯 Descripción del Proyecto

`LegacyInvoiceProcessor` es una aplicación empresarial heredada construida originalmente en **Java 7** que:
1. Lee y parsea facturas comerciales en formato XML utilizando anotaciones JAXB (`javax.xml.bind`).
2. Valida reglas de negocio (existencia de ID, coherencia de ítems y cuadratura del monto total calculado vs declarado).
3. Genera reportes en formato texto por consola.
4. Utiliza Log4j 1.x para el registro de eventos.

---

## 📂 Estructura del Repositorio

```
practica-clase-1-utn/
├── pom.xml                     # Configuración de compilación Maven
├── mvnw / mvnw.cmd             # Maven Wrapper portable (no requiere instalar Maven)
├── README.md                   # Este documento
├── docs/
│   ├── GUIA_DOCENTE_CLASE_1.md # Guía paso a paso de la clase y prompts de IA
│   └── MIGRATION_LOG.md        # Bitácora de migración
├── src/
│   ├── main/
│   │   ├── java/com/legacy/invoice/
│   │   │   ├── Invoice.java              # Entidad raíz XML (@XmlRootElement)
│   │   │   ├── InvoiceItem.java          # Línea de detalle XML
│   │   │   ├── InvoiceProcessor.java     # Lógica de deserialización JAXB
│   │   │   ├── InvoiceValidator.java     # Lógica de negocio de validación
│   │   │   └── InvoiceReportGenerator.java # Generador de reportes de texto
│   │   └── resources/
│   │       ├── sample-invoice.xml        # Factura XML de ejemplo
│   │       └── log4j.properties          # Configuración de logging
│   └── test/
│       ├── java/com/legacy/invoice/
│       │   ├── InvoiceProcessorTest.java # Tests de JAXB (afectados por la migración)
│       │   ├── InvoiceValidatorTest.java # Tests de negocio (test de control)
│       │   └── InvoiceReportGeneratorTest.java # Tests de reporte (test de control)
│       └── resources/
│           ├── valid-invoice.xml
│           └── invalid-invoice.xml
```

---

## 🚀 Dinámica de la Práctica (Clase 1)

### Objetivo
Migrar el proyecto desde **Java 7 a Java 21 LTS**, resolviendo la incompatibilidad producida por la remoción de JAXB del JDK y actualizando los espacios de nombres a **Jakarta EE 10** (`jakarta.xml.bind`), utilizando **Inteligencia Artificial como copiloto técnico**.

### Comandos Principales (usando Maven Wrapper)

- **Compilar:**
  ```powershell
  mvn clean compile
  ```
- **Ejecutar Tests:**
  ```powershell
  mvn test
  ```
- **Ejecutar la aplicación por línea de comandos:**
  ```powershell
  mvn exec:java -Dexec.mainClass="com.legacy.invoice.InvoiceProcessor" -Dexec.args="src/main/resources/sample-invoice.xml"
  ```

---
