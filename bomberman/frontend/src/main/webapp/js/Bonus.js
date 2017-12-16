/*
Bonus = Entity.extend({
    types: ['Speed', 'BBomb', 'BFire'],
    type: '',
    position: {},
    bmp: null,
    init: function(id, position, typePosition) {
        this.id = id;
        this.type = this.types[typePosition];
        this.position = position;
        this.bmp = new createjs.Bitmap(gGameEngine.bonusesImg);
        var pixels = Utils.convertToBitmapPosition(position);
        this.bmp.x = pixels.x;
        this.bmp.y = pixels.y;
        this.bmp.sourceRect = new createjs.Rectangle(typePosition * 32, 0, 32, 32);
        gGameEngine.stage.addChild(this.bmp);
    },
    remove: function() {
        gGameEngine.stage.removeChild(this.bmp);
    }
});*/
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

    material: '',

    init: function(id,material, position) {
        this.id = id;
        this.material = material;
        this.position = position;
        var img;
        if (material === 'Speed') {
            img = gGameEngine.bonusImgs.speed;
        } else if (material === 'BBomb') {
            img = gGameEngine.bonusImgs.bbomb;
        } else if (material === 'BFire') {
            img = gGameEngine.bonusImgs.bfire;
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