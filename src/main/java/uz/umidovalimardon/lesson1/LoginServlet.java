package uz.pdp.jakarta_ee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uz.pdp.jakarta_ee.db.UserDB;
import uz.pdp.jakarta_ee.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(name = "Login", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        String error = req.getParameter("error");
        String success = req.getParameter("success");

        String message = "";
        if (success != null && success.equals("registered")) {
            message = "<div class='success'>✅ Registration successful! Please login.</div>";
        }
        if (error != null) {
            message = switch (error) {
                case "invalid" -> "<div class='error'>❌ Invalid username or password!</div>";
                case "empty" -> "<div class='error'>❌ All fields are required!</div>";
                case "unauthorized" -> "<div class='error'>❌ Please login to continue!</div>";
                default -> "";
            };
        }

        writer.write("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>User Login</title>
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
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            padding: 20px;
                        }
                        .container {
                            background: white;
                            padding: 40px;
                            border-radius: 20px;
                            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
                            width: 100%;
                            max-width: 450px;
                        }
                        h1 {
                            color: #333;
                            text-align: center;
                            margin-bottom: 30px;
                            font-size: 32px;
                        }
                        .form-group {
                            margin-bottom: 20px;
                        }
                        label {
                            display: block;
                            margin-bottom: 8px;
                            color: #555;
                            font-weight: 600;
                            font-size: 14px;
                        }
                        input[type='text'],
                        input[type='password'] {
                            width: 100%;
                            padding: 12px 15px;
                            border: 2px solid #e0e0e0;
                            border-radius: 10px;
                            font-size: 16px;
                            transition: all 0.3s;
                        }
                        input[type='text']:focus,
                        input[type='password']:focus {
                            outline: none;
                            border-color: #667eea;
                            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
                        }
                        input[type='submit'] {
                            width: 100%;
                            padding: 15px;
                            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                            color: white;
                            border: none;
                            border-radius: 10px;
                            font-size: 18px;
                            font-weight: 600;
                            cursor: pointer;
                            transition: transform 0.2s;
                        }
                        input[type='submit']:hover {
                            transform: translateY(-2px);
                            box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
                        }
                        .error {
                            background-color: #fee;
                            color: #c33;
                            padding: 12px;
                            border-radius: 8px;
                            margin-bottom: 20px;
                            border-left: 4px solid #c33;
                        }
                        .success {
                            background-color: #efe;
                            color: #3c3;
                            padding: 12px;
                            border-radius: 8px;
                            margin-bottom: 20px;
                            border-left: 4px solid #3c3;
                        }
                        .register-link {
                            text-align: center;
                            margin-top: 20px;
                            color: #666;
                        }
                        .register-link a {
                            color: #667eea;
                            text-decoration: none;
                            font-weight: 600;
                        }
                        .register-link a:hover {
                            text-decoration: underline;
                        }
                    </style>
                </head>
                <body>
                    <div class='container'>
                        <h1>🔑 Login</h1>
                        %s
                        <form method='post' action='/login'>
                            <div class='form-group'>
                                <label>Username</label>
                                <input type='text' name='username' required>
                            </div>
                            
                            <div class='form-group'>
                                <label>Password</label>
                                <input type='password' name='password' required>
                            </div>
                            
                            <input type='submit' value='Login'>
                        </form>
                        
                        <div class='register-link'>
                            Don't have an account? <a href='/register'>Register here</a>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(message));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        System.out.println("\n========== LOGIN ATTEMPT ==========");
        System.out.println("Username: " + username);
        System.out.println("===================================");

        if (username == null || password == null ||
                username.trim().isEmpty() || password.trim().isEmpty()) {
            System.out.println("❌ Validation failed: Empty fields!");
            resp.sendRedirect("/login?error=empty");
            return;
        }

        Optional<User> userOptional = UserDB.login(username, password);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            resp.sendRedirect("/dashboard");
        } else {
            resp.sendRedirect("/login?error=invalid");
        }
    }
}