Messages = Class.extend({
    handler: {},

    init: function () {
        this.handler['Pawn'] = this.handlePawn;
        this.handler['Bomb'] = this.handleBomb;
        this.handler['Wood'] = this.handleTile;
        this.handler['Wall'] = this.handleTile;
        this.handler['Grass'] = this.handleTile;
        this.handler['Fire'] = this.handleFire;
        this.handler['Buff'] = this.handleBonus;
    },

    move: function (direction) {
        var template = {
            topic: "MOVE",
            data: {}
        };

        template.data.direction = direction.toUpperCase();
        template.data.possess = gInputEngine.possessed;
        return JSON.stringify(template);
    },

    plantBomb: function () {
        var template = {
            topic: "PLANT_BOMB",
            data: gInputEngine.possessed
        };

        return JSON.stringify(template);
    },


    handleReplica: function (msg) {
        var gameObjects = JSON.parse(msg.data).objects;
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
        var position = {};
        position.x = obj.position.x + 6;
        position.y = -obj.position.y + 12 * 33 - 6;
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

    handleBonus: function (obj) {
        var bonus = gGameEngine.bonuses.find(function (el) {
            return el.id === obj.id;
        });
        var types = ['speed', 'power', 'capacity']
        var position = Utils.getEntityPosition(obj.position);
        var typePosition = types.findIndex(s => s == obj.buffType.toLowerCase())
        if (bonus) {
            bonus.type = types[typePosition];
        } else {
            bonus = new Bonus(obj.id, position, typePosition);
            gGameEngine.bonuses.push(bonus);
        }
    }

});

gMessages = new Messages();