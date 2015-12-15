<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Law in Order</title>
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href='https://fonts.googleapis.com/css?family=Merriweather:400,400italic,700|Open+Sans:600|Open+Sans+Condensed:300,700' rel='stylesheet' type='text/css'>
        <link href="css/pagestyle.css" rel="stylesheet">

        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body data-base-url="${pageContext.request.contextPath}/">
        <div class="container">
            <div id ="header-div" class="row">
                <header>
                    <div id="title" class="col-xs-12">
                        <h1><a href=${pageContext.request.contextPath}>Law in Order</a></h1>
                    </div>
                    <div id="subtitle" class="col-xs-12">
                        <p>Putting the order in law since 2015</p>
                    </div>
                </header>
            </div>
            <nav id="nav-row" class="big-icon hidden-sm hidden-md hidden-lg hidden-xl col-xs-12">

                <div id="top-arrow" class="top-item"><a href="" class="" id="top-link">
                        <span class="menu-icon big-icon glyphicon glyphicon-triangle-top custom-icon"></span>
                    </a></div>

                <div id="toggler" class="top-item"><a href="" id="toggle-sidebar">
                        <span class="menu-icon big-icon glyphicon glyphicon-menu-hamburger custom-icon"></span>
                    </a></div>

            </nav>

            <div id="display-searched-tag"></div>

            <div class="row">
                <section id="post-area" class="col-xs-12 col-sm-8 col-md-9">
                    <ul id="post-list" class="post-list"></ul>

                </section>    

                <section id="sidebar" class="sidebar-default hidden-xs col-sm-4 col-md-3">
                    <h4>Most Popular Tags</h4>
                    <ul class="top-tags"></ul>
                    <h4>My Pages</h4>
                    <ul class="static-pages"></ul>
                    <h4>Quick Access</h4>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/control">Control Panel</a></li> 
                        <li><a href="${pageContext.request.contextPath}/">Go to Home Page</a></li>
                        

                    </ul>
                </section>
                <section id="big-sidebar" class="sidebar-default big-sidebar-hidden hidden-sm hidden-md hidden-lg hidden-xl">
                    <h4>Most Popular Tags</h4>
                    <ul class="top-tags"></ul>
                    <h4>My Pages</h4>
                    <ul class="static-pages"></ul>
                    <h4>Quick Access</h4>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/login">Log In</a></li> 
                        <li><a href="${pageContext.request.contextPath}/control">Control Panel</a></li> 
                        <li><a href="${pageContext.request.contextPath}/">Go to Home Page</a></li>
                        <li><a href="#">About the Author</a></li>
                        <li><a href="#">Archives</a></li>

                    </ul>
                </section>
            </div>


            <div class="row">
                <footer>
                    <div id="copyright">
                        <p>&copy; 2015 Lorem Ipsum</p>
                    </div>
                </footer>
            </div>
        </div>


        <!-- Scripts; ordering is important-->
        <script src="js/jquery-1.11.3.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/index.js"></script>
        <script src="js/layout.js"></script>
    </body>
</html>

