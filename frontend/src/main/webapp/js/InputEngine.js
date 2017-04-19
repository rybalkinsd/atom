InputEngine = Class.extend({

    // move notification fps
    fps: 60,

    /**
     * A dictionary mapping ASCII key codes to string values describing
     * the action we want to take when that key is pressed.
     */
    bindings: {},

    /**
     * A dictionary mapping actions that might be taken in our game
     * to a boolean value indicating whether that action is currently being performed.
     */
    actions: {},

    possessed: null,

    subscribers: [],

    init: function() { },

    setup: function() {
        this.bind(37, 'left');
        this.bind(38, 'up');
        this.bind(39, 'right');
        this.bind(40, 'down');


        this.bind(32, 'bomb');

        this.bind(13, 'restart');
        this.bind(27, 'escape');
        this.bind(77, 'mute');

        // move multiple presses with fps frequency
        this.keyboardController({
            37 : this.onKeyDown,
            38 : this.onKeyDown,
            39 : this.onKeyDown,
            40 : this.onKeyDown
        }, 1000 / this.fps);

        document.addEventListener('keydown', this.onKeyDown);
        document.addEventListener('keyup', this.onKeyUp);
    },

    onKeyUp: function(event) {
        var action = gInputEngine.bindings[event.keyCode];
        if (action) {
            gInputEngine.actions[action] = false;
            event.preventDefault();
        }
        return false;
    },

    onKeyDown: function(event) {
        var action = gInputEngine.bindings[event.keyCode];
        if (action) {
            gInputEngine.actions[action] = true;
            var subscribers = gInputEngine.subscribers[action];
            if (subscribers) {
                for (var i = 0; i < subscribers.length; i++ ) {
                    subscribers[i]()
                }
            }

            event.preventDefault();
        }
        return false;
    },

    /**
     * The bind function takes an ASCII keycode and a string representing
     * the action to take when that key is pressed.
     */
    bind: function(key, action) {
        this.bindings[key] = action;
    },

    subscribe: function (action, callback) {
        this.subscribers[action] = this.subscribers[action] || [];
        this.subscribers[action].push(callback)
    },

    // Keyboard input with customisable repeat (set to 0 for no key repeat)
    keyboardController: function(keys, repeat) {

        // Lookup of key codes to timer ID, or null for no repeat
        var timers = {};

        // When key is pressed and we don't already think it's pressed, call the
        // key action callback and set a timer to generate another one after a delay
        document.onkeydown= function(event) {
            var key = (event || window.event).keyCode;
            if (!(key in keys))
                return true;
            if (!(key in timers)) {
                timers[key] = null;
                keys[key](event);
                if (repeat !== 0)
                    var f = function () {
                        keys[key](event);
                    };
                timers[key]= setInterval(f, repeat);
            }
            return false;
        };

        // Cancel timeout and mark key as released on keyup
        document.onkeyup = function(event) {
            var key= (event || window.event).keyCode;
            if (key in timers) {
                if (timers[key] !== null)
                    clearInterval(timers[key]);
                delete timers[key];
            }
        };

        // When window is unfocused we may not get key events. To prevent this
        // causing a key to 'get stuck down', cancel all held keys
        window.onblur = function() {
            for (key in timers)
                if (timers[key] !== null)
                    clearInterval(timers[key]);
            timers= {};
        };
    }
});

gInputEngine = new InputEngine();
