import React from 'react';
import PropTypes from 'prop-types';
import UpdatePassword from './UpdatePassword';
import UpdateAccount from './UpdateAccount';
import PageTitle from './PageTitle';
import ViewContainer from './ViewContainer';

const contextTypes = {
  language: PropTypes.object,
};

const MyAccountView = (props, context) => {
  const language = context.language.MyAccountView;

  return (
    <ViewContainer>
      <PageTitle>{language.myAccount}</PageTitle>
      <UpdateAccount />
      <UpdatePassword />
    </ViewContainer>
  );
};

MyAccountView.contextTypes = contextTypes;

export default MyAccountView;
