## Contenedores [![Java CI with Gradle](https://github.com/xBaank/Contenedores/actions/workflows/gradle.yml/badge.svg)](https://github.com/xBaank/Contenedores/actions/workflows/gradle.yml)

![image](https://user-images.githubusercontent.com/90746957/195692649-c0c74924-c602-4533-888a-ef5b087f1269.png)

### Autores: Roberto Blázquez y Francisco Toribio

CLI para la importacion y exportacion de datos de residuos y contenedores en diferentes formatos.

## Formatos

- JSON
- XML
- CSV
- HTML

## Argumentos

| Opcion  | Argumentos                                                  | Descripcion                                |
|---------|-------------------------------------------------------------|--------------------------------------------|
| Parser  | [Directorio origen] [Directorio destino]                    | Exporta los csv a json y xml               |
| Resumen | [Directorio origen] [Directorio destino]                    | Exporta a html                             |
| Resumen | [Nombre distrito]  [Directorio origen] [Directorio destino] | Exporta a html solo los datos del distrito |

## Ejemplo

```bash 
java -jar Contenedores.jar parser ./ ./Formatos
```

## Video

[![Video](https://img.youtube.com/vi/dQw4w9WgXcQ/0.jpg)](https://www.youtube.com/watch?v=dQw4w9WgXcQ)



