import React, { Component } from "react";
import Member from "./Member";

class MembersList extends Component {
  render() {
    return (
      <div className="members-list">
        <div className="list-header">
          <p>Email</p>
          <p>Access Level</p>
        </div>
        <ul>
          {
            this.props.members.map((member, i) => (
              <Member key={i} member={member} onChange={this.props.onChange}/>
            ))
          }
        </ul>
      </div>
    );
  }
}

export default MembersList;
