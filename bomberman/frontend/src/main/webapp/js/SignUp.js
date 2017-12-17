SignUp = Class.extend({

    signUp: function () {
        var login = document
            .getElementById("signUpLoginInput")
            .value;
        var password = document
            .getElementById("signUpPasswordInput")
            .value;
        var passwordRepeat = document
            .getElementById("signUpPasswordRepeatInput")
            .value;
        if (password.length > 20) {
            document.getElementById("signUpRule").style.color = "red";
            document.getElementById("signUpPasswordInput").value = "";
            document.getElementById("signUpPasswordRepeatInput").value = "";
        }
        else {
            if (password === passwordRepeat) {
                $.ajax({
                    contentType: 'application/x-www-form-urlencoded',
                    data: {
                        "login": login,
                        "password": password
                    },
                    dataType: 'text',
                    type: 'POST',
                    url: gGameEngine.serverProxy.matchMakerUrl + "register",
                    success: function (data) {
                        if (data === "+") {
                            document.getElementById("signupModal").style.display = "none";
                            document.getElementById('body').style.overflow='auto';
                            document.getElementById("loginButton").style.display = "none";
                            document.getElementById("signupButton").style.display = "none";
                            gGameEngine.serverProxy.playerName = login;
                            document.getElementById("loginedInfo").innerHTML = "Login: <b>" + login + "</b>";
                            document.getElementById("signOut").style.display = "block";
                            document.getElementById("loginedInfo").style.display = "inline";
                            document.getElementById("historyButton").style.display = "block";
                            document.getElementById("game").style.display = "block";
                        }
                        else {
                            document.getElementById("signUpError").innerHTML = "<b>This login is already taken</b>";
                            document.getElementById("signUpRule").style.color = "black";
                            document.getElementById("signUpError").style.display = "inline";
                            document.getElementById("signUpPasswordInput").value = "";
                            document.getElementById("signUpPasswordRepeatInput").value = "";
                        }
                    },
                    error: function () {
                        document.getElementById("signUpError").innerHTML = "<b>Cannot register due to server error, try again later</b>";
                        document.getElementById("signUpRule").style.color = "black";
                        document.getElementById("signUpError").style.display = "inline";
                        document.getElementById("signUpPasswordInput").value = "";
                        document.getElementById("signUpPasswordRepeatInput").value = "";
                    }
                })
            }
            else {
                document.getElementById("signUpError").innerHTML = "<b>Passwords don't match</b>";
                document.getElementById("signUpError").style.display = "inline";
                document.getElementById("signUpPasswordInput").value = "";
                document.getElementById("signUpPasswordRepeatInput").value = "";
            }
        }
    }
})