import React, { Component } from "react";
import "./MyEventsView.css";


class MyEventsView extends Component {
  render() {
    return (
      <div className="view">
        <h2>My Events</h2>
        <div className="event-list">
          <div className="list-header">
            <p>Events</p>
          </div>
          <ul>
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
          <li>
            <div className="event-item">
              <p>Sample Event Two</p>
              <div className="item-action-container">
                <a className="error" href="">Delete</a>
                <a className="success" href="">Edit</a>
                <a href="">View</a>
              </div>
            </div>
          </li>
          <li>
            <div className="event-item">
              <p>Sample Event Three</p>
              <div className="item-action-container">
                <a className="error" href="">Delete</a>
                <a className="success" href="">Edit</a>
                <a href="">View</a>
              </div>
            </div>
          </li>
          </ul>
        </div>
      </div>
    );
  }
}

export default MyEventsView;
