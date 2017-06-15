import React, { Component } from 'react';
import Redirect from 'react-router/Redirect';
import Recaptcha from 'react-gcaptcha';
import PropTypes from 'prop-types';
import { isEmail } from 'validator';
import './RegisterView.css';
import ErrorList from './ErrorList';
import Client from '../Client';
import Loader from './Loader';

class RegisterView extends Component {
  constructor() {
    super();

    this.onInputChange = this.onInputChange.bind(this);
    this.onFormSubmit = this.onFormSubmit.bind(this);
  }
  state = {
    fields: {
      email: '',
      password: '',
      confirmpassword: '',
      org: '',
      neworg: '',
      recaptcha: '',
    },
    organizations: [],
    newOrg: 'hidden',
    fieldErrors: [],
    loading: false,
    redirect: false,
  }

  componentWillMount() {
    const uri = '/api/visitor/organizations';
    Client.get(uri)
      .then((organizations) => {
        if (organizations.length > 0) {
          this.setState({ organizations });
        }
      });
  }


  onInputChange(event) {
    const value = event.target.value;
    const name = event.target.name;
    const fields = this.state.fields;
    fields[name] = value;
    this.setState({ fields });

    if (this.state.fields.org === 'new') {
      const newOrg = '';
      this.setState({ newOrg });
    } else {
      const newOrg = 'hidden';
      this.setState({ newOrg });
    }
  }

  onFormSubmit(event) {
    event.preventDefault();
    this.setState({ fieldErrors: [] });
    let fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });


    // Return on Errors
    if (fieldErrors.length) return;

    // Do registration
    this.setState({ loading: true });
    const uri = '/api/visitor/registration';
    const user = {
      email: this.state.fields.email,
      password: this.state.fields.password,
      passwordConfirmation: this.state.fields.confirmpassword,
      organization: this.state.fields.neworg || this.state.fields.org,
      gRecaptchaResponse: this.state.fields.recaptcha,
    };

    Client.post(user, uri).then((res) => {
      this.setState({ loading: false });
      if (res.hasOwnProperty('message')) {
        fieldErrors = [];
        fieldErrors.push(res.message);
        this.setState({ fieldErrors });
        this.forceUpdate();
      } else {
        this.setState({ redirect: true });
        this.setState({ fields: {
          email: '',
          password: '',
          confirmpassword: '',
          org: '',
          neworg: '',
          recaptcha: '',
        } });
      }
    });
  }

  callback = function (key) {
    const fields = this.state.fields;
    fields.recaptcha = key;
    this.setState(fields);
  };

  validate(fields) {
    const errors = [];
    if (!fields.email) errors.push(this.context.language.Errors.emailRequired);
    if (!fields.password || !fields.confirmpassword) errors.push(this.context.language.Errors.passwordRequired);
    if (fields.password !== fields.confirmpassword) errors.push(this.context.language.Errors.passwordDoesNotMatch);
    if (!fields.recaptcha) errors.push(this.context.language.Errors.captchaFails);
    if (fields.password.length < 10) errors.push(this.context.language.Errors.shortPassword);
    if (fields.password.length > 255) errors.push(this.context.language.Errors.longPassword);
    if (!isEmail(fields.email)) errors.push(this.context.language.Errors.invalidEmail);
    return errors;
  }

  render() {
    const language = this.context.language.RegisterView;

    if (this.state.loading) {
      return (
        <Loader />
      );
    } else if (this.state.redirect) {
      return (
        <Redirect to={{
          pathname: '/login',
          state: { referrer: '/register' },
        }}
        />
      );
    }
    return (
      <div className="lightbox register">
        <h2 className="uppercase">{language.register}</h2>
        <form className="center" onSubmit={this.onFormSubmit}>
          <label htmlFor="email" className="capitalize">{language.email}:</label>
          <input
            name="email"
            type="text"
            value={this.state.fields.email}
            onChange={this.onInputChange}
          />
          <label htmlFor="password" className="capitalize">{language.password}:</label>
          <input
            name="password"
            value={this.state.fields.password}
            onChange={this.onInputChange}
            type="password"
          />
          <label htmlFor="confirmpassword" className="capitalize">{language.confirmPassword}:</label>
          <input
            name="confirmpassword"
            value={this.state.fields.confirmpassword}
            onChange={this.onInputChange}
            type="password"
          />
          <label htmlFor="org" className="capitalize">{language.organization}:</label>
          <select
            name="org"
            onChange={this.onInputChange}
            value={this.state.fields.org}
            defaultValue=""
          >
            {
                this.state.organizations.map(org => <option className="capitalize" value={org}>{org}</option>)
              }
            <option value="new">New Organization</option>
            <option value="">No Organization</option>
          </select>
          <div id="on-select-change" className={this.state.newOrg}>
            <label htmlFor="neworg" className="capitalize">{language.newOrganization}:</label>
            <input
              name="neworg"
              value={this.state.fields.neworg}
              onChange={this.onInputChange}
              type="text"
            />
          </div>
          <Recaptcha
            sitekey="6Le13yAUAAAAAC4D1Ml81bW3WlGN83bZo4FzHU7Z"
            verifyCallback={this.callback}
          />
          <ErrorList errors={this.state.fieldErrors} />
          <input
            type="submit"
            value={language.registerBtn}
            className="btn-primary"
          />
        </form>
      </div>
    );
  }
}

RegisterView.contextTypes = {
  language: PropTypes.object,
};

export default RegisterView;
