import React, { Component } from 'react';
import "./TopBar.css";
import ResponsiveMenu from "./ResponsiveMenu";


class TopBar extends Component {
  render() {
    return (
      <div className="topbar">
        <div className="brand">
          <p>Event Dashboard</p>
        </div>
        <ResponsiveMenu/>
        <select>
          <option selected disabled>Language</option>
          <option>Swedish</option>
          <option>English</option>
          <option>Norwegian</option>
          <option>Danish</option>
          <option>Icelandic</option>
        </select>
      </div>
    );
  }
}

export default TopBar;
