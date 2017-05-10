import React, { Component } from 'react';
import "./TopBar.css";
import ResponsiveMenu from "./ResponsiveMenu";


class TopBar extends Component {
  render() {
    return (
      <div className="topbar">
        <div className="brand">
          <p>Widget Dashboard</p>
        </div>
        <ResponsiveMenu/>
      </div>
    );
  }
}

export default TopBar;
