Player = Entity.extend({
    id: 0,

    /**
     * Moving speed
     */
    velocity: 2,

    /**
     * Max number of bombs user can spawn
     */
    bombsMax: 1,

    /**
     * How far the fire reaches when bomb explodes
     */
    bombStrength: 1,

    /**
     * Entity position on map grid
     */
    position: {},

    /**
     * Bitmap dimensions
     */
    size: {
        w: 48,
        h: 48
    },

    /**
     * Bitmap animation
     */
    bmp: null,

    alive: true,

    bombs: [],

    controls: {
        'up': 'up',
        'left': 'left',
        'down': 'down',
        'right': 'right',
        'bomb': 'bomb'
    },

    /**
     * Bomb that player can escape from even when there is a collision
     */
    escapeBomb: null,

    deadTimer: 0,

    init: function(position, controls, id) {
        if (id) {
            this.id = id;
        }

        if (controls) {
            this.controls = controls;
        }

        var img = gGameEngine.playerBoyImg;
        if (!(this instanceof Bot)) {
            if (this.id == 0) {
                img = gGameEngine.playerGirlImg;
            } else {
                img = gGameEngine.playerGirl2Img;
            }
        }

        var spriteSheet = new createjs.SpriteSheet({
            images: [img],
            frames: { width: this.size.w, height: this.size.h, regX: 10, regY: 12 },
            animations: {
                idle: [0, 0, 'idle'],
                down: [0, 3, 'down', 0.1],
                left: [4, 7, 'left', 0.1],
                up: [8, 11, 'up', 0.1],
                right: [12, 15, 'right', 0.1],
                dead: [16, 16, 'dead', 0.1]
            }
        });
        this.bmp = new createjs.Sprite(spriteSheet);

        this.position = position;
        var pixels = Utils.convertToBitmapPosition(position);
        this.bmp.x = pixels.x;
        this.bmp.y = pixels.y;

        gGameEngine.stage.addChild(this.bmp);

        this.bombs = [];
        this.setBombsListener();
    },

    setBombsListener: function() {
        // Subscribe to bombs spawning
        if (!(this instanceof Bot)) {
            var that = this;
            gInputEngine.addListener(this.controls.bomb, function() {
                // Check whether there is already bomb on this position
                for (var i = 0; i < gGameEngine.bombs.length; i++) {
                    var bomb = gGameEngine.bombs[i];
                    if (Utils.comparePositions(bomb.position, that.position)) {
                        return;
                    }
                }

                var unexplodedBombs = 0;
                for (var i = 0; i < that.bombs.length; i++) {
                    if (!that.bombs[i].exploded) {
                        unexplodedBombs++;
                    }
                }

                if (unexplodedBombs < that.bombsMax) {
                    var bomb = new Bomb(that.position, that.bombStrength);
                    gGameEngine.stage.addChild(bomb.bmp);
                    that.bombs.push(bomb);
                    gGameEngine.bombs.push(bomb);

                    bomb.setExplodeListener(function() {
                        Utils.removeFromArray(that.bombs, bomb);
                    });
                }
            });
        }
    },

    update: function() {
        if (!this.alive) {
            //this.fade();
            return;
        }
        if (gGameEngine.menu.visible) {
            return;
        }
        var position = { x: this.bmp.x, y: this.bmp.y };

        var dirX = 0;
        var dirY = 0;
        if (gInputEngine.actions[this.controls.up]) {
            this.animate('up');
            position.y -= this.velocity;
            dirY = -1;
        } else if (gInputEngine.actions[this.controls.down]) {
            this.animate('down');
            position.y += this.velocity;
            dirY = 1;
        } else if (gInputEngine.actions[this.controls.left]) {
            this.animate('left');
            position.x -= this.velocity;
            dirX = -1;
        } else if (gInputEngine.actions[this.controls.right]) {
            this.animate('right');
            position.x += this.velocity;
            dirX = 1;
        } else {
            this.animate('idle');
        }

        if (position.x != this.bmp.x || position.y != this.bmp.y) {
            if (!this.detectBombCollision(position)) {
                if (this.detectWallCollision(position)) {
                    // If we are on the corner, move to the aisle
                    var cornerFix = this.getCornerFix(dirX, dirY);
                    if (cornerFix) {
                        var fixX = 0;
                        var fixY = 0;
                        if (dirX) {
                            fixY = (cornerFix.y - this.bmp.y) > 0 ? 1 : -1;
                        } else {
                            fixX = (cornerFix.x - this.bmp.x) > 0 ? 1 : -1;
                        }
                        this.bmp.x += fixX * this.velocity;
                        this.bmp.y += fixY * this.velocity;
                        this.updatePosition();
                    }
                } else {
                    this.bmp.x = position.x;
                    this.bmp.y = position.y;
                    this.updatePosition();
                }
            }
        }

        if (this.detectFireCollision()) {
            this.die();
        }

        this.handleBonusCollision();
    },

    /**
     * Checks whether we are on corner to target position.
     * Returns position where we should move before we can go to target.
     */
    getCornerFix: function(dirX, dirY) {
        var edgeSize = 30;

        // fix position to where we should go first
        var position = {};

        // possible fix position we are going to choose from
        var pos1 = { x: this.position.x + dirY, y: this.position.y + dirX };
        var bmp1 = Utils.convertToBitmapPosition(pos1);

        var pos2 = { x: this.position.x - dirY, y: this.position.y - dirX };
        var bmp2 = Utils.convertToBitmapPosition(pos2);

        // in front of current position
        if (gGameEngine.getTileMaterial({ x: this.position.x + dirX, y: this.position.y + dirY }) == 'grass') {
            position = this.position;
        }
        // right bottom
        // left top
        else if (gGameEngine.getTileMaterial(pos1) == 'grass'
            && Math.abs(this.bmp.y - bmp1.y) < edgeSize && Math.abs(this.bmp.x - bmp1.x) < edgeSize) {
            if (gGameEngine.getTileMaterial({ x: pos1.x + dirX, y: pos1.y + dirY }) == 'grass') {
                position = pos1;
            }
        }
        // right top
        // left bottom
        else if (gGameEngine.getTileMaterial(pos2) == 'grass'
            && Math.abs(this.bmp.y - bmp2.y) < edgeSize && Math.abs(this.bmp.x - bmp2.x) < edgeSize) {
            if (gGameEngine.getTileMaterial({ x: pos2.x + dirX, y: pos2.y + dirY }) == 'grass') {
                position = pos2;
            }
        }

        if (position.x &&  gGameEngine.getTileMaterial(position) == 'grass') {
            return Utils.convertToBitmapPosition(position);
        }
    },

    /**
     * Calculates and updates entity position according to its actual bitmap position
     */
    updatePosition: function() {
        this.position = Utils.convertToEntityPosition(this.bmp);
    },

    /**
     * Returns true when collision is detected and we should not move to target position.
     */
    detectWallCollision: function(position) {
        var player = {};
        player.left = position.x;
        player.top = position.y;
        player.right = player.left + this.size.w;
        player.bottom = player.top + this.size.h;

        // Check possible collision with all wall and wood tiles
        var tiles = gGameEngine.tiles;
        for (var i = 0; i < tiles.length; i++) {
            var tilePosition = tiles[i].position;

            var tile = {};
            tile.left = tilePosition.x * gGameEngine.tileSize + 25;
            tile.top = tilePosition.y * gGameEngine.tileSize + 20;
            tile.right = tile.left + gGameEngine.tileSize - 30;
            tile.bottom = tile.top + gGameEngine.tileSize - 30;

            if(gGameEngine.intersectRect(player, tile)) {
                return true;
            }
        }
        return false;
    },

    /**
     * Returns true when the bomb collision is detected and we should not move to target position.
     */
    detectBombCollision: function(pixels) {
        var position = Utils.convertToEntityPosition(pixels);

        for (var i = 0; i < gGameEngine.bombs.length; i++) {
            var bomb = gGameEngine.bombs[i];
            // Compare bomb position
            if (bomb.position.x == position.x && bomb.position.y == position.y) {
                // Allow to escape from bomb that appeared on my field
                if (bomb == this.escapeBomb) {
                    return false;
                } else {
                    return true;
                }
            }
        }

        // I have escaped already
        if (this.escapeBomb) {
            this.escapeBomb = null;
        }

        return false;
    },

    detectFireCollision: function() {
        var bombs = gGameEngine.bombs;
        for (var i = 0; i < bombs.length; i++) {
            var bomb = bombs[i];
            for (var j = 0; j < bomb.fires.length; j++) {
                var fire = bomb.fires[j];
                var collision = bomb.exploded && fire.position.x == this.position.x && fire.position.y == this.position.y;
                if (collision) {
                    return true;
                }
            }
        }
        return false;
    },

    /**
     * Checks whether we have got bonus and applies it.
     */
    handleBonusCollision: function() {
        for (var i = 0; i < gGameEngine.bonuses.length; i++) {
            var bonus = gGameEngine.bonuses[i];
            if (Utils.comparePositions(bonus.position, this.position)) {
                this.applyBonus(bonus);
                bonus.destroy();
            }
        }
    },

    /**
     * Applies bonus.
     */
    applyBonus: function(bonus) {
        if (bonus.type == 'speed') {
            this.velocity += 0.8;
        } else if (bonus.type == 'bomb') {
            this.bombsMax++;
        } else if (bonus.type == 'fire') {
            this.bombStrength++;
        }
    },

    /**
     * Changes animation if requested animation is not already current.
     */
    animate: function(animation) {
        if (!this.bmp.currentAnimation || this.bmp.currentAnimation.indexOf(animation) === -1) {
            this.bmp.gotoAndPlay(animation);
        }
    },

    die: function() {
        this.alive = false;

        if (gGameEngine.countPlayersAlive() == 1 && gGameEngine.playersCount == 2) {
            gGameEngine.gameOver('win');
        } else if (gGameEngine.countPlayersAlive() == 0) {
            gGameEngine.gameOver('lose');
        }

        this.bmp.gotoAndPlay('dead');
        this.fade();
    },

    fade: function() {
        var timer = 0;
        var bmp = this.bmp;
        var fade = setInterval(function() {
            timer++;

            if (timer > 30) {
                bmp.alpha -= 0.05;
            }
            if (bmp.alpha <= 0) {
                clearInterval(fade);
            }

        }, 30);
    }
});