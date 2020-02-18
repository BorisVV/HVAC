package main.java;

import java.util.LinkedList;
import java.util.Date;
import java.util.Scanner;

public class HVAC {
    /** Program to manage service calls to furnaces and AC units
     */

    // Create a LinkedList object that store information provided by the user.
    private static LinkedList<ServiceCall> todayServiceCalls;
    private static LinkedList<ServiceCall> resolvedServiceCalls;
    //Global scanner used for all input
    private static Scanner scanner = new Scanner(System.in);
    // Create a variable that will be used in more than one instance when the Furnace model is called.
    static String furnaces = Furnace.FurnaceTypeManager.furnaceTypeUserChoices();
    // Global user input is used
    static String userInput = " ";

    public static void main(String[] args) {
        // Use todayServiceCalls as a Queue, So, add new calls to the end with add()
        // Remove a resolved call from the front of the queue with remove()
        // Check on the current call - the one at the head of the queue - with peek()

        //This will enable us to deal with calls in the order in which they were received
        todayServiceCalls = new LinkedList<ServiceCall>();

        // This will be used to store a list of resolved service calls.
        resolvedServiceCalls = new LinkedList<ServiceCall>();

        // quit will be change to true inside the while loop once the user selects quit.
        boolean quit = false;

        while (!quit) {
            // Display choices for user to pick...
            System.out.println("1. Add service call to queue");
            System.out.println("2. Resolve current call");
            System.out.println("3. Print current call");
            System.out.println("4. Print all outstanding calls");
            System.out.println("5. Print all resolved calls ");
            System.out.println("6. Quit");

//            int userChoice = getIntUserInput();
            // Get the number the user selected, if the user does not select a number, the program loop again.
            int userChoice = 0;
            try {
                userInput = scanner.nextLine();
                userChoice = Integer.parseInt(userInput);
            }catch (NumberFormatException nfe){
                System.out.println("Error with selection. Only numbers accepted...");
            }

            switch (userChoice) {

                case 1: {
                    addServiceCall();
                    break;
                }
                case 2: {
                    //Resolve service call

                    //Remove from head of the queue

                    if (todayServiceCalls.isEmpty()) {
                        System.out.println("No service calls today");
                        break;
                    }

                    ServiceCall resolvedCall = todayServiceCalls.remove();

                    System.out.println("Enter resolution for " + resolvedCall);

                    String resolution = getStringInput();
                    System.out.println("Enter fee charged to customer");
                    double fee = getPositiveDoubleInput();

                    resolvedCall.setResolution(resolution);
                    resolvedCall.setFee(fee);
                    resolvedCall.setResolvedDate(new Date());  //default resolved date is now

                    //Add this call to the list of resolved calls
                    resolvedServiceCalls.add(resolvedCall);
                    break;

                }
                case 3: {
                    //Print next service call - it is the one at the top of the queue
                    if (todayServiceCalls.isEmpty()) {
                        System.out.println("No service calls today");
                        break;
                    } else {
                        System.out.println(todayServiceCalls.peek());
                    }
                    break;
                }
                //Print all service calls
                case 4: {

                    System.out.println("Today's service calls are: ");

                    if (todayServiceCalls.isEmpty()) {
                        System.out.println("No service calls today");
                        break;
                    }

                    for (int call = 0; call < todayServiceCalls.size() ; call++) {
                        System.out.println("Service Call " + call + "\n" + todayServiceCalls.get(call) + "\n");
                    }
                    break;
                }

                case 5: {
                    System.out.println("List of resolved calls: ");

                    if (resolvedServiceCalls.isEmpty()) {
                        System.out.println("No resolved calls");
                        break;
                    }

                    for (ServiceCall c : resolvedServiceCalls) {
                        System.out.println(c + "\n");

                    }
                    break;
                }

                case 6: {
                    quit = true;
                    break;

                }

                default: {
                    System.out.println("Enter a number from the menu list provided only!");
                }

            }


        }

        System.out.println("Thanks, bye!");
        //Tidy up... close the scanner
        scanner.close();
    }


    private static void addServiceCall() {

        //What type of thing needs servicing?

        System.out.println("1. Add service call for furnace");
        System.out.println("2. Add service call for AC unit");
        System.out.println("3. Quit");

        int choice = getIntUserInput();

        switch (choice) {

            case 1: {
                // Case 1 was called (furnaces)
                System.out.println("Enter address of furnace");
                String address = getStringInput();

                System.out.println("Enter description of problem");
                String problem = getStringInput();

                int type = 0;
                while (type < 1 || type > 3) {
                    System.out.println(furnaces);
                    //We can only choose from types defined in FurnaceTypeManager
                    try {
                        // Declared global variable to get the user input.
                        userInput = scanner.nextLine();
                        type = Integer.parseInt(userInput); // Parse to see if the user entered an integer.
                        if (type < 1 || type > 3) {
                            // If the user enters any other number that is not in the list, print next line...
                            System.out.println("Number must match one of the number provided in the list, \nTry again...");
                        }
                    }
                    catch (NumberFormatException ime){
                        // Catch anything that is not an integer.
                        System.out.println("Number must be an integer and must match one of the number provided in the list. \nTry again...");
                    }
                }

                // Load the information to the object created below for the type of furnace picked.
                Furnace f = new Furnace(address, problem, new Date(), type);

                // Add the object into the array of LinkedList <todayServiceCalls>.
                todayServiceCalls.add(f);
                System.out.println("...Added the following furnace to list of calls:\n" + f);

                break; // Break case 1.
            }

            case 2: {

                System.out.println("Enter address of AC Unit");
                String address = getStringInput();
                System.out.println("Enter description of problem");
                String problem = getStringInput();
                System.out.println("Enter model of AC unit");
                String model = getStringInput();

                CentralAC ac = new CentralAC(address, problem, new Date(), model);
                todayServiceCalls.add(ac);
                System.out.println("Added the following AC unit to list of calls:\n" + ac);
                break; // Break case 2.
            }
            case 3: {
                return;

            }
            default: {
                System.out.println("Enter a number from the menu choices");
            }
        }
    }

    //Validation methods
    private static int getIntUserInput() {
        while (true) {
            try {
                String stringInput = scanner.nextLine();
                int intUserInput = Integer.parseInt(stringInput);
                return intUserInput;
             //If not a number (int) display a message to the user.
            } catch (NumberFormatException ime) {
                System.out.println("Please enter only a number that is on the list provided.");
            }
        }
    }

    private static double getPositiveDoubleInput() {

        while (true) {
            try {
                String stringInput = scanner.nextLine();
                double doubleInput = Double.parseDouble(stringInput);
                if (doubleInput >= 0) {
                    return doubleInput;
                } else {
                    System.out.println("Please enter a positive number");
                    continue;
                }
            } catch (NumberFormatException ime) {
                System.out.println("Please type a positive number");
            }
        }

    }

    private static String getStringInput() {

        String entry = scanner.nextLine();
        return entry;

    }
}

