import React, { Component } from "react";
import { isEmail } from "validator";
import ErrorList from "./ErrorList";

class UpdateAccount extends Component {
  state = {
    fields: {
      email: "",
      org: "",
      neworg: "",
    },
    fieldErrors: [],
  }

  validate(fields) {
    const errors = [];
    if (!fields.email) errors.push("Email Required!");
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
    event.preventDefault();
    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on Errors
    if (fieldErrors.length) return;

    this.setState({fields: {
      email: "",
      org: "",
      neworg: "",
    }})
  }

  render() {
    return (
      <div className="box">
        <h3>Update Account Details</h3>
        <form onSubmit={this.onFormSubmit.bind(this)}>
          <label htmlFor="email">Email:</label>
          <input type="text" name="email" value={this.state.fields.email} onChange={this.onInputChange.bind(this)}></input>
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
          <ErrorList errors={this.state.fieldErrors}/>
          <input type="submit" value="save" className="btn-primary"></input>
        </form>
      </div>
    );
  }
}

export default UpdateAccount;
