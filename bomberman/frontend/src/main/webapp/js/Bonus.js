Bonus = Entity.extend({
    types: ['speed', 'power', 'capacity'],

    type: '',
    position: {},
    bmp: null,

    init: function(id, position, typePosition) {
        this.id = id;
        this.type = this.types[typePosition];

        this.position = position;

        this.bmp = new createjs.Bitmap(gGameEngine.bonusesImg);
        this.bmp.x = position.x;
        this.bmp.y = position.y;
        this.bmp.sourceRect = new createjs.Rectangle(typePosition * 32, 0, 32, 32);
        gGameEngine.stage.addChild(this.bmp);
    },

    remove: function() {
        gGameEngine.stage.removeChild(this.bmp);
    }
});