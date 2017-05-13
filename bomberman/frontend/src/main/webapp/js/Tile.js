Tile = Entity.extend({
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

    material: '',

    init: function(id, material, position) {
        this.id = id;
        this.material = material;
        this.position = position;
        var img;
        if (material == 'grass') {
            img = gGameEngine.tilesImgs.grass;
        } else if (material === 'Wall') {
            img = gGameEngine.tilesImgs.wall;
        } else if (material === 'Wood') {
            img = gGameEngine.tilesImgs.wood;
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