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
            <select className="select-full capitalize" onChange={this.props.onChange.bind(this)} name={this.props.member.id} defaultValue={this.props.member.role}>
              <option value="USER" className="capitalize" disabled={this.props.member.role === "SUPER_ADMIN" ? true : false}>{language.user}</option>
              <option value="ADMIN" className="capitalize" disabled={this.props.member.role === "SUPER_ADMIN" ? true : false}>{language.admin}</option>
              <option value="SUPER_ADMIN" className="capitalize">{language.superadmin}</option>
              <option value="DETLETE" className="capitalize" disabled={this.props.member.role === "SUPER_ADMIN" ? true : false}>{language.delete}</option>
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
