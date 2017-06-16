import React from 'react';
import PropTypes from 'prop-types';
import Member from './Member';

const MembersList = (props, context) => (
  <div className="members-list">
    <div className="list-header">
      <p className="capitalize">{context.language.MembersView.email}</p>
      <p className="capitalize">{context.language.MembersView.access}</p>
    </div>
    <ul>
      {
        props.members.map(member => (
          <Member key={member.id} member={member} onChange={props.onChange} />
        ))
      }
    </ul>
  </div>
);

MembersList.contextTypes = {
  language: PropTypes.object,
};

MembersList.propTypes = {
  onChange: PropTypes.func.isRequired,
  members: PropTypes.arrayOf(
    PropTypes.object,
  ).isRequired,
};

export default MembersList;
