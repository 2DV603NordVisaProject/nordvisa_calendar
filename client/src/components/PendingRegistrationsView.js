import React, { Component } from "react";
import "./PendingRegistrationsView.css";
import RegistrationsList from "./RegistrationsList";
import Client from "../Client";
import PropTypes from "prop-types";

class PendingRegistrationsView extends Component {
  state = {
    registrations: [],
  }

  componentWillMount() {
    const registrations = Client.getRegistrations();
    this.setState({registrations});
  }

  render() {

    const language = this.context.language.PendingRegistrationsView;

    return (
      <div className="view pending-view">
        <h2 className="uppercase">{language.pending}</h2>
        <div className="list-header">
          <p className="capitalize">{language.email}</p>
          <p className="capitalize">{language.organization}</p>
          <p className="capitalize">{language.approve}</p>
        </div>
        <form>
          <RegistrationsList registrations={this.state.registrations}/>
        <input type="submit" className="btn-primary" value={language.approve}></input>
        </form>
      </div>
    );
  }
}

PendingRegistrationsView.contextTypes = {
  language: PropTypes.object,
}

export default PendingRegistrationsView;
