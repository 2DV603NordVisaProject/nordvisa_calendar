import React from 'react';
import PropTypes from 'prop-types';
import Member from './Member';

const MembersList = (props, context) => {
  const language = context.language.MembersView;

  return (
    <div className="members-list">
      <div className="list-header">
        <p className="capitalize">{language.email}</p>
        <p className="capitalize">{language.access}</p>
      </div>
      <ul>
        {
          props.members.map(member => (
            <Member key={null} member={member} onChange={props.onChange} />
          ))
        }
      </ul>
    </div>
  );
};


MembersList.contextTypes = {
  language: PropTypes.object,
};

export default MembersList;
