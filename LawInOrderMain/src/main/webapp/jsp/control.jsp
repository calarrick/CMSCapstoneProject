<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Law in Order</title>
        <link href='https://fonts.googleapis.com/css?family=Merriweather:400,400italic,700|Open+Sans:600|Open+Sans+Condensed:300,700' rel='stylesheet' type='text/css'>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/pagestyle.css" rel="stylesheet">


        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body data-base-url="${pageContext.request.contextPath}/">

        <div class="container admin-page">
            <div id ="header-div" class="row">
                <header>
                    <div id="title" class="col-xs-12">
                        <h1 id="page-title"><a href=${pageContext.request.contextPath}>Law in Order</a></h1>
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


            <div class="inner-container row">
                <div id="post-wrapper" class="post-wrapper col-sm-9 col-xs-12">

                    <div id="new_post_add_edit">
                        <h3 id="enter-post-header">Enter New Post</h3>
                        <p><span id="existing-post-id"></span></p>

                        <form role="form" name="postSubmit" id="postSubmit">

                            <div class="form-group col-sm-3"><h4 id="post-type-select-header">Post Type: </h4>
                                <p id="prev-pub-type"></p>

                                <input type="radio" name="post-type" value="POST" checked> Blog Post<br>
                                <sec:authorize access="hasRole('ROLE_ADMIN')"><input type="radio" name="post-type" value="STATIC_PAGE"> Static Page<br></sec:authorize>
                                </div>

                                <div class="form-group col-sm-4 col-md-4">
                                    <h4 id="date-select-header">Publication Date: </h4>
                                    <p id="prev-pub-date"></p>
                                    <input type="date" id="date" />
                                </div>

                                <div class="form-group col-sm-4 col-md-4"><h4 id="author-select-header">Author:</h4>
                                    
                                <p>Current selection is: <br><span id="curr-author"></span></p>


                                <sec:authorize access="hasRole('ROLE_ADMIN')"><select name="author-opts" id="author-opts">
                                        <option id="pre-select">Select an Author</option>

                                        <!-- will script to populate pull-down with users in the system -->
                                    </select></sec:authorize>

                                <sec:authorize access="hasRole('ROLE_AUTHOR')"><select name="author-opts-single" id="author-opts-single">
                                        <option id="pre-select">Select an Author</option>

                                        <!-- will script to populate pull-down with users in the system -->
                                    </select></sec:authorize>
                                    <br>

                                </div>

                                <div class="form-group col-sm-12">
                                    <h4 id="title-select-header">Post Title: </h4>
                                    <input type="text" class="form-control" id="post-title" name="post-title" placeholder="title" />
                                </div>

                                <div class="form-group col-sm-12">
                                    <h4>Tags: </h4>
                                    Separate by commas<br>
                                    <textarea id="tags" name="tags" form="postSubmit"></textarea>
                                </div>

                                <div class="form-group col-sm-12">
                                    <h4 id="main-post-body-header">Enter Main Post Body:</h4>
                                    <textarea id="post-body" name="post-body" form="postSubmit"></textarea>
                                </div>


                                <div class="form-group col-sm-6">
                                    <h4 id="pub-status-header">Publish</h4>
                                <p id="prev-pub-status"></p>
                                <sec:authorize access="hasRole('ROLE_ADMIN')"><input type="radio" name="pub-status" value="DRAFT"> Draft
                                    <br>
                                    <input type="radio" name="pub-status" id="pub-status" value="PUBLISH"> Publish
                                    <br>
                                    <input type="radio" name="pub-status" id="pub-status" value="DELETE"> Delete</sec:authorize>
                                <br>
                                <sec:authorize access="hasRole('ROLE_AUTHOR')"><input type="radio" id="pub-status" name="pub-status" value="DRAFT" checked>Pending Approval</sec:authorize>
                            </div>

                                <sec:authorize access="hasRole('ROLE_ADMIN')"><button type="submit" id="add-post" class="btn btn-default col-sm-6">Submit Post</button></sec:authorize>
                                <sec:authorize access="hasRole('ROLE_ADMIN')"><button type="submit" id="update-post" class="btn btn-default col-sm-6">Update Post</button></sec:authorize>
                                
                                <sec:authorize access="hasRole('ROLE_AUTHOR')"><button type="submit" id="add-post-single" class="btn btn-default col-sm-6">Submit Post</button></sec:authorize>
                                <sec:authorize access="hasRole('ROLE_AUTHOR')"><button type="submit" id="update-post" class="btn btn-default col-sm-6">Update Post</button></sec:authorize>
                        </form>
                    </div>



                </div>     





                <section id="sidebar" class="sidebar-default hidden-xs col-sm-3">
                    <h2>Control Panel</h2>
                
                    
                    
                
                <sec:authorize access="hasRole('ROLE_ADMIN')"><h4>Edit Posts and Drafts</h4>
                    <ul id="post-edit-list" class="post-list sidebar-nav"></ul>
                    
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_AUTHOR')"><h4>Drafts and Submissions</h4>
                    <ul id="post-edit-list" class="post-list sidebar-nav"></ul></sec:authorize>


                <sec:authorize access="hasRole('ROLE_ADMIN')"><h4>Pages</h4>
                    <ul id="page-edit-list" class="post-list sidebar-nav"></ul></sec:authorize>

                    <sec:authorize access="hasRole('ROLE_ADMIN')"><h4>Users</h4>

                        <ul id="user-edit-list" class="sidebar-nav"></ul></sec:authorize>

                        <div class="sidebar-panel" id="add-user-panel">
                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')"><form role="form" name="userSubmit" id="userSubmit">
                                <h5>Add User</h5>

                                <div class="form-group">
                                    <h6>User Information: </h6>
                                    <input type="text" id="username" placeholder="user" />
                                    <br>
                                    <input type="text" id="password" placeholder="password" />
                                    <br>
                                    <input type="text" id="email" placeholder="email@email.com" />
                                </div>

                                <div class="form-group">
                                    <h6>Role: </h6>
                                    <select name="role-select" id="role-select">
                                        <option value="ROLE_ADMIN" id="administrator">Administrator</option>
                                        <option value="ROLE_AUTHOR" id="author">Author</option>

                                    </select> 
                                </div>

                                <div class="form-group">
                                    <button type="submit" id="add-user" class="btn btn-default">Add User</button>
                                </div></form></sec:authorize>



                        </div>
                    </section>


                    <section id="big-sidebar" class="sidebar-default big-sidebar-hidden hidden-sm hidden-md hidden-lg hidden-xl">
                        <h2>Control Panel</h2>

                        <h4>Posts</h4>
                        <ul id="post-edit-list" class="post-list sidebar-nav"></ul>

                        <h4>Pages</h4>
                        <ul id="page-edit-list" class="post-list sidebar-nav"></ul>

                        <h4>Users</h4>

                        <ul id="user-edit-list" class="sidebar-nav"></ul>

                        <div class="sidebar-panel" id="add-user-panel">
                            <form role="form" name="userSubmit" id="userSubmit">
                                <h5>Add User</h5>

                                <div class="form-group">
                                    <h6>User Information: </h6>
                                    <input type="text" id="username" placeholder="user" />
                                    <br>
                                    <input type="text" id="password" placeholder="password" />
                                    <br>
                                    <input type="text" id="email" placeholder="email@email.com" />
                                </div>

                                <div class="form-group">
                                    <h6>Role: </h6>
                                    <select name="role-select" id="role-select">
                                        <option value="ROLE_ADMIN" id="administrator">Administrator</option>
                                        <option value="ROLE_USER" id="author">Author</option>

                                    </select> 
                                </div>

                                <div class="form-group">
                                    <button type="submit" id="add-user" class="btn btn-default">Add User</button>
                                </div></form>



                        </div>
                    </section>
                </div>


            </div>


            <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src='//cdn.tinymce.com/4/tinymce.min.js'></script>
        <script src="${pageContext.request.contextPath}/js/layout.js"></script>
        <script src="${pageContext.request.contextPath}/js/moment.js"></script>
        <script src="${pageContext.request.contextPath}/js/submitAndEdit.js"></script>
    </body>
</html>

