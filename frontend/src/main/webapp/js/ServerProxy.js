ServerProxy = Class.extend({

    host: "localhost:8090",

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
    },

    initSocket: function() {
        var self = this
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
            self.onReplicaReceived(JSON.parse(event.data));
        };

        this.socket.onerror = function(error) {
            console.log("Error " + error.message);
        };
    },

    onReplicaReceived: function (msg) {
        var gameObjects = JSON.parse(msg.data).objects;
        var replicatedObjects = [];

        for (var i = 0; i < gameObjects.length; i++) {
            var obj = gameObjects[i];
            console.log(i);
            if (!obj.hasOwnProperty("type")) {
                console.log(obj);
            }
            if (obj.type === "Wood") {
                   replicatedObjects.push(
                           new Tile('wood', { x: obj.position.x, y: obj.position.y })
                   );
            } else if (obj.type === "Wall") {
                   replicatedObjects.push(
                       new Tile('wall', { x: obj.position.x, y: obj.position.y })
                   );
            } else if (obj.type === "Pawn") {
                   replicatedObjects.push(
                       new Player({x: obj.position.x, y: obj.position.y})
                   );
            }
        };

        console.log(replicatedObjects);
    }
});

gServerProxy = new ServerProxy();