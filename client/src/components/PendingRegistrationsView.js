import React, { Component } from "react";
import "./PendingRegistrationsView.css";
import RegistrationsList from "./RegistrationsList";
import Client from "../Client";
import PropTypes from "prop-types";

class PendingRegistrationsView extends Component {
  state = {
    registrations: [],
    approve: [],
  }

  componentWillMount() {
    const uri = "/api/admin/registrations";
    Client.get(uri)
      .then(registrations => {
        this.setState({ registrations });
      });
  }

  onInputChange(event) {
    let approve = this.state.approve;

    if (approve.includes(event.target.value)) {
      approve = approve.filter((id) => {
        return id !== event.target.value;
      });
    } else {
      approve.push(event.target.value);
    }
    this.setState({approve});
  }

  onFormSubmit(event) {
    event.preventDefault();
    const uri = "/api/admin/registrations";
    const approve = this.state.approve;

    while (approve.length > 0) {
      const obj = {
        id: approve.shift(),
        approved: true
      }
      Client.post(obj, uri)
    }
    this.setState({ approve });
    this.forceUpdate();
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
        <form onSubmit={this.onFormSubmit.bind(this)}>
          <RegistrationsList registrations={this.state.registrations} onInputChange={this.onInputChange.bind(this)}/>
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
