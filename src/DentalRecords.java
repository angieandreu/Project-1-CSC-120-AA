import jdk.swing.interop.SwingInterOpUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * Holds the dental records of one Florida family.
 * @author Angie Andreu
 * @see DentalRecords
 */
public class DentalRecords {

    private static final Scanner keyboard = new Scanner(System.in);
    /**
     * The maximum family size.
     */
    public static final int MAX_FAM_SIZE = 6;
    /**
     * The maximum teeth per row.
     */
    public static final int MAX_TEETH_PER_ROW = 8;
    /**
     * Making upper teeth row 0.
     */
    public static final int UPPER_TEETH = 0;
    /**
     * Making lower teeth row 1.
     */
    public static final int LOWER_TEETH = 1;

    public static void main(String[] args) {

        System.out.println("Welcome to the Floridian Tooth Records!");
        System.out.println("---------------------------------------");

        int familySize = readFamilySize();

        String[] familyNames = new String[familySize];
        char[][][] familyTeeth = new char[familySize][2][];

        getFamilyInformation(familyNames, familyTeeth);

        menuLoop(familyNames, familyTeeth);

    }//end of main

    /**
     * Ensuring the size of the family is less than maximum and storing validated family size.
     * @return int with family size
     */
    private static int readFamilySize() {
        System.out.print("Please enter number of people in the family: ");
        int familySize = keyboard.nextInt();
        while (familySize < 1 || familySize > MAX_FAM_SIZE) {
            System.out.print("Invalid number of people, please try again: ");
            familySize = keyboard.nextInt();
        }//end of while loop

        return familySize;

    }//end of readFamilySize

    /**
     * Get input from user to store family names, upper teeth, and lower teeth in arrays.
     * @param familyNames 1 dimensional array storing family names
     * @param familyTeeth 3 dimensional array storing family names, upper teeth indexes, and lower teeth indexes
     */
    private static void getFamilyInformation(String[] familyNames, char[][][] familyTeeth) {

        for (int index = 0; index < familyNames.length; index++) {
            System.out.print("Please enter the name for family member " + (index + 1) + ": ");
            familyNames[index] = keyboard.next();
            keyboard.nextLine();

            System.out.print("Please enter the uppers for " + familyNames[index] + ": ");
            String rowOfUpperTeeth = keyboard.nextLine().toUpperCase();
            while (!validTeethCheck(rowOfUpperTeeth)) {
                rowOfUpperTeeth = keyboard.nextLine().toUpperCase();
            }//end of while loop

            familyTeeth[index][0] = rowOfUpperTeeth.toCharArray();

            System.out.print("Please enter the lowers for " + familyNames[index] + ": ");
            String rowOfLowerTeeth = keyboard.nextLine().toUpperCase();
            while (!validTeethCheck(rowOfLowerTeeth)) {
                rowOfLowerTeeth = keyboard.nextLine().toUpperCase();
            }//end of while loop

            familyTeeth[index][1] = rowOfLowerTeeth.toCharArray();

        }//end of for loop

    }// end of getFamilyInformation

    /**
     * Validating teeth row length and storing numbered teeth input.
     * @param rowOfTeethTypes String with teeth types
     * @return
     */
    private static boolean validTeethCheck(String rowOfTeethTypes) {
        int toothNumber;

        if (rowOfTeethTypes.length() > MAX_TEETH_PER_ROW) {
            System.out.print("Too many teeth, please try again: ");
            return (false);

        }//end of if statement

        for (toothNumber = 0; toothNumber < rowOfTeethTypes.length(); toothNumber++) {
            if (rowOfTeethTypes.charAt(toothNumber) != 'I' && rowOfTeethTypes.charAt(toothNumber) != 'B' && rowOfTeethTypes.charAt(toothNumber) != 'M') {
                System.out.print("Invalid teeth types, please try again: ");
                return (false);

            }//end of if statement

        }//end of for loop

        return (true);

    }//end of validTeethCheck

    /**
     * Getting and validating user input to extract a tooth and updating the index instance.
     * @param familyNames 1 dimensional array storing family names
     * @param familyTeeth 3 dimensional array storing family names, upper teeth indexes, and lower teeth indexes
     */
    private static void extractTooth(String[] familyNames, char[][][] familyTeeth) {
        String nameInput;
        int personIndex;

        System.out.print("Which family member? ");

        nameInput = keyboard.next();

        while (getIndex(familyNames, nameInput) == -1) {
            System.out.print("Invalid family member, please try again: ");
            nameInput = keyboard.next();

        }//end of while loop

        personIndex = getIndex(familyNames, nameInput);

        String layerInput = "n";
        int layerIndex;

        System.out.print("Which tooth layer? (U)pper or (L)ower: ");
        layerInput = keyboard.next().toUpperCase();
        boolean isValid = layerInput.equals("U") || layerInput.equals("L");
        while (!isValid) {
            System.out.print("Invalid tooth layer, please try again: ");
            layerInput = keyboard.next().toUpperCase();
            isValid = layerInput.equals("U") || layerInput.equals("L");

        }//end of while loop

        if (layerInput.toUpperCase().equals("U")) {
            layerIndex = UPPER_TEETH;
        }//end of if
        else {
            layerIndex = LOWER_TEETH;
        }//end of else

        int toothInput;
        boolean isToothAlreadyMissing;

        System.out.print("Which tooth number? ");

        do {
            toothInput = keyboard.nextInt();
            toothInput--;
            if (toothInput >= 0 && toothInput < familyTeeth[personIndex][layerIndex].length) {
                isToothAlreadyMissing = familyTeeth[personIndex][layerIndex][toothInput] == 'M';

                if (isToothAlreadyMissing) {
                    System.out.print("Missing tooth, please try again:");
                    toothInput = -1;
                }//end of second if statement
            }//end of first if statement
            else {
                System.out.print("Invalid tooth number, please try again: ");
            }//end of else

        }//end of do
        while ((toothInput < 0 || toothInput >= familyTeeth[personIndex][layerIndex].length));
        familyTeeth[personIndex][layerIndex][toothInput] = 'M';

    }//end of extractTooth

    /**
     * Ensuring the user input of family name exists in familyNames array.
     * @param familyNames 1 dimensional array storing family names
     * @param name        String containing individual input name
     * @return
     */
    private static int getIndex(String[] familyNames, String name) {
        for (int nameIndex = 0; nameIndex < familyNames.length; nameIndex++) {
            if (name.toUpperCase().equals(familyNames[nameIndex].toUpperCase())) {
                return nameIndex;
            }//end of if statement
        }//end of for loop
        return -1;

    }// end of getIndex

    /**
     * Using modified quadratic formula to calculate the family's root canal indices and printing them with proper formatting.
     * @param familyNames 1 dimensional array storing family names
     * @param familyTeeth 3 dimensional array storing family names, upper teeth indexes, and lower teeth indexes
     */
    private static void rootCanalIndices(String[] familyNames, char[][][] familyTeeth) {
        int[] teethCount = countingTeeth(familyNames, familyTeeth);
        double bCount = (double) teethCount[0];
        double iCount = (double) teethCount[1];
        double mCount = -(double) teethCount[2];

        System.out.printf("One root canal at: %.2f %n", (-bCount + Math.sqrt(bCount * bCount - 4 * iCount * mCount)) / (2 * iCount));
        System.out.printf("Another root canal at: %.2f %n", (-bCount - Math.sqrt(bCount * bCount - 4 * iCount * mCount)) / (2 * iCount));

    }//end of rootCanalIndices

    /**
     * Counting each tooth type and combining them.
     * @param familyNames 1 dimensional array storing family names
     * @param familyTeeth 3 dimensional array storing family names, upper teeth indexes, and lower teeth indexes
     * @return int with number of teeth
     */
    private static int[] countingTeeth(String[] familyNames, char[][][] familyTeeth) {

        int bCount = 0;
        int iCount = 0;
        int mCount = 0;

        for (int personIndex = 0; personIndex < familyNames.length; personIndex++) {
            for (int rowIndex = 0; rowIndex < familyTeeth[personIndex].length; rowIndex++) {
                for (int columnIndex = 0; columnIndex < familyTeeth[personIndex][rowIndex].length; columnIndex++) {
                    if (familyTeeth[personIndex][rowIndex][columnIndex] == 'B') {
                        bCount++;

                    }// end of if statement
                    else if (familyTeeth[personIndex][rowIndex][columnIndex] == 'I') {
                        iCount++;

                    }//end of else-if
                    else {
                        mCount++;
                    }//end of else

                }//end of third for loop

            }//end of second for loop

        }//end of first for loop
        int[] result = new int[3];
        result[0] = bCount;
        result[1] = iCount;
        result[2] = mCount;
        return result;

    }// end of countingTeeth

    /**
     * Printing the name, numbered upper, and numbered lower rows of teeth for each family member.
     * @param familyNames 1 dimensional array storing family names
     * @param familyTeeth 3 dimensional array storing family names, upper teeth indexes, and lower teeth indexes
     */
    private static void printFamilyTeeth(String[] familyNames, char[][][] familyTeeth) {
        for (int personIndex = 0; personIndex < familyNames.length; personIndex++) {
            System.out.println(familyNames[personIndex]);
            for (int rowIndex = 0; rowIndex < familyTeeth[personIndex].length; rowIndex++) {
                if (rowIndex == UPPER_TEETH)
                    System.out.print("  Uppers: ");
                else {
                    System.out.print("  Lowers: ");
                }// end of if else
                for (int columnIndex = 0; columnIndex < familyTeeth[personIndex][rowIndex].length; columnIndex++) {
                    System.out.print((columnIndex + 1) + ":" + familyTeeth[personIndex][rowIndex][columnIndex] + " ");

                }//end of third for loop
                System.out.println();

            }//end of second for loop

        }//end of first for loop

    }//end of printFamilyTeeth

    /**
     * Displaying the menu options for user input, validating user input, and calling function to print valid menu options.
     * @param familyNames 1 dimensional array storing family names
     * @param familyTeeth 3 dimensional array storing family names, upper teeth indexes, and lower teeth indexes
     */
    private static void menuLoop(String[] familyNames, char[][][] familyTeeth) {
        System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it: ");
        String menuChoice = keyboard.next();

        while (!menuChoice.toUpperCase().equals("X")) {
            switch (menuChoice.toUpperCase()) {
                case "P":
                    printFamilyTeeth(familyNames, familyTeeth);
                    System.out.print("P)rint, (E)xtract, (R)oot, e(X)it: ");
                    break;
                case "E":
                    extractTooth(familyNames, familyTeeth);
                    System.out.print("P)rint, (E)xtract, (R)oot, e(X)it: ");
                    break;
                case "R":
                    rootCanalIndices(familyNames, familyTeeth);
                    System.out.print("P)rint, (E)xtract, (R)oot, e(X)it: ");
                    break;
                default:
                    System.out.print("Invalid menu option, please try again: ");
            }//end of switch
            menuChoice = keyboard.next();
        }//end of while loop
        System.out.print("Exiting the Floridian Tooth Records :-)");

    }//end of menuLoop

}//end of DentalRecords class