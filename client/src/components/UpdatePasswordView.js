import React, { Component } from "react";
import PropTypes from "prop-types";

class UpdatePasswordView extends Component {
  render() {

    const language = this.context.language.UpdatePasswordView;

    return (
      <div className="lightbox login">
        <h2 className="uppercase">{language.updatePassword}</h2>
        <form>
          <label htmlFor="password" className="capitalize">{language.newPassword}:</label>
          <input name="password" type="password"></input>
          <label htmlFor="confirm-password" className="capitalize">{language.confirmPassword}:</label>
          <input name="confirm-password" type="password"></input>
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
