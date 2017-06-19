import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ErrorList from './ErrorList';
import Client from '../Client';
import PageTitle from './PageTitle';
import Button from './Button';
import ViewContainer from './ViewContainer';


class UpdatePasswordView extends Component {
  constructor() {
    super();

    this.onInputChange = this.onInputChange.bind(this);
    this.onFormSubmit = this.onFormSubmit.bind(this);
  }

  state = {
    fields: {
      urlId: this.props.id,
      password: '',
      passwordConfirmation: '',
    },
    fieldErrors: [],
  }

  onInputChange(event) {
    const fields = this.state.fields;
    fields[event.target.name] = event.target.value;
    this.setState({ fields });
  }

  onFormSubmit(event) {
    event.preventDefault();

    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on Errors
    if (fieldErrors.length) return;

    const uri = '/api/visitor/recover_password';
    console.log(this.state.fields);
    Client.post(this.state.fields, uri)
      .then((res) => {
        if (Object.prototype.hasOwnProperty.call(res, 'message')) {
          fieldErrors.push(res.message);
          this.setState({ fieldErrors });
        } else {
          fieldErrors.push('Password updated!');
          this.setState({ fieldErrors,
            fields: {
              urlId: '',
              password: '',
              passwordConfirmation: '',
            } });
        }
      });
  }

  validate(fields) {
    const errors = [];
    if (!fields.password || !fields.passwordConfirmation) {
      errors.push(this.context.language.Errors.passwordRequired);
    }
    if (fields.password !== fields.passwordConfirmation) {
      errors.push(this.context.language.Errors.passwordDoesNotMatch);
    }
    if (fields.password.length < 10) errors.push(this.context.language.Errors.shortPassword);
    if (fields.password.length > 255) errors.push(this.context.language.Errors.longPassword);
    return errors;
  }

  render() {
    const language = this.context.language.UpdatePasswordView;

    return (
      <ViewContainer size="small" className="login">
        <PageTitle>{language.updatePassword}</PageTitle>
        <form onSubmit={this.onFormSubmit}>
          <label htmlFor="password" className="capitalize">{language.newPassword}:</label>
          <input name="password" type="password" onChange={this.onInputChange} />
          <label htmlFor="passwordConfirmation" className="capitalize">{language.confirmPassword}:</label>
          <input name="passwordConfirmation" type="password" onChange={this.onInputChange} />
          <ErrorList errors={this.state.fieldErrors} />
          <Button form>{language.save}</Button>
        </form>
      </ViewContainer>
    );
  }
}

UpdatePasswordView.contextTypes = {
  language: PropTypes.object,
};

UpdatePasswordView.propTypes = {
  id: PropTypes.string.isRequired,
};

export default UpdatePasswordView;
