import React, { Component } from "react";
import PropTypes from "prop-types";

class Registration extends Component {

  render() {

    const language = this.context.language.PendingRegistrationsView;

    return (
      <li>
        <div className="pending-item">
          <div className="email">
            <p>{this.props.registration.email}</p>
          </div>
          <div className="org">
            <p>{this.props.registration.organization.name || this.props.registration.organization.changePending}</p>
          </div>
          <div className="approve-action">
            <select onChange={this.props.onInputChange.bind(this)} className="capitalize small-form" name={this.props.registration.id}>
              <option classname="capitalize">{language.action}</option>
              <option className="capitalize small-form" value="approve">{language.approve}</option>
              <option className="capitalize small-form" value="deny">{language.deny}</option>
            </select>
          </div>
        </div>
      </li>
    );
  }
}

Registration.contextTypes = {
  language: PropTypes.object,
}

export default Registration;
