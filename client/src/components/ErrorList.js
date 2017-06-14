import React from 'react';
import './ErrorList.css';

const ErrorList = () => (
  <ul className="error-list">
    {
    this.props.errors.map(error => (
      <li className={error.length > 40 ? 'long-error error capitalize' : 'error capitalize'} key={null}>{error}</li>
    ))
  }
  </ul>
);


export default ErrorList;
