import React from 'react';
import PropTypes from 'prop-types';
import UpdatePassword from '../components/UpdatePassword';
import UpdateAccount from '../components/UpdateAccount';
import PageTitle from '../components/PageTitle';
import PageContainer from '../components/PageContainer';

const contextTypes = {
  language: PropTypes.object,
};

const MyAccountPage = (props, context) => {
  const language = context.language.MyAccountView;

  return (
    <PageContainer>
      <PageTitle>{language.myAccount}</PageTitle>
      <UpdateAccount />
      <UpdatePassword />
    </PageContainer>
  );
};

MyAccountPage.contextTypes = contextTypes;

export default MyAccountPage;
