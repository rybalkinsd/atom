var Utils = {};

/**
 * Returns true if positions are equal.
 */
Utils.comparePositions = function(pos1, pos2) {
    return pos1.x == pos2.x && pos1.y == pos2.y;
};


/**
 * Convert bitmap pixels position to entity on grid position.
 */
Utils.convertToEntityPosition = function(pixels) {
    var position = {};
    position.x = Math.round(pixels.x / gGameEngine.tileSize);
    position.y = Math.round(pixels.y /gGameEngine.tileSize);
    return position;
};

Utils.getEntityPosition = function (pixels) {
    var position = {};
    position.x = pixels.x;
    position.y = -pixels.y + 12 * 32;
    return position;
}

/**
 * Convert entity on grid position to bitmap pixels position.
 */
Utils.convertToBitmapPosition = function(entity) {
    var position = {};
    position.x = entity.x * gGameEngine.tileSize;
    position.y = entity.y * gGameEngine.tileSize;
    return position;
};

/**
 * Removes an item from array.
 */
Utils.removeFromArray = function(array, item) {
    for (var i = 0; i < array.length; i++) {
        if (item == array[i]) {
            array.splice(i, 1);
        }
    }
    return array;
};

Utils.getCookies = function(name) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

Utils.setCookie = function(name, value, options) {
    options = options || {};

    var expires = options.expires;

    if (typeof expires == "number" && expires) {
        var d = new Date();
        d.setTime(d.getTime() + expires * 1000);
        expires = options.expires = d;
    }
    if (expires && expires.toUTCString) {
        options.expires = expires.toUTCString();
    }

    value = encodeURIComponent(value);

    var updatedCookie = name + "=" + value;

    for (var propName in options) {
        updatedCookie += "; " + propName;
        var propValue = options[propName];
        if (propValue !== true) {
            updatedCookie += "=" + propValue;
        }
    }

    document.cookie = updatedCookie;
}