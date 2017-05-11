import React, { Component } from "react";
import "./ViewContainer.css";
import LoginView from "./LoginView";
import Match from "react-router/Match";
import RegisterView from "./RegisterView";
import RecoverView from "./RecoverView";

class ViewContainer extends Component {
  render() {
    return (
      <div className="view-container">
        <Match pattern="/login" component={LoginView}/>
        <Match pattern="/register" component={RegisterView}/>
        <Match pattern="/recover-password" component={RecoverView}/>
      </div>
    );
  }
}

export default ViewContainer;
