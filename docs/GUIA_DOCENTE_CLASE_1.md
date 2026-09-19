# Guía Docente y Práctica: Clase 1 (UTN BA)

## Capacitación para Migración Java 7 a Java 21 con Asistencia de IA
**Tema:** Diagnóstico de incompatibilidades JDK, remoción de módulos Java EE (JAXB) y transición a Jakarta EE 10.

---

## 🎯 Objetivos de Aprendizaje

Al finalizar esta práctica, los participantes podrán:
1. Comprender el impacto del cambio arquitectónico entre Java 7 y Java 21 (modularización del JDK y eliminación de paquetes Java EE).
2. Experimentar de primera mano los errores de compilación producidos al intentar construir un proyecto Java 7 con Java 21.
3. Utilizar herramientas de Inteligencia Artificial (Copilot, Claude, Gemini, ChatGPT) para diagnosticar errores de migración con precisión técnica.
4. Aplicar la solución de migración a **Jakarta XML Binding 4.x** preservando la integridad de la lógica de negocio y las pruebas unitarias.
5. Documentar el proceso de migración en una bitácora técnica (`MIGRATION_LOG.md`).

---

## ⏱️ Cronograma Sugerido de la Clase (2 Horas)

| Bloque | Tiempo | Actividad |
|---|---|---|
| **Bloque 1** | 20 min | Introducción teórica: De Java 7 a Java 21 (Módulos, remoción de JAXB, javax vs jakarta). |
| **Bloque 2** | 25 min | Exploración del repositorio inicial en Java 7 y ejecución del intento de compilación en Java 21. |
| **Bloque 3** | 35 min | Interacción con la IA: Técnicas de prompting, diagnóstico de errores y propuesta de solución. |
| **Bloque 4** | 25 min | Aplicación de cambios (`pom.xml` y refactor de imports) y ejecución de tests de regresión. |
| **Bloque 5** | 15 min | Conclusiones, llenado de la bitácora (`MIGRATION_LOG.md`) y adelanto de Clase 2. |

---

## 🛠️ Guía Paso a Paso para la Práctica

### Paso 1: Clonar y explorar el proyecto inicial
El proyecto se encuentra en el estado inicial Java 7.
Observar:
- `pom.xml`: `<maven.compiler.source>1.7</maven.compiler.source>`, dependencias mínimas (Log4j, JUnit).
- `Invoice.java`: Usa anotaciones `javax.xml.bind.annotation.*`.
- `InvoiceValidator.java` e `InvoiceReportGenerator.java`: Clases de soporte sin dependencias JAXB.

### Paso 2: Provocar el error de compilación
Intentar compilar con Maven en el entorno Java 21:
```powershell
.\mvnw.cmd clean compile
```
**Resultado esperado:**
El compilador moderno rechaza `source 1.7` (`Source option 7 is no longer supported`).

El alumno procede a cambiar en `pom.xml`:
```xml
<maven.compiler.source>21</maven.compiler.source>
<maven.compiler.target>21</maven.compiler.target>
```
Y vuelve a ejecutar:
```powershell
.\mvnw.cmd clean compile
```
**Resultado esperado (Error didáctico clave):**
```
[ERROR] package javax.xml.bind.annotation does not exist
[ERROR] package javax.xml.bind does not exist
```

---

### Paso 3: Consultar a la IA (Práctica de Prompting)

Pedir a los alumnos que copien el error de la consola y consulten a su asistente de IA con los siguientes prompts sugeridos:

#### 💡 Prompt Inicial (Diagnóstico):
> *"Estoy migrando un proyecto heredado de Java 7 a Java 21 con Maven. Al cambiar el target a Java 21 recibo el error: `package javax.xml.bind does not exist`. ¿Por qué ocurre esto en Java 21 y cuáles son las alternativas modernas para solucionarlo en Maven?"*

#### 💡 Prompt de Refinamiento (Solución Jakarta EE 10):
> *"Quiero actualizar este proyecto al estándar moderno de Jakarta XML Binding 4.x compatible con Java 21 y Spring Boot 3. Proporcióname las dependencias exactas para `pom.xml` y qué cambios de imports debo hacer en el código fuente."*

---

### Paso 4: Aplicar la Solución

1. **En `pom.xml`**, agregar:
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

2. **En las clases Java** (`Invoice.java`, `InvoiceItem.java`, `InvoiceProcessor.java`, `InvoiceProcessorTest.java`), reemplazar:
   - `javax.xml.bind.` ➔ `jakarta.xml.bind.`

3. **Verificar que NO se toquen**:
   - `InvoiceValidator.java`
   - `InvoiceReportGenerator.java`

---

### Paso 5: Validar la Solución y Ejecución

1. **Compilar y ejecutar la suite de tests:**
   ```powershell
   .\mvnw.cmd clean test
   ```
   **Resultado esperado:** `Tests run: 6, Failures: 0, Errors: 0, Skipped: 0` ✅

2. **Ejecutar la aplicación completa:**
   ```powershell
   .\mvnw.cmd exec:java -Dexec.mainClass="com.legacy.invoice.InvoiceProcessor" -Dexec.args="src/main/resources/sample-invoice.xml"
   ```
   **Resultado esperado:**
   ```
   ============================================================
   INVOICE REPORT
   ============================================================
   ID:          INV-2024-001
   Date:        2024-01-15
   ------------------------------------------------------------
   Line Items:
   ------------------------------------------------------------
    1. Professional Consulting Services         150,00 x 10 = 1500,00
    2. Software License (Annual)                500,00 x 1 = 500,00
    3. Technical Support (Monthly)              200,00 x 1 = 200,00
   ------------------------------------------------------------
   TOTAL AMOUNT:    2200,00
   ============================================================
   ```

---

## ❓ Preguntas Frecuentes y Resolución de Problemas

- **¿Por qué `InvoiceValidatorTest` y `InvoiceReportGeneratorTest` pasaron sin cambios?**
  Porque representan código desacoplado de frameworks/librerías eliminadas (lógica pura de Java). Demuestra el principio de aislamiento en migraciones.
- **¿Por qué usamos `jakarta.xml.bind` en lugar de agregar la vieja dependencia `javax.xml.bind:jaxb-api:2.3.1`?**
  Aunque `javax.xml.bind 2.3.1` funcionaría como parche temporal, en el ecosistema Java 17+ y Java 21 (ej. Spring Boot 3, Hibernate 6, Quarkus 3) la industria adoptó definitivamente el namespace `jakarta.*`. Preparar el código para Jakarta EE es el estándar para migraciones a largo plazo.
