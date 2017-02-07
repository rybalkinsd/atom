InputProxy = Class.extend({

    socket: new WebSocket("ws://localhost:8090/events/"),

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
            console.log("D@ta " + event.data);
        };

        this.socket.onerror = function(error) {
            console.log("Error " + error.message);
        };
    }

});

gInputProxy = new InputProxy();