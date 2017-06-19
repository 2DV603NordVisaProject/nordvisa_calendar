import React, { Component } from 'react';
import { isEmail } from 'validator';
import PropTypes from 'prop-types';
import ErrorList from './ErrorList';
import Client from '../Client';
import Button from './Button';
import SubTitle from './SubTitle';

class UpdateAccount extends Component {
  constructor() {
    super();

    this.onInputChange = this.onInputChange.bind(this);
    this.onFormSubmit = this.onFormSubmit.bind(this);
  }

  state = {
    fields: {
      id: null,
      email: '',
      org: '',
      neworg: '',
    },
    organizations: [],
    fieldErrors: [],
  }

  componentWillMount() {
    const uri = '/api/user/current';
    Client.get(uri)
      .then((user) => {
        const fields = {
          id: user.id,
          email: user.email,
          org: user.organization,
          neworg: '',
        };
        this.setState({ fields });
      });

    const orgUri = '/api/visitor/organizations';
    Client.get(orgUri)
      .then((organizations) => {
        this.setState({ organizations });
      });
  }

  onInputChange(event) {
    const value = event.target.value;
    const name = event.target.name;
    const fields = this.state.fields;
    fields[name] = value;
    this.setState({ fields });

    const hiddenForm = document.querySelector('#on-select-change');

    if (this.state.fields.org === 'new') {
      hiddenForm.classList.remove('hidden');
    } else {
      hiddenForm.classList.add('hidden');
    }
  }

  onFormSubmit(event) {
    event.preventDefault();
    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on Errors
    if (fieldErrors.length) return;

    const uri = '/api/user/update_user_details';
    const user = {
      id: this.state.fields.id,
      email: this.state.fields.email,
      organization: this.state.fields.neworg || this.state.fields.org,
    };

    Client.post(user, uri);

    fieldErrors.push('Account updated!');
    this.setState({ fieldErrors });

    this.setState({ fields: {
      email: '',
      org: '',
      neworg: '',
    } });
  }

  validate(fields) {
    const errors = [];
    if (!fields.email) errors.push(this.context.language.Errors.emailRequired);
    if (!isEmail(fields.email)) errors.push(this.context.language.Errors.invalidEmail);
    return errors;
  }

  render() {
    const language = this.context.language.MyAccountView;

    return (
      <div className="box">
        <SubTitle>{language.updateDetails}</SubTitle>
        <form onSubmit={this.onFormSubmit}>
          <label htmlFor="email" className="capitalize">{language.email}:</label>
          <input type="text" name="email" value={this.state.fields.email} onChange={this.onInputChange} />
          <label htmlFor="org" className="capitalize">{language.organization}:</label>
          <select
            className="capitalize"
            name="org"
            onChange={this.onInputChange}
            value={this.state.fields.org}
            defaultValue=""
          >
            {
              this.state.organizations.map(org => <option className="capitalize" value={org}>{org}</option>)
            }
            <option value="new" className="capitalize">{language.newOrganization}</option>
            <option value="" className="capitalize">{language.noOrganization}</option>
          </select>
          <div id="on-select-change" className="hidden">
            <label htmlFor="neworg">{language.newOrganization}:</label>
            <input
              name="neworg"
              value={this.state.fields.neworg}
              onChange={this.onInputChange}
              type="text"
            />
          </div>
          <ErrorList errors={this.state.fieldErrors} />
          <Button form>{language.save}</Button>
        </form>
      </div>
    );
  }
}

UpdateAccount.contextTypes = {
  language: PropTypes.object,
};

export default UpdateAccount;
