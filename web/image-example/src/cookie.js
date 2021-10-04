const ACCESS_TOKEN_KEY = "accessToken";

function _setCookie(name, value, exp) {
    let date = new Date();
    date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);
    document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
}

function _getCookie(name) {
    let value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value ? value[2] : undefined;
}

function _deleteCookie (name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1999 00:00:10 GMT;';
}

export function _setAccessToken(token) {
    _setCookie(ACCESS_TOKEN_KEY, token, 1);
}

export function _getAccessToken() {
    return _getCookie(ACCESS_TOKEN_KEY);
}

export function _deleteAccessToken () {
    _deleteCookie(ACCESS_TOKEN_KEY);
}

