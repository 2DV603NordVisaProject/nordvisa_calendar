import React, { Component } from "react";
import "./ViewContainer.css";
import LoginView from "./LoginView";
import Match from "react-router/Match";

class ViewContainer extends Component {
  render() {
    return (
      <div className="view-container">
        <Match pattern="/login" component={LoginView}/>
      </div>
    );
  }
}

export default ViewContainer;
