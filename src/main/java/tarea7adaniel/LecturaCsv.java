/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea7adaniel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author daniel
 */
public class LecturaCsv {

    public static void main(String[] args) {

        // Fichero a leer
        String idFichero = "RelPerCen.csv";

        // Variables para guardar los datos que se van leyendo
        String[] tokens;
        String linea;

        //Array para almacenar las posicione donde se encuentra la secuencia de caracteres
        ArrayList<POJO> lista = new ArrayList<POJO>();

        System.out.println("Leyendo el fichero: " + idFichero);

        // Inicialización del flujo "datosFichero" en función del archivo llamado "idFichero"
        // Estructura try-with-resources. Permite cerrar los recursos una vez finalizadas
        // las operaciones con el archivo
        try ( Scanner datosFichero = new Scanner(new File(idFichero), "ISO-8859-1")) {

            datosFichero.nextLine();

            while (datosFichero.hasNextLine()) {

                linea = datosFichero.nextLine();

                // Se guarda en el array de String cada elemento de la
                // línea en función del carácter separador ,
                tokens = linea.split(",");

                //Creamos un objeto POJO vcio donde iremos añadiendole atributos
                POJO pojo = new POJO();

                //Utilizamos el metodo quitarComillas que lo que hace es quitar las comillas del principio y final de cada palabra
                pojo.setEmpleado(quitarComillas(tokens[0] + tokens[1]));
                pojo.setDni(quitarComillas(tokens[2]));
                pojo.setPuesto(quitarComillas(tokens[3]));
                //Creamos una variable fechaInicio y fechaFin para almacenar las fechas y quitarles las comillas
                String fechaInicio = quitarComillas(tokens[4]);
                pojo.setFechaInicio(LocalDate.parse(fechaInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                String fechaFin = quitarComillas(tokens[5]);
                //Como hay algunas fechas de fin vacias utilizaremos este codigo para comprobar si el tamaño de la palabra
                //es menor que tres, ya que si es asi significara que no contiene niguna fecha y lo pondremos a null
                if (fechaFin.length() < 3) {
                    pojo.setFechaFin(null);
                } else {
                    pojo.setFechaFin(LocalDate.parse(fechaFin, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }

                //Esto es para que en caso de que no haya número coloque un null
                if (tokens[6].length() < 3) {
                    pojo.setTelefono(null);
                } else {
                    pojo.setTelefono(quitarComillas(tokens[6]));
                }

                //En estos dos atributos no ha hecho falta quitar las comillas
                pojo.setEvaluador(Boolean.parseBoolean(tokens[7]));
                pojo.setCoordinador(Boolean.parseBoolean(tokens[8]));

                lista.add(pojo);

            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        ArrayList<POJO> empleados = new ArrayList<POJO>(empleados(lista));
        
        //-------------------------------------//

        // Fichero a crear. Ruta relativa a la carpeta raíz del proyecto
        String ficheroCrear = "POJO.csv";

        try ( BufferedWriter flujo = new BufferedWriter(new FileWriter(ficheroCrear))) {

            for (int i = 0; i < empleados.size(); i++) {
                flujo.write(empleados.get(i).getEmpleado() + ",");
                flujo.write(empleados.get(i).getDni() + ",");
                flujo.write(empleados.get(i).getPuesto() + ",");
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechain = empleados.get(i).getFechaInicio().format(formato);
                flujo.write( fechain + ",");
                String fechafi = empleados.get(i).getFechaFin().format(formato);
                if(fechafi.equalsIgnoreCase("null")){
                    flujo.write(" vacio ,");
                }else{
                    flujo.write( fechafi + ",");
                }
                
                if(empleados.get(i).equals("null")){
                    flujo.write(" vacio ,");
                }else{
                    flujo.write(empleados.get(i).getTelefono() + ",");
                }
                
                flujo.write(empleados.get(i).isEvaluador() + ",");
                flujo.write(empleados.get(i).isCoordinador() + ",");
                
                flujo.newLine();

            }

            // Metodo fluh() guarda cambios en disco 
            flujo.flush();
            System.out.println("Fichero " + ficheroCrear + " creado correctamente.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    //Metodo para quitar las comillas del principio y del final a las palabras
    private static String quitarComillas(String palabra) {

        String nueva = palabra.substring(1, palabra.length() - 1);

        return nueva;
    }

    //Metodo que me genera un arraylist con los empleados que llevan mas de 20 años en la empresa
    private static ArrayList<POJO> empleados(ArrayList<POJO> lista) {

        ArrayList<POJO> empleados = new ArrayList<POJO>();

        LocalDate hoy = LocalDate.now();

        for (int i = 0; i < lista.size(); i++) {
            LocalDate fechaIn = lista.get(i).getFechaInicio();
            LocalDate fechaFin = lista.get(i).getFechaFin();

            if (fechaFin == null) {
                long tiempoTrascurrido = ChronoUnit.YEARS.between(fechaIn, hoy);
                if (tiempoTrascurrido > 20) {
                    empleados.add(lista.get(i));
                }
            } else {
                long tiempo = ChronoUnit.YEARS.between(fechaIn, fechaFin);
                if (tiempo > 20) {
                    empleados.add(lista.get(i));
                }
            }

        }

        return empleados;
    }
}
