import React from 'react';
import PropTypes from 'prop-types';
import Registration from './Registration';

const RegistrationsList = props => (
  <ul>
    {
      props.registrations.map((registration, i) => (
        <Registration key={i} registration={registration} onInputChange={props.onInputChange} />
      ))
    }
  </ul>
);

RegistrationsList.propTypes = {
  registrations: PropTypes.arrayOf(
    PropTypes.object,
  ).isRequired,
  onInputChange: PropTypes.func.isRequired,
};

export default RegistrationsList;
