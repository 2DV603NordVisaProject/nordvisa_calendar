import React from 'react';
import PropTypes from 'prop-types';
import Registration from './Registration';

const propTypes = {
  registrations: PropTypes.arrayOf(
    PropTypes.object,
  ).isRequired,
  onInputChange: PropTypes.func.isRequired,
};

const contextTypes = {
  language: PropTypes.object,
};

const RegistrationsList = ({ registrations, onInputChange }, context) => {
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
      registrations.map(registration => (
        <Registration
          key={registration.id}
          registration={registration}
          onInputChange={onInputChange}
        />
      ))
    }
      </ul>
    </div>
  );
};

RegistrationsList.propTypes = propTypes;
RegistrationsList.contextTypes = contextTypes;

export default RegistrationsList;
