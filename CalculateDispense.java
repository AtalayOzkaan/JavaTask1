package cz.dn.task1;

/*=============================================================================
 |   Assignment:  Dispense Algorithm
 |       Author:  [Atalay Özkan (matalayozkan@gmail.com)]
 |
 |     Due Date:  [2021/11/12 03:00AM]
 |
 |  Description:  [Design dispense algorithm which for any entered amount combine
 |                Notes denominations
 |                Input(s):
 |                  a) Amount: Only positive number will be accepted.
 |                  b) Denominations: There can't be smaller than One and larger
 |                                    than Six. "-1" is exit to program.
 |                Dispense predictions
 |                  Rounding up or down according to denominations
 |                  Examples
 |                     amount: 1224, denomination: 50 -> amount: 1200
 |                     amount: 1234, denomination: 50 -> amount: 1250
 |                     amount: 1225, denomination: 50 -> amount: 1250
 |                Print:
 |                  If it is possible to dispense according to the entered 
 |                  inputs, do not print the amount.
 |                  If it is not possible to dispense according to the entered 
 |                  inputs, print both the amount and denominations.
 |                ]
 |
 |     Language:  [Java and Java Development Kit version is openjdk-17.0.1]
 | Ex. Packages:  [There are not additional requirement]
 |
 | Deficiencies:  [There is not known bug to fix. DB connection will be next
 |                released.]
 *===========================================================================*/

import java.util.*;
import java.util.stream.Collectors;

public class CalculateDispense {

    private static final int MAX_DENOM_COUNT = 6;

    public static void main(String[] args) {

        ArrayList<Integer> denomCountVal = new ArrayList<>();
        int amountVal;

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter dispense amount [It should be positive number]:");
        amountVal = scan.nextInt();

        // To check amount's input that is positive or negative
        int amountNewVal;
        while (amountVal <= 0) {
            System.out.println("Just positive number is accepted:");
            amountNewVal = scan.nextInt();
            amountVal = amountNewVal;
        }
        System.out.println("-----------------");

        //Maximum denomination is declared in the above Constants
        int denomVal;
        while (denomCountVal.size() < MAX_DENOM_COUNT) {
            System.out.println("Please Enter denominations. Type -1 for Exit:");
            denomVal = scan.nextInt();
            if (denomVal == -1) {
                if (denomCountVal.isEmpty()) {
                    System.out.println("You have not entered any denominations");
                    continue;
                }
                break;
            }
            while (denomVal <= 0) {
                System.out.println("Amount should be a positive integer");
                denomVal = scan.nextInt();
            }
            denomCountVal.add(denomVal);
        }
        scan.close();

        CalculateAndPrintDenomCount(amountVal, denomCountVal);
    }

    public static void CalculateAndPrintDenomCount(int amountVal, ArrayList<Integer> denominationList) {
        List<Integer> list = new ArrayList<>(denominationList);
        List<Integer> denomCountVal = list.stream().distinct().collect(Collectors.toList()); //To remove the duplicate denomination from the list

        Collections.sort(denomCountVal, Collections.reverseOrder()); // Sorting the list as descending

        //Rounding the amount up or down. It may be also able to call as a prediction part.
        int smallestDenom = denomCountVal.get(denomCountVal.size() - 1);
        String amountOutput = "";
        if (amountVal % smallestDenom != 0) {
            if (amountVal % smallestDenom < (smallestDenom / 2)) {
                amountVal -= amountVal % smallestDenom;
            } else {
                amountVal += smallestDenom - (amountVal % smallestDenom);
            }
            Formatter formatter = new Formatter();
            amountOutput = "amount:" + formatter.format("%, d", amountVal).toString().replaceAll(",", " "); //Amount format with space.
            formatter.close();
        }
        System.out.print("notes denominations count:");

        // Print the divisor value amd denominations as shown the sample format 6x 500, 1x 100
        int remainderVal, remainderLastVal, divisorVal;
        int size = denomCountVal.size();
        for (int i = 0; i < size; i++) {
            remainderVal = amountVal % (denomCountVal.get(i)); //250  34
            remainderLastVal = amountVal % denomCountVal.get(size - 1);
            divisorVal = (amountVal - remainderVal) / denomCountVal.get(i);
            if (remainderLastVal == 0) {
                amountVal = remainderVal;
                System.out.print(" " + divisorVal + "x " + denomCountVal.get(i));

                if (i != (size - 1)) {
                    System.out.print(",");
                }
            }
        }
        if (!"".equals(amountOutput)) {
            System.out.println("\n" + amountOutput);
        }
    }
}
