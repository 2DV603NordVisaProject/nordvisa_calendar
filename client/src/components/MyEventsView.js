import React, { Component } from "react";
import "./MyEventsView.css";
import EventsList from "./EventsList";


class MyEventsView extends Component {
  render() {
    return (
      <div className="view">
        <h2>My Events</h2>
        <EventsList/>
      </div>
    );
  }
}

export default MyEventsView;
