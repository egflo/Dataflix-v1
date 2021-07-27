
function cart_icon_update(){
	// don't cache ajax or content won't be fresh
	$.ajaxSetup ({
		cache: false
	});

	$.ajax({
		url : "CartServlet",
		data : {
			action: "num_items"
		},
		success : function(data, textStatus, jqXHR) {
			$('#cart_notification').text(data);

		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR.status);
		}
	});
}


function resetData()
{
    document.getElementById('info').reset();
}

$(document).ready(
	    function () {
	    	$('nav li ul').hide().removeClass('fallback');
	        $(".hoverli").hover(
	          function () {
	             $('account_menu').finish().slideDown('medium');
	          }, 
	          function () {
	             $('account_menu').finish().slideUp('medium');
	          }
	    );
	});

$(document).ready(function() {
	var overlay = $('<div class="overlay"></div>');
	$(document.body).append(overlay);
	
	$(".carttrigger").click(function(e) {
		var target = $(e.target);
		var height = $(document).scrollTop();
		var movie_id =  target.attr("id");

		$.ajax({
			url : "CartPopUpWindow",
			data : {
				movieid: movie_id
			},
			success : function(data, textStatus, jqXHR) {
				overlay.append('<div class="box"><div class="CartData"></div></div>');
				$('.CartData').html(data);

				overlay.show();
				overlay.css({
					position : "absolute",
					zIndex:1,
					top: height,
					left: 0,
					width: "100%",
					height: "100%",
					backgroundColor: "rgb(0,0,0)",
					backgroundColor: "rgba(0,0,0,.50)",
					overflowX: "hidden",
					overflowY: "hidden",
					transition: "0.10s"
				})

				document.body.style.overflow = 'hidden';
				cart_icon_update();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.status);
			}
		});
	});
	
	$(document).on('click', '#close_popup', function() {
			overlay.hide();
			overlay.empty();
			document.body.style.overflow = 'auto';
	});

});

$(document).ready(function() {
	var moviePopup = $('<div id="movie-window"></div>');
	$(document.body).append(moviePopup);
	
	$(".trigger").mouseover(function(event) {
		
		var $target = $(event.target);
		var offset = $target.offset();

		var movie_id =  $target.attr("id");
		$.ajax({
			url : "MoviePopUpWindow",
			data : {
				movieid: movie_id
			},
			success : function(data, textStatus, jqXHR) {
				moviePopup.append('<div id="MovieData"></div>');
				$('#MovieData').html(data);
				
				moviePopup.show();
				moviePopup.css({
					position : "absolute",
					top : offset.top + $target.height(),
					left : offset.left + $target.width(),
					backgroundColor : "white",
					border : "2px solid black"
				})
					
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.status);
			}
		});
	});

	$(".carttrigger").mouseleave(function(e) {
		setTimeout(function () {
			moviePopup.hide();
			moviePopup.empty();
		}, 6000);

	});
});



//Based on https://webdesign.tutsplus.com/tutorials/a-simple-javascript-technique-for-filling-star-ratings--cms-29450
$(document).ready(function() {
	var elements = document.querySelectorAll("[id^='star-rating_']");

	function servletCall(x) {
		$.post(
			"RatingsServlet",
			{data : JSON.stringify(x)},
			function(result) {
				var json = JSON.parse(result);
				let arrayLength = x.length;
				for (let i = 0; i < arrayLength; i++) {

					var item = json[x[i]];
					var float_rating = parseFloat(item.rating);
					var id_name = "star-rating_" + item.movieid;

					const starTotal = 5;
					const starPercentage = (float_rating / starTotal) * 100;

					const starPercentageRounded = `${(Math.round(starPercentage / 10) * 10)}%`;
					var elements = document.getElementById(id_name);
					elements.querySelector('.stars-inner').style.width = starPercentageRounded
				};
			});
	};

	var id_list = [];
	for (let i = 0; i < elements.length; i++) {
		//var id = elements[i].getAttribute("id");
		var id = elements[i].id;
		const words = id.split('_');
		id_list.push(words[1]);
	}
	servletCall(id_list);
});
