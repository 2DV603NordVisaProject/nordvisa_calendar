"use strict";

const url = "http://localhost:8080"

const Client = {
  post: (obj, uri) => {
    console.log(obj);
    const config = {
      method: "POST",
      headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
      },
      data: obj
    }

    return fetch(`${url}${uri}`, config).then(res => res.json);
 },
 login: () => {
   localStorage.setItem("logedIn", "true");
   localStorage.setItem("userLevel", "superadmin");

   return new Promise((resolve, reject) => {
   setTimeout(function(){
     resolve("Success!");
   }, 3000);
 });
 },
 logout: () => {
   localStorage.setItem("logedIn", "false");
   localStorage.setItem("userLevel", "null");
 },
 isLogedIn: () => {
   return JSON.parse(localStorage.getItem("logedIn"));
 },
 getUserLevel: () => {
   let accessLevel = 0;
   const userLevel = localStorage.getItem("userLevel");
   if (userLevel === "admin") {
     accessLevel = 1;
   } else if (userLevel === "superadmin") {
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
