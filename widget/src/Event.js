import React, { Component } from "react";
import "./Event.css"

class Event extends Component {
  render() {
    return (
      <div className="event-item">
        <div className="date">{this.props.event.date}</div>
        <div className="name"><a href="#" className="event-link">{this.props.event.name}</a></div>
      </div>
    )
  }
}

export default Event;
