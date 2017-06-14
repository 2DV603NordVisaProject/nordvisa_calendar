import React from 'react';
import './ErrorList.css';

const ErrorList = () => (
  <ul className="error-list">
    {
    this.props.errors.map((error, i) => (
      <li className={error.length > 40 ? 'long-error error capitalize' : 'error capitalize'} key={i}>{error}</li>
    ))
  }
  </ul>
);


export default ErrorList;
