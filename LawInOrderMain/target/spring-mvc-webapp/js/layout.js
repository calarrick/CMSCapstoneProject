/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//AFFIXING TOP-LEVEL NAV 
//$('#top-level-nav').affix({
//    offset: {
//        top: $('#header-div').position().top + $('#header-div').outerHeight(true)
//    }
//});

//var topNavFixedHeight = $('.affix').css("height");
//
//$('#post-area').css("padding-top", topNavFixedHeight);



$('#nav-row').affix({
    offset: {
        top: $('#nav-row').offset()
                .top
    }
});
    
    
    
//}).$('#post-area').css("padding-top", function(index,value){
//    
//    this = $('.affix').css("height");
//    
//    
//});



//POPULATING SIDEBAR WITH TAGS  
$.ajax({
    url: 'toptags'
}).success(function (data, status) {
    $.each(data, function (index, tag) {
// NEED TO ADD NUMBER OF TAGS NEXT TO TAG NAME
        $('.top-tags').append(
                $('<li>').html('<a href=# class="tag" data-tag-name="' + tag.tagName + '">#' + tag.tagName + '</a>'));

    });
});

//POPULATING SIDEBAR WITH STATIC PAGE LINKS
$.ajax({
    url: 'pages'
}).success(function (data, status) {
    $.each(data, function (index, page) {

        $('.static-pages').append(
                $('<li>').html('<a href="'+page.postUrl+'">' + page.postTitle + '</a>'));

    });
});


////CODE FOR COLLAPSIBLE POSTS
////==========================
//
//move to index, needs to be in scope with the post generation
//var previewChars = 200;
//var trailingText = '...';
//var readMoreText = "Read More >>";
//var readLessText = "Minimize <<";
//
//$(".post-excerpt").each(function () {
//    var content = $(this).html();
//
//    if (content.length > previewChars) {
//        var previewText = content.substr(0, previewChars);
//        var hiddenText = content.substr(previewChars);
//
//        var modifiedHTML = previewText +
//                '<span class="trailing-text">' + trailingText + '</span>' +
//                '<span class="rest-of-post">' + hiddenText + '</span><br/>' +
//                '<a href="" class="read-more-link minimized">' + readMoreText + '</a>';
//
//
//        $(this).html(modifiedHTML);
//
//    }
//
//});


//SWITCHING BETWEEN DEFAULT AND FIXED SIDEBAR   
$(document).scroll(function () {
    var topOffset = $('#post-area').position().top
//            + $('#nav-row').outerHeight(true)
    ;
    console.log(topOffset);
    if ($(document).scrollTop() > topOffset && $('#sidebar').hasClass('sidebar-default')) {

        $('#sidebar').removeClass('sidebar-default');
        $('#sidebar').addClass('sidebar-fixed');
        $('#sidebar').css("left", $("#post-area").offset().left + $('#post-area').outerWidth(true));
    } else if ($(document).scrollTop() <= topOffset && $('#sidebar').hasClass('sidebar-fixed')) {
        $('#sidebar').removeClass('sidebar-fixed');
        $('#sidebar').addClass('sidebar-default');
        $('#sidebar').css("left", "auto");
    }

});

//DYNAMICALLY POSITIONING FIXED SIDEBAR
$(window).on('resize', function () {
    if ($('#sidebar').hasClass("sidebar-fixed")) {
        var leftOffset = $("#post-area").offset().left + $('#post-area').outerWidth(true);
        $("#sidebar").css('left', leftOffset);
    }

});


//DISPLAY BIG SIDEBAR ON ICON CLICK
$('#toggle-sidebar').click(function (event) {
    event.preventDefault();

    if ($('#big-sidebar').hasClass("big-sidebar-hidden")) {
        $('#big-sidebar').removeClass("big-sidebar-hidden");
        $('#big-sidebar').addClass("big-sidebar-visible");
    } else if ($('#big-sidebar').hasClass("big-sidebar-visible")) {
        $('#big-sidebar').addClass("big-sidebar-hidden");
        $('#big-sidebar').removeClass("big-sidebar-visible");
    }
});

//CLICKABLE TAGS
$('body').on('click', '.tag', function (event) {
    event.preventDefault();
    var tagName = $(this).data('tag-name');
    var baseURL = $('body').data('base-url');



    $.ajax({
        url: 'tag/' + tagName
    }).success(function (data, status) {
        $('#display-searched-tag').html('<h2>Displaying posts with tag #' + tagName + ':</h2>');
        loadPosts(data, status);
    });

});


