import React, { Component } from "react";

class Registration extends Component {
  render() {
    return (
      <li>
        <div className="pending-item">
          <div className="email">
            <p>{this.props.registration.email}</p>
          </div>
          <div className="org">
            <p>{this.props.registration.org}</p>
          </div>
          <div className="approve-action">
            <input type="checkbox" className="approve"></input>
          </div>
        </div>
      </li>
    );
  }
}

export default Registration;
