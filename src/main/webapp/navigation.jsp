<nav>
    <div class="menu">
        <ul class="list_menu">
            <li><a href="CartServlet">
                    <span id="cart_link" class="fa-layers fa-fw">
                        <i id="cart_icon" class="fas fa-shopping-cart"></i>
                        <span id="cart_notification" class="fa-layers-counter" style="background:Tomato">0</span>
                    </span>
            </a>
            </li>
            <li><a href="AdvancedSearch.jsp">Search</a></li>
            <li class="dropdown">
                <a href="#" class="dropbtn">
                    <div class="account_container">
                        <p id="user_data">Hello, Sign In</p>
                        <b>Account & Orders <i id="nav_caret" class="fas fa-caret-down"></i></b>
                    </div>
                </a>
                <div class="dropdown-content" id="signin_dropdown">
                    <a href="AccountServlet">Orders</a>
                    <a href="AccountServlet">Account</a>
                    <a href="LogoutServlet">Sign Out</a>
                </div>
            </li>
        </ul>
    </div>
    <div class="search_box">
        <form autocomplete="off" method="get" action="SearchServlet">
            <table>
                <tr>
                    <td>
                        <select name="type" id="search_type">
                            <option value="all">All</option>
                            <option value="movie">Movie</option>
                            <option value="star">Star</option>
                            <option value="genre">Genre</option>
                        </select>
                    </td>
                    <td>
                        <input type="text" placeholder="Search..." id="search_term" name="term" required>
                    </td>
                    <td>
                        <button type="submit" id="search_submit"><i class="fa fa-search"></i></button>
                    </td>
                </tr>
            </table>
            <script>
                $("form#search_term").autocomplete("/fabflix/MovieAutoComplete");
            </script>
        </form>
    </div>
    <a id="film_icon_link" href="IndexServlet">
        <i id="film_icon" class="fas fa-film"></i>
    </a>
</nav>

<script type="text/javascript">
    $(document).ready(function() {
        $(function() {
            $("#search_term").autocomplete({
                minLength: 1,
                source: function(request, response) {
                    var t = $( "#search_type" ).val();
                    $.ajax({
                        url: "MovieAutoComplete",
                        dataType : "json",
                        data: { term: request.term , search_type: t},
                        success: function(data) {
                            response(data);
                        },
                        error: function(xhr, textStatus, errorThrown) {
                            console.log(jqXHR.status);
                        },
                    });
                }
            });
        });

        $(function() {
            var name='<%=session.getAttribute("uname")%>';
            if(name != "null") {
                document.getElementById("user_data").innerText = "Hello " + name + ",";
            }
            else {
                document.getElementById("signin_dropdown").innerHTML
                    = '<input type="button" id="sign_nav" onclick="sign_in()" value="Sign In"/>';
            }

        });
    });

    function sign_in() {
        document.location.href = "login.jsp";
    }

     $('.dropdown').hover(function() {
         $(this).find('.dropdown-content').css("visibility","visible");
     })

    $('.dropdown').mouseleave(function() {
        $(this).find('.dropdown-content').css("visibility","hidden");
    })
</script>