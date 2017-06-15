import React from 'react';
import PropTypes from 'prop-types';

const Registration = (props, context) => {
  const language = context.language.PendingRegistrationsView;

  return (
    <li>
      <div className="pending-item">
        <div className="email">
          <p>{props.registration.email}</p>
        </div>
        <div className="org">
          <p>{props.registration.organization.name || props.registration.organization.changePending}</p>
        </div>
        <div className="approve-action">
          <select onChange={props.onInputChange} className="capitalize small-form" name={props.registration.id}>
            <option className="capitalize">{language.action}</option>
            <option className="capitalize small-form" value="approve">{language.approve}</option>
            <option className="capitalize small-form" value="deny">{language.deny}</option>
          </select>
        </div>
      </div>
    </li>
  );
};

Registration.contextTypes = {
  language: PropTypes.object,
};

export default Registration;
