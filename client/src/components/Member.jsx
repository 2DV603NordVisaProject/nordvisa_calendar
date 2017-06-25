import React from 'react';
import PropTypes from 'prop-types';


const Member = ({ member, onChange }, context) => (
  <li>
    <div className="member-item">
      <div className="email-cell">
        <p>{member.email}</p>
      </div>
      <div className="access-select">
        <select className="select-full" style={{ textTransform: 'capitalize' }} onChange={onChange} name={member.id} defaultValue={member.role}>
          <option value="USER" className="capitalize" disabled={member.role === 'SUPER_ADMIN'}>{context.language.MembersView.user}</option>
          <option value="ADMIN" className="capitalize" disabled={member.role === 'SUPER_ADMIN'}>{context.language.MembersView.admin}</option>
          <option value="SUPER_ADMIN" className="capitalize">{context.language.MembersView.superadmin}</option>
          <option value="DELETE" className="capitalize" disabled={member.role === 'SUPER_ADMIN'}>{context.language.MembersView.delete}</option>
        </select>
      </div>
    </div>
  </li>
);


Member.contextTypes = {
  language: PropTypes.object,
};

Member.propTypes = {
  onChange: PropTypes.func.isRequired,
  member: PropTypes.shape({
    id: PropTypes.string,
    email: PropTypes.string,
    role: PropTypes.string,
  }).isRequired,
};

export default Member;
