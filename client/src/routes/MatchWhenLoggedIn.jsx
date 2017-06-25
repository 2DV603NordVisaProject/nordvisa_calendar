import React from 'react';
import Match from 'react-router/Match';
import Redirect from 'react-router/Redirect';
import PropTypes from 'prop-types';
import Client from '../Client';

const propTypes = {
  component: PropTypes.element.isRequired,
};

const MatchWhenLoggedIn = ({ component: Component, ...rest }) => (
  <Match
    {...rest}
    render={props => (
    Client.isLogedIn() ? (
      <Component {...props} />
    ) : (
      <Redirect to={{
        pathname: '/login',
      }}
      />
    )
  )}
  />
);

MatchWhenLoggedIn.propTypes = propTypes;

export default MatchWhenLoggedIn;
