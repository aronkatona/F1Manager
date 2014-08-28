$(document).ready(function() {

    $("#login #cancel").click(function() {
        $(this).parent().parent().hide();
        $("#main").show();
    });

    $("#onclick").click(function() {
        $("#contactdiv").css("display", "block");
        $("#main").hide();
    });
    
    $("#onclickSignup").click(function() {
        $("#signupdiv").css("display", "block");
    });

    $("#contact #cancel").click(function() {
        $(this).parent().parent().hide();
        $("#main").show();
    });
    
    $("#register #cancel").click(function() {
        $(this).parent().parent().hide();
        $("#main").show();
    });
    



//login form popup login-button click event
    $("#login").click(function() {
        var username = $("#username").val();
        var password = $("#passwordLogin").val();
        if (username == "" ||username == null)
        {
            alert("Username is empty...");
            
            return false;
        }
        else if(password == "" || password == null)
        {
        	alert("Password is empty...");
        	return false;
        }
        else
        {
            $("#logindiv").css("display", "none");
        }
    });
    
//login form popup login-button click event
    $("#signup").click(function() {
    	 var username = $("#yourname").val();
    	 var password = $("#passwordSignup").val();
         if (username == "" ||username == null)
         {
             alert("hi" + username);
             
             return false;
         }
         else if(password == "" || password == null)
         {
         	alert("Password is empty...");
         	return false;
         }
         else
         {
             $("#logindiv").css("display", "none");
         }
    });
    
});

 