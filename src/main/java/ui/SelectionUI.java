package ui;

import java.util.Scanner;
import java.util.List;

import persistence.Selection;

public class SelectionUI {
    public static boolean choseCategory(Selection selection, Scanner scanner, String optionCategory) {
        System.out.println();
        System.out.println("// Please Select one of the following options //");

        List<String> availableSelection = selection.getOptions(optionCategory);

        // Get the available options
        int opt = 1;
        for (String option: availableSelection) {
            System.out.println(option + ": " + opt);
            opt++;
        }

        while (true) {
            String optionNumber = scanner.nextLine().trim().toLowerCase();

            if (selection.isValidOption(optionCategory, optionNumber)) {
                System.out.println();
                System.out.println("// Category: " + selection.getSelectedOption(optionCategory, optionNumber) + " //");
                return true;
            } else {
                System.out.println("❌ Invalid option. Try again.\n");
            }
        }
    }
}
