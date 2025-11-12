package ec.edu.espe.petshop.controller;

import ec.edu.espe.petshop.model.Role;
import ec.edu.espe.petshop.model.User;
import java.util.HashMap;
import java.util.Map;

public class LoginSystem {
    private final Map<String, User> users;

    public LoginSystem() {
        users = new HashMap<>();
        // credenciales: admin (gerente) y employee (empleado)
        users.put("admin", new User("admin", "1234", Role.MANAGER));
        users.put("employee", new User("employee", "0000", Role.EMPLOYEE));
    }

    public User login(String username, String password) {
        if (username == null || password == null) return null;
        User u = users.get(username.trim());
        if (u != null && password.equals(u.getPassword())) return u;
        return null;
    }
}
