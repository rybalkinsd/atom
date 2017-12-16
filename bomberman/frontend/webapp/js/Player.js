Player = Entity.extend({
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



    init: function(id, position) {
        this.id = id;

        var img = gGameEngine.playerGirlImg;

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

        this.bmp.x = position.x;
        this.bmp.y = position.y;

        gGameEngine.stage.addChild(this.bmp);

        this.bombs = [];
    },


    update: function() {
        if (!this.alive) {
            return;
        }

        if (gInputEngine.possessed !== this.id) {
            return;
        }

        if (gInputEngine.actions[this.controls.up]) {
            this.animate('up');
        } else if (gInputEngine.actions[this.controls.down]) {
            this.animate('down');
        } else if (gInputEngine.actions[this.controls.left]) {
            this.animate('left');
        } else if (gInputEngine.actions[this.controls.right]) {
            this.animate('right');
        } else {
            this.animate('idle');
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
