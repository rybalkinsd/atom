Messages = Class.extend({
    move: function (direction) {
        var template = {
            topic: "MOVE",
            data: {}
        };

        if (direction === 'up') {
            template.data.direction = { x: 0, y: 1 }
        } else if (direction === 'down') {
            template.data.direction = { x: 0, y: -1 }
        } else if (direction === 'left') {
            template.data.direction = { x: -1, y: 0 }
        } else if (direction === 'right') {
            template.data.direction = { x: 1, y: 0 }
        }

        return JSON.stringify(template)
    }
});

gMessages = new Messages();