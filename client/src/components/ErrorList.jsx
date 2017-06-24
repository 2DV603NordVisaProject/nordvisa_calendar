import React from 'react';
import PropTypes from 'prop-types';
import './ErrorList.css';

const ErrorList = ({ errors }) => (
  <ul className="error-list">
    {
    errors.map(error => (
      <li style={{ textTransform: 'capitalize' }} className={error.length > 40 ? 'long-error error-text' : 'error-text'} key={null}>{error}</li>
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
