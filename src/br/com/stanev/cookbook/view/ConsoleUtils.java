package br.com.stanev.cookbook.view;

import java.util.Scanner;

public class ConsoleUtils {
	
	static Scanner scanner = new Scanner(System.in);
	
    private static final String INVALID_OPTION_MSG = "Invalid option. Please try again!";

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String getUserInput(String question) {
        return getUserOption(question);
    }

    public static String getUserOption(String message, String... options) {
        System.out.printf(message.concat("%n# : "));
        String option = scanner.nextLine().trim();
        if (options.length > 0) {
            while (!isValid(option, options)) {
                System.out.printf("%s%n# : ", INVALID_OPTION_MSG);
                option = scanner.nextLine().trim();
            }
        }
        return option;
    }

    public static double getUserDouble(String message) {
    	System.out.printf(message.concat("%n# : "));
        double valor = scanner.nextDouble();
        while (valor <= 0) {
                System.out.printf("%s%n# : ", INVALID_OPTION_MSG);
                valor = scanner.nextDouble();
        }
        return valor;
    }
    
    public static int getUserInt(String message) {
    	System.out.printf(message.concat("%n# : "));
    	int valor = scanner.nextInt();
    	while (valor <= 0) {
    		System.out.printf("%s%n# : ", INVALID_OPTION_MSG);
    		valor = scanner.nextInt();
    	}
    	return valor;
    }
    
     
    private static boolean isValid(String option, String... options) {
        for (String v : options) {
            if (v != null && v.equalsIgnoreCase(option)) {
                return true;
            }
        }
        return false;
    }
    
}
