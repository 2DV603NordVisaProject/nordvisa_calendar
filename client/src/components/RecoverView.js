import React, { Component } from "react";

class RecoverView extends Component {
  render() {
    return (
      <div className="lightbox login">
        <h2>Recover Password</h2>
        <form>
          <label htmlFor="email">Email:</label>
          <input name="email" type="text"></input>
          <button>request password</button>
        </form>
      </div>
    );
  }
}

export default RecoverView;
