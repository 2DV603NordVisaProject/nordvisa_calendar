import React, { Component } from "react";
import Event from "./Event";

class EventsList extends Component {
  render() {
    return (
      <div className="event-list">
        <div className="list-header">
          <p>Events</p>
        </div>
        <ul>
          <Event/>
        </ul>
      </div>
    );
  }
}

export default EventsList;
