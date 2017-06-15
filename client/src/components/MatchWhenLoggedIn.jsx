import React from 'react';
import Client from '../Client';
import Match from 'react-router/Match';
import Redirect from 'react-router/Redirect';

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


export default MatchWhenLoggedIn;
