package ec.edu.espe.petshop.controller;
//
import ec.edu.espe.petshop.model.Profile;
import java.util.HashMap;
import java.util.Map;

public class LoginSystem {
    private final Map<String, Profile> users = new HashMap<>();

    public LoginSystem() {
        //users.put("admin", new User("admin","1234", Role.MANAGER));
        //users.put("employee", new User("employee","0000", Role.EMPLOYEE));
          users.put("admin", new Profile("admin", "1234", "Manager"));
          users.put("employee", new Profile("employee", "0000", "Employee"));

    }

    public Profile login(String username, String password) {
        if (username == null || password == null) return null;
        Profile u = users.get(username.trim());
        if (u != null && u.getPassword().equals(password)) return u;
        return null;
    }
}

