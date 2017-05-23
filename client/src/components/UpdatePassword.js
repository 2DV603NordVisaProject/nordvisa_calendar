import React, { Component } from "react";
import ErrorList from "./ErrorList";
import PropTypes from "prop-types";
import Client from "../Client";

class UpdatePassword extends Component {
  state = {
    fields: {
      oldpassword: "",
      newpassword: "",
      confirmpassword: "",
    },
    fieldErrors: [],
  }

  componentWillMount() {
    const uri = "/api/user/current";
    Client.get(uri)
      .then(user => {
      const fields = {
        id: user.id,
        oldpassword: "",
        newpassword: "",
        confirmpassword: "",
      }
      this.setState({fields});
      })
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

    const uri = "/api/user/change_password";
    const user = {
      id: this.state.fields.id,
      oldPassword: this.state.fields.oldpassword,
      password: this.state.fields.password,
      passwordConfirmation: this.state.fields.confirmpassword,
    };

    Client.post(user, uri)

    fieldErrors.push("Password updated!");
    this.setState({ fieldErrors })

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

    const language = this.context.language.MyAccountView;

    return (
      <div className="box">
        <h3 className="capitalize">{language.updatePassword}</h3>
        <form onSubmit={this.onFormSubmit.bind(this)}>
          <label htmlFor="oldpassword" className="capitalize">{language.oldPassword}:</label>
          <input
            type="password"
            name="oldpassword"
            value={this.state.fields.oldpassword}
            onChange={this.onInputChange.bind(this)}>
          </input>
          <label htmlFor="newpassword" className="capitalize">{language.newPassword}:</label>
          <input
            type="password"
            name="newpassword"
            value={this.state.fields.newpassword}
            onChange={this.onInputChange.bind(this)}>
          </input>
          <label htmlFor="confirmpassword" className="capitalize">{language.confirmPassword}:</label>
          <input
            type="password"
            name="confirmpassword"
            value={this.state.fields.confirmpassword}
            onChange={this.onInputChange.bind(this)}>
          </input>
          <ErrorList errors={this.state.fieldErrors}/>
          <input type="submit" value={language.save} className="btn-primary"></input>
        </form>
      </div>
    );
  }
}
UpdatePassword.contextTypes = {
  language: PropTypes.object,
}


export default UpdatePassword;
