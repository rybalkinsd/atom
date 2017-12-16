Messages = Class.extend({
    handler: {},

    init: function () {
        this.handler['Pawn'] = this.handlePawn;
        this.handler['Bomb'] = this.handleBomb;
        this.handler['Wood'] = this.handleTile;
        this.handler['Wall'] = this.handleTile;
        this.handler['Fire'] = this.handleFire;
        this.handler['Speed'] = this.handleBonus;
        this.handler['Bombs'] = this.handleBonus;
        this.handler['Explosion'] = this.handleBonus;
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
        var survivors = new Set();

        for (var i = 0; i < gameObjects.length; i++) {
            var obj = gameObjects[i];
            if (gMessages.handler[obj.type] === undefined)
                continue;

            survivors.add(obj.id);
            gMessages.handler[obj.type](obj);
        }
        gGameEngine.gc(survivors);
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
    },

    handleTile: function (obj) {
        var tile = gGameEngine.tiles.find(function (el) {
            return el.id === obj.id;
        });
        var position = Utils.getEntityPosition(obj.position);
        if (tile) {
            tile.material = obj.type;
        } else {
            tile = new Tile(obj.id, obj.type, position);
            gGameEngine.tiles.push(tile);
        }
    },

     handleBonus: function (obj) {
            var bonus = gGameEngine.bonuses.find(function (el) {
                return el.id === obj.id;
            });
            var position = Utils.getEntityPosition(obj.position);
            if (bonus) {
                bonus.type = obj.type;
            } else {
                bonus = new Bonus(obj.id, obj.type, position);
                gGameEngine.bonuses.push(bonus);
            }
        },

    hello: function (name) {
        var template = {
            topic: "HELLO",
            data: {}
        };
        template.data = name;
        return JSON.stringify(template);
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
    }

});

gMessages = new Messages();