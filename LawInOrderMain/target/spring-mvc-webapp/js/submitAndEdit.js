/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//populates post/pages/user lists in the admin sidebar
callForPosts();
callForPages();
callForAuthors();
callForAuthorsSingle();

//changes button availability when is/isn't an existing post "in the panel" for editing
$('#add-post').toggle(true);
$('#update-post').toggle(false);

//variables

author;
prevPostId;
newPostType;
prevPostDate;
sendDate;
newPostType;
prevPostType;
userId;


//WYSIWIG editor configuration
tinymce.init({
    selector: 'textarea#post-body',
//      theme: 'modern',
//      width: 600,
//      height: 300,

    style_formats: [
//limits users of the WYSIWIG editor to layers of header hierarchy 
        //available within an individual content section per the document
        //structure of the site
        {title: 'Headers', items: [
                {title: 'Header 1', format: 'h3'},
                {title: 'Header 2', format: 'h4'},
                {title: 'Header 3', format: 'h5'},
                {title: 'Header 4', format: 'h6'},
                {title: 'Header 5', format: '<br>strong<br>'},
                {title: 'Header 6', format: '<br>em<br>'}
            ]}],
    plugins: [
        'advlist link lists charmap preview hr anchor spellchecker',
        'searchreplace wordcount visualblocks visualchars code fullscreen nonbreaking'
                ,
        'contextmenu directionality emoticons template '
    ],
    menubar: 'insert',
    schema: 'html5-strict',
    content_css: '',
    toolbar: 'insertfile undo redo | styleselect '
            + '| bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | print preview media fullpage | emoticons'
});




//pull-down selector of available authors, populates the text-entry box when moved
$('#author-opts').change(function () {

    
    author = $('#author-opts').val();
    if (author !== "Select an Author") {
        $('#author').val(author);
        $('#curr-author').text(author);
    }

});


//New content from the admin panel is added via JSON
//this is core function invoked by clicking the add-post button
$('#add-post').click(function (event) {

    event.preventDefault();
//    var authChecker = authorSet();
//    var author = authChecker.getAuthor();
    $.ajax({
        type: 'POST',
        url: 'publish',
        data: JSON.stringify({
            postType: $('input[name=post-type]:checked').val(),
            author: $('#author-opts').val(),
            postTitle: $('#post-title').val(),
            publishDateString: $('#date').val(),
            postBody: tinyMCE.activeEditor.getContent(),
            pubStatus: $('input[name=pub-status]:checked').val(),
            tags: $('#tags').val().split(',')

        }),
        headers:
                {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
        'dataType': 'json'
    })

            .success(function (data, status) {
                $('#post-type').val('');
                $('#post-title').val('');
                $('#author-opts').val('');
                $('#post-title').val('');
                $('#date').valueOf(new Date());
                $('#post-body').val('');
                $('#draft-status').val('');
                $('#tags').val('');
                tinyMCE.activeEditor.setContent('');
            });

    setTimeout(refreshAdminEditLists, 150);
    //without refresh as timeout callback it seemed like it would frequently 
    //'outpace' the server side and refresh without the added posts

});

$('#add-post-single').click(function (event) {

    event.preventDefault();
//    var authChecker = authorSet();
//    var author = authChecker.getAuthor();



    $.ajax({
        type: 'POST',
        url: 'publish',
        data: JSON.stringify({
            postType: $('input[name=post-type]:checked').val(),
            author: $('#author-opts-single').val(),
            postTitle: $('#post-title').val(),
            publishDateString: $('#date').val(),
            postBody: tinyMCE.activeEditor.getContent(),
            pubStatus: $('input[name=pub-status]:checked').val(),
            tags: $('#tags').val().split(',')

        }),
        headers:
                {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
        'dataType': 'json'
    })

            .success(function (data, status) {
                $('#post-type').val('');
                $('#post-title').val('');
                $('#author-opts-single').val('');
                $('#post-title').val('');
                $('#date').valueOf(new Date());
                $('#post-body').val('');
                $('#draft-status').val('');
                $('#tags').val('');
                tinyMCE.activeEditor.setContent('');
            });
    setTimeout(refreshAdminEditLists, 150);
//without refresh as timeout callback it would sometimes 
//'outpace' the server side and refresh without the newly-added posts
//150ms timer seems like it should be enough for most real-world http latencies
//and is less noticeable than longer earlier delays I tried

});




//main AJAX post-update method fires when selecting update-post button
//since posts processed here already have postId and already have a 
//post type (blog post or static page) assigned to them the main and subsidiary 
//functions here are more complex than for the add-post scenario

$('#update-post').click(function (event) {

    event.preventDefault();
    //var prevType = prevPostTyper();
    prevPostId = $('#existing-post-id').val();
    //var prevPostTypeCheck = prevChecker.getPrevPostType();

    newPostType = $('input[name=post-type]:checked').val();
    //var authChecker = authorSet();
    //var author = authGet.getAuthor();
    //prevPostDate = postDateTracker();
    //var prevDate = prevPostDate.getPrevPostDate();

    

    if ((prevPostDate.dayOfMonth) <= 9)
    {
        var dayValString = "0" + prevPostDate.dayOfMonth;
    }
    else
    {
        var dayValString = prevPostDate.dayOfMonth.toString();
    }


    var yearString = prevPostDate.year.toString();



    if ((prevPostDate.monthValue) <= 9)
    {
        var monthValString = "0" + prevPostDate.monthValue;
    }
    else {
        var monthValString = prevPostDate.monthValue.toString();
    }


    var prevDateString = yearString + "-" + monthValString + "-" + dayValString;

    if ($('#date').val() === prevDateString) {
        sendDate = ($('#date').val());

    }
    else {
        sendDate = prevDateString;
    }


    //if (prevPostTypeCheck !== newPostType) {
    if (prevPostType !== newPostType) {
//writes "new" post of new type
        editPost(newPostType, author, sendDate);
    }

//if post-type has not been changed, the second option PUTS the 
//existing content back to the server to update the entry
    else {
        editPost(prevPostType, author, sendDate);
    }
    ;
});


//AJAX put to modify post via its existing postID
//function editPostWithoutPostTypeChange(prevPostId, prevPostType, author, sendDate) {
function editPost(type, author, date) {

    $.ajax({
        type: 'PUT',
        url: 'post/' + oldPostId,
        data: JSON.stringify({
            postType: type,
            prevPostType: prevPostType,
            postId: oldPostId,
            author: author,
            postTitle: $('#post-title').val(),
            publishDateString: sendDate,
            postBody: tinyMCE.activeEditor.getContent(),
            pubStatus: $('input[name=pub-status]:checked').val(),
            tags: $('#tags').val().split(',')
        }),
        headers:
                {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
        'dataType': 'json'
    })
            .success(function (data, status) {

                $('#post-type').val('');
                $('#post-title').val('');
                $('#author').val('');
                $('#post-title').val('');
                $('#date').valueOf(new Date());
                $('#post-body').val('');
                $('#draft-status').val('');
                $('#tags').val('');
                tinyMCE.activeEditor.setContent('');
            });
}

//AJAX POST to add content into new Post of the updated type
//b/c of two auto-incrementing tables not synchronized and possibility
//of non-unique id when trying to switch type, need to handle here with 
//server (post-processor) backstop functionality
//(drawback of splitting "static" pages into own table but using one editor
//for both types)
//function updateWithContentPostTypeChange(prevPostId, newPostType, author, sendDate) {
//
//    $.ajax({
//        type: 'POST',
//        url: 'publish',
//        data: JSON.stringify({
//            postType: newPostType,
//            prevPostType: prevPostType,
//            author: author,
//            postTitle: $('#post-title').val(),
//            publishDateString: sendDate,
//            postBody: tinyMCE.activeEditor.getContent(),
//            pubStatus: $('input[name=pub-status]:checked').val(),
//            tags: $('#tags').val().split(',')
//
//        }),
//        headers:
//                {
//                    'Accept': 'application/json',
//                    'Content-Type': 'application/json'
//                },
//        'dataType': 'json'
//    })
//
//            .success(function (data, status) {
//                
//                $('#post-type').val('');
//                $('#post-title').val('');
//                $('#author').val('');
//                $('#post-title').val('');
//                $('#date').valueOf(new Date());
//                $('#post-body').val('');
//                $('#draft-status').val('');
//                $('#tags').val('');
//                tinyMCE.activeEditor.setContent('');
//            });
//
//
//}


$('#add-user').click(function (event) {

    event.preventDefault();
    $.ajax({
        type: 'POST',
        url: 'admin/user',
        data: JSON.stringify({
            username: $('#username').val(),
            email: $('#email').val(),
            password: $('#password').val(),
            role: $('#role-select').val()

        }),
        headers:
                {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
        'dataType': 'json'
    })
            .success(function (data, status) {
                $('#author').val('');
                $('#email').val('');
                $('#password').val('');
            });
    callForAuthors();
});


function callForAuthors() {

    clearAuthorsLists();
    $.ajax({
        url: "admin/userlist"

    }).success(function (data, status) {

        fillAuthorSelector(data, status);
        fillAuthorList(data, status);
    });
}


function callForAuthorsSingle() {
    $.ajax({
        url: "admin/singleuser"
    }).success(function (data, status) {
        fillAuthorSelectorSingle(data, status);
    });
}


function fillAuthorSelector(users, status) {


    $.each(users, function () {
        $('#author-opts').append($('<option>').attr("value", this).text(this));
    });
}

function fillAuthorSelectorSingle(user, status) {
    $.each(user, function () {
        $('#author-opts-single').append($('<option>').attr("value", this).text(this));
    });
}


function fillAuthorList(users, status) {
    $.each(users, function (user) {

        var userListItem = "id" + this + "_list_item";
        $('#user-edit-list').append($('<li>').attr("id", this + "_list_item")
                .html(this + "<a id" + this + "_edit> edit </a>" + " | "
                        + "<a id" + this + "_delete> delete </a>"));
    });
}


function refreshAdminEditLists() {

    callForPosts();
    callForPages();
    callForAuthorsSingle();



}


function callForPosts() {

    clearSidebarPosts();
    $.ajax({
        url: "posts/admin"
    }).success(function (data, status) {

        loadSidebarPosts(data, status);
    });

}


function callForPages() {


    clearSidebarPages();
    $.ajax({
        url: "pages"

    })
            .success(function (data, status) {
                loadSidebarPages(data, status);
            });
}


function clearSidebarPosts() {
    $('#post-edit-list').empty();
}


function clearSidebarPostsAuthor() {
    $('#author-post-edit-list').empty();
}


function clearSidebarPages() {
    $('#page-edit-list').empty();
}

function clearAuthorsLists() {

    $('#user-edit-list').empty();
    $('#author-opts').empty();
}


function loadSidebarPosts(listPosts, status) {


    $.each(listPosts, function (index, post) {


        $('#post-edit-list').append(
                $('<li>').html("<a id=" + post.postId + "_edit_post>"
                + post.postTitle + "</a>, " + post.author + "</br>"
                + post.publishDate.month + " " + post.publishDate.dayOfMonth
                + " " + post.publishDate.year + "<br>"
                + "(" + post.pubStatus + ")"));

        $('#' + post.postId + '_edit_post').click(function () {

            loadPostToEdit(post.postId);

        });


    });
}

function loadSidebarPostsAuthor(listPosts, status) {


    $.each(listPosts, function (index, post) {


        $('#post-edit-list').append(
                $('<li>').html("<a id=" + post.postId + "_edit_post>"
                + post.postTitle + "</a>, " + post.author + "</br>"
                + post.publishDate.month + " " + post.publishDate.dayOfMonth
                + " " + post.publishDate.year + "<br>"
                + "(" + post.pubStatus + ")"));

        $('#' + post.postId + '_edit_post').click(function () {

            loadPostToEdit(post.postId);

        });


    });
}
function loadSidebarPages(listPages, page) {


    $.each(listPages, function (index, page) {
        $('#page-edit-list').append(
                $('<li>').html("<a id=" + page.postId + "_edit_page>"
                + page.postTitle + "</a>, " + page.author + "</br>"
                ));

        $('#' + page.postId + '_edit_page').click(function () {

            loadPostToEdit(page.postId);

        });
    });
}




function loadPostToEdit(postId) {

   

    changeHeadingsToEditingMode();

    $('#add-post').toggle(false);
    $('#update-post').toggle(true);

//    if (postType === "POST") {

        $.ajax({
            url: "post/" + postId
        })
                .success(function (post) {

                    userId = post.userId;
                    
                    author = post.author;

                    alertUserToExistingData(post, author);

                    oldPostId = post.postId;
                    $('#existing-post-id').text(post.postId);
                    $('#author-opts').val(post.author);
                    $('#author').val(post.author);
                    
                    $('#post-title').val(post.postTitle);
                    $('#prev-pub-status').val(post.pubStatus);
                    $('#tags').val(post.tags);
                    tinyMCE.activeEditor.setContent(post.postBody);
                });
    }


//    if (postType === "STATIC_PAGE") {
function loadPageToEdit(postId) {

        $.ajax({
            url: "page/" + postId
        })
                .success(function (post) {

                    userId = post.userId;
                    author = post.author;
                    alertUserToExistingData(post, author);
                    oldPostId = post.postId;
                    $('#existing-post-id').val(post.postId);
                    $('#author-opts').val(post.author);
                    $('#author').val(post.author);
                    //prevChecker.setPrevPostType(postType);
                    $('#post-type').val(post.postType);
                    $('#post-title').val(post.postTitle);
                    $('#draft-status').val(post.pubStatus);
                    $('#tags').val(post.tags);
                    tinyMCE.activeEditor.setContent(post.postBody);
                });
    }





function changeHeadingsToEditingMode() {

    $('#enter-post-header').text("Edit/Publish/Unpublish Existing Post");
    $('#post-type-select-header').text("Edit post type?");
    $('#author-select-header').text("Change author?");
    $('#date-select-header').text("Edit published date?");
    $('#title-select-header').text("Edit title?");
    $('#main-post-body-header').text("Edit Main Post Body: ");
    $('#pub-status-header').text("Change Publication Status?");
}




function alertUserToExistingData(post, author) {


    prevPostType = post.postType;

    $('#prev-pub-type').html("Select to change type from:<br> "
            + "<span class='highlight'>" + post.postType.toLowerCase()
            .capitalizeFirstLetter() + "</span>");
    $('#prev-pub-status').html("Select to change from: "
            + "<span class='highlight'>" + post.pubStatus);
    $('#curr-author').html("<span class='highlight'>"
            + author + "</span>");

    prevPostDate = post.publishDate;

    $('#prev-pub-date').html("Select to change from:<br> " + "<span class='highlight'>"
            + post.publishDate.month.toLowerCase()
            .capitalizeFirstLetter() + " " + post.publishDate.dayOfMonth
            + " " + post.publishDate.year + "</span>");

    //$('#prev-pub-date').text(date.toString());




}


String.prototype.capitalizeFirstLetter = function () {
    return this.charAt(0).toUpperCase() + this.slice(1);
};


 