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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author daniel
 */
public class LecturaCsv {

    public static void main(String[] args) {

        // Fichero a leer
        String idFichero = "RelPerCen.csv";

        //Contador para contar los puestos que sean informático
        int contadorInfor = 0;
        //Variable que almacena si hay algún biologo que sea coordinador
        int contadorBiolo = 0;
        //Variable que almacena si hay algún Jonh
        int contadorNombre= 0;

        // Variables para guardar los datos que se van leyendo
        String[] tokens;
        String linea;
        //Esta vriable nos permite almacenar los tipos de datos dentro
        String[] primeraLinea = new String[8];

        //Array para almacenar las posicione donde se encuentra la secuencia de caracteres
        ArrayList<POJO> lista = new ArrayList<>();

        // Inicialización del flujo "datosFichero" en función del archivo llamado "idFichero"
        // Estructura try-with-resources. Permite cerrar los recursos una vez finalizadas
        // las operaciones con el archivo
        try ( Scanner datosFichero = new Scanner(new File(idFichero), "ISO-8859-1")) {

            linea = datosFichero.nextLine();

            primeraLinea = linea.split(",");

            datosFichero.nextLine();

            while (datosFichero.hasNextLine()) {

                linea = datosFichero.nextLine();

                // Se guarda en el array de String cada elemento de la
                // línea en función del carácter separador ,
                tokens = linea.split(",");

                contadorInfor += informatico(tokens);
                contadorBiolo += biologo(tokens);
                contadorNombre += jonh(tokens);

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

                //Aqui comprbaremos que es si o no y segun esto devolveremos true o false
                boolean evaluar = comprobar(quitarComillas(tokens[7]));
                boolean coordinar = comprobar(quitarComillas(tokens[8]));

                //En estos dos atributos no ha hecho falta quitar las comillas
                pojo.setEvaluador(evaluar);
                pojo.setCoordinador(coordinar);

                lista.add(pojo);

            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("El documento " + idFichero + " contiene " + contadorInfor + " Informáticos");
        if (contadorBiolo == 0) {
            System.out.println("No hay biólogos que son coordinadores");
        } else {
            System.out.println("Si hay biólogos que son coordinadores");
        }
        
        if (contadorNombre == 0) {
            System.out.println("No hay empleados llamados Jonh");
        } else {
            System.out.println("Si hay empleados llamados Jonh");
        }

        ArrayList<POJO> empleados = new ArrayList<>(empleados(lista));

        ArrayList<POJO> empleadosNif = new ArrayList<>(nif(lista));

        System.out.println("\nLos empleados que contienen N en su Dni son: ");
        for (POJO ls : empleadosNif) {
            System.out.println(ls);
        }

        //-------------------------------------//
        // Fichero a crear. Ruta relativa a la carpeta raíz del proyecto
        String ficheroCrear = "POJO.csv";

        try ( BufferedWriter flujo = new BufferedWriter(new FileWriter(ficheroCrear))) {

            //Este bucle lo he utilizado para escribir los tipos de datos almacenados en un array anteriormente
            for (int i = 0; i < primeraLinea.length; i++) {

                if (primeraLinea.length - 1 == i) {
                    flujo.write(primeraLinea[i]);
                } else {
                    flujo.write(primeraLinea[i] + ",");
                }

            }

            flujo.newLine();

            for (int i = 0; i < empleados.size(); i++) {

                flujo.write(empleados.get(i).getEmpleado() + ",");
                flujo.write(empleados.get(i).getDni() + ",");
                flujo.write(empleados.get(i).getPuesto() + ",");
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechain = empleados.get(i).getFechaInicio().format(formato);
                flujo.write(fechain + ",");

                //Aqui comprobamos que la fecha no sea nulla y si es asi escribira el valor como vacio
                if (empleados.get(i).getFechaFin() == null) {
                    flujo.write("vacio,");
                } else {
                    //Si la fecha no es nulla la escribira dandole formato
                    flujo.write(empleados.get(i).getFechaFin().format(formato) + ",");
                }

                if (empleados.get(i).getTelefono() == null) {
                    flujo.write("vacio,");
                } else {
                    flujo.write(empleados.get(i).getTelefono() + ",");
                }

                flujo.write(empleados.get(i).isEvaluador() + ",");
                flujo.write(empleados.get(i).isCoordinador() + ",");

                //Este metodo salta a la siguiente linea
                flujo.newLine();

            }

            // Metodo fluh() guarda cambios en disco 
            flujo.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    //Metodo para quitar las comillas del principio y del final a las palabras
    private static String quitarComillas(String palabra) {

        return palabra.substring(1, palabra.length() - 1);
    }

    //Metodo que me genera un arraylist con los empleados que llevan mas de 20 años en la empresa
    private static ArrayList<POJO> empleados(ArrayList<POJO> lista) {

        ArrayList<POJO> empleados = new ArrayList<>();

        LocalDate hoy = LocalDate.now();

        for (int i = 0; i < lista.size(); i++) {
            LocalDate fechaIn = lista.get(i).getFechaInicio();
            LocalDate fechaFin = lista.get(i).getFechaFin();

            if (fechaFin == null) {

                LocalDate fecha = hoy.minusYears(20);

                if (fechaIn.isBefore(fecha)) {
                    empleados.add(lista.get(i));
                }
                //Con esto calcularia todos los que han trabajado mas de 20 años sin contar a dia de hoy
//            } else {
//                long tiempo = ChronoUnit.YEARS.between(fechaIn, fechaFin);
//                if (tiempo > 20) {
//                    empleados.add(lista.get(i));
//                }
            }

        }

        return empleados;
    }

    //Metodo que combrueba si es si o no y devuelve un boolean
    private static boolean comprobar(String bol) {

        boolean opcion;

        if (bol.equalsIgnoreCase("no")) {
            opcion = false;
        } else {
            opcion = true;
        }

        return opcion;
    }

    //Metodo para contar el número de infromaticos
    private static int informatico(String[] tokens) {

        if (tokens[3].contains("Informática")) {
            return 1;
        }
        return 0;
    }

    //Metodo para comprobar si algún Biologo es coordinador
    private static int biologo(ArrayList<POJO> lista) {
        
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).getPuesto().equalsIgnoreCase("Biología y Geología P.E.S.") && 
                    lista.get(i).isCoordinador())){
                return 1;
            }
        }
        return 0;
    }

    //Metodo para ordenar los apellidos y obtener los empleados que contengan N en su Nif
    private static ArrayList<POJO> nif(ArrayList<POJO> lista) {

        ArrayList<POJO> empleados = new ArrayList<>();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getDni().contains("N")) {
                empleados.add(lista.get(i));
            }
        }

        Comparator<POJO> criterio = (p1, p2) -> p1.getEmpleado().compareTo(p2.getEmpleado());
        Collections.sort(empleados, criterio);

        return empleados;
    }

    //Metodo para comprobar si alguien se llama Jonh
    private static int jonh(String[] tokens) {

        if (tokens[1].contains("Jonh")) {
            return 1;
        }

        return 0;

    }
}
