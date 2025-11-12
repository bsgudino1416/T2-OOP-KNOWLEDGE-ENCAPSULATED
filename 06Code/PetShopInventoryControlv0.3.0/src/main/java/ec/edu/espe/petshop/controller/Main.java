package ec.edu.espe.petshop.controller;

import ec.edu.espe.petshop.model.InvalidProductDataException;
import ec.edu.espe.petshop.model.Role;
import ec.edu.espe.petshop.model.User;
import ec.edu.espe.petshop.utils.CsvManager;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String PRODUCTS_FILE = "petshop.csv";
    private static final String MOVEMENTS_FILE = "movements.csv";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginSystem login = new LoginSystem();
        CsvManager csv = new CsvManager(PRODUCTS_FILE, MOVEMENTS_FILE);
        ProductManager pm = new ProductManager(csv);
        ReportManager rm = new ReportManager(pm);

        System.out.println("=== PET SHOP INVENTORY SYSTEM ===");
        User u = null;
        while (u == null) {
            System.out.print("Usuario: ");
            String user = sc.nextLine();
            System.out.print("Contraseña: ");
            String pass = sc.nextLine();
            u = login.login(user, pass);
            if (u == null) System.out.println("Credenciales inválidas. Intente de nuevo.");
        }

        System.out.println("Bienvenido " + u.getUsername() + " | Rol: " + u.getRole());
        boolean exit = false;
        while (!exit) {
            showMenu(u.getRole());
            System.out.print("Seleccione una opción: ");
            String opt = sc.nextLine();
            try {
                switch (opt) {
                    case "1":
                        pm.showInventory();
                        break;
                    case "2":
                        if (u.getRole() == Role.MANAGER) pm.addProductInteractive(sc, u.getRole());
                        else System.out.println("Acceso restringido: solo gerente.");
                        break;
                    case "3":
                        System.out.print("ID del producto a vender: ");
                        String sellId = sc.nextLine().trim().toUpperCase();
                        System.out.print("Cantidad: ");
                        int q = parsePositiveInt(sc);
                        boolean sold = pm.registerSale(sellId, q);
                        if (sold) System.out.println("Venta registrada.");
                        else System.out.println("Venta fallida (ID inválido o stock insuficiente).");
                        break;
                    case "4":
                        System.out.print("ID del producto a ingresar (compra): ");
                        String pid = sc.nextLine().trim().toUpperCase();
                        System.out.print("Cantidad: ");
                        int qty = parsePositiveInt(sc);
                        boolean ok = pm.registerPurchase(pid, qty);
                        if (ok) System.out.println("Compra registrada.");
                        else System.out.println("Error registrando compra (ID inválido).");
                        break;
                    case "5":
                        System.out.print("Buscar texto (nombre/ID/categoría/marca): ");
                        String qtext = sc.nextLine();
                        List<?> results = pm.searchByText(qtext);
                        if (results.isEmpty()) System.out.println("No se encontraron resultados.");
                        else results.forEach(System.out::println);
                        break;
                    case "6":
                        rm.showInventorySummary();
                        break;
                    case "7":
                        rm.showTopSelling(5);
                        break;
                    case "8":
                        rm.showMovementsLastDays(30);
                        break;
                    case "9":
                        pm.saveAll();
                        System.out.println("Guardado y saliendo...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (InvalidProductDataException e) {
                System.out.println("Error de validación: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Entrada numérica inválida.");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
        sc.close();
    }

    private static void showMenu(Role role) {
        System.out.println("\n--- MENÚ ---");
        System.out.println("1. Mostrar inventario");
        if (role == Role.MANAGER) System.out.println("2. Agregar producto (Gerente)");
        else System.out.println("2. Agregar producto (Requiere gerente)");
        System.out.println("3. Registrar venta");
        System.out.println("4. Registrar compra");
        System.out.println("5. Búsqueda avanzada");
        System.out.println("6. Panel de control (resumen)");
        System.out.println("7. Top vendidos");
        System.out.println("8. Movimientos (últimos 30 días)");
        System.out.println("9. Guardar y salir");
    }

    private static int parsePositiveInt(Scanner sc) {
        while (true) {
            String line = sc.nextLine();
            try {
                int v = Integer.parseInt(line.trim());
                if (v <= 0) {
                    System.out.print("Ingrese un número entero positivo: ");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Ingrese un número entero positivo: ");
            }
        }
    }
}
