import React, { Component } from "react";

class UpdatePassword extends Component {
  render() {
    return (
      <div className="box">
        <h3>Update Account Password</h3>
        <form>
          <label htmlFor="oldpassword">Old Password:</label>
          <input type="password" name="oldpassword"></input>
          <label htmlFor="newpassword">New Password:</label>
          <input type="password" name="newpassword"></input>
          <label htmlFor="confirmpassword">New Password Again:</label>
          <input type="password" name="confirmpassword"></input>
          <input type="submit" value="save" className="btn-primary"></input>
        </form>
      </div>
    );
  }
}

export default UpdatePassword;
