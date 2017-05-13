
const Client = {
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
 }
};

export default Client;
