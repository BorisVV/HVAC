package main.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;


public class Furnace extends ServiceCall {

    private int furnaceType;

    public Furnace(String serviceAddress, String problemDescription, Date date, int furnaceType) {

        super(serviceAddress, problemDescription, date);

        //TODO Error checking - is this a valid furnace type?
        this.furnaceType = furnaceType;
    }

    @Override
    public String toString() {

        String typeString = FurnaceTypeManager.getTypeString(furnaceType);
        String resolvedDateString = ( resolvedDate == null) ? "Unresolved" : this.resolvedDate.toString();
        String resolutionString = ( this.resolution == null) ? "Unresolved" : this.resolution;
        String feeString = (fee == UNRESOLVED) ? "Unresolved" : "$" + Double.toString(fee);


        return "Furnace Service Call " + "\n" +
                "Service Address= " + serviceAddress + "\n" +
                "Problem Description = " + problemDescription  + "\n" +
                "Furnace Type = " + typeString + "\n" +
                "Reported Date = " + reportedDate + "\n" +
                "Resolved Date = " + resolvedDateString + "\n" +
                "Resolution = " + resolutionString + "\n" +
                "Fee = " + feeString ;

    }


    //Inner class to collect the different varieties of furnace
    //When we ask for the type, we'll use the constants from this class to display the choice of types.

    protected static class FurnaceTypeManager {

        protected static final int FORCED_AIR = 1;
        protected static final int BOILER = 2;
        protected static final int OCTOPUS = 3;

        static HashMap<Integer, String> furnaceTypes;

        //Static initialization block
        static {
            furnaceTypes = new HashMap<Integer, String>();
            furnaceTypes.put(FORCED_AIR,"Forced Air");
            furnaceTypes.put(BOILER,"Boiler/Radiators");
            furnaceTypes.put(OCTOPUS,"Older 'Octopus' Style");
        }

        public static String getTypeString(int userInput) {
            return furnaceTypes.get(userInput);
        }

        public static String furnaceTypeUserChoices() {
            //Get all of the keys from the Hashmap and sort them in order
            ArrayList<Integer> keys = new ArrayList<Integer>(furnaceTypes.keySet());
            Collections.sort(keys);

            //Build and return a string of all the keys and their values and
            //Display them to the user,
            String displayChoice = "";
            for (Integer k : keys) {
                displayChoice = displayChoice + k + " : " + furnaceTypes.get(k) + "\n";
            }
            return "Types of furnaces: \n" + displayChoice;
        }

    }

}
