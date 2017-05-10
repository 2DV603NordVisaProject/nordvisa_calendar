import React, { Component } from "react";
import "./RegisterView.css";

class RegisterView extends Component {

  onInputChange(event) {
    let value = event.target.value;

    if (value === "new") {
      document.querySelector(".change").classList.remove("hidden");
    } else {
      document.querySelector(".change").classList.add("hidden");
    }

  }

  render() {
    return (
      <div className="register">
        <h2>Register</h2>
        <form>
          <label htmlFor="email">Email:</label>
          <input name="email" type="text"></input>
          <label htmlFor="password">Password:</label>
          <input name="password" type="password"></input>
          <label htmlFor="confirmpassword">Password Again:</label>
          <input name="confirmpassword" type="password"></input>
          <label htmlFor="org">Organization:</label>
          <select name="org" onChange={this.onInputChange}>
            <option>NordVisa</option>
            <option value="new">New Organization</option>
            <option selected>No Organization</option>
          </select>
          <div className="change hidden">
            <label htmlFor="neworg">New Organization:</label>
            <input name="neworg" type="text"></input>
          </div>
          <div className="g-recaptcha" data-sitekey="6Le13yAUAAAAAC4D1Ml81bW3WlGN83bZo4FzHU7Z"></div>
          <button>REGISTER</button>
        </form>
      </div>
    );
  }
}

export default RegisterView;
