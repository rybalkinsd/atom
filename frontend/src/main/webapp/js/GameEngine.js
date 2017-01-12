GameEngine = Class.extend({
    tileSize: 32,
    tilesX: 17,
    tilesY: 13,
    size: {},
    fps: 50,
    botsCount: 2, /* 0 - 3 */
    playersCount: 2, /* 1 - 2 */
    bonusesPercent: 16,

    stage: null,
    menu: null,
    players: [],
    bots: [],
    tiles: [],
    bombs: [],
    bonuses: [],

    playerBoyImg: null,
    playerGirlImg: null,
    playerGirl2Img: null,
    tilesImgs: {},
    bombImg: null,
    fireImg: null,
    bonusesImg: null,

    playing: false,
    mute: false,
    soundtrackLoaded: false,
    soundtrackPlaying: false,
    soundtrack: null,

    init: function() {
        this.size = {
            w: this.tileSize * this.tilesX,
            h: this.tileSize * this.tilesY
        };
    },

    load: function() {
        // Init canvas
        this.stage = new createjs.Stage("canvas");
        this.stage.enableMouseOver();

        // Load assets
        var queue = new createjs.LoadQueue();
        var that = this;
        queue.addEventListener("complete", function() {
            that.playerBoyImg = queue.getResult("playerBoy");
            that.playerGirlImg = queue.getResult("playerGirl");
            that.playerGirl2Img = queue.getResult("playerGirl2");
            that.tilesImgs.grass = queue.getResult("tile_grass");
            that.tilesImgs.wall = queue.getResult("tile_wall");
            that.tilesImgs.wood = queue.getResult("tile_wood");
            that.bombImg = queue.getResult("bomb");
            that.fireImg = queue.getResult("fire");
            that.bonusesImg = queue.getResult("bonuses");
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
            {id: "bonuses", src: "img/bonuses.png"}
        ]);

        createjs.Sound.addEventListener("fileload", this.onSoundLoaded);
        createjs.Sound.alternateExtensions = ["mp3"];
        createjs.Sound.registerSound("sound/bomb.ogg", "bomb");
        createjs.Sound.registerSound("sound/game.ogg", "game");

        // Create menu
        this.menu = new Menu();
    },

    setup: function() {
        if (!gInputEngine.bindings.length) {
            gInputEngine.setup();
        }

        this.bombs = [];
        this.tiles = [];
        this.bonuses = [];

        // Draw tiles
        this.drawTiles();
        this.drawBonuses();

        this.spawnBots();
        this.spawnPlayers();

        // Toggle sound
        gInputEngine.addListener('mute', this.toggleSound);

        // Restart listener
        // Timeout because when you press enter in address bar too long, it would not show menu
        setTimeout(function() {
            gInputEngine.addListener('restart', function() {
                if (gGameEngine.playersCount == 0) {
                    gGameEngine.menu.setMode('single');
                } else {
                    gGameEngine.menu.hide();
                    gGameEngine.restart();
                }
            });
        }, 200);

        // Escape listener
        gInputEngine.addListener('escape', function() {
            if (!gGameEngine.menu.visible) {
                gGameEngine.menu.show();
            }
        });

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
    },

    onSoundLoaded: function(sound) {
        if (sound.id == 'game') {
            gGameEngine.soundtrackLoaded = true;
            if (gGameEngine.playersCount > 0) {
                gGameEngine.playSoundtrack();
            }
        }
    },

    playSoundtrack: function() {
        if (!gGameEngine.soundtrackPlaying) {
            gGameEngine.soundtrack = createjs.Sound.play("game", "none", 0, 0, -1);
            gGameEngine.soundtrack.setVolume(1);
            gGameEngine.soundtrackPlaying = true;
        }
    },

    update: function() {
        // Player
        for (var i = 0; i < gGameEngine.players.length; i++) {
            var player = gGameEngine.players[i];
            player.update();
        }

        // Bots
        for (var i = 0; i < gGameEngine.bots.length; i++) {
            var bot = gGameEngine.bots[i];
            bot.update();
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

    drawTiles: function() {
        for (var i = 0; i < this.tilesY; i++) {
            for (var j = 0; j < this.tilesX; j++) {
                if ((i == 0 || j == 0 || i == this.tilesY - 1 || j == this.tilesX - 1)
                    || (j % 2 == 0 && i % 2 == 0)) {
                    // Wall tiles
                    var tile = new Tile('wall', { x: j, y: i });
                    this.stage.addChild(tile.bmp);
                    this.tiles.push(tile);
                } else {
                    // Grass tiles
                    var tile = new Tile('grass', { x: j, y: i });
                    this.stage.addChild(tile.bmp);

                    // Wood tiles
                    if (!(i <= 2 && j <= 2)
                        && !(i >= this.tilesY - 3 && j >= this.tilesX - 3)
                        && !(i <= 2 && j >= this.tilesX - 3)
                        && !(i >= this.tilesY - 3 && j <= 2)) {

                        var wood = new Tile('wood', { x: j, y: i });
                        this.stage.addChild(wood.bmp);
                        this.tiles.push(wood);
                    }
                }
            }
        }
    },

    drawBonuses: function() {
        // Cache woods tiles
        var woods = [];
        for (var i = 0; i < this.tiles.length; i++) {
            var tile = this.tiles[i];
            if (tile.material == 'wood') {
                woods.push(tile);
            }
        }

        // Sort tiles randomly
        woods.sort(function() {
            return 0.5 - Math.random();
        });

        // Distribute bonuses to quarters of map precisely fairly
        for (var j = 0; j < 4; j++) {
            var bonusesCount = Math.round(woods.length * this.bonusesPercent * 0.01 / 4);
            var placedCount = 0;
            for (var i = 0; i < woods.length; i++) {
                if (placedCount > bonusesCount) {
                    break;
                }

                var tile = woods[i];
                if ((j == 0 && tile.position.x < this.tilesX / 2 && tile.position.y < this.tilesY / 2)
                    || (j == 1 && tile.position.x < this.tilesX / 2 && tile.position.y > this.tilesY / 2)
                    || (j == 2 && tile.position.x > this.tilesX / 2 && tile.position.y < this.tilesX / 2)
                    || (j == 3 && tile.position.x > this.tilesX / 2 && tile.position.y > this.tilesX / 2)) {

                    var typePosition = placedCount % 3;
                    var bonus = new Bonus(tile.position, typePosition);
                    this.bonuses.push(bonus);

                    // Move wood to front
                    this.moveToFront(tile.bmp);

                    placedCount++;
                }
            }
        }
    },

    spawnBots: function() {
        this.bots = [];

        if (this.botsCount >= 1) {
            var bot2 = new Bot({ x: 1, y: this.tilesY - 2 });
            this.bots.push(bot2);
        }

        if (this.botsCount >= 2) {
            var bot3 = new Bot({ x: this.tilesX - 2, y: 1 });
            this.bots.push(bot3);
        }

        if (this.botsCount >= 3) {
            var bot = new Bot({ x: this.tilesX - 2, y: this.tilesY - 2 });
            this.bots.push(bot);
        }

        if (this.botsCount >= 4) {
            var bot = new Bot({ x: 1, y: 1 });
            this.bots.push(bot);
        }
    },

    spawnPlayers: function() {
        this.players = [];

        if (this.playersCount >= 1) {
            var player = new Player({ x: 1, y: 1 });
            this.players.push(player);
        }

        if (this.playersCount >= 2) {
            var controls = {
                'up': 'up2',
                'left': 'left2',
                'down': 'down2',
                'right': 'right2',
                'bomb': 'bomb2'
            };
            var player2 = new Player({ x: this.tilesX - 2, y: this.tilesY - 2 }, controls, 1);
            this.players.push(player2);
        }
    },

    /**
     * Checks whether two rectangles intersect.
     */
    intersectRect: function(a, b) {
        return (a.left <= b.right && b.left <= a.right && a.top <= b.bottom && b.top <= a.bottom);
    },

    /**
     * Returns tile at given position.
     */
    getTile: function(position) {
        for (var i = 0; i < this.tiles.length; i++) {
            var tile = this.tiles[i];
            if (tile.position.x == position.x && tile.position.y == position.y) {
                return tile;
            }
        }
    },

    /**
     * Returns tile material at given position.
     */
    getTileMaterial: function(position) {
        var tile = this.getTile(position);
        return (tile) ? tile.material : 'grass' ;
    },

    gameOver: function(status) {
        if (gGameEngine.menu.visible) { return; }

        if (status == 'win') {
            var winText = "You won!";
            if (gGameEngine.playersCount > 1) {
                var winner = gGameEngine.getWinner();
                winText = winner == 0 ? "Player 1 won!" : "Player 2 won!";
            }
            this.menu.show([{text: winText, color: '#669900'}, {text: ' ;D', color: '#99CC00'}]);
        } else {
            this.menu.show([{text: 'Game Over', color: '#CC0000'}, {text: ' :(', color: '#FF4444'}]);
        }
    },

    getWinner: function() {
        for (var i = 0; i < gGameEngine.players.length; i++) {
            var player = gGameEngine.players[i];
            if (player.alive) {
                return i;
            }
        }
    },

    restart: function() {
        gInputEngine.removeAllListeners();
        gGameEngine.stage.removeAllChildren();
        gGameEngine.setup();
    },

    /**
     * Moves specified child to the front.
     */
    moveToFront: function(child) {
        var children = gGameEngine.stage.getNumChildren();
        gGameEngine.stage.setChildIndex(child, children - 1);
    },

    toggleSound: function() {
        if (gGameEngine.mute) {
            gGameEngine.mute = false;
            gGameEngine.soundtrack.resume();
        } else {
            gGameEngine.mute = true;
            gGameEngine.soundtrack.pause();
        }
    },

    countPlayersAlive: function() {
        var playersAlive = 0;
        for (var i = 0; i < gGameEngine.players.length; i++) {
            if (gGameEngine.players[i].alive) {
                playersAlive++;
            }
        }
        return playersAlive;
    },

    getPlayersAndBots: function() {
        var players = [];

        for (var i = 0; i < gGameEngine.players.length; i++) {
            players.push(gGameEngine.players[i]);
        }

        for (var i = 0; i < gGameEngine.bots.length; i++) {
            players.push(gGameEngine.bots[i]);
        }

        return players;
    }
});

gGameEngine = new GameEngine();