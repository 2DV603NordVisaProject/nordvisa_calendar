import React, { Component } from 'react';
import Member from './Member';
import PropTypes from 'prop-types';

class MembersList extends Component {
  render() {
    const language = this.context.language.MembersView;

    return (
      <div className="members-list">
        <div className="list-header">
          <p className="capitalize">{language.email}</p>
          <p className="capitalize">{language.access}</p>
        </div>
        <ul>
          {
            this.props.members.map((member, i) => (
              <Member key={i} member={member} onChange={this.props.onChange} />
            ))
          }
        </ul>
      </div>
    );
  }
}


MembersList.contextTypes = {
  language: PropTypes.object,
};

export default MembersList;
