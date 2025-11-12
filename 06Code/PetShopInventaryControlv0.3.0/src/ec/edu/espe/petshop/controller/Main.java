package ec.edu.espe.petshop.controller;

import ec.edu.espe.petshop.model.Profile;
import ec.edu.espe.petshop.utils.JsonManager;
import ec.edu.espe.petshop.model.InvalidProductDataException;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginSystem login = new LoginSystem();
        JsonManager json = new JsonManager("products.json","movements.json");
        ProductController pc = new ProductController(json);

        System.out.println("=== PET SHOP SYSTEM ===");
        Profile u = null;
        while (u == null) {
            System.out.print("Usuario: ");
            String user = sc.nextLine();
            System.out.print("Contraseña: ");
            String pass = sc.nextLine();
            u = login.login(user, pass);
            if (u == null) System.out.println("Credenciales inválidas.");
        }

        System.out.println("Bienvenido " + u.getUsername() + " | Rol: " + u.getRole());
        boolean exit = false;
        while (!exit) {
            showMenu(u.getRole());
            System.out.print("Seleccione opción: ");
            String opt = sc.nextLine();
            try {
                switch (opt) {
                    case "1":
                        pc.showInventorySummary();
                        break;
                    case "2":
                        if (u.getRole().equalsIgnoreCase("Manager")) {
                            pc.addProductInteractive(sc);
                        } else {
                            System.out.println("Acceso denegado. Solo gerente.");
                        }
                        break;
                    case "3":
                        System.out.print("ID producto a vender: ");
                        String idv = sc.nextLine().trim().toUpperCase();
                        System.out.print("Cantidad: ");
                        int qv = Integer.parseInt(sc.nextLine());
                        if (pc.registerSale(idv, qv))
                            System.out.println("Venta registrada.");
                        else
                            System.out.println("Fallo: ID inválido o stock insuficiente.");
                        break;
                    case "4":
                        System.out.print("ID producto a comprar: ");
                        String idc = sc.nextLine().trim().toUpperCase();
                        System.out.print("Cantidad: ");
                        int qc = Integer.parseInt(sc.nextLine());
                        if (pc.registerPurchase(idc, qc))
                            System.out.println("Compra registrada.");
                        else
                            System.out.println("Fallo: ID inválido.");
                        break;
                    case "5":
                        System.out.print("Buscar (texto): ");
                        String q = sc.nextLine();
                        List<?> res = pc.search(q);
                        if (res.isEmpty())
                            System.out.println("No se encontraron resultados.");
                        else
                            res.forEach(System.out::println);
                        break;
                    case "6":
                        pc.reportTopSold(5);
                        break;
                    case "7":
                        pc.reportMovementsLastDays(30);
                        break;
                    case "8":
                        pc.checkAlerts();
                        break;
                    case "9":
                        json.saveProducts(pc.allProducts());
                        System.out.println("Guardado. Saliendo...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (InvalidProductDataException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Entrada numérica inválida.");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
        sc.close();
    }

    private static void showMenu(String role) {
        System.out.println("\n--- MENÚ ---");
        System.out.println("1. Panel de control (resumen)");
        if (role.equalsIgnoreCase("Manager"))
            System.out.println("2. Agregar producto (Gerente)");
        else
            System.out.println("2. (Requiere gerente)");
        System.out.println("3. Registrar venta");
        System.out.println("4. Registrar compra");
        System.out.println("5. Búsqueda avanzada");
        System.out.println("6. Top vendidos");
        System.out.println("7. Movimientos (últimos 30 días)");
        System.out.println("8. Alertas de bajo stock");
        System.out.println("9. Guardar y salir");
    }
}

