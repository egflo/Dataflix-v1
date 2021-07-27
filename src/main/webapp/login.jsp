<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>FabFlix Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="${pageContext.request.contextPath}/css/loginStyle.css" rel="stylesheet" type="text/css" />
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        <script src='https://www.google.com/recaptcha/api.js'></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/js/all.min.js"></script>
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@600&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap" rel="stylesheet">
    </head>

    <body>
        <div class="login_block">
            <i id="film_icon" class="fas fa-film"></i>
            <form id='info' method="post" action="LoginServlet">
                <fieldset>
                    <h1 id="bold_text">Sign-In</h1>

                    <p>${message}</p>

                    <div id="mail">
                        <label for="email">Email</label>
                        <input type="text" id="email" name="email" required><br>
                    </div>

                    <div id="pass">
                        <label for="password">Password </label>
                        <input type="text" id="password" name="password" required><br>
                    </div>

                    <div id="login_button">
                        <input type='submit'  value='Login' />
                    </div>

                    <hr class="login_header" aria-hidden="true">

                    <div id="alt">
                        <input type='button' onclick="resetData()" value='Forget your password?' />
                        <input type="button" onclick="location.href='Registration.jsp'" value="Register an account"/>
                    </div>

                </fieldset>
                <div class="g-recaptcha" data-sitekey="6LeMyh4TAAAAACXi8tVVNyTHLjruLaT0CAb-hGir"></div>
            </form>
        </div>
    </body>
</html>