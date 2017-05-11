import React, { Component } from "react";
import "./LoginView.css";

class LoginView extends Component {
  render() {
    return (
      <div className="lightbox login">
        <h2>Login</h2>
        <form>
          <label htmlFor="email">Email:</label>
          <input name="email" type="text"></input>
          <label htmlFor="password">Password:</label>
          <input name="password" type="password"></input>
          <button>login</button>
        </form>
        <a href="/recover-password">Forgot Password?</a>
      </div>
    );
  }
}

export default LoginView;
