import React, { Component } from 'react';
import "./TopBar.css";
import ResponsiveMenu from "./ResponsiveMenu";
import Link from "react-router/Link";


class TopBar extends Component {
  render() {
    return (
      <div className="topbar">
        <div className="brand">
          <Link to="/user/event">Event Dashboard</Link>
        </div>
        <ResponsiveMenu/>
        <select defaultValue="english">
          <option>Swedish</option>
          <option value="english">English</option>
          <option>Norwegian</option>
          <option>Danish</option>
          <option>Icelandic</option>
        </select>
      </div>
    );
  }
}

export default TopBar;
