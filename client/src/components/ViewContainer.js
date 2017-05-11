import React, { Component } from "react";
import "./ViewContainer.css";
import LoginView from "./LoginView";
import Match from "react-router/Match";
import RegisterView from "./RegisterView";
import RecoverView from "./RecoverView";
import UpdatePasswordView from "./UpdatePasswordView";

class ViewContainer extends Component {
  render() {
    return (
      <div className="view-container">
        <Match pattern="/login" component={LoginView}/>
        <Match pattern="/register" component={RegisterView}/>
        <Match pattern="/recover-password" component={RecoverView}/>
        <Match pattern="/update-password" component={UpdatePasswordView}/>
      </div>
    );
  }
}

export default ViewContainer;
