import React from 'react';
import Match from 'react-router/Match';
import Redirect from 'react-router/Redirect';
import Client from '../Client';

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


export default MatchWhenAdmin;
