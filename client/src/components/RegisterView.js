import React, { Component } from "react";
import { isEmail } from "validator";
import "./RegisterView.css";
import ErrorList from "./ErrorList";
import PropTypes from "prop-types";
import Client from "../Client";
import Recaptcha from "react-gcaptcha";
import Loader from "./Loader";
import Redirect from "react-router/Redirect";

class RegisterView extends Component {
  state = {
    fields: {
      email: "",
      password: "",
      confirmpassword: "",
      org: "",
      neworg: "",
      recaptcha: "",
    },
    organizations: [],
    newOrg: "hidden",
    fieldErrors: [],
    _loading: false,
    _redirect: false,
  }

  componentWillMount() {
    const uri = "/api/visitor/organizations";
    Client.get(uri)
      .then(organizations => {
        if (organizations.length > 0) {
          this.setState({ organizations });
        }
      })
  }

  validate(fields) {
    const errors = [];
    if (!fields.email) errors.push(this.context.language.Errors.emailRequired);
    if (!fields.password || !fields.confirmpassword) errors.push(this.context.language.Errors.passwordRequired);
    if (fields.password !== fields.confirmpassword) errors.push(this.context.language.Errors.passwordDoesNotMatch);
    if (!fields.recaptcha) errors.push(this.context.language.Errors.captchaFails);
    if (fields.password.length < 10) errors.push(this.context.language.Errors.shortPassword);
    if (fields.password.length > 255) errors.push(this.context.language.Errors.longPassword);
    if (!isEmail(fields.email)) errors.push(this.context.language.Errors.invalidEmail);
    return errors;
  }

  callback = function (key) {
    let fields = this.state.fields;
    fields.recaptcha = key;
    this.setState(fields);
  }

  onInputChange(event) {
    const value = event.target.value;
    const name = event.target.name;
    const fields = this.state.fields;
    fields[name] = value;
    this.setState({ fields });

    const hiddenForm = document.querySelector("#on-select-change");

    if (this.state.fields.org === "new") {
      const newOrg = "";
      this.setState({newOrg});
    } else {
      const newOrg = "hidden";
      this.setState({newOrg});
    }
  }
  onFormSubmit(event) {
    event.preventDefault();
    this.setState({fieldErrors: []});
    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });


    // Return on Errors
    if(fieldErrors.length) return;

    // Do registration
    this.setState({_loading: true});
    const uri = "/api/visitor/registration";
    const user = {
      email: this.state.fields.email,
      password: this.state.fields.password,
      passwordConfirmation: this.state.fields.confirmpassword,
      organization: this.state.fields.neworg || this.state.fields.org,
      gRecaptchaResponse: this.state.fields.recaptcha,
    };


    Client.post(user, uri).then(res => {
      this.setState({_loading: false});
      if (res.hasOwnProperty("message")) {
        const fieldErrors = [];
        fieldErrors.push(res.message);
        this.setState({fieldErrors});
        this.forceUpdate();
      } else {
        this.setState({_redirect: true});
        this.setState({fields: {
          email: "",
          password: "",
          confirmpassword: "",
          org: "",
          neworg: "",
          recaptcha: "",
        }})
      }
    });
  }

  render() {

    const language = this.context.language.RegisterView;

    if (this.state._loading) {
      return (
        <Loader/>
      );
    } else if (this.state._redirect) {
      return (
        <Redirect to={{
            pathname: "/login",
            state:  { referrer: "/register" }
            }}/>
      );
    } else {
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
              {
                this.state.organizations.map(org => {
                  return <option className="capitalize" value={org}>{org}</option>
                })
              }
              <option value="new">New Organization</option>
              <option value="">No Organization</option>
            </select>
            <div id="on-select-change" className={this.state.newOrg}>
              <label htmlFor="neworg" className="capitalize">{language.newOrganization}:</label>
              <input
                name="neworg"
                value={this.state.fields.neworg}
                onChange={this.onInputChange.bind(this)}
                type="text">
              </input>
            </div>
            <Recaptcha
              sitekey='6Le13yAUAAAAAC4D1Ml81bW3WlGN83bZo4FzHU7Z'
              verifyCallback={this.callback.bind(this)}
              />
            <ErrorList errors={this.state.fieldErrors}/>
            <input
              type="submit"
              value={language.registerBtn}
              className="btn-primary"
              >
            </input>
          </form>
        </div>
      );
    }
  }
}

RegisterView.contextTypes = {
  language: PropTypes.object
}

export default RegisterView;
