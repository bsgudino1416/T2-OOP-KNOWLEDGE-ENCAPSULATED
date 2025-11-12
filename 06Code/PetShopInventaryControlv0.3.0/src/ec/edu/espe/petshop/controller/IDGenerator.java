package ec.edu.espe.petshop.controller;
//
import ec.edu.espe.petshop.model.Product;
import java.util.List;

public class IDGenerator {

    public static String generatePrefix(String category, String animal, String size) {
        String prefix = "";
        category = category == null ? "" : category.toUpperCase();
        animal = animal == null ? "" : animal.toLowerCase();
        size = size == null ? "" : size.toLowerCase();

        if ("FOOD".equals(category)) {
            switch (animal) {
                case "perro": prefix = "CP"; break;
                case "gato": prefix = "CG"; break;
                case "gallina": prefix = "CGLL"; break;
                case "caballo": prefix = "CCB"; break;
                case "conejo": prefix = "CCJ"; break;
                case "cerdo": prefix = "CCD"; break;
                case "vaca": prefix = "CVC"; break;
                default: prefix = "CXX";
            }
        } else if ("MEDICINE".equals(category)) {
            switch (animal) {
                case "perro": prefix = "MP"; break;
                case "gato": prefix = "MG"; break;
                case "gallina": prefix = "MGLL"; break;
                case "caballo": prefix = "MCB"; break;
                case "conejo": prefix = "MCJ"; break;
                case "cerdo": prefix = "MCD"; break;
                case "vaca": prefix = "MVC"; break;
                default: prefix = "MXX";
            }
        } else {
            String cat = category.length() >= 1 ? category.substring(0,1).toUpperCase() : "X";
            String a;
            switch (animal) {
                case "perro": a="P"; break;
                case "gato": a="G"; break;
                case "gallina": a="GLL"; break;
                case "caballo": a="CB"; break;
                case "conejo": a="CJ"; break;
                case "cerdo": a="CD"; break;
                case "vaca": a="VC"; break;
                default: a="X";
            }
            prefix = cat + a;
        }
        prefix += size.equalsIgnoreCase("pequeño") ? "P" : "G";
        return prefix;
    }

    public static String nextId(List<Product> products, String prefix) {
        int max = 0;
        for (Product p : products) {
            String pid = p.getId();
            if (pid != null && pid.startsWith(prefix)) {
                String num = pid.replaceAll("\\D+", "");
                if (!num.isEmpty()) {
                    try {
                        int n = Integer.parseInt(num);
                        if (n > max) max = n;
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
        return prefix + String.format("%02d", max + 1);
    }
}

