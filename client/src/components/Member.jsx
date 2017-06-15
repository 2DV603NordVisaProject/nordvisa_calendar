import React from 'react';
import PropTypes from 'prop-types';


const Member = (props) => {
  const language = this.context.language.MembersView;

  return (
    <li>
      <div className="member-item">
        <div className="email-cell">
          <p>{props.member.email}</p>
        </div>
        <div className="access-select">
          <select className="select-full capitalize" onChange={props.onChange} name={props.member.id} defaultValue={props.member.role}>
            <option value="USER" className="capitalize" disabled={props.member.role === 'SUPER_ADMIN'}>{language.user}</option>
            <option value="ADMIN" className="capitalize" disabled={props.member.role === 'SUPER_ADMIN'}>{language.admin}</option>
            <option value="SUPER_ADMIN" className="capitalize">{language.superadmin}</option>
            <option value="DELETE" className="capitalize" disabled={props.member.role === 'SUPER_ADMIN'}>{language.delete}</option>
          </select>
        </div>
      </div>
    </li>
  );
};


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
