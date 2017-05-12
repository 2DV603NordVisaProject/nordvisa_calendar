import React, { Component } from "react";
import "./RegisterView.css";

class RegisterView extends Component {

  onInputChange(event) {
    let value = event.target.value;
    let name = event.target.name;
    const fields = this.state.fields;
    fields[name] = value;
    this.setState({ fields });

    let hiddenForm = document.querySelector(".change");

    if (value === "new" && name === "org") {
      hiddenForm.classList.remove("hidden");
    } else {
      hiddenForm.classList.add("hidden");
    }
  }
  onFormSubmit(event) {
    //TODO
    event.preventDefault();
    console.log(`Email: ${this.refs.email.value}`);
    console.log(`Pass: ${this.refs.password.value}`);
    console.log(`Pass2: ${this.refs.confirmpassword.value}`);
    console.log(`Org: ${this.refs.org.value}`);
    console.log(`New Org: ${this.refs.neworg.value}`);
  }

  state = {
    fields: {
      email: "",
      password: "",
      confirmpassword: "",
      org: "",
      neworg: "",
    }
  }

  render() {
    return (
      <div className="lightbox register">
        <h2>Register</h2>
        <form className="center" onSubmit={this.onFormSubmit.bind(this)}>
          <label htmlFor="email">Email:</label>
          <input
            name="email"
            type="text"
            value={this.state.fields.email}
            onChange={this.onInputChange.bind(this)}>
          </input>
          <label htmlFor="password">Password:</label>
          <input
            name="password"
            value={this.state.fields.password}
            onChange={this.onInputChange.bind(this)}
            type="password">
          </input>
          <label htmlFor="confirmpassword">Password Again:</label>
          <input
            name="confirmpassword"
            value={this.state.fields.confirmpassword}
            onChange={this.onInputChange.bind(this)}
            type="password">
          </input>
          <label htmlFor="org">Organization:</label>
          <select
            name="org"
            onChange={this.onInputChange.bind(this)}
            value={this.state.fields.org}>
            <option>NordVisa</option>
            <option value="new">New Organization</option>
            <option defaultValue>No Organization</option>
          </select>
          <div className="change hidden">
            <label htmlFor="neworg">New Organization:</label>
            <input
              name="neworg"
              value={this.state.fields.neworg}
              onChange={this.onInputChange.bind(this)}
              type="text">
            </input>
          </div>
          <div className="g-recaptcha" data-sitekey="6Le13yAUAAAAAC4D1Ml81bW3WlGN83bZo4FzHU7Z"></div>
          <input type="submit" value="register" className="btn-primary"/>
        </form>
      </div>
    );
  }
}

export default RegisterView;
