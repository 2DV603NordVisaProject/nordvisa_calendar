import React, { Component } from "react";
import "./ViewContainer.css";
import LoginView from "./LoginView";
import Match from "react-router/Match";
import RegisterView from "./RegisterView";

class ViewContainer extends Component {
  render() {
    return (
      <div className="view-container">
        <Match pattern="/login" component={LoginView}/>
        <Match pattern="/register" component={RegisterView}/>
      </div>
    );
  }
}

export default ViewContainer;
