import React, { Component } from "react";
import "./PendingRegistrationsView.css";

class PendingRegistrationsView extends Component {
  render() {
    return (
      <div className="view pending-view">
        <h2>Pending Registrations</h2>
        <div className="list-header">
          <p>Email</p>
          <p>Organization</p>
          <p>Approve</p>
        </div>
        <form>
        <ul>
          <li>
            <div className="pending-item">
              <p>johan.gudmundsson2012@gmail.com</p>
              <p>The long sample organization</p>
              <input type="checkbox" className="approve"></input>
            </div>
          </li>
          <li>
            <div className="pending-item">
              <p>axel@gmail.com</p>
              <p>The long sample organization</p>
              <input type="checkbox" className="approve"></input>
            </div>
          </li>
          <li>
            <div className="pending-item">
              <p>johan.gudmundsson2012@gmail.com</p>
              <p>NordVisa</p>
              <input type="checkbox" className="approve"></input>
            </div>
          </li>
        </ul>
        <button>approve</button>
        </form>
      </div>
    );
  }
}

export default PendingRegistrationsView;
