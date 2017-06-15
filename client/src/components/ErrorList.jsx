import React from 'react';
import PropTypes from 'prop-types';
import './ErrorList.css';

const ErrorList = props => (
  <ul className="error-list">
    {
    props.errors.map(error => (
      <li className={error.length > 40 ? 'long-error error capitalize' : 'error capitalize'} key={null}>{error}</li>
    ))
  }
  </ul>
);

ErrorList.propTypes = {
  errors: PropTypes.arrayOf(
    PropTypes.string,
  ).isRequired,
};


export default ErrorList;
