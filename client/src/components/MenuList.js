import React, { Component } from "react";
import Client from "../Client";
import Link from "react-router/Link";

class MenuList extends Component {
  render() {
    return (
      <ul>
        {
          Client.getUserLevel() > 1 ? (
            <div>
              <li><Link to="/admin/members" className="menu-link">Members</Link></li>
              <li><Link to="/admin/pending-registrations" className="menu-link">Pending Registrations</Link></li>
            </div>
          ) : (
            <div></div>
          )
        }
        {
          Client.isLogedIn() ? (
            <div>
              <li><Link to="/user/account" className="menu-link">My Account</Link></li>
              <li><Link to="/user/event/create" className="menu-link">Create Event</Link></li>
              <li><Link to="/user/event" className="menu-link">My Events</Link></li>
              <li><Link to="/logout" className="menu-link">Logout</Link></li>
            </div>
          ) : (
            <div>
              <li><Link to="/login" className="menu-link">Login</Link></li>
              <li><Link to="/register" className="menu-link">Register</Link></li>
              <li><Link to="/recover-password" className="menu-link">Forgot Password</Link></li>
            </div>
          )
        }
        <li><Link to="/generate-widget" className="menu-link">Generate Widget</Link></li>
      </ul>
    )
  }
}

export default MenuList;
