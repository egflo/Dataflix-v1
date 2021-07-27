<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link href="${pageContext.request.contextPath}/css/SearchResultStyle.css" rel="stylesheet" type="text/css" />
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
        <h1>Movie Information</h1>
        
        <div class="movie_card">
            <table class="movietable">
                <tr>
                    <td>
                        <img class="movie_poster" src="${current.poster}" onerror="this.src='${pageContext.request.contextPath}/img/No-Image-Available.jpg;'">
                    </td>
                    <td colspan="2">
                        <table class="information">
                            <tr>
                                <td><h2>${current.title}</h2></td>
                                <td style="width:100px">
                                    <div class="meta_container">
                                        <b>${current.metacrtic}</b>
                                    </div>
                                </td>
                            </tr>
                            <tr><td colspan="2"><p>${current.year} - ${current.rated} - ${current.runtime}</p></td></tr>
                            <tr><td colspan="2"><p>Director: ${current.director}</p></td></tr>
                            <tr><td colspan="2"><p>${current.plot}</p></td></tr>
                            <tr><td colspan="2">
                                <p class="form-inline">Genre(s)</p>
                                <c:forEach items="${current.genres}" var="genre" varStatus="loop">
                                    <form class="form-inline" action="SearchServlet" method="get">
                                        <input type="hidden" name="type" value="genre">
                                        <input type="hidden" name="term" value="${genre.name}">
                                        <button class = "genre_button" type="submit">${genre.name}</button>
                                    </form>
                                </c:forEach>
                            </td></tr>
                            <tr>
                                <td colspan="2"><p>Language(s): ${current.language}</p></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="rating" id="star-rating_${current.movieid}">
                            <div class="stars-outer" aria-hidden = "true">
                                <div class="stars-inner" aria-hidden = "true"></div>
                            </div>
                            <p><small>Rating: ${current.rating} (${current.votes} votes)</small></p>
                        </div>
                    </td>
                    <td rowspan="2">
                        <div class = "parent">
                            <div class="cast">
                                <c:forEach items="${current.stars}" var="star" varStatus="loop">
                                    <div class="cast_photo">
                                        <a href="StarServlet?star_id=${star.id}&star_name=${star.name}">
                                            <img onmouseover="cast_image_over(this)" onmouseout="cast_image_out(this)"  src="${star.photo}" onerror="this.src='${pageContext.request.contextPath}/img/no_photo.png';">
                                        </a>
                                        <p>${star.name}</p>
                                    </div>
                                </c:forEach>
                            </div>
                            <button class="scroll_button_right" type="button"><i id ="arrow_right" class="fa fa-arrow-right"></i></button>
                            <button class="scroll_button_left" type="button"><i id ="arrow_left" class="fa fa-arrow-left"></i></button>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button class="carttrigger" id="${current.movieid}" type="button"> Buy for $${current.priceStr} </button>
                    </td>
                </tr>
            </table>
        </div>

    <script>
        $(function(){
            $("#nav-placeholder").load("navigation.jsp");
            cart_icon_update();
        });

        const button_left = document.getElementsByClassName('scroll_button_left')[0];
        const button_right = document.getElementsByClassName('scroll_button_right')[0];

        button_right.onclick = function () {
            var element = document.getElementsByClassName('cast')[0];
            var maxScroll = element.scrollWidth - element.clientWidth;

            if(element.scrollLeft >= maxScroll){
                button_right.style.visibility = "hidden";
                button_left.style.visibility = "visible";
            }
            else {
                element.scrollLeft += 100;
            }
        };

        button_left.onclick = function () {
            var element = document.getElementsByClassName('cast')[0];

            if(element.scrollLeft <= 0){
                button_right.style.visibility = "visible";
                button_left.style.visibility = "hidden";
            }
            else {
                element.scrollLeft -= 100;
            }
        };

        function cart() {
            location.replace("CartServlet")
        }

        function checkout() {
            location.replace("Checkout.jsp")
        }

        function cast_image_over(x) {
            x.style.border = "3px solid #207cca";
        }

        function cast_image_out(x) {
            x.style.border = "3px solid white";
        }

        $(document).ready(function() {
            var element = document.getElementsByClassName("meta_container")[0];
            var number = Number(element.textContent);

            if(number >= 61){
                element.style.backgroundColor = '#6c3';
            }
            else if(number >= 40 && number <= 60){
                element.style.backgroundColor = '#fc3';
            }
            else if(number >= 20 && number <= 39){
                element.style.backgroundColor = '#f00';
            }
            else {
                element.style.backgroundColor = '#E8E8E8';
            }
        });
    </script>
    </body>
</html>