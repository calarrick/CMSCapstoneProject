<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="custom" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>${post.postTitle}: Law in Order</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href='https://fonts.googleapis.com/css?family=Merriweather:400,400italic,700|Open+Sans:600|Open+Sans+Condensed:300,700' rel='stylesheet' type='text/css'>
        <link href="${pageContext.request.contextPath}/css/pagestyle.css" rel="stylesheet">

        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body data-base-url="${pageContext.request.contextPath}/">
        
            <div class="container">
            <div id ="header-div" class="row">
                <header>
                    <div id="title" class="col-xs-12">
                        <h2 id="page-title"><a href="${pageContext.request.contextPath}/">Law in Order</a></h1>
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
                <section id="post-area" class="col-xs-12 col-sm-9">


                    <div id="post-list" class="post-list">
                        <div class="single-post post">
                            <h1 class="post-title">
                                ${post.postTitle}
                            </h1>
                            <h3 class="post-byline"><span class="byline-author">${post.author}</span>, 
                                <span class="byline-date"><custom:formatDate pattern="MMMM d, uuuu" value="${post.publishDate}"/></span>

                            </h3>
                            <div class="post-body"> ${post.postBody}
                            </div>
                            <ul class="tag-area tag-list" id=${post.postId}-tags>             
                                <c:forEach  items="${post.tags}" var="currentTag">

                                    <li>
                                        <a href="#" class="tag" data-tag-name=${currentTag}>
                                            #${currentTag}</a>
                                    </li>


                                </c:forEach>          
                            </ul>

                        </div>
                    </div>

                </section>    

                <section id="sidebar" class="sidebar-default hidden-xs col-sm-3">
                    <h4>Most Popular Tags</h4>
                    <ul class="top-tags"></ul>
                    <h4>My Pages</h4>
                    <ul class="static-pages"></ul>
                    <h4>Quick Access</h4>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/admin">Admin Panel</a></li> 
                        <li><a href="${pageContext.request.contextPath}/">Go to Home Page</a></li>
                        <li><a href="#">About the Author</a></li>
                        <li><a href="#">Archives</a></li>

                    </ul>
                </section>
                        
                <section id="big-sidebar" class="sidebar-default big-sidebar-hidden hidden-sm hidden-md hidden-lg hidden-xl">
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
                    <h4>Quick Access</h4>
                    <ul>
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
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/index.js"></script>
        <script src="${pageContext.request.contextPath}/js/layout.js"></script>
    </body>
</html>
