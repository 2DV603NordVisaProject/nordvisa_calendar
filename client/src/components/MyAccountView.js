import React, { Component } from "react";
import UpdatePassword from "./UpdatePassword";
import UpdateAccount from "./UpdateAccount";

class MyAccountView extends Component {
  render() {
    return (
      <div className="view">
        <h2>My Account</h2>
        <UpdateAccount/>
        <UpdatePassword/>
      </div>
    );
  }
}

export default MyAccountView;
