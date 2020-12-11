# Tarea 3 Sistemas Operativos

#### Integrantes:
- Alfredo Llanos 201804536-5
- Sofia Mañana 201804535-7

#### Indicaciones:
- Programa desarrollado en sistema operativo Ubuntu 20.04 con lenguaje de programacion Java.
- Ejecutar make func en la Linea de Comando para ejercutar el problema 1.
- Ejecutar make algo en la Linea de Comando para ejercutar el problema 2.

#### Implementacion:
- Para la explicacion del problema 2, utilizamos el algortimo de busqueda binaria en un arreglo ordenado. El ejemplo de prueba usado es un arreglo del 0 al 4, utiliamos uno muy pequeño debido a que la diferencia de tiempo es visible con arreglos chicos, ya que no usar hebras es mucho mas eficiente que no utilizarlas en este caso. Se creo hebra por cada mitad del arreglo y si no se encontraba el elemento, se creaba otra hebra mas con la mitad de la mitad anterior y así sucesivamente. Al crear tantas hebras, se generan muchos cambios de contexto lo que aumenta el tiempo total de ejecucion. Como el algortimo sin hebras, solo recorre el arreglo, lo cual si es costoso pero menos costoso que hacer todos los cambios de contexto necesarios para soportar las hebras, especialmente si el elemento se encuentra en uno de los extremos.