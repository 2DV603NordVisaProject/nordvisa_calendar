import React from 'react';
import PropTypes from 'prop-types';
import Recaptcha from 'react-gcaptcha';
import Button from './Button';
import ErrorList from './ErrorList';


const RegisterForm = ({ onFormSubmit,
  fields, fieldErrors, onInputChange, callback, organizations }, context) => {
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
      <label htmlFor="org" style={{ textTransform: 'capitalize' }}>{language.organization}:</label>
      <select
        name="org"
        style={{ textTransform: 'capitalize' }}
        onChange={onInputChange}
        value={fields.org}
        defaultValue=""
      >
        {
            organizations.map(org => <option value={org}>{org}</option>)
          }
        <option value="new">New Organization</option>
        <option value="">No Organization</option>
      </select>
      <div id="on-select-change" className={fields.org === 'new' ? '' : 'hidden'}>
        <label htmlFor="neworg" style={{ textTransform: 'capitalize' }}>{language.newOrganization}:</label>
        <input
          name="neworg"
          value={fields.neworg}
          onChange={onInputChange}
          type="text"
        />
      </div>
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
  fields: PropTypes.shape({}).isRequired,
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
