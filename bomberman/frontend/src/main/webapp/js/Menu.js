Menu = Class.extend({
    visible: true,

    views: [],

    init: function() {
        gGameEngine.botsCount = 4;
        gGameEngine.playersCount = 0;

        this.showLoader();
    },

    show: function(text) {
        this.visible = true;

        this.draw(text);
    },

    hide: function() {
        this.visible = false;

        for (var i = 0; i < this.views.length; i++) {
            gGameEngine.stage.removeChild(this.views[i]);
        }

        this.views = [];
    },

    update: function() {
        if (this.visible) {
            for (var i = 0; i < this.views.length; i++) {
                gGameEngine.moveToFront(this.views[i]);
            }
        }
    },

    setHandCursor: function(btn) {
        btn.addEventListener('mouseover', function() {
            document.body.style.cursor = 'pointer';
        });
        btn.addEventListener('mouseout', function() {
            document.body.style.cursor = 'auto';
        });
    },

    setMode: function(mode) {
        this.hide();

        gGameEngine.playing = true;
        gGameEngine.restart();
    },

    draw: function(text) {
        var that = this;

        // semi-transparent black background
        var bgGraphics = new createjs.Graphics().beginFill("rgba(0, 0, 0, 0.5)").drawRect(0, 0, gGameEngine.size.w, gGameEngine.size.h);
        var bg = new createjs.Shape(bgGraphics);
        gGameEngine.stage.addChild(bg);
        this.views.push(bg);

        // game title
        text = text || [{text: 'Boom', color: '#ffffff'}];

        var title1 = new createjs.Text(text[0].text, "bold 35px Helvetica", text[0].color);

        var titleWidth = title1.getMeasuredWidth();

        title1.x = gGameEngine.size.w / 2 - titleWidth / 2;
        title1.y = gGameEngine.size.h / 2 - title1.getMeasuredHeight() / 2 - 80;
        gGameEngine.stage.addChild(title1);
        this.views.push(title1);

        // modes buttons
        var modeSize = 110;
        var modesY = title1.y + title1.getMeasuredHeight() + 40;

        // singleplayer button
        var singleX = gGameEngine.size.w / 2 - 55;
        var singleBgGraphics = new createjs.Graphics().beginFill("rgba(0, 0, 0, 0.5)").drawRect(singleX, modesY, modeSize, modeSize);
        var singleBg = new createjs.Shape(singleBgGraphics);
        gGameEngine.stage.addChild(singleBg);
        this.views.push(singleBg);
        this.setHandCursor(singleBg);
        singleBg.addEventListener('click', function() {
            that.setMode('single');
        });

        var singleTitle = new createjs.Text("Play", "16px Helvetica", "#ff4444");
        var singleTitleWidth = singleTitle.getMeasuredWidth();
        var modeTitlesY = modesY + modeSize - singleTitle.getMeasuredHeight() - 20;

        singleTitle.x = singleX + (modeSize - singleTitleWidth) / 2;
        singleTitle.y = modeTitlesY;
        gGameEngine.stage.addChild(singleTitle);
        this.views.push(singleTitle)

        var iconsY = modesY + 13;
        var singleIcon = new createjs.Bitmap("img/betty.png");
        singleIcon.sourceRect = new createjs.Rectangle(0, 0, 48, 48);
        singleIcon.x = singleX + (modeSize - 48) / 2;
        singleIcon.y = iconsY;
        gGameEngine.stage.addChild(singleIcon);
        this.views.push(singleIcon);

    },

    showLoader: function() {
        var bgGraphics = new createjs.Graphics().beginFill("#000000").drawRect(0, 0, gGameEngine.size.w, gGameEngine.size.h);
        var bg = new createjs.Shape(bgGraphics);
        gGameEngine.stage.addChild(bg);

        var loadingText = new createjs.Text("Loading...", "20px Helvetica", "#FFFFFF");
        loadingText.x = gGameEngine.size.w / 2 - loadingText.getMeasuredWidth() / 2;
        loadingText.y = gGameEngine.size.h / 2 - loadingText.getMeasuredHeight() / 2;
        gGameEngine.stage.addChild(loadingText);
        gGameEngine.stage.update();
    }
});