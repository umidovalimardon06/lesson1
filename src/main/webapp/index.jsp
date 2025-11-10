<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome - Jakarta EE</title>
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
            padding: 60px 40px;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            text-align: center;
            max-width: 600px;
            width: 100%;
        }
        h1 {
            color: #333;
            margin-bottom: 15px;
            font-size: 36px;
        }
        .subtitle {
            color: #666;
            margin-bottom: 40px;
            font-size: 18px;
            line-height: 1.6;
        }
        .btn-container {
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
            margin-bottom: 30px;
        }
        .btn {
            padding: 15px 40px;
            border: none;
            border-radius: 10px;
            font-size: 18px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            transition: transform 0.2s;
            display: inline-block;
        }
        .btn:hover {
            transform: translateY(-3px);
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        .btn-success {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            color: white;
            box-shadow: 0 5px 15px rgba(17, 153, 142, 0.4);
        }
        .links {
            color: #999;
            font-size: 14px;
            line-height: 1.8;
        }
        .links a {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
        }
        .links a:hover {
            text-decoration: underline;
        }
        .emoji {
            font-size: 50px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="emoji">🚀</div>
    <h1>Welcome to Jakarta EE</h1>
    <p class="subtitle">
        User Management System with Registration & Authentication
    </p>

    <div class="btn-container">
        <a href="/register" class="btn btn-success">Create Account</a>
        <a href="/login" class="btn btn-primary">Login</a>
    </div>

    <div class="links">
        Already have an account? <a href="/login">Login here</a><br>
        New user? <a href="/register">Register now</a>
    </div>
</div>
</body>
</html>