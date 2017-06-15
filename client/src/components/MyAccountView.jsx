import React, { Component } from 'react';
import UpdatePassword from './UpdatePassword';
import UpdateAccount from './UpdateAccount';
import PropTypes from 'prop-types';

class MyAccountView extends Component {
  render() {
    const language = this.context.language.MyAccountView;

    return (
      <div className="view">
        <h2 className="capitalize">{language.myAccount}</h2>
        <UpdateAccount />
        <UpdatePassword />
      </div>
    );
  }
}

export default MyAccountView;


MyAccountView.contextTypes = {
  language: PropTypes.object,
};
