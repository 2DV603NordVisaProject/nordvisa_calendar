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
          <li>
            <div className="pending-item">
              <div className="email">
                <p>axel@gmail.com</p>
              </div>
              <div className="org">
                <p>The long sample organization</p>
              </div>
              <div className="approve-action">
                <input type="checkbox" className="approve"></input>
              </div>
            </div>
          </li>
          <li>
            <div className="pending-item">
              <div className="email">
                <p>axel@gmail.com</p>
              </div>
              <div className="org">
                <p>NordVisa</p>
              </div>
              <div className="approve-action">
                <input type="checkbox" className="approve"></input>
              </div>
            </div>
          </li>
        </ul>
        <input type="submit" className="btn-primary" value="approve"></input>
        </form>
      </div>
    );
  }
}

export default PendingRegistrationsView;
