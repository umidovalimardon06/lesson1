package uz.pdp.jakarta_ee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uz.pdp.jakarta_ee.model.User;

import java.io.IOException;

@WebServlet(name = "Logout", value = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                System.out.println("\n========== LOGOUT ==========");
                System.out.println("User: " + user.getUsername());
                System.out.println("✅ User logged out successfully!");
                System.out.println("============================");
            }
            session.invalidate();
        }

        resp.sendRedirect("/login");
    }
}