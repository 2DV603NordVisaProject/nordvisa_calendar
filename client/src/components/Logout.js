import React, { Component } from "react";
import Redirect from "react-router/Redirect";
import Client from "../Client";

class Logout extends Component {
  constructor() {
    super();
    Client.logout();
  }
  render() {
    return (
      <Redirect
        to='/login'
      />
    );
  }
}

export default Logout;
