package uz.pdp.jakarta_ee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uz.pdp.jakarta_ee.model.User;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Dashboard", value = "/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            System.out.println("\n❌ Unauthorized access attempt to dashboard!");
            resp.sendRedirect("/login?error=unauthorized");
            return;
        }

        User user = (User) session.getAttribute("user");

        System.out.println("\n========== DASHBOARD ACCESS ==========");
        System.out.println("User: " + user.getUsername());
        System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("======================================");

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        writer.write("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Dashboard</title>
                    <style>
                        * {
                            margin: 0;
                            padding: 0;
                            box-sizing: border-box;
                        }
                        body {
                            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                            min-height: 100vh;
                            padding: 40px 20px;
                        }
                        .container {
                            max-width: 700px;
                            margin: 0 auto;
                            background: white;
                            padding: 40px;
                            border-radius: 20px;
                            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
                        }
                        h1 {
                            color: #333;
                            text-align: center;
                            margin-bottom: 10px;
                            font-size: 32px;
                        }
                        .welcome {
                            text-align: center;
                            color: #667eea;
                            font-size: 18px;
                            margin-bottom: 30px;
                        }
                        .user-info {
                            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                            padding: 30px;
                            border-radius: 15px;
                            margin: 20px 0;
                        }
                        .user-info h2 {
                            color: #333;
                            margin-bottom: 20px;
                            font-size: 24px;
                        }
                        .info-item {
                            display: flex;
                            padding: 12px 0;
                            border-bottom: 1px solid rgba(0,0,0,0.1);
                        }
                        .info-item:last-child {
                            border-bottom: none;
                        }
                        .info-label {
                            font-weight: 600;
                            color: #555;
                            width: 150px;
                        }
                        .info-value {
                            color: #333;
                            flex: 1;
                        }
                        .btn {
                            display: block;
                            width: 100%%;
                            padding: 15px;
                            border: none;
                            border-radius: 10px;
                            font-size: 16px;
                            font-weight: 600;
                            cursor: pointer;
                            text-align: center;
                            text-decoration: none;
                            transition: transform 0.2s;
                            margin-top: 15px;
                        }
                        .btn:hover {
                            transform: translateY(-2px);
                        }
                        .btn-calc {
                            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
                            color: white;
                        }
                        .btn-logout {
                            background: linear-gradient(135deg, #eb3349 0%, #f45c43 100%);
                            color: white;
                        }
                    </style>
                </head>
                <body>
                    <div class='container'>
                        <h1>👋 Dashboard</h1>
                        <div class='welcome'>Welcome back, %s!</div>
                        
                        <div class='user-info'>
                            <h2>📋 User Information</h2>
                            <div class='info-item'>
                                <span class='info-label'>User ID:</span>
                                <span class='info-value'>%s</span>
                            </div>
                            <div class='info-item'>
                                <span class='info-label'>First Name:</span>
                                <span class='info-value'>%s</span>
                            </div>
                            <div class='info-item'>
                                <span class='info-label'>Last Name:</span>
                                <span class='info-value'>%s</span>
                            </div>
                            <div class='info-item'>
                                <span class='info-label'>Username:</span>
                                <span class='info-value'>%s</span>
                            </div>
                            <div class='info-item'>
                                <span class='info-label'>Email:</span>
                                <span class='info-value'>%s</span>
                            </div>
                        </div>
                        
                        <a href='/calc' class='btn btn-calc'>🧮 Go to Calculator</a>
                        <a href='/logout' class='btn btn-logout'>🚪 Logout</a>
                    </div>
                </body>
                </html>
                """.formatted(
                user.getFirstName(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail()
        ));
    }
}