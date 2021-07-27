<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Cart</title>
    <link href="${pageContext.request.contextPath}/css/navigationStyle.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/CartStyle.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/js/all.min.js"></script>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@600&display=swap" rel="stylesheet">
</head>

<!--Navigation bar-->
<div id="nav-placeholder">

</div>

<body>
    <div class="Shopping_Information">

        <div class="Order_Summary">
            <h2 class="order_header">Order Summary</h2>
            <hr class="order_sum_header" aria-hidden="true">
            <table class="sum_table">
                <tr>
                    <td><p>Orginal Price</p></td>
                    <td><p>$${total}</p></td>
                </tr>
                <tr>
                    <td><p>Sales Tax</p></td>
                    <td><p>Calculated in Checkout</p></td>
                </tr>
            </table>
            <hr class="order_sum_header" aria-hidden="true">
            <table class="sum_table">
                <tr>
                    <td><b>Total</b></td>
                    <td><b>$${total}</b></td>
                </tr>
            </table>

            <div class="button_order">
                <form method="post" action="CartServlet">
                    <p id="msg">${message}</p>
                    <input type="submit"  name="action" value="Checkout" />
                    <input type="submit"  name="action" value="Empty Cart"/>
                </form>
            </div>
        </div>

        <div class="Cart_Summary">
            <h2>My Cart</h2>
            <table class="item_table">
                <c:forEach items="${results}" var="current">
                    <tr>
                        <td> <img class="movie_poster" src="${current.key.poster}" onerror="this.src='${pageContext.request.contextPath}/img/No-Image-Available.jpg;'"></td>
                        <td>
                            <b>${current.key.title}</b>
                            <p>${current.key.year}</p>
                            <p>Rated: ${current.key.rated}</p>
                        </td>
                        <td>
                            <form id="qty" method="get" action="CartServlet">
                                <select id="qty_value_${current.key.movieid}">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                </select>
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="title" value="${current.key.title}">
                                <input type="hidden" name="movieid" value="${current.key.movieid}">
                            </form>
                        <td> $${current.key.price}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <script>
        $(function(){
            $("#nav-placeholder").load("navigation.jsp");
            cart_icon_update();
        });

        $(document).ready(function () {
            <c:forEach items="${results}" var="current">
                document.getElementById("qty_value_${current.key.movieid}").value = "${current.value}";
            </c:forEach>
        });

    </script>

	</body>
</html>