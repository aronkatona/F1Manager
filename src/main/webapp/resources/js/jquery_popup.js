$(document).ready(function() {

    $("#login #cancel").click(function() {
        $(this).parent().parent().hide();
    });

    $("#onclick").click(function() {
        $("#contactdiv").css("display", "block");
    });

    $("#contact #cancel").click(function() {
        $(this).parent().parent().hide();
    });


//login form popup login-button click event
    $("#login").click(function() {
        var username = $("#username").val();
        if (username == "" ||username == null)
        {
            alert("Username is empty...");
            
            return false;
        }
        else
        {
            $("#logindiv").css("display", "none");
        }
    });

});

 