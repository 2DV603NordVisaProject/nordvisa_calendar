import React from 'react';
import PropTypes from 'prop-types';
import './ErrorList.css';

const propTypes = {
  errors: PropTypes.arrayOf(
    PropTypes.string,
  ).isRequired,
};

const ErrorList = ({ errors }) => (
  <ul className="error-list">
    {
    errors.map((error, i) => (
      <li style={{ textTransform: 'capitalize' }} className={error.length > 40 ? 'long-error error-text' : 'error-text'} key={i}>{error}</li>
    ))
  }
  </ul>
);

ErrorList.propTypes = propTypes;
export default ErrorList;
