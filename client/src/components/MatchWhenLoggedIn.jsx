import React from 'react';
import Match from 'react-router/Match';
import Redirect from 'react-router/Redirect';
import Client from '../Client';


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
