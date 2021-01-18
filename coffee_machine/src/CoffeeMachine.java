import java.util.Scanner;

public class CoffeeMachine {
    public static Scanner scanner = new Scanner(System.in);
    public static int water = 400;
    public static int milk = 400;
    public static int beans = 400;
    public static int disposalCups = 9;
    public static int moneyInMachine = 550;
    public static String action;
    public static boolean exit = false;

    public static void main(String[] args) {

        while (!exit) {

            showMenu();

            switch (action) {
                case "buy":
                    buyCoffee();
                    break;
                case "fill":
                    fillOutMachine();
                    break;
                case "take":
                    takeMoney();
                    break;
                case "remaining":
                    showCoffeeMachine();
                    break;
                case "exit":
                    exit = true;
                    break;
            }
        }
    }

    public static void showMenu() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        action = scanner.nextLine();
    }

    public static void showCoffeeMachine() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " of water");
        System.out.println(milk + " of milk");
        System.out.println(beans + " of coffee beans");
        System.out.println(disposalCups + " of disposable cups");
        System.out.println(moneyInMachine + " of money");
    }

    public static void fillOutMachine() {
        System.out.println("Write how many ml of water do you want to add: ");
        int addWater = 0;
        try {
            addWater = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Write how many ml of milk do you want to add: ");
        int addMilk = 0;
        try {
            addMilk = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Write how many grams of coffee beans do you want to add: ");
        int addBeans = 0;
        try {
            addBeans = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Write how many disposable cups of coffee do you want to add: ");
        int addDisposalCups = 0;
        try {
            addDisposalCups = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
        }

        water += addWater;
        milk += addMilk;
        beans += addBeans;
        disposalCups += addDisposalCups;
    }

    public static void takeMoney() {
        System.out.println("I gave you $" + moneyInMachine);
        moneyInMachine = 0;
    }

    public static void buyCoffee() {

        int pick = 0;

        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");

        try {
            pick = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (pick) {
            case 1:
                if (water < 250) {
                    System.out.println("Sorry, not enough water!");
                } else if (beans < 16) {
                    System.out.println("Sorry, not enough beans!");
                } else if (disposalCups == 0) {
                    System.out.println("Sorry, not enough disposable cups");
                } else {
                    System.out.println("I have enough resources, making you a coffee!");
                    water -= 250;
                    beans -= 16;
                    disposalCups--;
                    moneyInMachine += 4;
                }
                break;
            case 2:
                if (water < 350) {
                    System.out.println("Sorry, not enough water!");
                } else if (milk < 75) {
                    System.out.println("Sorry, not enough milk!");
                } else if (beans < 16) {
                    System.out.println("Sorry, not enough beans!");
                } else if (disposalCups == 0) {
                    System.out.println("Sorry, not enough disposable cups");
                } else {
                    System.out.println("I have enough resources, making you a coffee!");
                    water -= 350;
                    milk -= 75;
                    beans -= 20;
                    disposalCups--;
                    moneyInMachine += 7;
                }
                break;
            case 3:
                if (water < 200) {
                    System.out.println("Sorry, not enough water!");
                } else if (milk < 100) {
                    System.out.println("Sorry, not enough milk!");
                } else if (beans < 12) {
                    System.out.println("Sorry, not enough beans!");
                } else if (disposalCups == 0) {
                    System.out.println("Sorry, not enough disposable cups");
                } else {
                    System.out.println("I have enough resources, making you a coffee!");
                    water -= 200;
                    milk -= 100;
                    beans -= 12;
                    disposalCups--;
                    moneyInMachine += 6;
                }
                break;
        }
    }
}
