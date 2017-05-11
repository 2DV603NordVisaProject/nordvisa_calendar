import React, { Component } from "react";
import "./RecoverView.css";

class RecoverView extends Component {
  render() {
    return (
      <div className="recover">
        <h2>Recover Password</h2>
        <form>
          <label htmlFor="email">Email:</label>
          <input name="email" type="text"></input>
          <button>REQUEST PASSWORD</button>
        </form>
      </div>
    );
  }
}

export default RecoverView;
