Menu = Class.extend({
    visible: true,

    bmp: null,

    views: [],

    init: function () {

        gGameEngine.playersCount = 0;

        var spriteSheet = new createjs.SpriteSheet({
            images: [gGameEngine.playerGirlImg],
            frames: { width: 48, height: 48, regX: 10, regY: 12 },
            animations: {
                down: [0, 3, 'down', 0.1]
            }
        });
        this.bmp = new createjs.Sprite(spriteSheet);
        this.bmp.x = gGameEngine.size.w / 2 - 48 / 2;
        this.bmp.y = gGameEngine.size.h / 2 - 48 / 2;

        this.showLoader();
    },

    show: function (text) {
        this.visible = true;
        this.clear();
        gGameEngine.stage.update();
        this.draw(text);
    },

    clear: function () {
        for (var i = 0; i < this.views.length; i++) {
            gGameEngine.stage.removeChild(this.views[i]);
        }

        this.views = [];
    },

    hide: function () {
        this.visible = false;

        this.clear();
    },

    update: function () {
        if (this.visible) {
            for (var i = 0; i < this.views.length; i++) {
                gGameEngine.moveToFront(this.views[i]);
            }
        }
    },

    setHandCursor: function (btn) {
        btn.addEventListener('mouseover', function () {
            document.body.style.cursor = 'pointer';
        });
        btn.addEventListener('mouseout', function () {
            document.body.style.cursor = 'auto';
        });
    },

    start: function () {
        gGameEngine.menu.hide();
        gGameEngine.playing = true;
        gGameEngine.serverProxy.getSessionIdFromMatchMaker();
        gGameEngine.restart();
        this.showPlayersWaiting();
    },

    draw: function (text) {
        var that = this;

        // semi-transparent black background
        var bgGraphics = new createjs.Graphics().beginFill("rgba(0, 0, 0, 0.5)").drawRect(0, 0, gGameEngine.size.w, gGameEngine.size.h);
        var bg = new createjs.Shape(bgGraphics);
        gGameEngine.stage.addChild(bg);
        this.views.push(bg);


        // start button
        var startButtonX = gGameEngine.size.w / 2 - 55;
        var startButtonY = gGameEngine.size.h / 2 - 80;
        var startButtonSize = 110;

        var singleBgGraphics = new createjs.Graphics().beginFill("rgba(0, 0, 0, 0.5)").drawRect(startButtonX, startButtonY, startButtonSize, startButtonSize);
        var singleBg = new createjs.Shape(singleBgGraphics);
        gGameEngine.stage.addChild(singleBg);
        this.views.push(singleBg);
        this.setHandCursor(singleBg);
        singleBg.addEventListener('click', function () {
            that.start();
        });

        var playButton = new createjs.Text("Play", "32px Helvetica", "#ff4444");

        var singleTitleWidth = playButton.getMeasuredWidth();
        var modeTitlesY = startButtonY + startButtonSize - playButton.getMeasuredHeight() - 20;

        playButton.x = startButtonX + (startButtonSize - singleTitleWidth) / 2;
        playButton.y = modeTitlesY;
        if (text != null) {
            var myText = new createjs.Text(text["text"], "32px Helvetica", text["color"]);
            myText.x = gGameEngine.size.w / 2 - myText.getMeasuredWidth() / 2;
            myText.y = playButton.y + 82;
            gGameEngine.stage.addChild(myText);
            this.views.push(myText);
        }
        gGameEngine.stage.addChild(playButton);
        this.views.push(playButton);
        var iconsY = startButtonY + 13;
        var singleIcon = new createjs.Bitmap("img/betty.png");
        singleIcon.sourceRect = new createjs.Rectangle(0, 0, 48, 48);
        singleIcon.x = startButtonX + (startButtonSize - 48) / 2;
        singleIcon.y = iconsY;
        gGameEngine.stage.addChild(singleIcon);
        this.views.push(singleIcon);
    },

    showLoader: function () {
        var bgGraphics = new createjs.Graphics().beginFill("#000000").drawRect(0, 0, gGameEngine.size.w, gGameEngine.size.h);
        var bg = new createjs.Shape(bgGraphics);
        gGameEngine.stage.addChild(bg);
        this.views.push(bg);

        var loadingText = new createjs.Text("Loading...", "20px Helvetica", "#FFFFFF");
        loadingText.x = gGameEngine.size.w / 2 - loadingText.getMeasuredWidth() / 2;
        loadingText.y = gGameEngine.size.h / 2 - loadingText.getMeasuredHeight() / 2 - 150;
        gGameEngine.stage.addChild(loadingText);
        this.views.push(loadingText);
        gGameEngine.stage.update();
    },

    showPlayersWaiting: function () {
        var bgGraphics = new createjs.Graphics().beginFill("#000000").drawRect(0, 0, gGameEngine.size.w, gGameEngine.size.h);
        var bg = new createjs.Shape(bgGraphics);
        gGameEngine.stage.addChild(bg);
        this.views.push(bg);

        var loadingText = new createjs.Text("Game searching...", "20px Helvetica", "#FFFFFF");
        loadingText.x = gGameEngine.size.w / 2 - loadingText.getMeasuredWidth() / 2;
        loadingText.y = gGameEngine.size.h / 2 - loadingText.getMeasuredHeight() / 2 - 150;
        gGameEngine.stage.addChild(loadingText);
        this.views.push(loadingText);

        this.bmp.gotoAndPlay('down');
        gGameEngine.stage.addChild(this.bmp);
        this.views.push(this.bmp);
        gGameEngine.stage.update();
    },

    showConnectionsWaiting: function () {
        var bgGraphics = new createjs.Graphics().beginFill("#000000").drawRect(0, 0, gGameEngine.size.w, gGameEngine.size.h);
        var bg = new createjs.Shape(bgGraphics);
        gGameEngine.stage.addChild(bg);
        this.views.push(bg);

        var loadingText = new createjs.Text("Waiting for players...", "20px Helvetica", "#FFFFFF");
        loadingText.x = gGameEngine.size.w / 2 - loadingText.getMeasuredWidth() / 2;
        loadingText.y = gGameEngine.size.h / 2 - loadingText.getMeasuredHeight() / 2 - 150;
        gGameEngine.stage.addChild(loadingText);
        this.views.push(loadingText);
        gGameEngine.stage.addChild(this.bmp);
        this.views.push(this.bmp);
        gGameEngine.stage.update();
    }
});