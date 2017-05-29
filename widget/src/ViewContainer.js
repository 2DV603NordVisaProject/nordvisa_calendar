import React, { Component } from "react";
import CalendarView from "./CalendarView";

class ViewContainer extends Component {
  render() {

    // Quick ref to props
    const view = this.props.view;

    // Render UI based on view state
    if (view === "calendar") {
      return (
        <CalendarView events={this.props.events}/>
      )
    } else {
      // Render map
    }
  }
}

export default ViewContainer;
