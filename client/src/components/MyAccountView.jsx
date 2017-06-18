import React from 'react';
import PropTypes from 'prop-types';
import UpdatePassword from './UpdatePassword';
import UpdateAccount from './UpdateAccount';
import PageTitle from './PageTitle';

const MyAccountView = (props, context) => {
  const language = context.language.MyAccountView;

  return (
    <div className="view">
      <PageTitle>{language.myAccount}</PageTitle>
      <UpdateAccount />
      <UpdatePassword />
    </div>
  );
};

export default MyAccountView;


MyAccountView.contextTypes = {
  language: PropTypes.object,
};
