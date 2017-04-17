InputEngine = Class.extend({
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

    subscribers: [],

    init: function() {
    },

    setup: function() {
        this.bind(38, 'up');
        this.bind(37, 'left');
        this.bind(40, 'down');
        this.bind(39, 'right');
        this.bind(32, 'bomb');
        this.bind(18, 'bomb');

        this.bind(13, 'restart');
        this.bind(27, 'escape');
        this.bind(77, 'mute');

        document.addEventListener('keydown', this.onKeyDown);
        document.addEventListener('keyup', this.onKeyUp);
    },

    onKeyUp: function(event) {
        var action = gInputEngine.bindings[event.keyCode];
        if (action) {
            gInputEngine.actions[action] = true;
            event.preventDefault();
        }
        return false;
    },

    onKeyDown: function(event) {
        var action = gInputEngine.bindings[event.keyCode];
        if (action) {
            gInputEngine.actions[action] = false;

            var subscribers = gInputEngine.subscribers[action];
            if (subscribers) {
                for (var i = 0; i <subscribers.length; i++ ) {
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
    }
});

gInputEngine = new InputEngine();