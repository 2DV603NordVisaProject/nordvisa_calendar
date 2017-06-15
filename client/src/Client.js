'use strict';

const url = `${location.protocol}//${location.host}`;

// Serializes an object into foo=bar&baz=quuz form
const serialize = function serialize(obj) {
  const str = [];
  const keys = Object.keys(obj);
  let i;
  for (i = 0; i < keys.length; i += 1) {
    if (Object.prototype.hasOwnProperty.call(obj, keys[i])) {
      str.push(`${encodeURIComponent(keys[i])}=${encodeURIComponent(obj[keys[i]])}`);
    }
  }
  return str.join('&');
};

const getCookie = function getCookie(name) {
  // const nameEQ = `${name}=`;
  const ca = document.cookie.split(';');
  let value = null;
  let i;
  for (i = 0; i < ca.length; i += 1) {
    const currentCookie = ca[i];
    if (currentCookie.indexOf(name)) {
      const length = currentCookie.length;
      const valueStart = currentCookie.indexOf('=') + 1;
      value = currentCookie.substr(valueStart, length);
    }
  }
  return value;
};

const Client = {
  post: (obj, uri) => {
    const req = new Request(`${url}${uri}`, {
      method: 'POST',
      credentials: 'include',
      headers: new Headers({ 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' }),
      body: serialize(obj),
    });

    return fetch(req)
    .then((res) => {
      const contentType = res.headers.get('content-type');

      if (contentType && contentType.indexOf('application/json') !== -1) {
        return res.json();
      }

      return '';
    })
    .then(json => json)
    .catch(err => err);
  },
  get: (uri) => {
    const req = new Request(`${url}${uri}`, {
      method: 'GET',
      credentials: 'include',
      mode: 'no-cors',
      headers: new Headers({ 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' }),
    });

    return fetch(req)
    .then((res) => {
      const contentType = res.headers.get('content-type');

      if (contentType && contentType.indexOf('application/json') !== -1) {
        return res.json();
      }

      return '';
    })
    .then(json => json)
    .catch(err => err);
  },
  postEvent: (eventObj/* , uri*/) => {
    const data = new FormData();
    data.append('file', eventObj.file);

    fetch(`${url}/api/upload`, {
      method: 'POST',
      body: data,
    })
    .then(res => res.json())
    .then((json) => {
      console.log(json);
    });
  },
  uploadImage: (imageFile) => {
    const data = new FormData();
    data.append('files', imageFile);

    const req = new Request(`${url}/api/upload`, {
      method: 'POST',
      body: data,
    });

    return fetch(req)
    .then((res) => {
      if (!res.ok) {
        throw new Error(res.status);
      }

      return res.json();
    })
    .then(json => json)
    .catch((err) => {
      console.log(err.message);
      return err;
    });
  },
  login: (userObj, uri) => {
    const req = new Request(`${url}${uri}`, {
      method: 'POST',
      mode: 'no-cors',
      credentials: 'include',
      headers: new Headers({ 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' }),
      body: serialize(userObj),
    });

    return fetch(req)
    .then((res) => {
      if (res.ok) {
        document.cookie = 'ESESSION=TRUE; expires=;';

        const reqUser = new Request(`${url}/api/user/current`, {
          method: 'GET',
          mode: 'no-cors',
          credentials: 'include',
          headers: new Headers({ 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' }),
        });

        fetch(reqUser)
        .then(resp => resp.json())
        .then((json) => {
          document.cookie = `accessLevel=${json.role}; expires=;`;
        });

        return 'success';
      }
      return res.json();
    })
    .then(json => json)
    .catch(err => err);
  },
  logout: () => {
    const req = new Request(`${url}/logout`, {
      method: 'POST',
      mode: 'no-cors',
      credentials: 'include',
      headers: new Headers({ 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' }),
    });

    fetch(req)
    .then((res) => {
      if (res.ok) {
        document.cookie = 'ESESSION=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
        document.cookie = 'accessLevel=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
      }
    })
    .catch(err => err);
  },
  isLogedIn: () => {
    const cookies = document.cookie;
    if (cookies.indexOf('ESESSION') === -1) {
      return false;
    }
    return true;
  },
  getUserLevel: () => {
    if (getCookie('accessLevel') === null) {
      return 0;
    }

    const userLevel = getCookie('accessLevel');

    if (userLevel === 'ADMIN') {
      return 1;
    } else if (userLevel === 'SUPER_ADMIN') {
      return 2;
    }
    return 0;
  },
};

export default Client;
