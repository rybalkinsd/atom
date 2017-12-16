Fire = Entity.extend({
    /**
     * Entity position on map grid
     */
    position: {},

    /**
     * Bitmap dimensions
     */
    size: {
        w: 38,
        h: 38
    },

    /**
     * Bitmap animation
     */
    bmp: null,

    init: function(id, position) {
        this.id = id;
        var spriteSheet = new createjs.SpriteSheet({
            images: [gGameEngine.fireImg],
            frames: { width: this.size.w, height: this.size.h, regX: 0, regY: 0 },
            animations: {
                idle: [0, 5, null, 0.4],
            }
        });
        this.bmp = new createjs.Sprite(spriteSheet);
        this.bmp.gotoAndPlay('idle');
        var that = this;
        this.bmp.addEventListener('animationend', function() {
            that.remove();
        });

        this.position = position;

        this.bmp.x = position.x + 2;
        this.bmp.y = position.y - 5;

        gGameEngine.stage.addChild(this.bmp);
    },

    update: function() {
    },

    remove: function() {
        gGameEngine.stage.removeChild(this.bmp);
    }
});