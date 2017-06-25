import React from 'react';
import PropTypes from 'prop-types';
import UpdatePassword from '../components/UpdatePassword';
import UpdateAccount from '../components/UpdateAccount';
import PageTitle from '../components/PageTitle';
import ViewContainer from '../components/PageContainer';

const contextTypes = {
  language: PropTypes.object,
};

const MyAccountPage = (props, context) => {
  const language = context.language.MyAccountView;

  return (
    <ViewContainer>
      <PageTitle>{language.myAccount}</PageTitle>
      <UpdateAccount />
      <UpdatePassword />
    </ViewContainer>
  );
};

MyAccountPage.contextTypes = contextTypes;

export default MyAccountPage;
