## Contenedores [![Java CI with Gradle](https://github.com/xBaank/Contenedores/actions/workflows/gradle.yml/badge.svg)](https://github.com/xBaank/Contenedores/actions/workflows/gradle.yml)

![image](https://user-images.githubusercontent.com/90746957/195692649-c0c74924-c602-4533-888a-ef5b087f1269.png)

### Autores: Roberto Blázquez y Francisco Toribio

CLI para la importacion y exportacion de datos de residuos y contenedores en diferentes formatos.

## Formatos

- JSON
- XML
- CSV
- HTML

## Opciones

| Opcion  | Argumentos                                                  | Descripcion                                |
|---------|-------------------------------------------------------------|--------------------------------------------|
| Parser  | [Directorio origen] [Directorio destino]                    | Exporta los csv a json y xml               |
| Resumen | [Directorio origen] [Directorio destino]                    | Exporta a html                             |
| Resumen | [Nombre distrito]  [Directorio origen] [Directorio destino] | Exporta a html solo los datos del distrito |

## Argumentos opcionales

| Argumentos opcionales (se aplican a todas las opciones) | Descripcion                                  |
|---------------------------------------------------------|----------------------------------------------|
| -residuos                                               | Especifica el archivo de residuos a leer     |
| -contenedores                                           | Especifica el archivo de contenedores a leer |

## Ejemplo

```bash 
java -jar Contenedores.jar parser ./ ./Formatos
java -jar Contenedores.jar resumen ./ ./Formatos
java -jar Contenedores.jar resumen centro ./ ./Formatos

java -jar Contenedores.jar -residuos=residuos.csv -contenedores=contenedores.json resumen centro ./ ./Formatos
```

## Capturas

![image](.assets/cmd.png)
![image](.assets/html.png)
![image](.assets/html2.png)
![image](.assets/html3.png)






