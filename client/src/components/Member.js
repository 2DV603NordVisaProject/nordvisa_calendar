import React, { Component } from "react";

class Member extends Component {
  render() {
    return (
      <li>
        <div className="member-item">
          <div className="email-cell">
            <p>{this.props.member.email}</p>
          </div>
          <div className="access-select">
            <select className="select-full" onChange={this.props.onChange} name={this.props.member.email} defaultValue={this.props.member.userLevel}>
              <option value="user" disabled={this.props.member.userLevel === "superadmin" ? true : false}>User</option>
              <option value="admin" disabled={this.props.member.userLevel === "superadmin" ? true : false}>Admin</option>
              <option value="superadmin">Superadmin</option>
            </select>
          </div>
        </div>
      </li>
    );
  }
}

export default Member;
