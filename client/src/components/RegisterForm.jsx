import React from 'react';
import PropTypes from 'prop-types';
import Recaptcha from 'react-gcaptcha';
import Button from './Button';
import ErrorList from './ErrorList';
import OrganizationSelect from './OrganizationSelect';


const RegisterForm = ({
  onFormSubmit,
  fields,
  fieldErrors,
  onInputChange,
  callback,
  organizations,
  }, context) => {
  const language = context.language.RegisterView;
  return (
    <form className="center" onSubmit={onFormSubmit}>
      <label htmlFor="email" style={{ textTransform: 'capitalize' }}>{language.email}:</label>
      <input
        name="email"
        type="text"
        value={fields.email}
        onChange={onInputChange}
      />
      <label htmlFor="password" style={{ textTransform: 'capitalize' }}>{language.password}:</label>
      <input
        name="password"
        value={fields.password}
        onChange={onInputChange}
        type="password"
      />
      <label htmlFor="confirmpassword" style={{ textTransform: 'capitalize' }}>{language.confirmPassword}:</label>
      <input
        name="confirmpassword"
        value={fields.confirmpassword}
        onChange={onInputChange}
        type="password"
      />
      <OrganizationSelect
        onInputChange={onInputChange}
        fields={fields}
        organizations={organizations}
      />
      <Recaptcha
        sitekey="6Le13yAUAAAAAC4D1Ml81bW3WlGN83bZo4FzHU7Z"
        verifyCallback={callback}
      />
      <ErrorList errors={fieldErrors} />
      <Button form>{language.registerBtn}</Button>
    </form>
  );
};

RegisterForm.contextTypes = {
  language: PropTypes.object,
};

RegisterForm.propTypes = {
  onFormSubmit: PropTypes.func.isRequired,
  fields: PropTypes.shape({
    email: PropTypes.string,
    password: PropTypes.string,
    confirmPassword: PropTypes.string,
    org: PropTypes.string,
    newOrg: PropTypes.string,
  }).isRequired,
  callback: PropTypes.func.isRequired,
  onInputChange: PropTypes.func.isRequired,
  organizations: PropTypes.arrayOf(
    PropTypes.string,
  ).isRequired,
  fieldErrors: PropTypes.arrayOf(
    PropTypes.string,
  ).isRequired,
};

export default RegisterForm;
