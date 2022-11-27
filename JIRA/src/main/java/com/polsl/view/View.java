package com.polsl.view;

import java.util.Scanner;
/**
 * This class is resposible for displaying contnet and receviewing 
 * it from user perspective
 * @author Roch Fedorowicz
 * @version 1.1
 * @since 1.0
*/
public class View {
    
    /**
     * It scans one word form user's input
     * @return scaned parameter from console
    */
    public static String scanSingleInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
 
    /**
     * It displays contnent specified in argument
     * @param string Content in string to be displayed
    */
    public static void displayContent(String string) {
        System.out.println(string);
    }
    
    /**
     * It scans whole line form user's input
     * @return scaned line from console
    */
    public static String scanWholeLineInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
