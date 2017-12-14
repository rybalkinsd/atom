ServerProxy = Class.extend({
    gameServerUrl: "localhost:8090",
    matchMakerUrl: "http://localhost:8080",
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
        var name = $('#name').serialize();
        console.log(name);
        if(!name){
            alert("Please input login");
            console.log("Empty login, retry login");
        }
        var settings = {
            "method": "POST",
            "crossDomain": true,
            "url": this.matchMakerUrl + "/matchmaker/join",
            "data": name
        }
        $.ajax(settings).done(function(data){
            this.gameId=data;
            console.log("Matchmaker returned gameId=" + data);
            that.connectToGameServer(this.gameId, name);
        }).fail(function(){
            alert("Matchmaker request failed, use default gameId=" + this.gameId);
            console.log("Matchmaker request failed, use default gameId=" + this.gameId);
            that.connectToGameServer(this.gameId, name);
        });
    },

    connectToGameServer : function(gameId, login) {
        var self = this;
        this.socket = new WebSocket("ws://" + this.gameServerUrl + "/events/connect?gameId=" + gameId + "&" + login);

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
            console.log("123")
            console.log(event)
            var msg = JSON.parse(event.data);
            console.log(msg);
            if (self.handler[msg.topic] === undefined)
                return;

            self.handler[msg.topic](msg);
        };

        this.socket.onerror = function (error) {
            console.log("Error " + error.message);
        };
    }

});
