Messages = Class.extend({
    handler: {},

    init: function () {
        this.handler['Pawn'] = this.handlePawn;
        this.handler['Bomb'] = this.handleBomb;
        this.handler['Wood'] = this.handleTile;
        this.handler['Wall'] = this.handleTile;
        this.handler['Grass'] = this.handleTile;
        // this.handler['Fire'] = this.handleFire;
    },

    move: function (direction) {
        var template = {
            topic: "MOVE",
            data: {}
        };

        template.data.direction = direction.toUpperCase();
        return JSON.stringify(template);
    },

    plantBomb: function () {
        var template = {
            topic: "PLANT_BOMB",
            data: {}
        };

        return JSON.stringify(template);
    },


    handleReplica: function (msg) {
        var gameObjects = JSON.parse(msg.data);

        for (var i = 0; i < gameObjects.length; i++) {
            var obj = gameObjects[i];
            if (gMessages.handler[obj.type] === undefined)
                continue;

            gMessages.handler[obj.type](obj);
        }
    },

    handlePossess: function (msg) {
        console.log('handlePossess');
        console.log('msg.data', msg.data);
        console.log('gGameEngine', gGameEngine.stage);
        gInputEngine.possessed = parseInt(msg.data);
    },

    handlePawn: function(obj) {
        var player = gGameEngine.players.find(function (el) {
            return el.id === obj.id;
        });
        var position = Utils.getEntityPosition(obj.position);

        if (player) {
            player.bmp.x = position.x;
            player.bmp.y = position.y;
        } else {
            console.log(new Date().getTime() + " handel new player " + obj.id);
            player = new Player(obj.id, position);
            gGameEngine.players.push(player);
        }
    },

    handleBomb: function(obj) {
        var bomb = gGameEngine.bombs.find(function (el) {
            return el.id === obj.id;
        });
        var position = Utils.getEntityPosition(obj.position);

        if (bomb) {
            bomb.bmp.x = position.x;
            bomb.bmp.y = position.y;
        } else {
            bomb = new Bomb(obj.id, position);
            gGameEngine.bombs.push(bomb);
        }
    },

    handleTile: function(obj) {
        var tile = gGameEngine.tiles.find(function (el) {
            return el.id === obj.id;
        });
        var position = Utils.getEntityPosition(obj.position);
        if (tile) {
            tile.bmp.x = position.x;
            tile.bmp.y = position.y;
        } else {
            tile = new Tile(obj.id, obj.type, position);
            gGameEngine.tiles.push(tile);
        }
    }

    // handleFire: function (obj) {
    //
    // }

});

gMessages = new Messages();