package uz.umidovalimardon.lesson1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uz.umidovalimardon.lesson1.db.DB;
import uz.umidovalimardon.lesson1.model.User;

import java.io.IOException;

@WebServlet(name = "Login", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        System.out.println("======== LOGIN ========");
        System.out.println("Username: " + username);

        for (User user : DB.users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);

                System.out.println("✅ Login successful!");
                System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
                System.out.println("=======================");

                resp.sendRedirect("/home.jsp");
                return;
            }
        }

        System.out.println("❌ Login failed: Invalid credentials!");
        System.out.println("=======================");
        resp.sendRedirect("/login?error=invalid");
    }
}