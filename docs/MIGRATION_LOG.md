# Bitácora de Migración — LegacyInvoiceProcessor

## Sesión: Clase 1 — Migración Java 7 → Java 21 & JAXB a Jakarta XML Binding

### 1. Estado Inicial (Línea Base Java 7)
- **Versión de Java configurada:** 1.7
- **Mecanismo XML:** `javax.xml.bind` (embebido en JDK 7 `rt.jar`)
- **Logging:** Log4j 1.2.17
- **Tests unitarios:** 6 tests (InvoiceProcessorTest: 2, InvoiceValidatorTest: 2, InvoiceReportGeneratorTest: 2)

---

### 2. Diagnóstico del Error al migrar a Java 21

#### Error 1: Opción de compilador obsoleta
Al compilar con un JDK 21 moderno manteniendo `source 1.7` / `target 1.7`:
```
[ERROR] Source option 7 is no longer supported. Use 8 or later.
[ERROR] Target option 7 is no longer supported. Use 8 or later.
```

#### Error 2: Desaparición de JAXB del JDK
Al actualizar la configuración de compilador a Java 21 (`<maven.compiler.release>21</maven.compiler.release>`):
```
[ERROR] /.../Invoice.java:[3,24] package javax.xml.bind.annotation does not exist
[ERROR] /.../InvoiceItem.java:[3,24] package javax.xml.bind.annotation does not exist
[ERROR] /.../InvoiceProcessor.java:[5,23] package javax.xml.bind does not exist
[ERROR] /.../InvoiceProcessorTest.java:[6,23] package javax.xml.bind does not exist
```

**Causa Raíz:**
- En **Java 9/10**, los módulos de Java EE (`java.xml.bind`, `java.activation`, etc.) fueron marcados como deprecados.
- En **Java 11**, fueron **completamente eliminados** del JDK estándar.
- En la transición de Java EE a Eclipse Foundation (Jakarta EE 9/10), el paquete cambió su namespace estándar de `javax.xml.bind` a `jakarta.xml.bind`.

---

### 3. Plan de Cambios Aplicados (Asistido por IA)

#### A. Actualización de `pom.xml`
1. Actualizar propiedades del compilador a Java 21:
   ```xml
   <properties>
     <maven.compiler.source>21</maven.compiler.source>
     <maven.compiler.target>21</maven.compiler.target>
     <maven.compiler.release>21</maven.compiler.release>
   </properties>
   ```
2. Incorporar dependencias de **Jakarta XML Binding 4.x** e implementación de referencia:
   ```xml
   <!-- Jakarta XML Binding API -->
   <dependency>
     <groupId>jakarta.xml.bind</groupId>
     <artifactId>jakarta.xml.bind-api</artifactId>
     <version>4.0.2</version>
   </dependency>

   <!-- Implementación de Referencia en Runtime -->
   <dependency>
     <groupId>com.sun.xml.bind</groupId>
     <artifactId>jaxb-impl</artifactId>
     <version>4.0.5</version>
     <scope>runtime</scope>
   </dependency>
   ```

#### B. Actualización de código Java (Espacio de nombres)
Modificar únicamente los imports de `javax.xml.bind.*` a `jakarta.xml.bind.*` en:
- `Invoice.java`
- `InvoiceItem.java`
- `InvoiceProcessor.java`
- `InvoiceProcessorTest.java`

**Archivos sin modificaciones (Prueba de Control):**
- `InvoiceValidator.java` (Lógica de negocio pura)
- `InvoiceReportGenerator.java` (Generador de reportes)
- `InvoiceValidatorTest.java`
- `InvoiceReportGeneratorTest.java`

---

### 4. Resultados de la Verificación

| Verificación | Estado | Detalle |
|---|---|---|
| Compilación (`mvn clean compile`) | ✅ EXITOSA | 0 errores, compilado con Java 21 |
| Pruebas Unitarias (`mvn test`) | ✅ 6/6 PASS | 0 fallos, 0 errores |
| Ejecución CLI (`InvoiceProcessor`) | ✅ EXITOSA | Reporte de factura generado correctamente |
| Lógica de Negocio | ✅ INTACTA | 0 modificaciones en validadores ni cálculos |

---

### 5. Análisis de Deuda Técnica y Próximos Pasos (Clase 2)
- **Log4j 1.2.17:** Versión desactualizada con vulnerabilidades conocidas. En siguientes clases se evaluará la migración a SLF4J + Logback / Log4j2.
- **Exposición REST:** En la Clase 2 se integrará Spring Boot 3 para exponer `InvoiceProcessor.processInvoice()` a través de un endpoint HTTP sin alterar la lógica de negocio.
