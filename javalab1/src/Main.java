import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //        Serialize Admin
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("client.dat"))) {
            ArrayList<Account> accounts = new ArrayList<>();
            ArrayList<CreditCard> creditCards = new ArrayList<>();
            accounts.add(new Account(100));
            accounts.add(new Account(200));
            creditCards.add(new CreditCard(1000));
            creditCards.add(new CreditCard(2000));
            Client client = new Client(accounts, creditCards);
            oos.writeObject(client);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        Client client = null;

        //      Deserialize Client
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("client.dat"))) {
            client = (Client) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        int userInput;
        while (true) {
            boolean isOpen = true;
            System.out.println("Выберете кем вы являетесь: ");
            System.out.println("1. Админ");
            System.out.println("2. Клиент");
            System.out.println("3. Выйти из программы");
            userInput = getIntInput();
            if (userInput == 1) {
                while (isOpen) {
                    System.out.println("Вы вошли как админ");
                    System.out.println("Выберете что будете делать дальше");
                    System.out.println("1. Заблокировать карту за превышение кредита");
                    System.out.println("2. Выйти");
                    userInput = getIntInput();
                    switch (userInput) {
                        case 1 -> {
                            int i = 1;
                            for (var creditCard : client.getCreditCards()) {
                                if (creditCard.getMoney() < 0) {
                                    creditCard.setBlocked();
                                    System.out.println(i + " " + creditCard);
                                    System.out.println("Карта была заблокирована");
                                } else {
                                    System.out.println(i + " " + creditCard);
                                    System.out.println("Клиент не превысил кредит");
                                }
                                i++;
                            }
                        }
                        case 2 -> isOpen = false;
                        default -> System.out.println("Попробуйте ввести еще раз!");
                    }
                }
            } else if (userInput == 2) {
                System.out.println("Вы вошли как клиент");
                while (isOpen) {
                    System.out.println("1. Просмотреть счета");
                    System.out.println("2. Просмотреть кредитные карты");
                    System.out.println("3. Оплатить заказ");
                    System.out.println("4. Заблокировать кредитную карту");
                    System.out.println("5. Аннулировать счет");
                    System.out.println("6. Выйти");
                    userInput = getIntInput();
                    switch (userInput) {
                        case 1 -> client.showAccounts();
                        case 2 -> client.showCreditCards();
                        case 3 -> {
                            System.out.println("Введите сумму которую хотите заплатить");
                            int sum = getIntInput();
                            System.out.println("Выбирите способ оплаты: ");
                            System.out.println("1. Счет");
                            System.out.println("2. Кредитная карта");
                            int userInput1 = getIntInput();
                            if (userInput1 == 1) {
                                client.showAccounts();
                                int userInput2 = getIntInput();
                                try {
                                    if (userInput2 > client.getAccounts().size())
                                        throw new MyException("Счета с таким номером не существует");
                                } catch (MyException m) {
                                    System.out.println(m.getMessage());
                                    System.out.println("Попробуйте еще раз!");
                                    break;
                                }
                                if (client.getAccounts().get(userInput2 - 1).getMoney() < sum
                                        || client.getAccounts().get(userInput2 - 1).isCanceled()) {
                                    System.out.println("Недостаточно средств или ваш счёт заблокирован!");
                                } else client.getAccounts().get(userInput2 - 1).pay(sum);
                            } else if (userInput1 == 2) {
                                client.showCreditCards();
                                int userInput3 = getIntInput();
                                try {
                                    if (userInput3 > client.getAccounts().size())
                                        throw new MyException("Карты с таким номером не существует");
                                } catch (MyException m) {
                                    System.out.println(m.getMessage());
                                    System.out.println("Попробуйте еще раз!");
                                    break;
                                }
                                if (client.getCreditCards().get(userInput3 - 1).isBlocked())
                                    System.out.println("Карта заблокирована");
                                else client.getCreditCards().get(userInput3 - 1).pay(sum);
                            }
                        }
                        case 4 -> {
                            client.showCreditCards();
                            int userInput4 = getIntInput();
                            try {
                                if (userInput4 > client.getAccounts().size())
                                    throw new MyException("Счета с таким номером не существует");
                            } catch (MyException m) {
                                System.out.println(m.getMessage());
                                System.out.println("Попробуйте еще раз!");
                                break;
                            }
                            client.getCreditCards().get(userInput4 - 1).setBlocked();
                        }
                        case 5 -> {
                            client.showAccounts();
                            int userInput5 = getIntInput();
                            try {
                                if (userInput5 > client.getAccounts().size())
                                    throw new MyException("Счета с таким номером не существует");
                            } catch (MyException m) {
                                System.out.println(m.getMessage());
                                System.out.println("Попробуйте еще раз!");
                                break;
                            }
                            client.getAccounts().get(userInput5 - 1).setCanceled();
                        }
                        case 6 -> isOpen = false;
                        default -> System.out.println("Попробуйте ввести еще раз!");
                    }
                }
            } else if (userInput == 3) {
                return;
            } else {
                System.out.println("Попробуйте ввести еще раз!");
            }
        }
    }

    public static int getIntInput() {
        Scanner in = new Scanner(System.in);
        int userInput = 0;
        boolean inputError = true;
        while (inputError) {
            try {
                String userInput1 = in.nextLine();
                userInput = Integer.parseInt(userInput1);
                inputError = (userInput < 1);
            } catch (NumberFormatException e) {
                inputError = true;
            }
            if (inputError)
                System.out.println("Ошибка ввода попробуйте еще раз!");
        }
        return userInput;
    }
}

class Client implements Serializable {
    private ArrayList<Account> accounts;
    private ArrayList<CreditCard> creditCards;


    public Client(ArrayList<Account> accounts, ArrayList<CreditCard> creditCards) {
        this.accounts = accounts;
        this.creditCards = creditCards;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public void setCreditCards(ArrayList<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public void showAccounts() {
        int i = 1;
        System.out.println("Выберите счет: ");
        for (var account : this.getAccounts()) {
            System.out.print(i + " ");
            System.out.println(account.toString());
            i++;
        }
    }

    public void showCreditCards() {
        int i = 1;
        System.out.println("Выберите кредитную карту: ");
        for (var creditCard : this.getCreditCards()) {
            System.out.print(i + " ");
            System.out.println(creditCard.toString());
            i++;
        }
    }


    @Override
    public String toString() {
        return "Client{" +
                "accounts=" + accounts +
                ", creditCards=" + creditCards +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(accounts, client.accounts) && Objects.equals(creditCards, client.creditCards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accounts, creditCards);
    }
}

abstract class Payment implements Serializable {
    private int money;

    public Payment(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void pay(int money) {
        this.money -= money;
    }

    @Override
    public String
    toString() {
        return "Payment{" +
                "money=" + money +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return money == payment.money;
    }

    @Override
    public int hashCode() {
        return Objects.hash(money);
    }
}

class Account extends Payment {
    private boolean isCanceled = false;

    public Account(int money) {
        super(money);
    }

    public void setCanceled() {
        isCanceled = true;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    @Override
    public String toString() {
        return "Account{" +
                "money=" + super.getMoney() + ", " +
                "isCanceled=" + isCanceled +
                '}';
    }
}

class CreditCard extends Payment {
    private boolean isBlocked = false;

    public CreditCard(int money) {
        super(money);
    }

    public void setBlocked() {
        isBlocked = true;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "credit=" + super.getMoney() + ", " +
                "isBlocked=" + isBlocked +
                '}';
    }
}

class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
}