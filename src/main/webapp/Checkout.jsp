<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link href="${pageContext.request.contextPath}/css/CheckOutStyle.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/navigationStyle.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/js/all.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@600&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@700&display=swap" rel="stylesheet">
    <title>Search Result</title>
</head>

    <!--Navigation bar-->
    <div id="nav-placeholder">

    </div>

    <body>
        <div class="check_out_block">
            <form id='info' method="post" action="CheckOutServlet">
                <fieldset>
                    <p>${message}</p>

                    <h1 class="bold_text">Checkout Information</h1>

                    <div id="cc">
                        <label for="creditcard">Credit Card No.: </label>
                        <input type="text" id="creditcard" name="creditcard" required><br>
                    </div>

                    <div id="cc_expire">
                        <label for="expire">Expiration Date: </label>
                        <input type="text" id="expire" placeholder="YYYY-MM-DD" name="expire" required><br>
                    </div>

                    <div id="first_name">
                        <label for="fname">First Name: </label>
                        <input type="text" id="fname" name="fname" required><br>
                    </div>


                    <div id="last_name">
                        <label for="lname">Last Name: </label>
                        <input type="text" id="lname" name="lname" required><br>
                    </div>

                </fieldset>
            </form>
        <script>
            $(function(){
                $("#nav-placeholder").load("navigation.jsp");
                cart_icon_update();
            });
        </script>
        </div>
    </body>
</html>