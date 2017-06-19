import React from 'react';
import PropTypes from 'prop-types';

const Registration = (props, context) => (
  <li>
    <div className="pending-item">
      <div className="email">
        <p>{props.registration.email}</p>
      </div>
      <div className="org">
        <p>
          {
            props.registration.organization.name || props.registration.organization.changePending
          }
        </p>
      </div>
      <div className="approve-action">
        <select onChange={props.onInputChange} className="small-form" style={{ textTransform: 'capitalize' }} name={props.registration.id}>
          <option>{context.language.PendingRegistrationsView.action}</option>
          <option className="small-form" value="approve">{context.language.PendingRegistrationsView.approve}</option>
          <option className="small-form" value="deny">{context.language.PendingRegistrationsView.deny}</option>
        </select>
      </div>
    </div>
  </li>
);

Registration.contextTypes = {
  language: PropTypes.object,
};

Registration.propTypes = {
  registration: PropTypes.shape({
    organization: PropTypes.string,
    email: PropTypes.string,
    id: PropTypes.string,
  }).isRequired,
  onInputChange: PropTypes.func.isRequired,
};

export default Registration;
