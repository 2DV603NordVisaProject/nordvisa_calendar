import React, { Component } from "react";
import "./ErrorList.css";

class ErrorList extends Component {
  render() {
    return (
      <ul className="error-list">
      {
        this.props.errors.map((error, i) =>  (
          <li className="error" key={i}>{error}</li>
        ))
      }
      </ul>
    )
  }
}

export default ErrorList;
