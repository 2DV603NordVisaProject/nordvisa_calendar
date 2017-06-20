import React from 'react';
import PropTypes from 'prop-types';
import ErrorList from './ErrorList';
import Button from './Button';

const LoginForm = (props, context) => {
  const language = context.language.LoginView;
  return (
    <form onSubmit={props.onFormSubmit}>
      <label htmlFor="email" style={{ textTransform: 'capitalize' }}>{language.email}:</label>
      <input
        name="email"
        value={props.fields.email}
        onChange={props.onInputChange}
        type="text"
      />
      <label htmlFor="password" style={{ textTransform: 'capitalize' }}>{language.password}:</label>
      <input
        name="password"
        onChange={props.onInputChange}
        value={props.fields.password}
        type="password"
      />
      <ErrorList errors={props.fieldErrors} />
      <Button form>{language.login}</Button>
    </form>
  );
};

LoginForm.contextTypes = {
  language: PropTypes.object,
};

LoginForm.propTypes = {
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

export default LoginForm;
