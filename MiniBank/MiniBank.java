import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class MiniBank {

    static Scanner sc = new Scanner(System.in);

    static HashMap<String, Account> accounts = new HashMap<>();

    static final String DATA_FILE = "accounts.dat";
    static final String LOG_FILE = "transactions.log";

    // Bank Information
    record BankInfo(String name, String branch) {}

    // Menu Options
    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        STATEMENT,
        LIST,
        REPORT,
        BATCH_DEPOSIT,
        EXIT
    }

    // Customer Class
    static class Customer implements Serializable {

        private static final long serialVersionUID = 1L;

        static int count = 100;

        String name;
        String email;
        String mobile;
        String customerId;

        Customer(String name, String email, String mobile) {

            this.name = name;
            this.email = email;
            this.mobile = mobile;

            customerId = "CUST" + (++count);
        }

        public String toString() {
            return name + " (" + customerId + ")";
        }
    }

    // Abstract Account Class
    static abstract class Account
            implements Serializable, Comparable<Account> {

        private static final long serialVersionUID = 1L;

        static int count = 1000;

        final String accountNumber;

        Customer customer;

        protected long balance;

        boolean active = true;

        Account(Customer customer, long balance) {

            accountNumber = "AC" + (++count);

            this.customer = customer;
            this.balance = balance;
        }

        public synchronized void deposit(long amount)
                throws Exception {

            if (amount <= 0) {
                throw new Exception("Invalid deposit amount");
            }

            balance += amount;

            log("DEPOSIT", accountNumber, amount);
        }

        public synchronized void withdraw(long amount)
                throws Exception {

            if (amount <= 0) {
                throw new Exception("Invalid withdrawal amount");
            }

            if (!canWithdraw(amount)) {
                throw new Exception("Insufficient balance");
            }

            balance -= amount;

            log("WITHDRAW", accountNumber, amount);
        }

        abstract double interestRate();

        abstract boolean canWithdraw(long amount);

        public long getBalance() {
            return balance;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public int compareTo(Account a) {
            return accountNumber.compareTo(a.accountNumber);
        }

        public boolean equals(Object obj) {

            if (this == obj) {
                return true;
            }

            if (!(obj instanceof Account)) {
                return false;
            }

            Account other = (Account) obj;

            return accountNumber.equals(other.accountNumber);
        }

        public int hashCode() {
            return accountNumber.hashCode();
        }

        public String toString() {

            return accountNumber
                    + " | " + customer
                    + " | Balance: Rs." + balance;
        }
    }

    // Savings Account
    static class SavingsAccount extends Account {

        private static final long serialVersionUID = 1L;

        long minimumBalance = 0;

        SavingsAccount(Customer c, long amount) {
            super(c, amount);
        }

        double interestRate() {
            return 4.0;
        }

        boolean canWithdraw(long amount) {
            return balance - amount >= minimumBalance;
        }
    }

    // Current Account
    static class CurrentAccount extends Account {

        private static final long serialVersionUID = 1L;

        long overdraftLimit = 5000;

        CurrentAccount(Customer c, long amount) {
            super(c, amount);
        }

        double interestRate() {
            return 0.0;
        }

        boolean canWithdraw(long amount) {
            return balance - amount >= -overdraftLimit;
        }
    }

    // Fixed Deposit Account
    static class FixedDepositAccount extends Account {

        private static final long serialVersionUID = 1L;

        FixedDepositAccount(Customer c, long amount) {
            super(c, amount);
        }

        double interestRate() {
            return 7.0;
        }

        boolean canWithdraw(long amount) {
            return false;
        }
    }

    // Generic Repository
    static class Repository<T> {

        HashMap<String, T> data = new HashMap<>();

        T save(String id, T value) {
            data.put(id, value);
            return value;
        }

        T findById(String id) {
            return data.get(id);
        }

        ArrayList<T> findAll() {
            return new ArrayList<>(data.values());
        }

        boolean delete(String id) {
            return data.remove(id) != null;
        }
    }

    // Transaction Processor
    static class TransactionProcessor {

        ExecutorService pool =
                Executors.newFixedThreadPool(4);

        void submit(Runnable task) {
            pool.execute(task);
        }

        void stop() throws InterruptedException {

            pool.shutdown();

            if (!pool.awaitTermination(1, TimeUnit.MINUTES)) {
                pool.shutdownNow();
            }
        }
    }

    // Producer Consumer Buffer
    static class TransactionBuffer {

        Queue<String> queue = new LinkedList<>();

        synchronized void produce(String item)
                throws InterruptedException {

            while (queue.size() == 5) {
                wait();
            }

            queue.add(item);

            notifyAll();
        }

        synchronized String consume()
                throws InterruptedException {

            while (queue.isEmpty()) {
                wait();
            }

            String item = queue.remove();

            notifyAll();

            return item;
        }
    }

    // Transaction Logging
    static synchronized void log(
            String type, String id, long amount) {

        String line = type + " " + id + " " + amount;

        try {

            Files.writeString(
                    Paths.get(LOG_FILE),
                    line + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

        } catch (IOException e) {

            System.out.println(
                    "Log error: " + e.getMessage()
            );
        }
    }

    // Save Accounts
    static void saveAccounts() {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(DATA_FILE))) {

            out.writeObject(
                    new ArrayList<>(accounts.values())
            );

            System.out.println("Accounts saved successfully.");

        } catch (IOException e) {

            System.out.println(
                    "Save error: " + e.getMessage()
            );
        }
    }

    // Load Accounts
    @SuppressWarnings("unchecked")
    static void loadAccounts() {

        File file = new File(DATA_FILE);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(file))) {

            ArrayList<Account> list =
                    (ArrayList<Account>) in.readObject();

            for (Account a : list) {

                accounts.put(a.accountNumber, a);

                try {
                    int number = Integer.parseInt(
                            a.accountNumber.substring(2)
                    );

                    Account.count = Math.max(
                            Account.count, number
                    );

                    int customerNumber = Integer.parseInt(
                            a.customer.customerId.substring(4)
                    );

                    Customer.count = Math.max(
                            Customer.count, customerNumber
                    );

                } catch (Exception ignored) {
                    // Ignore invalid old IDs
                }
            }

            System.out.println("Accounts loaded successfully.");

        } catch (Exception e) {

            System.out.println(
                    "Load error: " + e.getMessage()
            );
        }
    }

    // Open Account
    static void openAccount() {

        try {

            System.out.print("Customer name: ");
            String name = sc.nextLine();

            if (name.trim().isEmpty()) {
                throw new Exception("Name cannot be empty");
            }

            System.out.print("Email: ");
            String email = sc.nextLine();

            if (!Pattern.matches(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                    email)) {

                throw new Exception("Invalid email");
            }

            System.out.print("Mobile: ");
            String mobile = sc.nextLine();

            if (!Pattern.matches("[6-9][0-9]{9}", mobile)) {
                throw new Exception("Invalid mobile number");
            }

            System.out.println("\nSelect Account Type:");

            System.out.println("1. Savings Account");
            System.out.println("2. Current Account");
            System.out.println("3. Fixed Deposit Account");

            System.out.print("Enter choice: ");

            int type = Integer.parseInt(sc.nextLine());

            System.out.print("Opening balance: ");

            long amount = Long.parseLong(sc.nextLine());

            if (amount < 0) {
                throw new Exception("Invalid opening balance");
            }

            Customer c = new Customer(
                    name, email, mobile
            );

            Account a;

            switch (type) {

                case 1:
                    a = new SavingsAccount(c, amount);
                    break;

                case 2:
                    a = new CurrentAccount(c, amount);
                    break;

                case 3:
                    a = new FixedDepositAccount(c, amount);
                    break;

                default:
                    throw new Exception("Invalid account type");
            }

            accounts.put(a.accountNumber, a);

            System.out.println("\nAccount opened successfully!");

            System.out.println(a);

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    // Find Account
    static Account findAccount() {

        System.out.print("Enter account number: ");

        String id = sc.nextLine();

        return accounts.get(id);
    }

    // Deposit
    static void deposit() {

        try {

            Account a = findAccount();

            if (a == null) {
                throw new Exception("Account not found");
            }

            System.out.print("Enter amount: ");

            long amount = Long.parseLong(sc.nextLine());

            a.deposit(amount);

            System.out.println("Deposit successful!");

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    // Withdraw
    static void withdraw() {

        try {

            Account a = findAccount();

            if (a == null) {
                throw new Exception("Account not found");
            }

            System.out.print("Enter amount: ");

            long amount = Long.parseLong(sc.nextLine());

            a.withdraw(amount);

            System.out.println("Withdrawal successful!");

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    // Transfer
    static void transfer() {

        try {

            System.out.print("From account: ");

            Account from = accounts.get(sc.nextLine());

            System.out.print("To account: ");

            Account to = accounts.get(sc.nextLine());

            if (from == null || to == null) {
                throw new Exception("Account not found");
            }

            if (from == to) {
                throw new Exception(
                        "Cannot transfer to same account"
                );
            }

            System.out.print("Enter amount: ");

            long amount = Long.parseLong(sc.nextLine());

            if (amount <= 0) {
                throw new Exception("Invalid amount");
            }

            // Lock accounts in consistent order
            Account first;
            Account second;

            if (from.accountNumber.compareTo(
                    to.accountNumber) < 0) {

                first = from;
                second = to;

            } else {

                first = to;
                second = from;
            }

            synchronized (first) {

                synchronized (second) {

                    from.withdraw(amount);

                    to.deposit(amount);
                }
            }

            System.out.println("Transfer successful!");

        } catch (Exception e) {

            System.out.println(
                    "Transfer failed: " + e.getMessage()
            );
        }
    }

    // Account Statement
    static void statement() {

        Account a = findAccount();

        if (a == null) {

            System.out.println("Account not found");

            return;
        }

        System.out.println("\n------ ACCOUNT STATEMENT ------");

        System.out.println("Account: " + a.accountNumber);

        System.out.println("Customer: " + a.customer);

        System.out.println("Balance: Rs." + a.getBalance());

        System.out.println(
                "Interest Rate: " + a.interestRate() + "%"
        );

        System.out.println("Date: " + LocalDateTime.now());

        System.out.println("--------------------------------");
    }

    // List Accounts
    static void listAccounts() {

        ArrayList<Account> list =
                new ArrayList<>(accounts.values());

        list.sort(
                Comparator.comparingLong(Account::getBalance)
                        .reversed()
        );

        if (list.isEmpty()) {

            System.out.println("No accounts found");

            return;
        }

        System.out.println("\n------ ALL ACCOUNTS ------");

        for (Account a : list) {
            System.out.println(a);
        }
    }

    // Daily Report
    static void report() {

        long deposits = 0;
        long withdrawals = 0;

        try {

            if (!Files.exists(Paths.get(LOG_FILE))) {

                System.out.println("No transaction history");

                return;
            }

            for (String line :
                    Files.readAllLines(Paths.get(LOG_FILE))) {

                String[] parts = line.split(" ");

                if (parts.length != 3) {
                    continue;
                }

                long amount = Long.parseLong(parts[2]);

                if (parts[0].equals("DEPOSIT")) {
                    deposits += amount;
                }

                if (parts[0].equals("WITHDRAW")) {
                    withdrawals += amount;
                }
            }

            String result =
                    "Total Deposits: Rs." + deposits +
                    "\nTotal Withdrawals: Rs." + withdrawals +
                    "\nNet Change: Rs." + (deposits - withdrawals);

            Files.writeString(
                    Paths.get("daily-report.txt"),
                    result
            );

            System.out.println("\n------ DAILY REPORT ------");

            System.out.println(result);

        } catch (Exception e) {

            System.out.println(
                    "Report error: " + e.getMessage()
            );
        }
    }

    // Batch Deposit
    static void batchDeposit() {

        if (accounts.isEmpty()) {

            System.out.println("No accounts available");

            return;
        }

        TransactionProcessor processor =
                new TransactionProcessor();

        for (Account a : accounts.values()) {

            processor.submit(() -> {

                try {

                    a.deposit(10);

                    System.out.println(
                            "Rs.10 deposited into "
                                    + a.accountNumber
                    );

                } catch (Exception e) {

                    System.out.println(e.getMessage());
                }
            });
        }

        try {

            processor.stop();

            System.out.println("Batch deposit completed!");

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println("Batch interrupted");
        }
    }

    // Display Menu
    static void showMenu() {

        System.out.println("\n========== MINIBANK ==========");

        System.out.println("1. Open Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Account Statement");
        System.out.println("6. List Accounts");
        System.out.println("7. Daily Report");
        System.out.println("8. Batch Deposit");
        System.out.println("9. Exit");

        System.out.print("Enter choice: ");
    }

    // Main Method
    public static void main(String[] args) {

        BankInfo bank = new BankInfo(
                "MiniBank", "Ahmedabad"
        );

        System.out.println("================================");

        System.out.println(
                bank.name() + " - " + bank.branch()
        );

        System.out.println("Welcome to MiniBank");

        System.out.println("================================");

        loadAccounts();

        boolean running = true;

        while (running) {

            showMenu();

            try {

                int choice = Integer.parseInt(
                        sc.nextLine()
                );

                MenuOption option = switch (choice) {

                    case 1 -> MenuOption.OPEN_ACCOUNT;

                    case 2 -> MenuOption.DEPOSIT;

                    case 3 -> MenuOption.WITHDRAW;

                    case 4 -> MenuOption.TRANSFER;

                    case 5 -> MenuOption.STATEMENT;

                    case 6 -> MenuOption.LIST;

                    case 7 -> MenuOption.REPORT;

                    case 8 -> MenuOption.BATCH_DEPOSIT;

                    case 9 -> MenuOption.EXIT;

                    default -> null;
                };

                if (option == null) {

                    System.out.println("Invalid choice");

                    continue;
                }

                switch (option) {

                    case OPEN_ACCOUNT -> openAccount();

                    case DEPOSIT -> deposit();

                    case WITHDRAW -> withdraw();

                    case TRANSFER -> transfer();

                    case STATEMENT -> statement();

                    case LIST -> listAccounts();

                    case REPORT -> report();

                    case BATCH_DEPOSIT -> batchDeposit();

                    case EXIT -> {

                        saveAccounts();

                        System.out.println(
                                "Thank you for using MiniBank!"
                        );

                        running = false;
                    }
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number"
                );
            }
        }

        sc.close();
    }
}