import React, { Component } from "react";
import "./PendingRegistrationsView.css";
import RegistrationsList from "./RegistrationsList";

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
          <RegistrationsList/>
        <input type="submit" className="btn-primary" value="approve"></input>
        </form>
      </div>
    );
  }
}

export default PendingRegistrationsView;
