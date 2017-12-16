Bonus = Entity.extend({
    /**
     * Entity position on map grid
     */
    position: {},

    /**
     * Bitmap dimensions
     */
    size: {
        w: 32,
        h: 32
    },

    /**
     * Bitmap animation
     */
    bmp: null,

    type: '',

    init: function(id, type, position) {
        this.id = id;
        this.type = type;
        this.position = position;
        var img;
        if (type == 'Speed') {
            img = gGameEngine.tilesImgs.speed;
        } else if (type === 'Bombs') {
            img = gGameEngine.tilesImgs.bombs;
        } else if (type === 'Explosion') {
            img = gGameEngine.tilesImgs.explosion;
        }
        this.bmp = new createjs.Bitmap(img);

        this.bmp.x = position.x;
        this.bmp.y = position.y;

        gGameEngine.stage.addChild(this.bmp);
    },

    update: function() {
    },

    remove: function() {
        gGameEngine.stage.removeChild(this.bmp);
    }
});