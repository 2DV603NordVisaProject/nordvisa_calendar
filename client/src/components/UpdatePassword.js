import React, { Component } from "react";
import ErrorList from "./ErrorList";

class UpdatePassword extends Component {
  state = {
    fields: {
      oldpassword: "",
      newpassword: "",
      confirmpassword: "",
    },
    fieldErrors: [],
  }

  validate(fields) {
    const errors = [];
    if (!fields.newpassword || !fields.confirmpassword || !fields.oldpassword) errors.push("Password Required!");
    if (fields.newpassword !== fields.confirmpassword) errors.push("Password's doesn't match!");
    if (fields.newpassword.length < 10) errors.push("Pasword needs to be atleast 10 characters long!");
    if (fields.newpassword.length > 255) errors.push("Password can't be longer than 255 characters");
    return errors;
  }

  onFormSubmit(event) {
    event.preventDefault();
    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on Errors
    if (fieldErrors.length) return;

    this.setState({fields: {
      oldpassword: "",
      newpassword: "",
      confirmpassword: "",
    }});
  }

  onInputChange(event) {
    const value = event.target.value;
    const name = event.target.name;
    const fields = this.state.fields;
    fields[name] = value;
    this.setState({ fields });
  }

  render() {
    return (
      <div className="box">
        <h3>Update Account Password</h3>
        <form onSubmit={this.onFormSubmit.bind(this)}>
          <label htmlFor="oldpassword">Old Password:</label>
          <input
            type="password"
            name="oldpassword"
            value={this.state.fields.oldpassword}
            onChange={this.onInputChange.bind(this)}>
          </input>
          <label htmlFor="newpassword">New Password:</label>
          <input
            type="password"
            name="newpassword"
            value={this.state.fields.newpassword}
            onChange={this.onInputChange.bind(this)}>
          </input>
          <label htmlFor="confirmpassword">New Password Again:</label>
          <input
            type="password"
            name="confirmpassword"
            value={this.state.fields.confirmpassword}
            onChange={this.onInputChange.bind(this)}>
          </input>
          <ErrorList errors={this.state.fieldErrors}/>
          <input type="submit" value="save" className="btn-primary"></input>
        </form>
      </div>
    );
  }
}

export default UpdatePassword;
