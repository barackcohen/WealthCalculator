package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class WealthCalculator {

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n******\nWelcome to Wealth Calculator!");
        System.out.println("******\nThis program will calculate the size of your investments account when you retire.");
        System.out.println("Considering you consistently add to it every month, you will witness the power of compound interest!");
        System.out.println("Let us begin,\n");

        System.out.println("What is your starting point?");
        int startingPoint = Integer.parseInt(scanner.nextLine());

        System.out.println("How much will you invest, per month?");
        int monthlyAddition = Integer.parseInt(scanner.nextLine());

        System.out.println("What is the estimated net yearly yield of your investments account? (percentage)");
        int yearlyYieldPercentage = Integer.parseInt(scanner.nextLine());
        double yearlyYield = 1 + ((double)yearlyYieldPercentage) / 100;
        double monthlyYield = CalculateMonthlyYield(yearlyYield);

        System.out.println("And how many years do you have till you retire?");
        int remainingYears = Integer.parseInt(scanner.nextLine());
        int remainingMonths = remainingYears * 12;

        System.out.println("Would you like to see the growth? (y/n)");
        String answer = scanner.nextLine();
        int showGrowth;
        showGrowth = answer.equals("y") ? 1 : answer.equals("n") ? 0 : -1;
        if (showGrowth == -1) {
            System.out.println("BAD!");
            return;
        }

        System.out.println("Are you ready?");
        System.out.println(".\n.\n.");

        double retirementWealth = CalculateRetirementWealth(startingPoint, monthlyAddition, monthlyYield, remainingMonths, showGrowth);

        System.out.print("You will have: ");
        System.out.printf("%f", retirementWealth);
        System.out.println(" when you retire!\n.");

        int totalInvested = startingPoint + monthlyAddition*remainingMonths;
        double profit = retirementWealth - totalInvested;
        double expectedTaxes = profit / 4; // at 25% tax rates
        double overallYield = 100 * (profit / totalInvested);
        double netWealth = retirementWealth - expectedTaxes;

        System.out.printf("Profit: %f\n", profit);
        System.out.printf("Expected taxes: %f\n", expectedTaxes);
        System.out.printf("Overall yield (percentage): %f\n", overallYield);
        System.out.printf("Networth of investments account: %f\n", netWealth);
    }

    private static double CalculateMonthlyYield(double yearlyYield) // taken from StackOverflow
    {
        double xPre = Math.random() % 10;
        double error = 0.0000001;
        double delX = 2147483647;
        double current = 0.0;

        while (delX > error) {
            current = ((12 - 1.0) * xPre + (double) yearlyYield / Math.pow(xPre, 12 - 1)) / (double) 12;
            delX = Math.abs(current - xPre);
            xPre = current;
        }
        return current;
    }

    public static double CalculateRetirementWealth(int startingPoint, int monthlyAddition, double monthlyYield, int remainingMonths, int showGrowth)
    {
        double currentAmount = startingPoint;
        for (int i = 0; i < remainingMonths; i++)
        {
            currentAmount = (currentAmount + monthlyAddition) * monthlyYield;
            if (showGrowth == 1 & i > 0 & i%60 == 0)
            {
                System.out.print("After " + i/12 + " years you have: ");
                System.out.printf("%f\n", currentAmount);
            }
        }
        System.out.println(".");
        return currentAmount;
    }
}
