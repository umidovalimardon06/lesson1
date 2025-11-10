package uz.pdp.jakarta_ee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uz.pdp.jakarta_ee.db.UserDB;
import uz.pdp.jakarta_ee.model.User;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Register", value = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        String error = req.getParameter("error");

        String errorMessage = "";
        if (error != null) {
            errorMessage = switch (error) {
                case "username" -> "<div class='error'>❌ Username already exists!</div>";
                case "email" -> "<div class='error'>❌ Email already exists!</div>";
                case "password" -> "<div class='error'>❌ Passwords do not match!</div>";
                case "empty" -> "<div class='error'>❌ All fields are required!</div>";
                default -> "";
            };
        }

        writer.write("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>User Registration</title>
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
                            max-width: 500px;
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
                        input[type='email'],
                        input[type='password'] {
                            width: 100%;
                            padding: 12px 15px;
                            border: 2px solid #e0e0e0;
                            border-radius: 10px;
                            font-size: 16px;
                            transition: all 0.3s;
                        }
                        input[type='text']:focus,
                        input[type='email']:focus,
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
                        .login-link {
                            text-align: center;
                            margin-top: 20px;
                            color: #666;
                        }
                        .login-link a {
                            color: #667eea;
                            text-decoration: none;
                            font-weight: 600;
                        }
                        .login-link a:hover {
                            text-decoration: underline;
                        }
                    </style>
                </head>
                <body>
                    <div class='container'>
                        <h1>🔐 Register</h1>
                        %s
                        <form method='post' action='/register'>
                            <div class='form-group'>
                                <label>First Name</label>
                                <input type='text' name='firstName' required>
                            </div>
                            
                            <div class='form-group'>
                                <label>Last Name</label>
                                <input type='text' name='lastName' required>
                            </div>
                            
                            <div class='form-group'>
                                <label>Username</label>
                                <input type='text' name='username' required>
                            </div>
                            
                            <div class='form-group'>
                                <label>Email Address</label>
                                <input type='email' name='email' required>
                            </div>
                            
                            <div class='form-group'>
                                <label>Password</label>
                                <input type='password' name='password' required>
                            </div>
                            
                            <div class='form-group'>
                                <label>Confirm Password</label>
                                <input type='password' name='repassword' required>
                            </div>
                            
                            <input type='submit' value='Create Account'>
                        </form>
                        
                        <div class='login-link'>
                            Already have an account? <a href='/login'>Login here</a>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(errorMessage));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String repassword = req.getParameter("repassword");

        System.out.println("\n========== REGISTRATION ATTEMPT ==========");
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("==========================================");

        if (firstName == null || lastName == null || username == null ||
                email == null || password == null || repassword == null ||
                firstName.trim().isEmpty() || lastName.trim().isEmpty() ||
                username.trim().isEmpty() || email.trim().isEmpty() ||
                password.trim().isEmpty() || repassword.trim().isEmpty()) {
            System.out.println("❌ Validation failed: Empty fields!");
            resp.sendRedirect("/register?error=empty");
            return;
        }

        if (!password.equals(repassword)) {
            System.out.println("❌ Validation failed: Passwords don't match!");
            resp.sendRedirect("/register?error=password");
            return;
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        boolean registered = UserDB.registerUser(user);

        if (registered) {
            resp.sendRedirect("/login?success=registered");
        } else {
            if (UserDB.findByUsername(username).isPresent()) {
                resp.sendRedirect("/register?error=username");
            } else if (UserDB.findByEmail(email).isPresent()) {
                resp.sendRedirect("/register?error=email");
            }
        }
    }
}