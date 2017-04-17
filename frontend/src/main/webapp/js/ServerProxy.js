ServerProxy = Class.extend({

    host: "192.168.0.103:8090",

    socket: null,

    init: function() {
        this.initSocket();
        var self = this;
        gInputEngine.subscribe('up', function() {
            self.socket.send(gMessages.move('up'))
        });
        gInputEngine.subscribe('down', function() {
            self.socket.send(gMessages.move('down'))
        });
        gInputEngine.subscribe('left', function() {
            self.socket.send(gMessages.move('left'))
        });
        gInputEngine.subscribe('right', function() {
            self.socket.send(gMessages.move('right'))
        });
        gInputEngine.subscribe('bomb', function() {
            self.socket.send(gMessages.plantBomb())
        });
    },

    initSocket: function() {
        var self = this;
        this.socket = new WebSocket("ws://" + this.host + "/events");

        this.socket.onopen = function() {
            console.log("Connection established.");
        };

        this.socket.onclose = function(event) {
            if (event.wasClean) {
                console.log('closed');
            } else {
                console.log('alert close');
            }
            console.log('Code: ' + event.code + ' cause: ' + event.reason);
        };

        this.socket.onmessage = function(event) {
            console.log("D@ta received");
            self.onReplicaReceived(event.data);
        };

        this.socket.onerror = function(error) {
            console.log("Error " + error.message);
        };
    },

    onReplicaReceived: function (msg) {
        var parsedMsg = JSON.parse(msg);
        var gameObjects = JSON.parse(parsedMsg.data).objects;
        var replicatedObjects = [];

        for (var i = 0; i < gameObjects.length; i++) {
            var obj = gameObjects[i];
            console.log(i);
            if (!obj.hasOwnProperty('type')) {
                console.log('хуй');
            }
            console.log(obj.type);
            console.log(obj.id);
            console.log(obj.position);

        }

        console.log(replicatedObjects);
    }


});

gServerProxy = new ServerProxy();