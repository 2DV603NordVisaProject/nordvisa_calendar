import React, { Component } from "react";

class Event extends Component {
  render() {
    return (
      <li>
        <div className="event-item">
          <p>Sample Event One</p>
          <div className="item-action-container">
            <a className="error" href="">Delete</a>
            <a className="success" href="">Edit</a>
            <a href="">View</a>
          </div>
        </div>
      </li>
    );
  }
}

export default Event;
