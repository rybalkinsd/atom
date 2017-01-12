Bot = Player.extend({
    /**
     * Current direction
     */
    direction: 'up',
    lastDirection: '',

    /**
     * Directions that are not allowed to go because of collision
     */
    excludeDirections: [],

    /**
     * Current X axis direction
     */
    dirX: 0,

    /**
     * Current Y axis direction
     */
    dirY: -1,

    /**
     * Target position on map we are heading to
     */
    previousPosition: {},
    targetPosition: {},
    targetBitmapPosition: {},

    bombsMax: 1,

    wait: false,

    startTimerMax: 60,
    startTimer: 0,
    started: false,

    init: function(position) {
        this._super(position);
        this.findTargetPosition();
        this.startTimerMax = Math.random() * 60;
    },

    update: function() {
         if (!this.alive) {
            this.fade();
            return;
        }

        this.wait = false;

        if (!this.started && this.startTimer < this.startTimerMax) {
            this.startTimer++;
            if (this.startTimer >= this.startTimerMax) {
                this.started = true;
            }
            this.animate('idle');
            this.wait = true;
        }

        if (this.targetBitmapPosition.x == this.bmp.x && this.targetBitmapPosition.y == this.bmp.y) {

            // If we bumped into the wood, burn it!
            // If we are near player, kill it!
            if (this.getNearWood() || this.wantKillPlayer()) {
                this.plantBomb();
            }

            // When in safety, wait until explosion
            if (this.bombs.length) {
                if (this.isSafe(this.position)) {
                    this.wait = true;
                }
            }

            if (!this.wait) {
                this.findTargetPosition();
            }
        }

        if (!this.wait) {
            this.moveToTargetPosition();
        }
        this.handleBonusCollision();

        if (this.detectFireCollision()) {
            // Bot has to die
            this.die();
        }

    },

    /**
     * Finds the next tile position where we should move.
     */
    findTargetPosition: function() {
        var target = { x: this.position.x, y: this.position.y };
        target.x += this.dirX;
        target.y += this.dirY;

        var targets = this.getPossibleTargets();
        // Do not go the same way if possible
        if (targets.length > 1) {
            var previousPosition = this.getPreviousPosition();
            for (var i = 0; i < targets.length; i++) {
                var item = targets[i];
                if (item.x == previousPosition.x && item.y == previousPosition.y) {
                    targets.splice(i, 1);
                }
            }
        }
        this.targetPosition = this.getRandomTarget(targets);
        if (this.targetPosition && this.targetPosition.x) {
            this.loadTargetPosition(this.targetPosition);
            this.targetBitmapPosition = Utils.convertToBitmapPosition(this.targetPosition);
        }
    },

    /**
     * Moves a step forward to target position.
     */
    moveToTargetPosition: function() {
        this.animate(this.direction);

        var velocity = this.velocity;
        var distanceX = Math.abs(this.targetBitmapPosition.x - this.bmp.x);
        var distanceY = Math.abs(this.targetBitmapPosition.y - this.bmp.y);
        if (distanceX > 0 && distanceX < this.velocity) {
            velocity = distanceX;
        } else if (distanceY > 0 && distanceY < this.velocity) {
            velocity = distanceY;
        }

        var targetPosition = { x: this.bmp.x + this.dirX * velocity, y: this.bmp.y + this.dirY * velocity };
        if (!this.detectWallCollision(targetPosition)) {
            this.bmp.x = targetPosition.x;
            this.bmp.y = targetPosition.y;
        }

        this.updatePosition();
    },

    /**
     * Returns near grass tiles.
     */
    getPossibleTargets: function() {
        var targets = [];
        for (var i = 0; i < 4; i++) {
            var dirX;
            var dirY;
            if (i == 0) { dirX = 1; dirY = 0; }
            else if (i == 1) { dirX = -1; dirY = 0; }
            else if (i == 2) { dirX = 0; dirY = 1; }
            else if (i == 3) { dirX = 0; dirY = -1; }

            var position = { x: this.position.x + dirX, y: this.position.y + dirY };
            if (gGameEngine.getTileMaterial(position) == 'grass' && !this.hasBomb(position)) {
                targets.push(position);
            }
        }

        var safeTargets = [];
        for (var i = 0; i < targets.length; i++) {
            var target = targets[i];
            if (this.isSafe(target)) {
                safeTargets.push(target);
            }
        }

        var isLucky = Math.random() > 0.3;
        return safeTargets.length > 0 && isLucky ? safeTargets : targets;
    },

    /**
     * Loads vectors and animation name for target position.
     */
    loadTargetPosition: function(position) {
        this.dirX = position.x - this.position.x;
        this.dirY = position.y - this.position.y;
        if (this.dirX == 1 && this.dirY == 0) {
            this.direction = 'right';
        } else if (this.dirX == -1 && this.dirY == 0) {
            this.direction = 'left';
        } else if (this.dirX == 0 && this.dirY == 1) {
            this.direction = 'down';
        } else if (this.dirX == 0 && this.dirY == -1) {
            this.direction = 'up';
        }
    },

    /**
     * Gets previous position by current position and direction vector.
     */
    getPreviousPosition: function() {
        var previous = { x: this.targetPosition.x, y: this.targetPosition.y };
        previous.x -= this.dirX;
        previous.y -= this.dirY;
        return previous;
    },

    /**
     * Returns random item from array.
     */
    getRandomTarget: function(targets) {
        return targets[Math.floor(Math.random() * targets.length)];
    },

    applyBonus: function(bonus) {
        this._super(bonus);

        // It is too dangerous to have more bombs available
        this.bombsMax = 1;
    },

    /**
     * Game is over when no bots and one player left.
     */
    die: function() {
        this._super();
        var botsAlive = false;

        // Cache bots
        var bots = [];
        for (var i = 0; i < gGameEngine.bots.length; i++) {
            bots.push(gGameEngine.bots[i]);
        }

        for (var i = 0; i < bots.length; i++) {
            var bot = bots[i];
            // Remove bot
            if (bot == this) {
                gGameEngine.bots.splice(i, 1);
            }
            if (bot.alive) {
                botsAlive = true;
            }
        }

        if (!botsAlive && gGameEngine.countPlayersAlive() == 1) {
            gGameEngine.gameOver('win');
        }
    },

    /**
     * Checks whether there is any wood around.
     */
    getNearWood: function() {
        for (var i = 0; i < 4; i++) {
            var dirX;
            var dirY;
            if (i == 0) { dirX = 1; dirY = 0; }
            else if (i == 1) { dirX = -1; dirY = 0; }
            else if (i == 2) { dirX = 0; dirY = 1; }
            else if (i == 3) { dirX = 0; dirY = -1; }

            var position = { x: this.position.x + dirX, y: this.position.y + dirY };
            if (gGameEngine.getTileMaterial(position) == 'wood') {
                return gGameEngine.getTile(position);
            }
        }
    },

    /**
     * Checks whether player is near. If yes and we are angry, return true.
     */
    wantKillPlayer: function() {
        var isNear = false;

        for (var i = 0; i < 4; i++) {
            var dirX;
            var dirY;
            if (i == 0) { dirX = 1; dirY = 0; }
            else if (i == 1) { dirX = -1; dirY = 0; }
            else if (i == 2) { dirX = 0; dirY = 1; }
            else if (i == 3) { dirX = 0; dirY = -1; }

            var position = { x: this.position.x + dirX, y: this.position.y + dirY };
            for (var j = 0; j < gGameEngine.players.length; j++) {
                var player = gGameEngine.players[j];
                if (player.alive && Utils.comparePositions(player.position, position)) {
                    isNear = true;
                    break;
                }
            }
        }

        var isAngry = Math.random() > 0.5;
        if (isNear && isAngry) {
            return true;
        }
    },

    /**
     * Places the bomb in current position
     */
    plantBomb: function() {
        for (var i = 0; i < gGameEngine.bombs.length; i++) {
            var bomb = gGameEngine.bombs[i];
            if (Utils.comparePositions(bomb.position, this.position)) {
                return;
            }
        }

        if (this.bombs.length < this.bombsMax) {
            var bomb = new Bomb(this.position, this.bombStrength);
            gGameEngine.stage.addChild(bomb.bmp);
            this.bombs.push(bomb);
            gGameEngine.bombs.push(bomb);

            var that = this;
            bomb.setExplodeListener(function() {
                Utils.removeFromArray(that.bombs, bomb);
                that.wait = false;
            });
        }
    },

    /**
     * Checks whether position is safe  and possible explosion cannot kill us.
     */
    isSafe: function(position) {
        for (var i = 0; i < gGameEngine.bombs.length; i++) {
            var bomb = gGameEngine.bombs[i];
            var fires = bomb.getDangerPositions();
            for (var j = 0; j < fires.length; j++) {
                var fire = fires[j];
                if (Utils.comparePositions(fire, position)) {
                    return false;
                }
            }
        }
        return true;
    },

    hasBomb: function(position) {
        for (var i = 0; i < gGameEngine.bombs.length; i++) {
            var bomb = gGameEngine.bombs[i];
            if (Utils.comparePositions(bomb.position, position)) {
                return true;
            }
        }
        return false;
    }
});