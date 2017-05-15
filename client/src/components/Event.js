import React, { Component } from "react";
import Link from "react-router/Link";

class Event extends Component {
  render() {
    return (
      <li>
        <div className="event-item">
          <p>{this.props.event.name}</p>
          <div className="item-action-container">
            <a className="error clickable" href="" name={this.props.event.id} onClick={this.props.delete.bind(this)}>Delete</a>
            <Link to={`/user/event/edit/${this.props.event.id}`} className="success">Edit</Link>
            <Link to={`/user/event/view/${this.props.event.id}`}>View</Link>
          </div>
        </div>
      </li>
    );
  }
}

export default Event;
