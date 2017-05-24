"use strict";

const url = "http://localhost:8080"

const serialize = function(obj) {
  var str = [];
  for(var p in obj)
    if (obj.hasOwnProperty(p)) {
      str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
    }
  return str.join("&");
};

const getCookie = function(name) {
  let nameEQ = name + "=";
  let ca = document.cookie.split(';');
  let value = null;
  for(var i=0;i < ca.length;i++) {
      var currentCookie = ca[i];
      if (currentCookie.indexOf(name)) {
        let length = currentCookie.length
        let valueStart = currentCookie.indexOf("=") + 1;
        value = currentCookie.substr(valueStart, length);
      }

  }
  return value;
}

const Client = {
  post: (obj, uri) => {
    const req = new Request(`${url}${uri}`, {
      method: "POST",
      credentials: 'include',
      headers: new Headers({"Content-Type" : "application/x-www-form-urlencoded; charset=utf-8"}),
      body: serialize(obj)
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
        return err;
      });
 },
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
       return err;
     });
 },
 postEvent: (eventObj, uri) => {
   let data = new FormData()
   data.append("file", eventObj.file)

   fetch(`${url}/api/upload`, {
     method: "POST",
     body: data
   }).then(res => {
     return res.json();
   }).then(json => {
     console.log(json);
   })
 },
 uploadImage: (imageFile) => {
   let data = new FormData();
   data.append("files", imageFile);
   console.log(imageFile);
   console.log(Array.from(data.entries()));

   const req = new Request(`${url}/api/upload`, {
     method: "POST",
     body: data
   });

   return fetch(req)
    .then(res => {
      if(!res.ok) {
        throw new Error(res.status);
      }

      return res.json();
    }).then(json => {
      return json;
    }).catch(err => {
      console.log(err.message);
      return err;
    });
 },
 login: function(userObj, uri) {

   const req = new Request(`${url}${uri}`, {
     method: "POST",
     mode: "no-cors",
     credentials: 'include',
     headers: new Headers({"Content-Type" : "application/x-www-form-urlencoded; charset=utf-8"}),
     body: serialize(userObj)
   })

   return fetch(req)
     .then(res => {

       if (res.ok) {
         document.cookie = "ESESSION=TRUE; expires=;";

         const reqUser = new Request(`${url}/api/user/current`, {
           method: "GET",
           mode: "no-cors",
           credentials: 'include',
           headers: new Headers({"Content-Type" : "application/x-www-form-urlencoded; charset=utf-8"}),
         })

         fetch(reqUser)
          .then(res => {
            return res.json();
          })
          .then(json => {
            document.cookie = `accessLevel=${json.role}; expires=;`
          })

         return "success";
       } else {
         return res.json();
       }
     })
     .then(json => {
       return json;
     })
     .catch(err => {
       return err;
     });
 },
 logout: () => {
   const req = new Request(`${url}/logout`, {
     method: "POST",
     mode: "no-cors",
     credentials: 'include',
     headers: new Headers({"Content-Type" : "application/x-www-form-urlencoded; charset=utf-8"}),
   })

   fetch(req)
     .then(res => {
       if (res.ok) {
         document.cookie = "ESESSION=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
         document.cookie = "accessLevel=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
       }
     })
     .catch(err => {
       return err;
     });
 },
 isLogedIn: () => {
   const cookies = document.cookie;
   if (cookies.indexOf("ESESSION") === -1) {
     return false;
   } else {
     return true;
   }
 },
 getUserLevel: () => {
   if (getCookie(accessLevel) === null) {
     return;
   }

   const userLevel = getCookie(accessLevel);

   let accessLevel = 0;
   if (userLevel === "ADMIN") {
     accessLevel = 1;
   } else if (userLevel === "SUPER_ADMIN") {
     accessLevel = 2;
   }
   return accessLevel;
 },
 getMembers: () => {
   return [
     {
       email: "sample1@gmail.com",
       userLevel: "user",
     },
     {
       email: "sample2@gmail.com",
       userLevel: "admin",
     },
     {
       email: "sample3@gmail.com",
       userLevel: "superadmin",
     }
   ];
 },
 getRegistrations: () => {
   return [
     {
       email: "johan.gudmundsson2012@gmail.com",
       org: "The long sample organization",
     },
     {
       email: "johan.gudmundsson2012@gmail.com",
       org: "sample org",
     },
     {
       email: "axel@gmail.com",
       org: "NordVisa",
     }
   ];
 },
 getEvents: () => {
   return [
     {
       name: "Sample Event One",
       id: 1,
       date: "2020 July",
       location: "YOLO Land",
       desc: "test test test test test test test test test",
     },
     {
       name: "Sample Event Two",
       id: 2,
       name: "Swag?",
       date: "2020 July",
       location: "YOLO Land",
       desc: "test test test test test test test test test",
     },
     {
       name: "Sample Event Three",
       id: 3,
       name: "Swag?",
       date: "2020 July",
       location: "YOLO Land",
       desc: "test test test test test test test test test",
     },
   ];
 },
 getEvent: (id) => {
   const events = [
     {
       name: "Sample Event One",
       id: 1,
       date: "2020 July",
       location: "YOLO Land",
       desc: "test test test test test test test test test",
     },
     {
       name: "Sample Event Two",
       id: 2,
       name: "Swag?",
       date: "2020 July",
       location: "YOLO Land",
       desc: "test test test test test test test test test",
     },
     {
       name: "Sample Event Three",
       id: 3,
       name: "Swag?",
       date: "2020 July",
       location: "YOLO Land",
       desc: "test test test test test test test test test",
     },
   ];

   return event = events.find((event) => event.id == id);
 }
};

export default Client;
