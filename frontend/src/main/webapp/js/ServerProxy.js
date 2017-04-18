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
            self.onReplicaReceived(event.data);
        };

        this.socket.onerror = function(error) {
            console.log("Error " + error.message);
        };
    },

    onReplicaReceived: function (msg) {
        var parsedMsg = JSON.parse(msg);
        var gameObjects = JSON.parse(parsedMsg.data).objects;


        for (var i = 0; i < gameObjects.length; i++) {
            var obj = gameObjects[i];
            if (!obj.hasOwnProperty('type')) {
                // console.log('хуй');
            }
            if (obj.type === 'Pawn') {
                var position = Utils.getEntityPosition(obj.position);

                var player = gGameEngine.players.find(function (el) {
                    return el.id === obj.id;
                });

                if (player) {
                    player.bmp.x = position.x;
                    player.bmp.y = position.y;
                } else {
                    player = new Player(position);
                    player.id = obj.id;
                    gGameEngine.players.push(player);
                }

            } else if (obj.type === 'Bomb') {
                var position = Utils.getEntityPosition(obj.position);

                var bomb = new Bomb(2, position, 2);
                gGameEngine.bombs.push(bomb);
                gGameEngine.stage.addChild(bomb.bmp);
            }
        }
    }

});
