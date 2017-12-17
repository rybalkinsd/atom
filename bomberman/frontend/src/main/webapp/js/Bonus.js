Bonus = Entity.extend({
    types: ['speed', 'bomb', 'fire'],

    type: '',
    position: {},
    bmp: null,

    init: function (id, type , position) {
        this.id = id;
        this.type = type;
        this.position = position;
        var typePosition;
        if (type === 'speed') {
            typePosition = 0;
        } else if (type === 'bomb') {
            typePosition = 1;
        } else if (type === 'fire') {
            typePosition = 2;
        }

        this.bmp = new createjs.Bitmap(gGameEngine.bonusesImg);
        this.bmp.x = position.x;
        this.bmp.y = position.y;
        this.bmp.sourceRect = new createjs.Rectangle(typePosition * 32, 0, 32, 32);

        gGameEngine.stage.addChild(this.bmp);
    },

    update: function() {
    },

    remove: function () {
        gGameEngine.stage.removeChild(this.bmp);
    }
});