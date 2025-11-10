package uz.umidovalimardon.lesson1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.umidovalimardon.lesson1.db.DB;
import uz.umidovalimardon.lesson1.model.User;

import java.io.IOException;

@WebServlet(name = "Register", value = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String repassword = req.getParameter("repassword");

        System.out.println("======== REGISTER ========");
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);

        if (!password.equals(repassword)) {
            System.out.println("❌ Error: Passwords don't match!");
            System.out.println("==========================");
            resp.sendRedirect("/register?error=password");
            return;
        }

        // Check for duplicate username or email
        for (User existingUser : DB.users) {
            if (existingUser.getUsername().equals(username)) {
                System.out.println("❌ Error: Username already exists!");
                System.out.println("==========================");
                resp.sendRedirect("/register?error=username");
                return;
            }
            if (existingUser.getEmail().equals(email)) {
                System.out.println("❌ Error: Email already exists!");
                System.out.println("==========================");
                resp.sendRedirect("/register?error=email");
                return;
            }
        }

        User user = new User(firstName, lastName, username, email, password);
        DB.users.add(user);

        System.out.println("✅ User registered successfully!");
        System.out.println("==========================");

        resp.sendRedirect("/login");
    }
}