import React, { Component } from "react";
import Event from "./Event";
import "./CalendarView.css";

class CalendarView extends Component {
  render() {
    return (
      <div className="event-list">
        {
          this.props.events.map(event => (
            <Event event={event}/>
          ))
        }
      </div>
    )
  }
}

export default CalendarView;
