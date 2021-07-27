<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Advanced Search</title>
        <link href="${pageContext.request.contextPath}/css/navigationStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/AdvancedSearchStyle.css" rel="stylesheet" type="text/css" />
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
    </head>

    <!--Navigation bar-->
    <div id="nav-placeholder">

    </div>

    <body>
        <div class="login_block">
            <form id='info' method="post" action="AdvancedSearchServlet">
                <fieldset>
                    <p>${message}</p>

                    <h1 class="bold_text">Movie Information</h1>

                    <div id="movie_title">
                        <label for="title">Title</label>
                        <input type="text" id="title" name="title"><br>
                    </div>

                    <div id="movie_year">
                        <label for="year">Year </label>
                        <input type="text" id="year" name="year"><br>
                    </div>

                    <div id="movie_director">
                        <label for="director">Director </label>
                        <input type="text" id="director" name="director"><br>
                    </div>

                    <hr class="search_header" aria-hidden="true">

                    <h1 class="bold_text">Cast Information</h1>

                    <div id="cast_name">
                        <label for="name">Name</label>
                        <input type="text" id="name" name="name"><br>
                    </div>

                    <div id="birth_year">
                        <label for="cast_year">Birth Year</label>
                        <input type="text" id="cast_year" name="cast_year" ><br>
                    </div>

                    <hr class="search_header" aria-hidden="true">

                    <h1 class="bold_text">Genre Information</h1>

                    <div id="genre_name">
                        <label for="name">Genre</label>
                        <input type="text" id="genre" name="genre"><br>
                    </div>

                    <hr class="search_header" aria-hidden="true">

                    <div id="search_buttons">
                        <input type='submit' value='Submit' />
                        <input type="button" onclick="resetData()" value="Reset"/>
                    </div>

                 </fieldset>
            </form>
        </div>
        <script>
            $(function(){
                $("#nav-placeholder").load("navigation.jsp");
                cart_icon_update();
            });
        </script>
    </body>
</html>