import React, { Component } from "react";

class Registration extends Component {
  render() {
    return (
      <li>
        <div className="pending-item">
          <div className="email">
            <p>johan.gudmundsson2012@gmail.com</p>
          </div>
          <div className="org">
            <p>The long sample organization</p>
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
