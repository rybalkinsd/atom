Bomb = Entity.extend({
    /**
     * Entity position on map grid
     */
    position: {},

    /**
     * How far the fire reaches when bomb explodes
     */
    strength: 1,

    /**
     * Bitmap dimensions
     */
    size: {
        w: 28,
        h: 28
    },

    /**
     * Bitmap animation
     */
    bmp: null,

    /**
     * Timer in frames
     */
    timer: 0,

    /**
     * Max timer value in seconds
     */
    timerMax: 2,

    exploded: false,

    fires: [],

    explodeListener: null,

    init: function(id, position, strength) {
        this.id = id;
        this.strength = strength;

        var spriteSheet = new createjs.SpriteSheet({
            images: [gGameEngine.bombImg],
            frames: {
                width: this.size.w,
                height: this.size.h,
                regX: -1,
                regY: -1
            },
            animations: {
                idle: [0, 4, "idle", 0.2]
            }
        });
        this.bmp = new createjs.Sprite(spriteSheet);
        this.bmp.gotoAndPlay('idle');

        this.position = position;
        this.bmp.x = position.x;
        this.bmp.y = position.y;

        this.fires = [];
        gGameEngine.stage.addChild(this.bmp);
    },

    //remove: function() {
      //  gGameEngine.stage.removeChild(this.bmp);
    //}
    remove: function() {
            gGameEngine.stage.removeChild(this.bmp);
            for (var i = 0; i < gGameEngine.bombs.length; i++) {
                var bomb = gGameEngine.bombs[i];
                if (this == bomb) {
                    gGameEngine.bombs.splice(i, 1);
                }
            }
            createjs.Sound.play("bomb");
    }
});