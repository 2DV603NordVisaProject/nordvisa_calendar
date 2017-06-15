import React, { Component } from 'react';
import Redirect from 'react-router/Redirect';
import { isEmail } from 'validator';
import PropTypes from 'prop-types';
import ErrorList from './ErrorList';
import Client from '../Client';


class RecoverView extends Component {
  constructor() {
    super();

    this.onInputChange = this.onInputChange.bind(this);
    this.onFormSubmit = this.onFormSubmit.bind(this);
  }
  state = {
    fields: {
      email: '',
    },
    fieldErrors: [],
    redirect: false,
  }


  onFormSubmit(event) {
    event.preventDefault();
    let fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on error.
    if (fieldErrors.length) return;

    const uri = '/api/visitor/request_password_recovery';

    Client.post(this.state.fields, uri)
      .then(() => {
        fieldErrors = ['Email sent!'];
        this.setState({ fieldErrors,
          fields: {
            email: '',
          } });
      });


    this.setState({ fields: {}, redirect: true });
  }

  onInputChange(event) {
    const fields = this.state.fields;
    fields[event.target.name] = event.target.value;
    this.setState({ fields });
  }

  validate(fields) {
    const errors = [];
    if (!isEmail(fields.email)) errors.push(this.context.language.Errors.invalidEmail);
    if (!fields.email) errors.push(this.context.language.Errors.invalidEmail);
    return errors;
  }

  render() {
    const language = this.context.language.RecoverView;

    if (this.state.redirect) {
      return (
        <Redirect to={{
          pathname: '/login',
          state: { referrer: '/recover-password' },
        }}
        />
      );
    }
    return (
      <div className="lightbox login">
        <h2 className="uppercase">{language.recover}</h2>
        <form onSubmit={this.onFormSubmit}>
          <label htmlFor="email" className="capitalize">{language.email}:</label>
          <input
            name="email"
            type="text"
            value={this.state.fields.email}
            onChange={this.onInputChange}
          />
          <ErrorList errors={this.state.fieldErrors} />
          <input type="submit" value={language.request} className="btn-primary uppercase" />
        </form>
      </div>
    );
  }
}

RecoverView.contextTypes = {
  language: PropTypes.object,
};

export default RecoverView;
