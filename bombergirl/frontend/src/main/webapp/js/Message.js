Messages = Class.extend({
    handler: {},

    init: function () {
        this.handler['Pawn'] = this.handlePawn;
        this.handler['Bomb'] = this.handleBomb;
        // this.handler['Wood'] = this.handleWood;
        // this.handler['Wall'] = this.handleWall;
        // this.handler['Grass'] = this.handleGrass;
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
    }

    // handleWood: function(obj) {
    //     var wood = gGameEngine.tiles.find(function (el) {
    //         return el.id = obj.id;
    //     });
    //     var position = Utils.getEntityPosition(obj.position);
    //     if (wood) {
    //         wood.bmp.x = position.x;
    //         wood.bmp.y = position.y;
    //     } else {
    //         wood = new Tile('wood', position);
    //         gGameEngine.tiles.push(wood);
    //     }
    // },
    //
    // handleWall: function(obj) {
    //     var wall = gGameEngine.tiles.find(function (el) {
    //         return el.id = obj.id;
    //     });
    //     var position = Utils.getEntityPosition(obj.position);
    //     if (wall) {
    //         wall.bmp.x = position.x;
    //         wall.bmp.y = position.y;
    //     } else {
    //         wall = new Tile('wall', position);
    //         gGameEngine.tiles.push(wall);
    //     }
    // },
    //
    // handleGrass: function(obj) {
    //     var grass = gGameEngine.tiles.find(function (el) {
    //         return el.id = obj.id;
    //     });
    //     var position = Utils.getEntityPosition(obj.position);
    //     if (grass) {
    //         grass.bmp.x = position.x;
    //         grass.bmp.y = position.y;
    //     } else {
    //         grass = new Tile('grass', position);
    //         gGameEngine.tiles.push(grass);
    //     }
    // },
    //
    // handleFire: function (obj) {
    //
    // }

});

gMessages = new Messages();