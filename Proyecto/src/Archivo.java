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
* Clase : Archivo
* Responsabilidad : Realizar el debido manejo de los archivos del programa,
* obtener la información del problema del archivo correspondiente y guardar
* los datos de solución del problema en un archivo determinado.
*  
*/

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class Archivo {
    
    
    /**
     * abrirArchivo
     * Propósito: método que se encarga de permitir seleccionar un archivo de los archivos almacenadso
     * en el PC y se obtiene línea a línea la información almacenada en el archivo, esta información
     * se va guardando en una variable string
     * @return 
     */
    public String abrirArchivo(){
        // Se declaran e inicializan las variables string a utilizar
        String auxiliarBr =""; 
        String info="";
        
        try{
            // Se declara un objeto JFileChooser el cual es el que nos va a permitir seleccionar
            // el archivo correspondiente
            JFileChooser myFileChooser = new JFileChooser();
            myFileChooser.showOpenDialog(null); // Permite abrir la ventana para seleccionar el archivo
        
            File archivo = myFileChooser.getSelectedFile(); // Se obtiene el archivo seleccionado y se asigna a una variable
                                                            // de tipo File.
            
            // Si el archivo obtenido es distinto de null, es decir, sí se seleccionó un archivo.
            if(archivo != null){
                // Se declara un objeto FileReader que permitirá la lectura del contenido del archivo
                FileReader lector = new FileReader(archivo);
                // Se declara un objeto BufferedReader que permitirá la lectura de cada una de las líneas del archivo
                BufferedReader br = new BufferedReader(lector);
                
                // Mientras que la línea leído mediante el BufferedReader sea distinta de null
                while( (auxiliarBr = br.readLine()) != null){
                    info += auxiliarBr + "*"; // Se concatena el contenido de la línea del archivo al string info, junto con
                                              // un "*", el cual hace las veces de separador
                }
                
                //Se cierra el buffer
                br.close();
                
            }
            
            
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error al momento de parsear", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Archivo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IOException Error, sorry.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        
        // Se retorna el string info, que contiene la información leída del archivo, separando las líneas con "*"
        return info;
        
    }
    
    
    /**
     * escribirResultados
     * Propósito: método que se encarga de guardar los resultados de la solución del problema planteado
     * en un archivo de texto, se reciben 2 strings, el primero indica la estrategia por la cual se
     * resolvió el problema (voraz o dinámica) y el segundo contiene la información de la solución
     * del problema
     * @param metodo
     * @param informacion 
     */
    public void escribirResultados(String metodo, String informacion){
        
        // Se declara un objeto de  tipo File y se inicializa en null
        File fichero = null;
        // Se declara un objeto de tipo BufferedWriter, el cual permitirá la escritura en el archivo
        BufferedWriter bw = null;
        try{
            // Se crea un nuevo objeto de tipo File con el nombre "resultados" y se concatena el
            // nombre del método por el cual se resolvió el problema, el objeto creado se asigna
            // a la variable fichero.
            fichero = new File("resultados"+metodo+".txt");
            
            // Se crea un nuevo objeto BufferedWriter el cual recibe un objeto FileWriter que permitirá
            // la escritura en el objeto archivo fichero.
            bw = new BufferedWriter(new FileWriter(fichero));
            
            // Si el archivo no existe, se escriben en el archivo los datos de la solución obtenidos mediante 
            // el string información.
            if(!fichero.exists()){
                bw.write(informacion); 
            }
            //En caso de que el archivo ya exista, se elimina y se crea uno nuevo para que no se vaya a sobreescribir
            else{
                fichero.delete();
                fichero = new File("resultados"+metodo+".txt");
                bw.write(informacion);
            }
            
            
        }
        catch(Exception e){
            e.printStackTrace();
        } finally{
            try{
                //Nos aseguramos de que se cierre el fichero
                if (bw != null){
                    bw.close();
                }
            }
            catch(Exception e2){
                e2.printStackTrace();
            }
        }
        
    }
    
    
    
    
}
