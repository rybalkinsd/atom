Bonus = Entity.extend({
    types: ['Bonus_Speed', 'Bonus_Bomb', 'Bonus_Fire'],

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
});