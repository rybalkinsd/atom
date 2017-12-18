GameEngine = Class.extend({
    tileSize: 32,
    tilesX: 17,
    tilesY: 13,
    size: {},
    fps: 60,
    playersCount: 2,
    bonusesPercent: 16,

    stage: null,
    menu: null,
    players: [],
    tiles: [],
    bombs: [],
    bonuses: [],
    fires: [],

    playerBoyImg: null,
    playerGirlImg: null,
    playerGirl2Img: null,
    tilesImgs: {},
    bombImg: null,
    fireImg: null,
    bonusesImgs: {},

    playing: false,
    mute: false,
    soundtrackLoaded: false,
    soundtrackPlaying: false,
    soundtrack: null,

    serverProxy: null,

    init: function () {
        this.size = {
            w: this.tileSize * this.tilesX,
            h: this.tileSize * this.tilesY
        };
    },

    load: function () {
        // Init canvas
        this.stage = new createjs.Stage("canvas");
        this.stage.enableMouseOver();

        // Load assets
        var queue = new createjs.LoadQueue();
        var that = this;
        queue.addEventListener("complete", function () {
            that.playerBoyImg = queue.getResult("playerBoy");
            that.playerGirlImg = queue.getResult("playerGirl");
            that.playerGirl2Img = queue.getResult("playerGirl2");
            that.tilesImgs.grass = queue.getResult("tile_grass");
            that.tilesImgs.wall = queue.getResult("tile_wall");
            that.tilesImgs.wood = queue.getResult("tile_wood");
            that.bombImg = queue.getResult("bomb");
            that.fireImg = queue.getResult("fire");
            that.bonusesImgs.speed = queue.getResult("bonus_speed");
            that.bonusesImgs.bombs = queue.getResult("bonus_bomb");
            that.bonusesImgs.explosion = queue.getResult("bonus_explosion");
            that.setup();
        });
        queue.loadManifest([
            {id: "playerBoy", src: "img/george.png"},
            {id: "playerGirl", src: "img/betty.png"},
            {id: "playerGirl2", src: "img/betty2.png"},
            {id: "tile_grass", src: "img/tile_grass.png"},
            {id: "tile_wall", src: "img/tile_wall.png"},
            {id: "tile_wood", src: "img/tile_wood.png"},
            {id: "bomb", src: "img/bomb.png"},
            {id: "fire", src: "img/fire.png"},
            {id: "bonus_speed", src: "img/bonus_speed.png"},
            {id: "bonus_bomb", src: "img/bonus_bomb.png"},
            {id: "bonus_explosion", src: "img/bonus_explosion.png"},
        ]);

        createjs.Sound.addEventListener("fileload", this.onSoundLoaded);
        createjs.Sound.alternateExtensions = ["mp3"];
        createjs.Sound.registerSound("sound/bomb.ogg", "bomb");
        createjs.Sound.registerSound("sound/game.ogg", "game");

        this.menu = new Menu();
    },

    setup: function () {
        if (!gInputEngine.bindings.length) {
            gInputEngine.setup();
        }

        this.bombs = [];
        this.tiles = [];
        this.bonuses = [];

        this.serverProxy = new ServerProxy();

        // Toggle sound
        gInputEngine.subscribe('mute', this.toggleSound);

        // Start loop
        if (!createjs.Ticker.hasEventListener('tick')) {
            createjs.Ticker.addEventListener('tick', gGameEngine.update);
            createjs.Ticker.setFPS(this.fps);
        }

        if (gGameEngine.playersCount > 0) {
            if (this.soundtrackLoaded) {
                this.playSoundtrack();
            }
        }

        if (!this.playing) {
            this.menu.show();
        }

        this.drawTiles();
    },

    onSoundLoaded: function (sound) {
        if (sound.id == 'game') {
            gGameEngine.soundtrackLoaded = true;
            if (gGameEngine.playersCount > 0) {
                gGameEngine.playSoundtrack();
            }
        }
    },

    playSoundtrack: function () {
        if (!gGameEngine.soundtrackPlaying) {
            gGameEngine.soundtrack = createjs.Sound.play("game", "none", 0, 0, -1);
            gGameEngine.soundtrack.setVolume(1);
            gGameEngine.soundtrackPlaying = true;
        }
    },

    drawTiles: function () {
        for (var i = 0; i < this.tilesY; i++) {
            for (var j = 0; j < this.tilesX; j++) {
                // Grass tiles
                var img = new Image();
                img.src = "img/tile_grass.png";

                var bitmap = new createjs.Bitmap(img);

                bitmap.x = j * 32;
                bitmap.y = i * 32;

                this.stage.addChild(bitmap);
            }
        }
    },

    update: function () {
        // Player
        for (var i = 0; i < gGameEngine.players.length; i++) {
            var player = gGameEngine.players[i];

            player.update(player.id);
        }

        // Bombs
        for (var i = 0; i < gGameEngine.bombs.length; i++) {
            var bomb = gGameEngine.bombs[i];
            bomb.update();
        }

        // Menu
        gGameEngine.menu.update();

        // Stage
        gGameEngine.stage.update();
    },

    // gameOver: function(status) {
    //     if (gGameEngine.menu.visible) { return; }
    //
    //     if (status == 'win') {
    //         var winText = "You won!";
    //         if (gGameEngine.playersCount > 1) {
    //             var winner = gGameEngine.getWinner();
    //             winText = winner == 0 ? "Player 1 won!" : "Player 2 won!";
    //         }
    //         this.menu.show([{text: winText, color: '#669900'}, {text: ' ;D', color: '#99CC00'}]);
    //     } else {
    //         this.menu.show([{text: 'Game Over', color: '#CC0000'}, {text: ' :(', color: '#FF4444'}]);
    //     }
    // },

     gameOver: function(msg) {
     //location.reload();
     if (msg.data == "\"YOU LOSE\"") {
        createjs.Sound.stop("game");
        this.menu.showWithText("GAME OVER :(", "#ff4444");
     }
     else {
        createjs.Sound.stop("game");
        this.menu.showWithText("YOU WON! :)", "#00FF00");
     }
     },


    restart: function () {
        // gInputEngine.removeAllListeners();
        gGameEngine.stage.removeAllChildren();
        gGameEngine.setup();
        createjs.Sound.play("game");
        this.serverProxy = new ServerProxy();

    },

    /**
     * Moves specified child to the front.
     */
    moveToFront: function (child) {
        var children = gGameEngine.stage.getNumChildren();
        gGameEngine.stage.setChildIndex(child, children - 1);
    },

    toggleSound: function () {
        if (gGameEngine.mute) {
            gGameEngine.mute = false;
            gGameEngine.soundtrack.resume();
        } else {
            gGameEngine.mute = true;
            gGameEngine.soundtrack.pause();
        }
    },

    findObject: function (id) {
        [this.bombs, this.bonuses, this.tiles, this.players].forEach(function (it) {
                    var i = it.length;
                    while (i--) {
                        if (id == it[i].id) {
                            return true;
                        }
                    }
                });
        return false;
        },

    clearPlayers: function () {
    [this.players, this.fires, this.bombs].forEach(function (it) {
         var i = it.length;
         while (i--) {
                it[i].remove();
                it.splice(i, 1);
        }
    });
    },

    gc: function (gameObjects) {
        var survivors = new Set();

        for (var i = 0; i < gameObjects.length; i++) {
            var wasDeleted = false;
            var obj = gameObjects[i];

            if (obj.type == 'Pawn') {
                gMessages.handler[obj.type](obj);
                survivors.add(obj.id);
                continue;
            }

            [this.tiles, this.bombs, this.bonuses].forEach(function (it) {
                var i = it.length;
                while (i--) {
                    if (obj.id == it[i].id) {
                        it[i].remove();
                        it.splice(i, 1);
                        wasDeleted = true;
                    }
                }
            });

            if (!wasDeleted && obj.type != 'Pawn') {
                gMessages.handler[obj.type](obj);
            }
        }

        [this.players].forEach(function (it) {
             var i = it.length;
             while (i--) {
                if (!survivors.has(it[i].id)) {
                    it[i].remove();
                    it.splice(i, 1);
                }
            }
        });
    }

});

gGameEngine = new GameEngine();