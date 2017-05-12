import React, { Component } from "react";
import "./ResponsiveMenu.css";


class ResponsiveMenu extends Component {

  onCollapseMenuClick(event) {
    event.preventDefault();
    document.querySelector(".menu-btn").classList.toggle("change");
    document.querySelector(".responsive-menu").classList.toggle("expand");
  }

  render() {
    return (
      <div>
        <div className="menu-btn" onClick={this.onCollapseMenuClick}>
	        <div className="bar1"></div>
	        <div className="bar2"></div>
	        <div className="bar3"></div>
        </div>
        <div className="responsive-menu">
          <ul>
            <li>Login/Logout</li>
            <li>Register</li>
            <li>Forgot Password</li>
            <li>My Account</li>
            <li>Members</li>
            <li>Create Event</li>
            <li>My Events</li>
            <li>Generate Widget</li>
            <li>Pending Registrations</li>
          </ul>
        </div>
      </div>
    );
  }
}

export default ResponsiveMenu;
