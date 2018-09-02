Messages = Class.extend({
    handler: {},

    init: function () {
        this.handler['Pawn'] = this.handlePawn;
        this.handler['Bomb'] = this.handleBomb;
        this.handler['Wood'] = this.handleTile;
        this.handler['Wall'] = this.handleTile;
        this.handler['Fire'] = this.handleFire;
        this.handler['Grass'] = this.handleTile;
        this.handler['Bonus'] = this.handleBonus;
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
        var gameObjects = JSON.parse(msg.data).objects;
        var survivors =  new Set();
        var deleted = JSON.parse(msg.data).deleted;
        var winnerId = JSON.parse(msg.data).winnerId;
        var gameOver = JSON.parse(msg.data).gameOver;

        for (var i = 0; i < deleted.length; i++) {
            var deletedObj = deleted[i];
            /*if (gMessages.handler[obj.type] == undefined)
                continue;*/
            survivors.add(deletedObj.id);
        }

        for (var i = 0; i < gameObjects.length; i++) {
            var obj = gameObjects[i];
            /*if (gMessages.handler[obj.type] === undefined)
                continue;*/

            //survivors.add(deletedObj.id);
            gMessages.handler[obj.type](obj);
        }
        gGameEngine.gc(survivors);
        if (gameOver == true) {
            gGameEngine.gameOver(winnerId);
        }
    },

    handlePossess: function (msg) {
        gInputEngine.possessed = parseInt(msg.data);
        gGameEngine.menu.clear();
    },

    handleGameStart: function (msg) {
        gGameEngine.menu.clear();
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

    handleTile: function (obj) {
        var tile = gGameEngine.tiles.find(function (el) {
            return el.id === obj.id;
        });

        //var position = Utils.getEntityPosition(Utils.convertToBitmapPosition(obj.position));
        var position = Utils.getEntityPosition(obj.position);
        if (tile) {
            tile.material = obj.type;
        } else {
            tile = new Tile(obj.id, obj.type, position);
            gGameEngine.tiles.push(tile);
        }
    },

    handleFire: function (obj) {
        var fire = gGameEngine.fires.find(function (el) {
            return el.id === obj.id;
        });

        var position = Utils.getEntityPosition(obj.position);
        if (!fire) {
            fire = new Fire(obj.id, position);
            gGameEngine.fires.push(fire);
        }
    },

    handleBonus: function(obj) {
        var bonus = gGameEngine.bonuses.find(function (el) {
            return el.id === obj.id;
        });
        var position = Utils.getEntityPosition(obj.position);

        if (bonus) {
            bonus.bmp.x = position.x;
            bonus.bmp.y = position.y;
        } else {
            bonus = new Bonus(obj.id, position, obj.typePosition);
            gGameEngine.bonuses.push(bonus);
        }
    },

});

gMessages = new Messages();