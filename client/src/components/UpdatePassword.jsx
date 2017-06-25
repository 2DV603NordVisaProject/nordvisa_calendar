import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ErrorList from './ErrorList';
import Client from '../Client';
import Button from './Button';
import SubTitle from './SubTitle';

const contextTypes = {
  language: PropTypes.object,
};

class UpdatePassword extends Component {
  constructor() {
    super();

    this.onInputChange = this.onInputChange.bind(this);
    this.onFormSubmit = this.onFormSubmit.bind(this);
  }
  state = {
    fields: {
      oldpassword: '',
      newpassword: '',
      confirmpassword: '',
    },
    fieldErrors: [],
  }

  componentDidMount() {
    const uri = '/api/user/current';
    Client.get(uri)
      .then((user) => {
        const fields = {
          id: user.id,
          oldpassword: '',
          newpassword: '',
          confirmpassword: '',
        };
        this.setState({ fields });
      });
  }

  onFormSubmit(event) {
    event.preventDefault();
    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on Errors
    if (fieldErrors.length) return;

    const uri = '/api/user/change_password';
    const user = {
      id: this.state.fields.id,
      oldPassword: this.state.fields.oldpassword,
      password: this.state.fields.newpassword,
      passwordConfirmation: this.state.fields.confirmpassword,
    };

    Client.post(user, uri)
      .then((res) => {
        if (Object.prototype.hasOwnProperty.call(res, 'message')) {
          fieldErrors.push(res.message);
          this.setState({ fieldErrors });
          this.forceUpdate();
        } else {
          fieldErrors.push('Password updated!');
          this.setState({ fieldErrors });

          this.setState({ fields: {
            oldpassword: '',
            newpassword: '',
            confirmpassword: '',
          } });
        }
      });
  }

  onInputChange(event) {
    const value = event.target.value;
    const name = event.target.name;
    const fields = this.state.fields;
    fields[name] = value;
    this.setState({ fields });
  }

  validate(fields) {
    const errors = [];
    if (!fields.newpassword || !fields.confirmpassword || !fields.oldpassword) {
      errors.push(this.context.language.Errors.passwordRequired);
    }
    if (fields.newpassword !== fields.confirmpassword) {
      errors.push(this.context.language.Errors.passwordDoesNotMatch);
    }
    if (fields.newpassword.length < 10) errors.push(this.context.language.Errors.shortPassword);
    if (fields.newpassword.length > 255) errors.push(this.context.language.Errors.longPassword);
    return errors;
  }

  render() {
    const language = this.context.language.MyAccountView;
    const style = {
      border: '1px solid grey',
      maxWidth: '600px',
      borderRadius: '3px',
      margin: '0 auto 20px',
    };

    return (
      <div style={style}>
        <SubTitle>{language.updatePassword}</SubTitle>
        <form onSubmit={this.onFormSubmit}>
          <label htmlFor="oldpassword" style={{ textTransform: 'capitalize' }}>{language.oldPassword}:</label>
          <input
            type="password"
            name="oldpassword"
            value={this.state.fields.oldpassword}
            onChange={this.onInputChange}
          />
          <label htmlFor="newpassword" style={{ textTransform: 'capitalize' }}>{language.newPassword}:</label>
          <input
            type="password"
            name="newpassword"
            value={this.state.fields.newpassword}
            onChange={this.onInputChange}
          />
          <label htmlFor="confirmpassword" style={{ textTransform: 'capitalize' }}>{language.confirmPassword}:</label>
          <input
            type="password"
            name="confirmpassword"
            value={this.state.fields.confirmpassword}
            onChange={this.onInputChange}
          />
          <ErrorList errors={this.state.fieldErrors} />
          <Button form>{language.save}</Button>
        </form>
      </div>
    );
  }
}

UpdatePassword.contextTypes = contextTypes;

export default UpdatePassword;
