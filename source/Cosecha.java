/**
* Proyecto - Fundamentos de Análisis y Diseño de Algoritmos
*
* Integrantes: 
* Jhonier Andrés Calero Rodas   1424599
* Fabio Andrés Castañeda Duarte 1424386
* Juan Pablo Moreno Muñoz       1423437
* Joan Manuel Tovar Guzmán      1423124
* 
* Archivo: Archivo.java 
* Fecha de entrega: 19/07/2016
* Universidad del Valle 
* FADA
* EISC
* 
* Clase : Cosecha
* Responsabilidad : Realizar el debido cálculo de las soluciones del
* problema planteado, haciendo uso de las 2 estrategias planteadas,
* programación voraz y programación dinámica.
*  
*/

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.lang.Math;
import java.text.DecimalFormat;


public class Cosecha {
    
    // Se realiza la declaración de las variables globales a utilizar
    
    int numMeses; //numero de meses en el periodo de cosecha
    int prodPorCiclo;  //Cantidad de papa por cada ciclo de cosecha
    double valorPorBulto; //valor de cada bulto
    int ingresosTotales; //ingresos totales de todas las cosechas
    int[] demandaMinima; //Contiene la demanda mínima de todos los meses
    int[] demandaMaxima; //Contiene la demanda máxima de todos los meses
    int[] mesesCosechados; //Contiene 1's o 0's, dependiendo si se cosecha en el mes i
    double[] ganancias; //Contiene las ganancias de cosechar en cada mes i
    double[] costo; //Vector de costos para programación dinámica
    

    /**
     * Constructor de la clase
     * Propósito: inicializar debidamente cada una de las variables 
     * a utilizar.
     */
    public Cosecha() {
        numMeses = 0;
        prodPorCiclo = 0;
        valorPorBulto = 0;
        ingresosTotales = 0;
    }

    /**
     * setNumMeses
     * Propósito: método que permite asignar un valor a la variable
     * global numMeses, de esta manera se facilita la actualización
     * de dicha variable.
     * @param n 
     */
    public void setNumMeses(int n) {
        numMeses = n;
    }

    /**
     * setProdPorCiclo
     * Propósito: método que permite asignar un valor a la variable
     * globlal proPorCiclo.
     * @param n 
     */
    public void setProdPorCiclo(int n) {
        prodPorCiclo = n;
    }

    /**
     * setValorPorBulto
     * Propósito: método que permite asignar un valor a la variable
     * globar valorPorBulto.
     * @param n 
     */
    public void setValorPorBulto(int n) {
        valorPorBulto = n;
    }
    
    /**
     * setGanancias
     * Propósito: método que permite llenar el arreglo ganancias,
     * realizando el cálculo correspondiente, el cual depende
     * de la producción por ciclo, la demanda mínima y máxima.
     * Los valores anteriores se han obtenido con anticipación.
     */
    public void setGanancias(){
        
        ganancias = new double[numMeses]; // Se inicializa el arreglo de ganancias
        double ganancia = 0; // se inicializa la variable que almacenará el valor de la ganancia de cada mes
        
        // Ciclo que se encarga de llenar el arreglo
        for (int i = 0; i < numMeses; i++) {

            // Se verifican las condiciones correspondientes para así poder
            // hacer el debido cálculo
            if (prodPorCiclo < demandaMinima[i]) {
                ganancia = venderPapaMitadPrecio(prodPorCiclo);
                ganancias[i] = ganancia;
            } else if (prodPorCiclo > demandaMaxima[i]) {
                int bultosRest = prodPorCiclo - demandaMaxima[i];
                ganancia = (demandaMaxima[i] * valorPorBulto) + venderPapaMitadPrecio(bultosRest);
                ganancias[i] = ganancia;
            } else {
                ganancia = prodPorCiclo * valorPorBulto;
                ganancias[i] = ganancia;
            }

        }               
                
    }
    
    
    /**
     * reiniciarCostosyMeses
     * Propósito: método que se encarga de inicializar en 0 los valores en 
     * las posiciones del arreglo de costos y en el arreglo de meses cose-
     * chados.
     */
    public void reiniciarCostosYMeses(){
        
        for(int i =0; i<numMeses; i++){
            costo[i]=0;             // Se inicializa en 0 el valor de la posición i de costos
            mesesCosechados[i] = 0; // Se inicializa en 0 el valor de la posición i de mesesCosechados
        }        
    }
    
    /**
     * setValores
     * Propósito: método que recibe una variable string que contiene la información
     * que se obtuvo del archivo, la cual está separada por "*" por cada línea que
     * se leyó, en este método se separa la información y se van almacenando en las
     * variables y arreglos destinadas para eso.
     * @param infoArchivo
     * @return 
     */
    public int setValores(String infoArchivo) {
        
        int error=0; // se inicializa una variable que nos permitirá saber si hubo un error.
        StringTokenizer st = new StringTokenizer(infoArchivo, "*");
                
        try {
            while (st.hasMoreTokens()) {
                //1er linea: Numero de meses
                setNumMeses(Integer.parseInt(st.nextToken()));

                //Se inicializan los arreglos de demandas
                demandaMinima = new int[numMeses];
                demandaMaxima = new int[numMeses];
                
                int i=0;
                //2da linea: Demanda minima. Toca partir el string por espacios ' '
                String linea2 = st.nextToken();
                StringTokenizer st2 = new StringTokenizer(linea2); // Se separa la 2da línea por espacios
                while (st2.hasMoreTokens()) {
                    int num = Integer.parseInt(st2.nextToken()); // Se obtiene el valor siguiente de la 2da línea
                    demandaMinima[i] = num;  // Se asigna el valor obtenido de la línea al valor en la posición i del arreglo
                    i++;
                }

                i=0;
                //3ra linea: Demanda maxima. Toca partir el string por espacios ' '
                String linea3 = st.nextToken();
                StringTokenizer st3 = new StringTokenizer(linea3); // Se separa la 3ra línea por espacios
                while (st3.hasMoreTokens()) {
                    int num = Integer.parseInt(st3.nextToken()); // Se obtiene el valor siguiente de la 3ra línea
                    demandaMaxima[i] = num; // Se asigna el valor obtenido de la línea al valor en la posicion i del arreglo
                    i++;
                }

                //4ta linea: Produccion por ciclo
                setProdPorCiclo(Integer.parseInt(st.nextToken())); // Se asigna el valor almacenado en la 4ta línea a la
                                                                   // variable correspondiente

                //5ta linea: Valor por bulto
                setValorPorBulto(Integer.parseInt(st.nextToken())); // Se asigna el valor almacenado en la 5ta línea a la
                                                                    // variable correpondiente
            }

            setGanancias(); // Se llena el arreglo de ganancias 
            
            // Se declaran los arreglos de mesesCosechados y costo
            mesesCosechados = new int[numMeses]; 
            costo = new double[numMeses];
            
            // Se inicializan los arreglos de mesesCosechados y costo con 0 en los valores de
            // todas sus posiciones
            reiniciarCostosYMeses(); 
                                                

        } catch (Exception e) {
            // En caso de que ocurra algún error, se asigna -1 a la variable error
            JOptionPane.showMessageDialog(null, "El archivo no está escrito de manera legible. Por favor revíselo", "ERROR", JOptionPane.ERROR_MESSAGE);
            error = -1;
        }
        
        // Se retorna la variable error, si retorna un 0 no hubo error, si retorna un -1 hubo algún error.
        return error;
    }
    
    /**
     * datosCargados
     * Propósito: almacenar en un string los datos del problema que se obtuvieron del archivo,
     * entre los cuales están: el número de meses, las demandas mínimas, demandas máximas,
     * producción por ciclo y valor por bulto. Al final se retorna este string con toda la 
     * información organizada.
     * @return 
     */
    public String datosCargados(){
        
        String info = ""; // Se inicializa la variable string donde se almacenará la información
        
        // Se va concatenando al string los datos obtenidos del problema
        info = "Se cargaron los siguientes datos:  \n\n";
        info += "Numero de meses: " + numMeses + "\n"; 
        info += "Las demandas minimas: {";
        
        // Se recorre el arreglo de demandas mínimas para concatenar cada valor de la demanda
        // mínima por cada mes
        for (int i = 0; i < demandaMinima.length; i++) {
            info += demandaMinima[i] + ",";
        }
        
        info += "}\n";
        info += "Las demandas Maximas: {";
        
        // Se recorre el arreglo de demandas máximas para concatenar cada valor de la demanda 
        // máxima de cada mes
        for (int i = 0; i < demandaMaxima.length; i++) {
            info += demandaMaxima[i] + ",";
        }
        
        info += "}\n";
        info += "Produccion por ciclo: " + prodPorCiclo + "\n";
        info += "Valor por bulto: " + valorPorBulto;
                     
        // Se retorna el string con toda la información almacenada
        return info; 
        
    }
    
    /**
     * venderPapaNormal
     * Propósito: método que recibe una cantidad de papa y calcula el valor total de la
     * venta, vendiendola al valor exacto de cada bulto.
     * @param cantPapa
     * @return 
     */
    public double venderPapaNormal(int cantPapa) {
        double venta = cantPapa * valorPorBulto; // Se calcula el valor de la venta
        return venta; // Se retorna el valor calculado
    }

    /**
     * venderPapaMitadPrecio
     * Propósito: método que recibe una cantidad de papa determinada y realiza el calculo
     * de la venta total, teniendo en cuenta que se va a vender a la mitad de precio de
     * cada bulto.
     * @param cantPapa
     * @return 
     */
    public double venderPapaMitadPrecio(int cantPapa) {
        double venta = cantPapa * (valorPorBulto / 2); // Se calcula el valor de la venta
        return venta; // Se retorna el valor calculado
    }

    /**
     * infoCosecha
     * Propósito: método que revisa en cuántos meses se cosecharon y obteniendo las posiciones
     * del arreglo de mesesCosechados en los cuales hay un 1, se obtienen los meses en los 
     * cuales se realizaron dichas cosechas.
     * @return 
     */
    public String infoCosecha() {
        
        int conteo = 0; // Se declara e inicializa la variable que cuenta la cantidad de meses en que se cosechó
        double venta = 0; //Se declara e inicializa la variable que contiene el valor total de la venta
        String infoMeses=""; // String que va a almacenar los meses en que se cosecharon
        venta = ventasTotales(); // Se hace el llamado al método que calcula el total de la venta y se asigna
                                 // el valor obtenido a la variable venta.
                
        // Ciclo que recorre el arreglo de meses cosechados
        for (int i = 0; i < mesesCosechados.length; i++) {
            
            // Si el valor almacenado en al posición i del arreglo es 1, quiere decir que en el
            // mes (i+1) se cosechó
            if(mesesCosechados[i] == 1){
                infoMeses += (i+1) + "<-- Mes cosechado.\n"; // Se concatena el número del mes en que se cosechó
                conteo+=1; // Se incrementa el número de meses cosechados en 1
            }
        }
        
        // Se declara un formato para la representación de los decimales
        DecimalFormat formater = new DecimalFormat("#.#####");
                
        // Se declara un string info, toda la información obtenida: la venta total y el número de meses
        // en los que se cosechó, es almacenda en este string.
        
        String info = "Los ingresos fueron: " + formater.format(venta) + "\nSe cosechó en " + conteo + " meses. \n";
        
        // Se concatena al string info, el número cada mes en que se cosechó
        info+=infoMeses;
        
        // Se retorna el string que contiene toda la información
        return info;
    }
    
    /**
     * ventasTotales
     * Propósito: método que se encarga de calcular la venta total obtenida por la ganancia obtenida en cada mes
     * que se cosechó.
     * @return 
     */
    public double ventasTotales(){
        
        double venta =0; // Se declara e inicializa la variable que almacenará el valor total
        
        // Se recorre el arreglo mesesCosechados
        for(int i=0; i<mesesCosechados.length; i++){
            
            // Si el valor almacenado en la posición i del arreglo es 1, entonces se suma al valor
            // de la variable venta, la ganancia que es obtenida en el mes i.
            if(mesesCosechados[i] == 1){
                venta +=ganancias[i];
            }
        }

        // Se retorna el valor de la venta total
        return venta;
    }
    
    
    
    /**
     * resultadosCosechaArchivo
     * Propósito: método que se encarga de almacenar en un string toda la información de la 
     * solución del problema, cada resultado será guardado en una línea del string. 
     * @return 
     */
    public String resultadosCosechaArchivo(){
        
        int conteo = 0; // Se declara e inicializa la variable que cuenta la cantidad de meses en que se cosechó
        double venta = 0; //Se declara e inicializa la variable que contiene el valor total de la venta
        String infoMeses=""; // String que va a almacenar los meses en que se cosecharon
        venta = ventasTotales(); // Se hace el llamado al método que calcula el total de la venta y se asigna
                                 // el valor obtenido a la variable venta.
        
        // Ciclo que recorre el arreglo de meses cosechados
        for (int i = 0; i < mesesCosechados.length; i++) {
            
            // Si el valor almacenado en al posición i del arreglo es 1, quiere decir que en el
            // mes (i+1) se cosechó
            if(mesesCosechados[i] == 1){
                infoMeses += (i+1) +"\n"; // Se concatena el número de mes en que se cosechó al string
                conteo+=1; // Se incrementa el número de meses cosechados en 1
            }
        }
        
        // Se declara un formato para la representación de los decimales
        DecimalFormat formater = new DecimalFormat("#.#####");
                
        // Se declara un string info, toda la información obtenida: la venta total y el número de meses
        // en los que se cosechó y cada número de mes en que se cosechó, es almacenda en este string.
        String info =  formater.format(venta) + "\n" + conteo + "\n" + infoMeses;
        
        // Se retorna el string info
        return info;
    }
    
      
    /**
     * resolverVoraz
     * Propósito: método que se encarga de solucionar el problema planteado por medio de la
     * estrategia voraz, la cual se basa en el hecho de cosechar cada 3 meses partiendo del
     * 3er mes.
     */
    public void resolverVoraz() {
                   
        reiniciarCostosYMeses(); // Se inicializa el arreglo de mesesCosechados y costo con 0 en los valores de cada posición
        
        // Si el número de meses a considerar es menor que 3, no es posible cosechar, pues el proceso de
        // siembra dura 3 meses, así que se retorna un mensaje indicando esto.
        if (numMeses < 3) {
            JOptionPane.showMessageDialog(null, "There're not even 3 months. We cannot harvest.", "ERROR", JOptionPane.ERROR_MESSAGE);
            
        } else {
            // Si el número de meses a considerar es mayor o igual a 3
            
            // Se hace un ciclo desde el indice igual a 2, con un paso de 3 y hasta
            // que se llegue al número de meses menos uno.
            for (int i = 2; i < numMeses; i = i + 3) {
                
                mesesCosechados[i]=1; // Se asigna al valor de la posición i del arreglo de mesesCosechados un 1, el cual
                                      // indica que en ese mes se cosechó.
            }
            
        }

    }
     
    /**
     * resolverDinamica
     * Propósito: método que se encarga de obtener la solución del problema planteado
     * usando la estrategia dinámica descrita anteriormente, la cual se basa en el 
     * hecho de cosechar o no cosechar. Teniendo un número de meses n, la solución
     * va a depender de la solución óptima para n-1 meses o para n-3 meses dependiendo
     * de si no se cosechó o sí se cosechó, respectivamente.
     */
    public void resolverDinamica(){
        
        reiniciarCostosYMeses(); // Se inicializan los arreglos de mesesCosechados y costo
        calcularCosto(); // Se llama el método que se encarga de llenar el arreglo de costos
        identificarMesesCosecha(); // Se llama al método que se encarga de revisar los meses en los cuales se cosechó.
        
    }    
    
    /**
     * calcularCosto
     * Propósito: método que se encarga de llenar el arreglo de costo aplicando la estrategia
     * dinámica.
     */
    public void calcularCosto(){
        
        // Se recorre el arreglo de costo
        for (int i = 0; i < costo.length; i++) {
         
            // Si el indice es menor que 2, quiere decir que no se puede cosechar,
            // así que en esa posición del arreglo se pone un 0.
            if(i < 2){
                costo[i]=0;                
            }
            
            // Si el indice es igual a 2, entonces la ganancia máxima será la que 
            // se obtiene en ese mes.
            else if(i==2){
                costo[i]=ganancias[i];
            }
            
            // Si el indice es mayor que 2, entonces se calculará al máximo entre cosechar ese
            // mes, lo cual implica obtener la ganancia de ese mes más el costo de los 3 meses
            // anteriores y entre no cosechar ese mes, que implica el costo del mes anterior. 
            else{
                costo[i]= Math.max(ganancias[i]+costo[i-3], costo[i-1]);
            }
                        
        }    
        
    }
    
    /**
     * identificarMesesCosecha
     * Propósito: método que se encarga de revisar cuáles son los meses en que se cosecha-
     * ron e indicarlo llenando el arreglo de mesesCosechados
     */
    public void identificarMesesCosecha(){
        
        int i=numMeses-1; // Se inicializa el indice en el número de meses a considerar menos 1
        
        // mientras el indice sea mayor o giual a 2
        while(i>=2){
            
            // El valor en la posición i del arreglo de costos es diferente al valor en la posición
            // i-1 del arreglo de costos, entonces quiere decir que se cosechó en ese mes y se 
            // pone un 1 en la posición i del arreglo mesesCosechados, además se disminuye el 
            // indice en 3
            if(costo[i] != costo[i-1]){
                           
                mesesCosechados[i]=1;
                i = i-3;
            }   
            
            // Si no, entonces quiere decir que no se cosechó en ese mes y se pone un 0 en la posición
            // i del arreglo mesesCosechados, y se disminuye el indice en 1.
            else{
                mesesCosechados[i]=0;
                i = i-1; 
            }
            
        }
        
        
    }
            
    
    
}
    
    
        