package uz.pdp.jakarta_ee.db;

import uz.pdp.jakarta_ee.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDB {
    private static List<User> users = new ArrayList<>();
    private static Long idCounter = 1L;

    public static boolean registerUser(User user) {
        if (findByUsername(user.getUsername()).isPresent()) {
            System.out.println("❌ Registration failed: Username '" + user.getUsername() + "' already exists!");
            return false;
        }

        if (findByEmail(user.getEmail()).isPresent()) {
            System.out.println("❌ Registration failed: Email '" + user.getEmail() + "' already exists!");
            return false;
        }

        user.setId(idCounter++);
        users.add(user);

        System.out.println("✅ User registered successfully:");
        System.out.println("   ID: " + user.getId());
        System.out.println("   Name: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("   Username: " + user.getUsername());
        System.out.println("   Email: " + user.getEmail());
        System.out.println("-----------------------------------");

        return true;
    }

    public static Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public static Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    public static Optional<User> login(String username, String password) {
        Optional<User> userOpt = users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            System.out.println("✅ Login successful:");
            System.out.println("   Username: " + user.getUsername());
            System.out.println("   Name: " + user.getFirstName() + " " + user.getLastName());
            System.out.println("-----------------------------------");
        } else {
            System.out.println("❌ Login failed: Invalid username or password!");
            System.out.println("-----------------------------------");
        }

        return userOpt;
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}