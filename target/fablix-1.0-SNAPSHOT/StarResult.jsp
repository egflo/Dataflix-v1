<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="${pageContext.request.contextPath}/css/StarResultStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/navigationStyle.css" rel="stylesheet" type="text/css" />

        <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/js/all.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@600&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@700&display=swap" rel="stylesheet">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/js/all.min.js"></script>

        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


        <title>Star Result</title>
	</head>

    <!--Navigation bar-->
    <div id="nav-placeholder">

    </div>
    
	<body>
		<h2>Cast Information</h2>

        <div class="cast_container">
            <div class="cast_photo">
                <img src="${star.photo}" onerror="this.src='img/No-Image-Available.jpg';"/>
            </div>
            <div class="cast_data">
                <table class="star_information">
                    <tr><td><b>${star.name}</b></td></tr>
                    <tr><td><p>${star.details}</p></td></tr>
                    <tr><td>
                        <div class="bio_text">
                            <p>${star.bio}</p>
                        </div>
                    </td></tr>
                </table>
            </div>
        </div>

        <h2>Filmography</h2>

        <div class="movie_list">
            <c:forEach items="${star.starredin}" var="movie" varStatus="loop">
                <div class="movie_data">
                    <img class="movie_poster" src="${movie.poster}" onerror="this.src='${pageContext.request.contextPath}/img/No-Image-Available.jpg;'">

                    <div class="collapse width" id="${movie.movieid}">
                        <div class="info_popout">
                            <table class="movie_info">
                                <tr><td><b>${movie.title}</b></td></tr>
                                <tr><td><p>${movie.year} - ${movie.rated} - ${movie.runtime}</p></td></tr>
                                <tr><td><p>Director: ${movie.director}</p></td></tr>
                                <tr><td><p>${movie.plot}</p></td></tr>
                            </table>
                        </div>
                    </div>
                </div>

            </c:forEach>
        </div>

        <script>
            $(function(){
                $("#nav-placeholder").load("navigation.jsp");
                cart_icon_update();
            });

            $(document).ready(function() {
              //  $('.info_popout').hide();
            });

            $('.movie_data').mouseenter(function(){
                $(this).css("border","2px solid #207cca");
            });

            $('.movie_data').click(function(){
                $(this).find('.collapse').collapse('toggle');

                if (parseInt($(this).width(), 10) <= 150 ) {
                    $(this).css("width", "555px");
                } else {
                    $(this).css("width", "150px");
                }




                //$(this).find('.info_popout').show();
                //$(this).find('.info_popout').css("width","350px")
                //$(this).css({"width": "520px", "z-index": "1"});
            });

            $('.movie_data').mouseleave(function(){
                //$(this).css("width", "150px");
               // $(this).find('.collapse').collapse();
                //$(this).find('.info_popout').css("width","0px")
                //$(this).css({"width": "150px", "z-index": "0"});
                //$(this).find('.info_popout').hide();
                $(this).css("border","2px solid white");
            });

        </script>
	</body>
</html>