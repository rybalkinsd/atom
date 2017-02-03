Messages = Class.extend({
    move: function (direction) {
        var template = {
            topic: "MOVE"
        };

        if (direction === "up") {
            template.direction = { x: 0, y: 1 }
        } else if (direction === "down") {
            template.direction = { x: 0, y: -1 }
        } else if (direction === "left") {
            template.direction = { x: -1, y: 0 }
        } else if (direction === "right") {
            template.direction = { x: 1, y: 0 }
        }

        return JSON.stringify(template)
    }
});

gMessages = new Messages();