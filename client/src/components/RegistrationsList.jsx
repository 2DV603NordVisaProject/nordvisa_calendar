import React from 'react';
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


export default RegistrationsList;
