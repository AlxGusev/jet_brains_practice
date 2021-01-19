import java.util.Scanner;

public class CoffeeMachine {

    public static Scanner scanner = new Scanner(System.in);
    public static int water = 400;
    public static int milk = 400;
    public static int beans = 400;
    public static int disposalCups = 9;
    public static int moneyInMachine = 550;
    public static Action action;
    public static Condition currentCondition;

    public static void main(String[] args) {

        while (currentCondition != Condition.NOT_WORKING) {

            showMenu();

            switch (action) {
                case BUY:
                    currentCondition = Condition.MAKING_COFFEE;
                    buyCoffee();
                    break;
                case FILL:
                    currentCondition = Condition.FILLING_OUT;
                    System.out.println(currentCondition.status);
                    fillOutMachine();
                    break;
                case TAKE:
                    currentCondition = Condition.TAKING_OUT_MONEY;
                    System.out.println(currentCondition.status);
                    takeMoney();
                    break;
                case REMAINING:
                    currentCondition = Condition.SHOWING_REMAINING_RESOURCES;
                    System.out.println(currentCondition.status);
                    showCoffeeMachine();
                    break;
                case EXIT:
                    currentCondition = Condition.NOT_WORKING;
                    System.out.println(currentCondition.status);
                    break;
            }
        }
    }

    public static void showMenu() {
        currentCondition = Condition.WORKING;
        System.out.println(currentCondition.status);
        System.out.println("Write action (buy, fill, take, remaining, exit):");

        String input = scanner.nextLine();

        if ("buy".equals(input)) {
            action = Action.BUY;
        } else if ("fill".equals(input)) {
            action = Action.FILL;
        } else if ("take".equals(input)) {
            action = Action.TAKE;
        } else if ("remaining".equals(input)) {
            action = Action.REMAINING;
        } else if ("exit".equals(input)) {
            action = Action.EXIT;
        } else {
            System.out.println("Incorrect input. Enter proper action from the list");
            showMenu();
        }
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
        water += addWater();
        milk += addMilk();
        beans += addBeans();
        disposalCups += addDisposalCups();
    }

    public static int addWater() {
        int addWater = 0;
        System.out.println("Write how many ml of water do you want to add: ");
        try {
            addWater = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Error!!! Incorrect input");
        }
        return addWater;
    }

    public static int addMilk() {
        int addMilk = 0;
        System.out.println("Write how many ml of milk do you want to add: ");
        try {
            addMilk = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Error!!! Incorrect input");
        }
        return addMilk;
    }

    public static int addBeans() {
        int addBeans = 0;
        System.out.println("Write how many grams of coffee beans do you want to add: ");
        try {
            addBeans = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Error!!! Incorrect input");
        }
        return addBeans;
    }

    public static int addDisposalCups() {
        int addDisposalCups = 0;
        System.out.println("Write how many disposable cups of coffee do you want to add: ");
        try {
            addDisposalCups = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Error!!! Incorrect input");
        }
        return addDisposalCups;
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
            if (pick  > 3) {
                System.out.println("Error!!! Wrong number");
            }
        } catch (Exception e) {
            System.out.println("Error!!! Incorrect input");
        }

        switch (pick) {
            case 1:
                checkingResources(pick);
                if (currentCondition == Condition.MAKING_COFFEE){
                    water -= 250;
                    beans -= 16;
                    disposalCups--;
                    moneyInMachine += 4;
                }
                break;
            case 2:
                checkingResources(pick);
                if (currentCondition == Condition.MAKING_COFFEE){
                    water -= 350;
                    milk -= 75;
                    beans -= 20;
                    disposalCups--;
                    moneyInMachine += 7;
                }
                break;
            case 3:
                checkingResources(pick);
                if (currentCondition == Condition.MAKING_COFFEE){
                    water -= 200;
                    milk -= 100;
                    beans -= 12;
                    disposalCups--;
                    moneyInMachine += 6;
                }
                break;
        }
    }

    public static void checkingResources(int typeOfCoffee) {
        currentCondition = Condition.CHECKING_RESOURCES;
        System.out.println(currentCondition.status);

        if ((typeOfCoffee == 1 && water < 250) || (typeOfCoffee == 2 && water < 350) || (typeOfCoffee == 3 && water < 200)){
            System.out.println("Sorry, not enough water!");
        } else if ((typeOfCoffee == 2 && milk < 75) || (typeOfCoffee == 3 && milk < 200)) {
            System.out.println("Sorry, not enough milk!");
        } else if ((typeOfCoffee == 1 && beans < 16) || (typeOfCoffee == 2 && beans < 16) || (typeOfCoffee == 3 && beans < 12)){
            System.out.println("Sorry, not enough beans!");
        } else if (disposalCups == 0) {
            System.out.println("Sorry, not enough disposable cups");
        } else {
            System.out.println("I have enough resources, making you a coffee!");
            currentCondition = Condition.MAKING_COFFEE;
            System.out.println(currentCondition.status);
            System.out.println("Coffee is ready");
        }
    }

    enum Condition {
        WORKING("Working..."),
        NOT_WORKING("Coffee Machine is off"),
        MAKING_COFFEE("Making coffee..."),
        FILLING_OUT("Filling out..."),
        TAKING_OUT_MONEY("Taking out money..."),
        SHOWING_REMAINING_RESOURCES("Showing resources..."),
        CHECKING_RESOURCES("Checking resources...");

        private final String status;

        Condition(String status) {
            this.status = status;
        }
    }

    enum Action {
        BUY,
        FILL,
        TAKE,
        REMAINING,
        EXIT
    }
}
