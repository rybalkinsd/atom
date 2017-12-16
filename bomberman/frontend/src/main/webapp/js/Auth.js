Authentication = Class.extend({
	loggined: false,
	login: "",
	password: "",

	ShowLogin: function() {
	    StartHide();
	    PopInShow();
	},

    ShowRegistry : function() {
        StartHide();
        PopUpShow();
    },

    CancelEvent: function() {
        PopInHide();
        PopUpHide();
        StartShow();
    },

	Login: function() {
		var inner_login = $("#popinlog").val();
		var inner_password = $("#popinpass").val();
		if (!inner_login) {
			alert("login field is empty");
			return false;
		}
		if (!inner_password) {
			alert("password field is empty");
			return false;
		}
		this.login = inner_login;
		this.password = inner_password;
		var result = serverProxy.loginFromMatchMaker(this.login, this.password);
		if (result[0]) {
			PopInHide();
			StartHide();
			this.loggined = true;
			return true;
		} else {
			alert(result[1]);
			return false;
		}
	},
	
	Registry: function() {
		var inner_login = $("#popreglog").val();
		var password1 = $("#popregpass1").val();
		var password2 = $("#popregpass2").val();
		if (!inner_login) {
			alert("login field is empty");
			return false;
		}
        if (/\s/g.test(inner_login)) {
            alert("login cannot contain whitespace");
            return false;
        }
		if (!password1) {
			alert("enter password field is empty");
			return false;
		}
		if (!password2) {
			alert("confirm password field is empty");
			return false;
		}
		if (password1 == password2) {
			this.login = inner_login;
			this.password = password1;
			var result = serverProxy.registryFromMatchMaker(this.login, this.password);
			if (result[0]) {
				serverProxy.getSessionIdFromMatchMaker(this.login, this.password);
				PopUpHide();
				StartHide();
				this.loggined = true;
				return true;
			} else {
				alert(result[1]);
				return false;
			}
		} else {
			alert("ERROR! Passwords don't match!");
			return false;
		}
	}, 
	
	Logout: function() {
	    serverProxy.logout(this.login, this.password);
		this.loggined = false;
	}
});
auth = new Authentication();

$(document).ready(function() {
    StartShow();
    //Скрыть PopIn при загрузке страницы
    PopInHide();
    //Скрыть PopUp при загрузке страницы
    PopUpHide();
});
//Функция отображения PopIn
function PopInShow() {
    $("#popin").show();
}
//Функция скрытия PopIn
function PopInHide() {
    $("#popin").hide();
}
//Функция отображения PopUp
function PopUpShow() {
    $("#popup").show();
}
//Функция скрытия PopUp
function PopUpHide() {
    $("#popup").hide();
}
function StartShow() {
    $("#popstart").show();
}
function StartHide() {
    $("#popstart").hide();
}