import React, { Component } from "react";

class UpdatePasswordView extends Component {
  render() {
    return (
      <div className="lightbox login">
        <h2>Update Password</h2>
        <form>
          <label htmlFor="password">New Password:</label>
          <input name="password" type="password"></input>
          <label htmlFor="confirm-password">New Password Again:</label>
          <input name="confirm-password" type="password"></input>
          <button>save</button>
        </form>
      </div>
    );
  }
}

export default UpdatePasswordView;
