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
        <h1>${message}</h1>

        <div class="sorting">
            <form class = "form-inline"  id="num_sort" method="get" action="SearchServlet">
                <input type="hidden" name="type" value="limit">
                <label for="num">Results Per Page:</label>
                <select onchange="this.form.submit()" name="term" id="num">
                    <option value="5" selected>5</option>
                    <option value="10">10</option>
                    <option value="15">15</option>
                    <option value="25" >25</option>
                </select>
            </form>

            <form  class = "form-inline" id="form_sort" action="SearchServlet">
                <input type="hidden" name="type" value="sortby">
                <label for="sort_by">Sort By:</label>
                <select onchange="this.form.submit()" name="term" id="sort_by">
                    <option value="best_seller">Best Sellers</option>
                    <option value="most_pop">Most Popular</option>
                    <option value="A_Z">Title A-Z</option>
                    <option value="Z_A">Title Z-A</option>
                    <option value="year_asc">Year Ascend</option>
                    <option value="year_dsc">Year Descend</option>
                </select>
            </form>
        </div>

        <c:forEach items="${results}" var="current">
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
                        <div class = "parent" id = "parent_${current.movieid}">
                            <div class="cast" id="cast_${current.movieid}">
                                <c:forEach items="${current.stars}" var="star" varStatus="loop">
                                    <div class="cast_photo">
                                        <a href="StarServlet?star_id=${star.id}&star_name=${star.name}">
                                            <img onmouseover="cast_image_over(this)" onmouseout="cast_image_out(this)"  src="${star.photo}" onerror="this.src='${pageContext.request.contextPath}/img/no_photo.png';">
                                        </a>
                                        <p>${star.name}</p>
                                    </div>
                                </c:forEach>
                            </div>
                            <button id="${current.movieid}" onClick="scroll_cast_right(this.id)" class="scroll_button_right" type="button"><i id ="arrow_right" class="fa fa-arrow-right"></i></button>
                            <button id="${current.movieid}" onClick="scroll_cast_left(this.id)" class="scroll_button_left" type="button"><i id ="arrow_left" class="fa fa-arrow-left"></i></button>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button class="carttrigger" id="${current.movieid}" type="button"> Buy for $${current.priceStr} </button>
                    </td>
                </tr>
            </table>
        </c:forEach>
		
		<form class="page_buttons" method="get" action="${servlet}">
            <input type="hidden" name="type" value="page">
			<input type="submit" name="term" value="Prev"/>
            <input type="submit" name="term" value="Next"/>
        </form>        


        <script>
            $(function(){
                $("#nav-placeholder").load("navigation.jsp");
                cart_icon_update();
            });

            function scroll_cast_right(id) {
                var element = document.getElementById('cast_' + id);
                var maxScroll = element.scrollWidth - element.clientWidth;

                if(element.scrollLeft >= maxScroll){
                    $('#parent_' + id).find('.scroll_button_right').css("visibility","hidden");
                    $('#parent_' + id).find('.scroll_button_left').css("visibility","visible");
                }
                else {

                    element.scrollLeft += 100;
                }
            }

            function scroll_cast_left(id) {
                var element = document.getElementById('cast_' + id);

                if(element.scrollLeft <= 0){
                    $('#parent_' + id).find('.scroll_button_right').css("visibility","visible");
                    $('#parent_' + id).find('.scroll_button_left').css("visibility","hidden");
                }
                else {
                    element.scrollLeft -= 100;
                }
            }

            function cast_image_over(x) {
                x.style.border = "3px solid #207cca";
            }

            function cast_image_out(x) {
                x.style.border = "3px solid white";
            }

            $(document).ready(function() {
                var elements = document.getElementsByClassName("meta_container");

                for (var i = 0; i < elements.length; i++) {
                    const element = elements[i];
                    const number = Number(element.textContent);

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
                }

            });
        </script>
    </body>
</html>