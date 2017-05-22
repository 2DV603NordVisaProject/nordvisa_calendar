import React, { Component } from "react";
import PropTypes from "prop-types";

class UpdatePasswordView extends Component {
  render() {

    state = {
      fields: {
        urlId: this.props.id,
        password: "",
        passwordConfirmation: "",
      }
    }

    onInputChange(event) {
      const fields = this.state.fields;
      fields[event.target.name] = event.target.value;
      this.setState({ fields });
    }

    onFormSubmit(event) {
      event.preventDefault();

      const uri = "/api/visitor/recover_password";
      Client.post(this.state.fields, uri)
        .then(res =>{
          this.setState({ fields: {
            urlId: "",
            passwrod: "",
            passwordConfirmation: ""
          }})
        })
    }

    const language = this.context.language.UpdatePasswordView;

    return (
      <div className="lightbox login">
        <h2 className="uppercase">{language.updatePassword}</h2>
        <form onSubmit={this.onFormSubmit.bind(this)}>
          <label htmlFor="password" className="capitalize">{language.newPassword}:</label>
          <input name="password" type="password" onChange={this.onInputChange.bing(this)}></input>
          <label htmlFor="confirm-password" className="capitalize">{language.confirmPassword}:</label>
          <input name="confirm-password" type="password" onChange={this.onInputChange.bing(this)}></input>
          <input type="submit" className="btn-primary" value={language.save}></input>
        </form>
      </div>
    );
  }
}

UpdatePasswordView.contextTypes = {
  language: PropTypes.object,
}

export default UpdatePasswordView;
