/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//****************ON PAGE LOAD********************


//if (location.pathname === "/LawInOrder/") {
    $.ajax({
        url: "posts"
    }).success(function (data, status) {

        loadPosts(data, status);

    });
//}
//
//else if (location.pathname.match("LawInOrder") !== null){
//    
//    loadOnePost();
//    
//}


//****************FUNCTIONS***********************

//POPULATE PAGE WITH BLOG POSTS

var loadPosts = function (listPosts, status) {
    clearPage();

    $.each(listPosts, function (index, post) {
        var tags = post.tags;
        var month = post.publishDate.month;
        var monthAbbrev = month.toLowerCase().capitalizeFirstLetter();


        $('#post-list').append(
                $('<section class="post-in-list post">').html(
                '<h2 class=post-title>'
                + '<a href=' + post.postUrl +' ' + 'class=post-link data-post-id='
                + post.postId + '>' + post.postTitle
                + '</a><h3 class=post-byline><span class=byline-author>'
                + post.author + '</span>, <span class=byline-date>' + monthAbbrev
                + " " + post.publishDate.dayOfMonth
                + " " + post.publishDate.year + '</span></h3>'
                //+ '<p class=post-body>' + post.postBody + '<ul id=post-'
                + '<div class=post-excerpt id=' + post.postId + '_post-excerpt>'
                + post.postExcerpt + '    <strong><em><a id=' + post.postId + '_rest>Read '
                + ' the Rest >>>' + '</a></strong></em></div><div class=post-body id=' 
                + post.postId + '_post-body>' + post.postBody + '</div>'
                + '<ul id=' + post.postId + "-tags class=tag-area tag-list></ul>")
                );
        
        $('.post-body').toggle(false);
        $('#' + post.postId + '_rest').click(function(){
            $('#' + post.postId + '_post-body').toggle(true);
            $('#' + post.postId + '_post-excerpt').toggle(false);
        });

        $.each(post.tags, function (index, tag) {
            $('#' + post.postId + '-tags').append($('<li class="tag-list-item">').html(
                    '<a href="#" class="tag" data-tag-name="' + tag + '">#' + tag + '</a>'
                    ));
        });

    });



    //CODE FOR COLLAPSIBLE POSTS
//==========================
//    var previewChars = 200;
//    var trailingText = '...';
//    var readMoreText = "Read More >>";
//    var readLessText = "Minimize <<";
//
//    $(".post-excerpt").each(function () {
//        var content = $(this).html();
//
//        if (content.length > previewChars) {
//            var previewText = content.substr(0, previewChars);
//            var hiddenText = content.substr(previewChars);
//
//            var modifiedHTML = previewText +
//                    '<span class="trailing-text">' + trailingText + '</span>' +
//                    '<span class="rest-of-post">' + hiddenText + '</span><br/>' +
//                    '<a href="" class="read-more-link minimized">' + readMoreText + '</a>';
//
//
//            $(this).html(modifiedHTML);
//
//        }
//
//    });
//

};



String.prototype.capitalizeFirstLetter = function () {
    return this.charAt(0).toUpperCase() + this.slice(1);
};


var clearPage = function () {
    $('#post-list').empty();
};



////TITLE LINKS FOR INDIVIDUAL POSTS
//$('#post-area').on('click', '.post-link', function (event) {
//    event.preventDefault();
//
//    $.ajax({
//        url: "post/" + $(this).data('post-id')
//    }).success(function (data, status) {
//        loadPosts(data, status);
//    });
//});





