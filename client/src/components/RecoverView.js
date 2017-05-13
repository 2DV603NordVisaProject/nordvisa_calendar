import React, { Component } from "react";
import { isEmail } from "validator";
import ErrorList from "./ErrorList";

class RecoverView extends Component {
  state = {
    fields: {
      email: "",
    },
    fieldErrors: [],
  }

  validate(fields) {
    const errors = [];
    if (!isEmail(fields.email)) errors.push("Invalid Email");
    if (!fields.email) errors.push("You must enter a email!");
    return errors;
  }

  onFormSubmit(event) {
    event.preventDefault();
    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on error.
    if (fieldErrors.length) return;

    this.setState({ fields: {
      email: "",
    }})
  }

  onInputChange(event) {
    const fields = this.state.fields;
    fields[event.target.name] = event.target.value;
    this.setState({ fields });
  }
  render() {
    return (
      <div className="lightbox login">
        <h2>Recover Password</h2>
        <form onSubmit={this.onFormSubmit.bind(this)}>
          <label htmlFor="email">Email:</label>
          <input
            name="email"
            type="text"
            value={this.state.fields.email}
            onChange={this.onInputChange.bind(this)}>
          </input>
          <ErrorList errors={this.state.fieldErrors}/>
          <input type="submit" value="request password" className="btn-primary"></input>
        </form>
      </div>
    );
  }
}

export default RecoverView;
