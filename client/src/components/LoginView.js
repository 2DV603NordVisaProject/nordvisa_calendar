import React, { Component } from "react";
import "./LoginView.css";
import { isEmail } from "validator";
import ErrorList from "./ErrorList";
import Client from "../Client";
import Redirect from "react-router/Redirect";
import Loader from "./Loader";
import Link from "react-router/Link";

class LoginView extends Component {
  state = {
    fields: {
      email: "",
      password: "",
    },
    fieldErrors: [],
    loginInProgress: false,
    shouldRedirect: false,
  }

  performLogin() {
    this.setState({ loginInProgress: true });
    Client.login().then(() => (
      this.setState({ shouldRedirect: true })
    ));
  }

  validate(fields) {
    const errors = []
    if (!isEmail(fields.email)) errors.push("Email is invalid!");
    if (!fields.password) errors.push("Password field is empty!")
    return errors
  }

  onFormSubmit(event) {
    event.preventDefault();

    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on Errors
    if (fieldErrors.length) return;

    //TODO Might change
    this.performLogin();
    this.setState({fields: {email: "", password: ""}})
  }

  onInputChange(event) {
    let fields = this.state.fields;
    fields[event.target.name] = event.target.value;
    this.setState({fields});
  }
  render() {
    if (this.state.shouldRedirect) {
      return (
        <Redirect to="/user/my-events"/>
      );
    } else if (this.state.loginInProgress) {
      return (
        <Loader/>
      );
    } else {
      return (
        <div className="lightbox login">
          <h2>Login</h2>
          <form onSubmit={this.onFormSubmit.bind(this)}>
            <label htmlFor="email">Email:</label>
            <input
              name="email"
              value={this.state.fields.email}
              onChange={this.onInputChange.bind(this)}
              type="text">
            </input>
            <label htmlFor="password">Password:</label>
            <input
              name="password"
              onChange={this.onInputChange.bind(this)}
              value={this.state.fields.password}
              type="password">
            </input>
            <ErrorList errors={this.state.fieldErrors}/>
            <input type="submit" className="btn-primary" value="login"></input>
          </form>
          <Link to="/recover-password">Forgot Password?</Link>
        </div>
      );
    }
  }
}

export default LoginView;
