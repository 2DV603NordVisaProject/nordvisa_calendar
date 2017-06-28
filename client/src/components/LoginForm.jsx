import React from 'react';
import PropTypes from 'prop-types';
import ErrorList from './ErrorList';
import Button from './Button';

const contextTypes = {
  language: PropTypes.object,
};

const propTypes = {
  onFormSubmit: PropTypes.func.isRequired,
  onInputChange: PropTypes.func.isRequired,
  fields: PropTypes.shape({
    email: PropTypes.string,
    password: PropTypes.string.isRequired,
  }).isRequired,
  fieldErrors: PropTypes.arrayOf(
    PropTypes.string,
  ).isRequired,
};

const LoginForm = ({ onInputChange, fieldErrors, onFormSubmit, fields }, context) => {
  const language = context.language.LoginView;
  return (
    <form onSubmit={onFormSubmit}>
      <label htmlFor="email" style={{ textTransform: 'capitalize' }}>{language.email}:</label>
      <input
        name="email"
        value={fields.email}
        onChange={onInputChange}
        type="text"
      />
      <label htmlFor="password" style={{ textTransform: 'capitalize' }}>{language.password}:</label>
      <input
        name="password"
        onChange={onInputChange}
        value={fields.password}
        type="password"
      />
      <ErrorList errors={fieldErrors} />
      <Button form>{language.login}</Button>
    </form>
  );
};

LoginForm.propTypes = propTypes;
LoginForm.contextTypes = contextTypes;

export default LoginForm;
