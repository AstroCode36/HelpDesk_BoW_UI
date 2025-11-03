# DesktopBoWUI

Este proyecto es la interfaz de escritorio (UI) para la aplicación HelpDesk_BoW.

IMPORTANTE: para que esta UI funcione correctamente es necesario inicializar y compilar el siguiente repositorio y generar un archivo JAR desde IntelliJ:

https://github.com/Joaco2603/HelpDesk_BoW

## Requisitos

- Java (JDK) instalado y configurado en tu sistema.
- IntelliJ IDEA (Community o Ultimate) para compilar el repositorio dependiente y generar el JAR.

## Pasos (resumidos)

1. Clona el repositorio dependiente en tu máquina:

   git clone https://github.com/Joaco2603/HelpDesk_BoW.git

2. Abre el proyecto `HelpDesk_BoW` en IntelliJ:

   - File -> Open... -> selecciona la carpeta del repo `HelpDesk_BoW`.

3. Genera el JAR en IntelliJ (una forma común):

   - File -> Project Structure -> Artifacts
   - Haz clic en `+` -> `JAR` -> `From modules with dependencies...`
   - Selecciona el módulo principal y, si corresponde, la clase principal.
   - Configura el output directory y aplica los cambios.
   - Ve a Build -> Build Artifacts... -> selecciona el artifact creado -> Build.

   El JAR resultante normalmente estará en `out/artifacts/<artifact_name>/<artifact_name>.jar`.

4. Copia el JAR generado a este proyecto (`DesktopBoWUI`). Recomendación: crea una carpeta `libs/` en la raíz del proyecto y coloca allí el JAR, por ejemplo:

   - `DesktopBoWUI/libs/HelpDesk_BoW.jar`

5. Añade el JAR al classpath de este proyecto en IntelliJ:

   - File -> Project Structure -> Modules -> selecciona el módulo -> pestaña `Dependencies` -> `+` -> JARs or directories -> selecciona `libs/HelpDesk_BoW.jar` -> OK.

   Alternativamente, puedes añadir `libs/*` al classpath si usas ejecución por línea de comandos.

6. Ejecuta la UI desde IntelliJ (Main class `MainUI` o según corresponda). Asegúrate de que la dependencia JAR esté incluida en las dependencias de ejecución.

## Ejecución desde línea de comandos (opcional)

Si prefieres ejecutar desde la línea de comandos y ya compilaste este proyecto (por ejemplo con `javac` o desde IntelliJ), puedes usar algo similar a:

Windows PowerShell:

```powershell
java -cp "libs/*;out/production/DesktopBoWUI" MainUI
```

Ajusta la ruta `out/production/DesktopBoWUI` al directorio donde IntelliJ generó tus clases compiladas.

## Notas y suposiciones

- Se asume que el JAR generado desde `HelpDesk_BoW` contiene las clases y dependencias necesarias para que esta UI funcione.
- Si el repositorio `HelpDesk_BoW` contiene dependencias externas adicionales, asegúrate de empaquetarlas dentro del JAR (uber/fat JAR) o colocarlas también en `libs/` y añadirlas al classpath.
- Si prefieres un flujo automatizado (por ejemplo, gradle/maven), instala y configura el sistema de build correspondiente en `HelpDesk_BoW` y genera el JAR usando sus tareas (`gradle build` / `mvn package`).

## Contacto

Si necesitas ayuda con los pasos para generar el JAR o integrarlo en este proyecto, comparte el contenido del `pom.xml`/`build.gradle` (si existe) del repo `HelpDesk_BoW` y te indico pasos más precisos.

---

Archivo creado automáticamente: instrucciones para generar e integrar el JAR dependiente.

## 👨‍💻 Autores
- Joaquin Alberto Pappa Larreal
- Carlos Viud
- Daniel Guzman


## 📄 Licencia
Este proyecto está licenciado bajo la Licencia MIT.