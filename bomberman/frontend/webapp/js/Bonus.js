Bonus = Entity.extend({
    types: ['SPEED', 'BOMBS', 'RANGE'],

    type: '',
    position: {},
    size: {
        w: 32,
        h: 32
    },

    bmp: null,

    init: function(id, position, type) {
        this.id = id;
        this.type = type;
        this.position = position;
        var img;
        if (type === 'SPEED') {
            img = gGameEngine.bonusesImgs.speed;
        } else if (type === 'BOMBS') {
            img = gGameEngine.bonusesImgs.bombs;
        } else if (type === 'RANGE') {
            img = gGameEngine.bonusesImgs.explosion;
        }

        this.bmp = new createjs.Bitmap(img);
        this.bmp.regX = -1;
        this.bmp.regY = -1;
        this.bmp.x = position.x;
        this.bmp.y = position.y;
        this.bmp.regX = -1;
        this.bmp.regY = -1;
        gGameEngine.stage.addChild(this.bmp);
    },

    update: function() {
    },

    remove: function() {
        gGameEngine.stage.removeChild(this.bmp);
    }
});