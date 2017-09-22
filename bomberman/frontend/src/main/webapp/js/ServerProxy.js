ServerProxy = Class.extend({

    host: "localhost:8090",

    socket: null,

    handler: {},

    init: function() {
        this.handler['REPLICA'] = gMessages.handleReplica;
        this.handler['POSSESS'] = gMessages.handlePossess;

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

        this.initSocket();
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
            var msg = JSON.parse(event.data);
            if (self.handler[msg.topic] === undefined)
                return;

            self.handler[msg.topic](msg);
        };

        this.socket.onerror = function(error) {
            console.log("Error " + error.message);
        };
    }

});
