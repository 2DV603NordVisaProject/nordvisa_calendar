import React, { Component } from "react";
import "./LoginView.css";

class LoginView extends Component {
  render() {
    return (
      <div className="login">
        <h2>Login</h2>
        <form>
          <label htmlFor="email">Email:</label>
          <input name="email" type="text"></input>
          <label htmlFor="password">Password:</label>
          <input name="password" type="password"></input>
          <button>LOGIN</button>
        </form>
        <a href="/reset">Forgot Password?</a>
      </div>
    );
  }
}

export default LoginView;
