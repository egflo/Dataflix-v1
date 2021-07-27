<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Account</title>
    <link href="${pageContext.request.contextPath}/css/accountStyle.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/navigationStyle.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/js/all.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@600&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@700&display=swap" rel="stylesheet">
</head>

<!--Navigation bar-->
<div id="nav-placeholder">

</div>

<body>
    <div class="sidenav">
        <a id="account_link" href="#about">Account</a>
        <a id="order_link" href="#services">Orders</a>
    </div>

    <div class="main_account">
        <h2>My Account</h2>
        <div class="account_block">
            <form id='account_info' method="post" action="AccountServlet">
                <fieldset>
                    <input type="hidden" id="account_action" name="account_action" value="update">
                    <input type="hidden" id="customer_id" name="customer_id" value="${user.id}">

                    <p>${message}</p>

                    <h3 class="bold_text">User Information</h3>

                    <div id="first_name">
                        <label for="fname">First Name</label>
                        <input type="text" id="fname" name="fname" value="${user.first_name}"><br>
                    </div>

                    <div id="last_name">
                        <label for="lname">Last Name </label>
                        <input type="text" id="lname" name="lname" value="${user.last_name}"><br>
                    </div>

                    <div id="email">
                        <label for="mail">Email </label>
                        <input type="text" id="mail" name="mail" value="${user.email}"><br>
                    </div>

                    <hr class="search_header" aria-hidden="true">

                    <h3 class="bold_text">Address Information</h3>
                    <div id ='country_selection'>
                        <label for="country">Country/Region</label>
                        <select id="country" name="country">
                            <option value="US">United States</option>
                            <option value="MEX">Mexico</option>
                            <option value="CAD">Canada</option>
                        </select>
                    </div>

                    <div id="address_description">
                        <label for="address">Address</label>
                        <input type="text" id="address" name="address" value="${user.address}">
                    </div>

                    <div id="address_container">
                        <div id="city_description">
                            <label for="city">City</label>
                            <input type="text" id="city" name="city" value="${user.city}">
                        </div>

                        <div id ='state_selection'>
                            <label for="country">Country/Region</label>
                            <select id="state" name="state">
                                <option value="AL">Alabama</option>
                                <option value="AK">Alaska</option>
                                <option value="AZ">Arizona</option>
                                <option value="AR">Arkansas</option>
                                <option value="CA">California</option>
                                <option value="CO">Colorado</option>
                                <option value="CT">Connecticut</option>
                                <option value="DE">Delaware</option>
                                <option value="DC">District Of Columbia</option>
                                <option value="FL">Florida</option>
                                <option value="GA">Georgia</option>
                                <option value="HI">Hawaii</option>
                                <option value="ID">Idaho</option>
                                <option value="IL">Illinois</option>
                                <option value="IN">Indiana</option>
                                <option value="IA">Iowa</option>
                                <option value="KS">Kansas</option>
                                <option value="KY">Kentucky</option>
                                <option value="LA">Louisiana</option>
                                <option value="ME">Maine</option>
                                <option value="MD">Maryland</option>
                                <option value="MA">Massachusetts</option>
                                <option value="MI">Michigan</option>
                                <option value="MN">Minnesota</option>
                                <option value="MS">Mississippi</option>
                                <option value="MO">Missouri</option>
                                <option value="MT">Montana</option>
                                <option value="NE">Nebraska</option>
                                <option value="NV">Nevada</option>
                                <option value="NH">New Hampshire</option>
                                <option value="NJ">New Jersey</option>
                                <option value="NM">New Mexico</option>
                                <option value="NY">New York</option>
                                <option value="NC">North Carolina</option>
                                <option value="ND">North Dakota</option>
                                <option value="OH">Ohio</option>
                                <option value="OK">Oklahoma</option>
                                <option value="OR">Oregon</option>
                                <option value="PA">Pennsylvania</option>
                                <option value="RI">Rhode Island</option>
                                <option value="SC">South Carolina</option>
                                <option value="SD">South Dakota</option>
                                <option value="TN">Tennessee</option>
                                <option value="TX">Texas</option>
                                <option value="UT">Utah</option>
                                <option value="VT">Vermont</option>
                                <option value="VA">Virginia</option>
                                <option value="WA">Washington</option>
                                <option value="WV">West Virginia</option>
                                <option value="WI">Wisconsin</option>
                                <option value="WY">Wyoming</option>
                            </select>
                        </div>

                        <div id="zip_code">
                            <label for="address">Zip Code</label>
                            <input type="text" id="zip" name="zip" value="${user.postcode}">
                        </div>
                    </div> <br>

                    <hr class="search_header" aria-hidden="true">

                    <h3 class="bold_text">Billing Information</h3>

                    <div id="credit_card">
                        <label for="cc_id">Credit Card</label>
                        <input type="text" id="cc_id" name="cc_id" value="${user.cc_id}"><br>
                    </div>

                    <div id="credit_card_fname">
                        <label for="cc_fname">First Name</label>
                        <input type="text" id="cc_fname" name="cc_fname" value="${user.cc_fname}"><br>
                    </div>

                    <div id="credit_card_lname">
                        <label for="cc_lname">First Name</label>
                        <input type="text" id="cc_lname" name="cc_lname" value="${user.cc_lname}"><br>
                    </div>

                    <hr class="search_header" aria-hidden="true">

                    <div id="account_buttons">
                        <input type="submit" id="account_submit" value="Submit">
                        <input type="button" onclick="resetData()" value="Reset"/>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>

    <div class="main_order">
        <h2>My Orders</h2>
        <div class="Order_Summary">
            <c:forEach items="${orders}" var="order">
                <div class="order_container">
                    <div class="order_header">
                        <div class="order_id">
                            <b>Order: FLX-${order.orderId}</b>
                        </div>
                        <div class="order_date">
                            <b>${order.saleDate}</b>
                        </div>
                    </div>

                    <hr class="order_line" aria-hidden="true">

                    <c:forEach items="${order.movies}" var="current">
                        <div class="order_item">
                            <div class="movie_poster">
                                <img class="movie_poster" src="${current.key.poster}" onerror="this.src='${pageContext.request.contextPath}/img/No-Image-Available.jpg;'">
                            </div>

                            <div class="order_summary">
                                <div class="order_title">
                                    <b>${current.key.title}</b>
                                </div>
                                <div class="order_qty">
                                    <p>Qty: ${current.value}</p>
                                </div>
                                <div class="order_link">
                                    <a href="MovieServlet?movieid=${current.key.movieid}">Buy it again</a>
                                </div>
                            </div>

                            <div class="order_fulfillment">
                                <div class="order_email">
                                    <b>Email Delivery</b>
                                    <p>${user.email}</p>
                                </div>
                            </div>

                            <div class="order_total">
                                <b>Total</b>
                                <b>$${order.total}</b>
                            </div>
                        </div>
                    </c:forEach>
                </div> <br>
            </c:forEach>
        </div>
    </div>

        <script>
            function resetData()
            {
                document.getElementById('account_info').reset();
                document.getElementById("state").value = "${user.state}";
            }

            $(document).ready(function () {
                $('.main_account').show();
                $('.main_order').hide();

                document.getElementById("state").value = "${user.state}";
            });

            $('#account_link').click(function () {
                $('.main_account').show();
                $('.main_order').hide();

            });

            $('#order_link').click(function () {
                $('.main_account').hide();
                $('.main_order').show();
            });

            $(function(){
                $("#nav-placeholder").load("navigation.jsp");
                cart_icon_update();
            });
        </script>

</body>
</html>