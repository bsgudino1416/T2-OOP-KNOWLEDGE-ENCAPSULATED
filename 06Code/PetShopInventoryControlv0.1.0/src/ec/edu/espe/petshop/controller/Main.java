package ec.edu.espe.petshop.controller;

import ec.edu.espe.petshop.model.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Store store = new Store("Happy Paws");

        Order currentOrder = null;

        int option;
        do {
            System.out.println("\n=== PET SHOP SYSTEM ===");
            System.out.println("1. Add Product");
            System.out.println("2. Show Inventory");
            System.out.println("3. Create Order");
            System.out.println("4. Generate Invoice");
            System.out.println("5. Generate Report");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1 -> {
                    System.out.print("Enter product ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter product name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter price: ");
                    double price = sc.nextDouble();
                    System.out.print("Enter stock: ");
                    int stock = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter category name: ");
                    String type = sc.nextLine();
                    System.out.print("Enter category description: ");
                    String desc = sc.nextLine();
                    AnimalCategory category = new AnimalCategory(id, type, desc);
                    System.out.print("Enter supplier name: ");
                    String sname = sc.nextLine();
                    System.out.print("Enter supplier contact: ");
                    String contact = sc.nextLine();
                    System.out.print("Enter supplier phone: ");
                    String phone = sc.nextLine();
                    Supplier supplier = new Supplier(id, sname, contact, phone);
                    Product p = new Product(id, name, price, stock, supplier, category);
                    store.getInventory().addProduct(p);
                    System.out.println("✅ Product added successfully!");
                }
                case 2 -> store.getInventory().showInventory();
                case 3 -> {
                    System.out.print("Enter customer name: ");
                    String cname = sc.nextLine();
                    System.out.print("Enter customer address: ");
                    String address = sc.nextLine();
                    System.out.print("Enter customer phone: ");
                    String cphone = sc.nextLine();
                    Customer cust = new Customer(1, cname, address, cphone);
                    System.out.print("Enter employee name: ");
                    String ename = sc.nextLine();
                    System.out.print("Enter employee role: ");
                    String role = sc.nextLine();
                    Employee emp = new Employee(1, ename, role);

                    currentOrder = new Order(1, cust, emp);

                    store.getInventory().showInventory();
                    System.out.print("Enter product ID to order: ");
                    int pid = sc.nextInt();
                    System.out.print("Enter quantity: ");
                    int qty = sc.nextInt();
                    sc.nextLine();

                    Product selected = store.getInventory().findProductById(pid);
                    if (selected != null) {
                        currentOrder.addDetail(new OrderDetail(selected, qty));
                        System.out.println("✅ Order created successfully!");
                    } else {
                        System.out.println("⚠️ Product not found!");
                    }
                }
                case 4 -> {
                    if (currentOrder != null) {
                        Invoice inv = new Invoice(1, currentOrder);
                        inv.showInvoice();
                    } else {
                        System.out.println("️ No order created yet.");
                    }
                }
                case 5 -> {
                    Report rep = new Report();
                    rep.generateInventoryReport(store.getInventory());
                }
                case 6 -> System.out.println(" Exiting system...");
                default -> System.out.println("Invalid option.");
            }
        } while (option != 6);
        sc.close();
    }
}
