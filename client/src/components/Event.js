import React, { Component } from "react";

class Event extends Component {
  render() {
    return (
      <li>
        <div className="event-item">
          <p>{this.props.event.name}</p>
          <div className="item-action-container">
            <a className="error" href="" name={this.props.event.id} onClick={this.props.delete.bind(this)}>Delete</a>
            <a className="success" href="">Edit</a>
            <a href="">View</a>
          </div>
        </div>
      </li>
    );
  }
}

export default Event;
