/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea7adaniel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author NitroPc
 */
public class Prueba {

    public static void main(String[] args) {
        String a = "asdfadf";

        LocalDate hoy = LocalDate.now();

        System.out.println(hoy);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedString = hoy.format(formato);
        
        System.out.println(formattedString);
    }

}
