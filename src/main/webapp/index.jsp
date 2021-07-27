<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>FabFlix Main WebPage</title>
        <link href="${pageContext.request.contextPath}/css/navigationStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/indexStyle.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
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
    	<div class="browsing">		
    		<h2>Most Rated</h2>
    		<table>
    			<tr>
    				<c:forEach items="${rated}" var="current">
						<td>
							<img src="${current.poster}" onerror="this.src='${pageContext.request.contextPath}/img/No-Image-Available.jpg;'">
						</td>
    	 	    	</c:forEach>
    			</tr>
    			<tr>
    				<c:forEach items="${rated}" var="current">
						<td><h4>${current.title} (${current.year})</h4></td>
    	 	    	</c:forEach> 	 			    			
    			</tr>
				<tr>
					<c:forEach items="${rated}" var="current">
						<td>
							<div class="rating" id="star-rating_${current.movieid}">
								<div class="stars-outer" aria-hidden = "true">
									<div class="stars-inner" aria-hidden = "true"></div>
								</div>
								<p><small>Rating: ${current.rating} (${current.votes} votes)</small></p>
							</div>
						</td>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach items="${rated}" var="current">
						<td>
							<form method="get" action="MovieServlet">
								<input type="hidden" name="movieid" value="${current.movieid}">
								<input type="hidden" name="title" value="${current.title}">
								<button class = "movie_button" type="submit">View Details</button>
							</form>
						</td>
					</c:forEach>
				</tr>
    		</table>

			<h2>Best Sellers</h2>
			<table>
				<tr>
					<c:forEach items="${sellers}" var="current">
						<td>
							<img src="${current.poster}" onerror="this.src='${pageContext.request.contextPath}/img/No-Image-Available.jpg;'">
						</td>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach items="${sellers}" var="current">
						<td><h4>${current.title} (${current.year})</h4></td>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach items="${sellers}" var="current">
						<td>
							<div class="rating" id="star-rating_${current.movieid}">
								<div class="stars-outer" aria-hidden = "true">
									<div class="stars-inner" aria-hidden = "true"></div>
								</div>
								<p><small>Rating: ${current.rating} (${current.votes} votes)</small></p>
							</div>
						</td>
					</c:forEach>
				</tr>
				<tr>
					<c:forEach items="${sellers}" var="current">
						<td>
							<form method="get" action="MovieServlet">
								<input type="hidden" name="movieid" value="${current.movieid}">
								<input type="hidden" name="title" value="${current.title}">
								<button class = "movie_button" type="submit">View Details</button>
							</form>
						</td>
					</c:forEach>
				</tr>
			</table>
    	 </div>

	<script>
		$(function(){
			$("#nav-placeholder").load("navigation.jsp");
			cart_icon_update();
		});
	</script>

    </body>
</html>