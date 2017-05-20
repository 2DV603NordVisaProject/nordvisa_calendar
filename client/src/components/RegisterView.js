import React, { Component } from "react";
import { isEmail } from "validator";
import "./RegisterView.css";
import ErrorList from "./ErrorList";
import PropTypes from "prop-types";
import Client from "../Client";

class RegisterView extends Component {
  state = {
    fields: {
      email: "",
      password: "",
      confirmpassword: "",
      org: "",
      neworg: "",
    },
    fieldErrors: [],
  }

  validate(fields) {
    const errors = [];
    if (!fields.email) errors.push("Email Required!");
    if (!fields.password || !fields.confirmpassword) errors.push("Password Required!");
    if (fields.password !== fields.confirmpassword) errors.push("Password's doesn't match!");
    if (fields.password.length < 10) errors.push("Password is too short!");
    if (fields.password.length > 255) errors.push("Password is too long!");
    if (!isEmail(fields.email)) errors.push("Invalid email!");
    return errors;
  }

  onInputChange(event) {
    const value = event.target.value;
    const name = event.target.name;
    const fields = this.state.fields;
    fields[name] = value;
    this.setState({ fields });

    const hiddenForm = document.querySelector(".change");

    if (value === "new" && name === "org") {
      hiddenForm.classList.remove("hidden");
    } else {
      hiddenForm.classList.add("hidden");
    }
  }
  onFormSubmit(event) {
    //TODO
    event.preventDefault();
    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on Errors
    if (this.state.fieldErrors.length) return;

    // Do registration
    const uri = "/api/visitor/registration";
    const user = {
      email: this.state.fields.email,
      password: this.state.fields.password,
      passwordConfirmation: this.state.fields.confirmpassword,
      organization: this.state.fields.neworg || this.state.fields.org
    };


    Client.post(user, uri).then(res => {
      const errors = [];
      errors.push(res);
      if (this.state.fieldErrors.length) return;
    })

    this.setState({fields: {
      email: "",
      password: "",
      confirmpassword: "",
      org: "",
      neworg: "",
    }})
  }

  render() {

    const language = this.context.language.RegisterView;

    return (
      <div className="lightbox register">
        <h2 className="uppercase">{language.register}</h2>
        <form className="center" onSubmit={this.onFormSubmit.bind(this)}>
          <label htmlFor="email" className="capitalize">{language.email}:</label>
          <input
            name="email"
            type="text"
            value={this.state.fields.email}
            onChange={this.onInputChange.bind(this)}>
          </input>
          <label htmlFor="password" className="capitalize">{language.password}:</label>
          <input
            name="password"
            value={this.state.fields.password}
            onChange={this.onInputChange.bind(this)}
            type="password">
          </input>
          <label htmlFor="confirmpassword" className="capitalize">{language.confirmPassword}:</label>
          <input
            name="confirmpassword"
            value={this.state.fields.confirmpassword}
            onChange={this.onInputChange.bind(this)}
            type="password">
          </input>
          <label htmlFor="org" className="capitalize">{language.organization}:</label>
          <select
            name="org"
            onChange={this.onInputChange.bind(this)}
            value={this.state.fields.org}
            defaultValue="">
            <option value="NordVisa">NordVisa</option>
            <option value="new">New Organization</option>
            <option value="">No Organization</option>
          </select>
          <div className="change hidden">
            <label htmlFor="neworg" className="capitalize">{language.newOrganization}:</label>
            <input
              name="neworg"
              value={this.state.fields.neworg}
              onChange={this.onInputChange.bind(this)}
              type="text">
            </input>
          </div>
          <div className="g-recaptcha" data-sitekey="6Le13yAUAAAAAC4D1Ml81bW3WlGN83bZo4FzHU7Z"></div>
          <ErrorList errors={this.state.fieldErrors}/>
          <input type="submit" value={language.registerBtn} className="btn-primary"/>
        </form>
      </div>
    );
  }
}

RegisterView.contextTypes = {
  language: PropTypes.object
}

export default RegisterView;
