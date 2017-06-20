import React from 'react';
import PropTypes from 'prop-types';
import Registration from './Registration';

const RegistrationsList = (props, context) => {
  const language = context.language.PendingRegistrationsView;

  return (
    <div>
      <div className="list-header" style={{ textTransform: 'capitalize' }}>
        <p>{language.email}</p>
        <p>{language.organization}</p>
        <p>{language.approve}</p>
      </div>
      <ul>
        {
      props.registrations.map(registration => (
        <Registration
          key={registration.id}
          registration={registration}
          onInputChange={props.onInputChange}
        />
      ))
    }
      </ul>
    </div>
  );
};

RegistrationsList.contextTypes = {
  language: PropTypes.object,
};

RegistrationsList.propTypes = {
  registrations: PropTypes.arrayOf(
    PropTypes.object,
  ).isRequired,
  onInputChange: PropTypes.func.isRequired,
};

export default RegistrationsList;
