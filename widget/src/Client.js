
const url = "http://localhost:8080"

const serialize = function(obj) {
  var str = [];
  for(var p in obj)
    if (obj.hasOwnProperty(p)) {
      str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
    }
  return str.join("&");
};

export default {
  get: (uri) => {
    const req = new Request(`${url}${uri}`, {
      method: "GET",
      credentials: 'include',
      mode: "no-cors",
      headers: new Headers({"Content-Type" : "application/x-www-form-urlencoded; charset=utf-8"}),
    })

    return fetch(req)
      .then(res => {
        const contentType = res.headers.get("content-type");

        if (contentType && contentType.indexOf("application/json") !== -1) {
          return res.json();
        }

        return "";
      })
      .then(json => {
        return json;
      })
      .catch(err => {
        console.log(err);;
      });
  }
}

/*
get: function() {
  return [
    {
      name: "a short sample event",
      date: "2017-07-21",
    },
    {
      name: "a very very very long sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
    {
      name: "a short sample event",
      date: "july 21",
    },
  ];
}
 */
