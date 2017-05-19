import React, { Component } from "react";
import PropTypes from "prop-types";

class Member extends Component {
  render() {

    const language = this.context.language.MembersView;

    return (
      <li>
        <div className="member-item">
          <div className="email-cell">
            <p>{this.props.member.email}</p>
          </div>
          <div className="access-select">
            <select className="select-full capitalize" onChange={this.props.onChange} name={this.props.member.email} defaultValue={this.props.member.userLevel}>
              <option value="user" className="capitalize" disabled={this.props.member.userLevel === "superadmin" ? true : false}>{language.user}</option>
              <option value="admin" className="capitalize" disabled={this.props.member.userLevel === "superadmin" ? true : false}>{language.admin}</option>
              <option value="superadmin" className="capitalize">{language.superadmin}</option>
            </select>
          </div>
        </div>
      </li>
    );
  }
}

Member.contextTypes = {
  language: PropTypes.object,
}

export default Member;
