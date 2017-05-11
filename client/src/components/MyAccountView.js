import React, { Component } from "react";
import "./MyAccountView.css";

class MyAccountView extends Component {
  render() {
    return (
      <div className="my-account">
        <h2>My Account</h2>
        <div className="box">
          <h3>Update Account Details</h3>
          <form>
            <label htmlFor="email">Email:</label>
            <input type="text" name="email"></input>
            <label htmlFor="org">Organization:</label>
            <input type="text" name="org"></input>
            <button>SAVE</button>
          </form>
        </div>
        <div className="box">
          <h3>Update Account Password</h3>
          <form>
            <label htmlFor="oldpassword">Old Password:</label>
            <input type="password" name="oldpassword"></input>
            <label htmlFor="newpassword">New Password:</label>
            <input type="password" name="newpassword"></input>
            <label htmlFor="confirmpassword">New Password Again:</label>
            <input type="password" name="confirmpassword"></input>
            <button>SAVE</button>
          </form>
        </div>
      </div>
    );
  }
}

export default MyAccountView;
