import React, { Component } from "react";

class UpdateAccount extends Component {
  render() {
    return (
      <div className="box">
        <h3>Update Account Details</h3>
        <form>
          <label htmlFor="email">Email:</label>
          <input type="text" name="email"></input>
          <label htmlFor="org">Organization:</label>
          <input type="text" name="org"></input>
          <input type="submit" value="save" className="btn-primary"></input>
        </form>
      </div>
    );
  }
}

export default UpdateAccount;
