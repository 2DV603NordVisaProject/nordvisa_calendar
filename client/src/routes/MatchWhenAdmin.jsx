import React from 'react';
import Match from 'react-router/Match';
import Redirect from 'react-router/Redirect';
import PropTypes from 'prop-types';
import Client from '../Client';

const propTypes = {
  component: PropTypes.element.isRequired,
};

const MatchWhenAdmin = ({ component: Component, ...rest }) => (
  <Match
    {...rest}
    render={props => (
    Client.isLogedIn() ? (
      Client.getUserLevel() > 0 ? (
        <Component {...props} />
      ) : (
        <Redirect to={{
          pathname: '/user/event',
        }}
        />
      )
    ) : (
      <Redirect to={{
        pathname: '/login',
      }}
      />
    )
  )}
  />
);

MatchWhenAdmin.propTypes = propTypes;

export default MatchWhenAdmin;
