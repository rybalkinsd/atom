Login = Class.extend({

    login: function (){
        var login = document
            .getElementById("loginInput")
            .value;
        var password = document
            .getElementById("loginPassword")
            .value;
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": login,
                "password": password
            },
            dataType: 'text',
            type: 'POST',
            url: gGameEngine.serverProxy.matchMakerUrl + "login",
            success: function (data) {
                if (data === "+") {
                    document.getElementById("loginModal").style.display = "none";
                    document.getElementById('body').style.overflow='auto';
                    document.getElementById("loginButton").style.display = "none";
                    document.getElementById("signupButton").style.display = "none";
                    gGameEngine.serverProxy.playerName = login;
                    document.getElementById("loginedInfo").innerHTML = "Login: <b>" + login + "</b>";
                    document.getElementById("signOut").style.display = "block";
                    document.getElementById("loginedInfo").style.display = "inline";
                    document.getElementById("historyButton").style.display = "block";
                    document.getElementById("game").style.display = "block";
                } else {
                    document.getElementById("loginError").innerHTML = "<b>Incorrect login or password</b>";
                    document.getElementById("loginError").style.display = "inline";
                    document.getElementById("loginPassword").value = "";
                }
            },
            error: function () {
                document.getElementById("loginError").innerHTML = "<b>Cannot login due to server error, try again late</b>";
                document.getElementById("loginError").style.display = "inline";
                document.getElementById("loginPassword").value = "";
            }
        })

	}

});