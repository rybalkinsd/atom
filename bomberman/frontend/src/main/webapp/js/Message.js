Messages = Class.extend({
    handler: {},

    init: function () {
        this.handler['Pawn'] = this.handlePawn;
        this.handler['Bomb'] = this.handleBomb;
        this.handler['Wood'] = this.handleTile;
        this.handler['Wall'] = this.handleTile;
        this.handler['Fire'] = this.handleFire;
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
        var gameObjects = JSON.parse(msg.data);
        var survivors = new Set();

        gGameEngine.gc(gameObjects);
    },

     handleGameOver: function (msg) {
            gGameEngine.gameOver(msg);
            gGameEngine.clearPlayers();
     },

    handlePossess: function (msg) {
        gInputEngine.possessed = parseInt(msg.data);
    },

    handlePawn: function(obj) {
        var player = gGameEngine.players.find(function (el) {
            return el.id === obj.id;
        });
        var position = Utils.getEntityPosition(obj.position);
        var direction = obj.direction;
        if (player) {
            player.bmp.x = position.x;
            player.bmp.y = position.y;
            player.direction = direction;
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

    handleBonus: function(obj) {
        var bonus = gGameEngine.bonuses.find(function (el) {
            return el.id === obj.id;
        });
        var position = Utils.getEntityPosition(obj.position);

        if (bonus) {
            bonus.bmp.x = position.x;
            bonus.bmp.y = position.y;
        } else {
            bonus = new Bonus(obj.id, position, obj.bonusType);
            gGameEngine.bonuses.push(bonus);
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