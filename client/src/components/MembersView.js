import React, { Component } from "react";
import "./MembersView.css";

class MembersView extends Component {

  render() {
    return (
      <div className="members view">
        <h2>Members</h2>
        <form>
          <div className="members-list">
            <div className="list-header">
              <p>Email</p>
              <p>Access Level</p>
            </div>
            <ul>
              <li>
                <div className="member-item">
                  <div className="email-cell">
                    <p>sample@gmail.com</p>
                  </div>
                  <div className="access-select">
                    <select className="select-full">
                      <option selected>User</option>
                      <option>Admin</option>
                      <option>Superadmin</option>
                    </select>
                  </div>
                </div>
              </li>
              <li>
                <div className="member-item">
                  <div className="email-cell">
                    <p>sample@gmail.com</p>
                  </div>
                  <div className="access-select">
                    <select className="select-full">
                      <option selected>User</option>
                      <option>Admin</option>
                      <option>Superadmin</option>
                    </select>
                  </div>
                </div>
              </li>
              <li>
                <div className="member-item">
                  <div className="email-cell">
                    <p>sample@gmail.com</p>
                  </div>
                  <div className="access-select">
                    <select className="select-full">
                      <option selected>User</option>
                      <option>Admin</option>
                      <option>Superadmin</option>
                    </select>
                  </div>
                </div>
              </li>
            </ul>
          </div>
          <input type="submit" value="save" className="btn-primary"></input>
        </form>
      </div>
    );
  }
}

export default MembersView;
