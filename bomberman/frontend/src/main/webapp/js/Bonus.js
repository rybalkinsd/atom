Bonus = Entity.extend({
    types: ['speed', 'bomb', 'fire'],

    type: '',
    position: {},
    size: {
            w: 32,
            h: 32
        },
    bmp: null,

    init: function(id, position, typePosition) {
        this.id = id;
        this.type = this.types[typePosition];
        this.position = position;
        this.bmp = new createjs.Bitmap(gGameEngine.bonusesImg);
        this.bmp.sourceRect = new createjs.Rectangle(typePosition * 32, 0, 32, 32);
        this.bmp.x = position.x;
        this.bmp.y = position.y;

        gGameEngine.stage.addChild(this.bmp);
    },

    remove: function() {
        gGameEngine.stage.removeChild(this.bmp);
    },

    update: function() {
    }
});