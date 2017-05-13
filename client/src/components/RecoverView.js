import React, { Component } from "react";

class RecoverView extends Component {
  render() {
    return (
      <div className="lightbox login">
        <h2>Recover Password</h2>
        <form>
          <label htmlFor="email">Email:</label>
          <input name="email" type="text"></input>
          <input type="submit" value="request password" className="btn-primary"></input>
        </form>
      </div>
    );
  }
}

export default RecoverView;
