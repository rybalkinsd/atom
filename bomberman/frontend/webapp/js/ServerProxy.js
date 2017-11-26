ServerProxy = Class.extend({
    gameServerUrl: "localhost:8090",
    matchMakerUrl: "localhost:8080/matchmaker/join",
    gameId: "1234",

    socket: null,

    handler: {},

    init: function () {
        this.handler['REPLICA'] = gMessages.handleReplica;
        this.handler['POSSESS'] = gMessages.handlePossess;

        var self = this;
        gInputEngine.subscribe('up', function () {
            self.socket.send(gMessages.move('up'))
        });
        gInputEngine.subscribe('down', function () {
            self.socket.send(gMessages.move('down'))
        });
        gInputEngine.subscribe('left', function () {
            self.socket.send(gMessages.move('left'))
        });
        gInputEngine.subscribe('right', function () {
            self.socket.send(gMessages.move('right'))
        });
        gInputEngine.subscribe('bomb', function () {
            self.socket.send(gMessages.plantBomb())
        });
    },

    getSessionIdFromMatchMaker: function () {
        var that = this;
        var login = $("#loginInput").val();
        if(!login){
            alert("Please input login");
            console.log("Empty login, retry login");
        }
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "name": login
            },
            dataType: 'text',
            success: function(data){
                that.gameId=data;
                console.log("Matchmaker returned gameId=" + data);
                that.connectToGameServer(that.gameId, login);
            },
            error: function(){
                alert("Matchmaker request failed, use default gameId=" + that.gameId);
                console.log("Matchmaker request failed, use default gameId=" + that.gameId);
                that.connectToGameServer(that.gameId, login);
            },
            processData: false,
            type: 'POST',
            url: that.matchMakerUrl
        });
    },

    connectToGameServer: function (gameId, login) {
        var self = this;
        this.socket = new WebSocket("ws://" + this.gameServerUrl + "/game/connect?gameId=" + gameId + "&name=" + login);

        this.socket.onopen = function () {
            console.log("Connection established.");
        };

        this.socket.onclose = function (event) {
            if (event.wasClean) {
                console.log('closed');
            } else {
                console.log('alert close');
            }
            console.log('Code: ' + event.code + ' cause: ' + event.reason);
        };

        this.socket.onmessage = function (event) {
            var msg = JSON.parse(event.data);
            if (self.handler[msg.topic] === undefined)
                return;

            self.handler[msg.topic](msg);
        };

        this.socket.onerror = function (error) {
            console.log("Error " + error.message);
        };
    }

});
