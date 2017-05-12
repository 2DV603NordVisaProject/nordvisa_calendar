import React, { Component } from "react";
import "./LoginView.css";

class LoginView extends Component {
  state = {
    fields: {
      email: "",
      password: "",
    },
  }

  onFormSubmit(event) {
    event.preventDefault();
    this.setState({fields: {email: "", password: ""}})
  }

  onInputChange(event) {
    let fields = this.state.fields;
    fields[event.target.name] = event.target.value;
    this.setState({fields});
  }
  render() {
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
          <input type="submit" className="btn-primary" value="login"></input>
        </form>
        <a href="/recover-password">Forgot Password?</a>
      </div>
    );
  }
}

export default LoginView;
