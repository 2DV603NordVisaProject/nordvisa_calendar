import React from 'react';
import PropTypes from 'prop-types';
import UpdatePassword from './UpdatePassword';
import UpdateAccount from './UpdateAccount';

const MyAccountView = (context) => {
  const language = context.language.MyAccountView;

  return (
    <div className="view">
      <h2 className="capitalize">{language.myAccount}</h2>
      <UpdateAccount />
      <UpdatePassword />
    </div>
  );
};

export default MyAccountView;


MyAccountView.contextTypes = {
  language: PropTypes.object,
};
