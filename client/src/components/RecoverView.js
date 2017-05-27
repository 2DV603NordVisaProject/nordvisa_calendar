import React, { Component } from "react";
import { isEmail } from "validator";
import ErrorList from "./ErrorList";
import PropTypes from "prop-types";
import Client from "../Client";
import Redirect from "react-router/Redirect";

class RecoverView extends Component {
  state = {
    fields: {
      email: "",
    },
    fieldErrors: [],
    _redirect: false,
  }

  validate(fields) {
    const errors = [];
    if (!isEmail(fields.email)) errors.push(this.context.language.Errors.invalidEmail);
    if (!fields.email) errors.push(this.context.language.Errors.invalidEmail);
    return errors;
  }

  onFormSubmit(event) {
    event.preventDefault();
    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on error.
    if (fieldErrors.length) return;

    const uri = "/api/visitor/request_password_recovery";

    Client.post(this.state.fields, uri)
      .then(res => {
        const fieldErrors = ["Email sent!"];
        this.setState({fieldErrors, fields: {
          email: "",
        }});
      });



    this.setState({ fields: {}, _redirect: true });
  }

  onInputChange(event) {
    const fields = this.state.fields;
    fields[event.target.name] = event.target.value;
    this.setState({ fields });
  }
  render() {

    const language = this.context.language.RecoverView;

    if (this.state._redirect) {
      return (
        <Redirect to={{
            pathname: "/login",
            state:  { referrer: "/recover-password" }
            }}/>
      );
    } else {
      return (
        <div className="lightbox login">
          <h2 className="uppercase">{language.recover}</h2>
          <form onSubmit={this.onFormSubmit.bind(this)}>
            <label htmlFor="email" className="capitalize">{language.email}:</label>
            <input
              name="email"
              type="text"
              value={this.state.fields.email}
              onChange={this.onInputChange.bind(this)}>
            </input>
            <ErrorList errors={this.state.fieldErrors}/>
            <input type="submit" value={language.request} className="btn-primary uppercase"></input>
          </form>
        </div>
      );
    }
  }
}

RecoverView.contextTypes = {
  language: PropTypes.object,
}

export default RecoverView;
