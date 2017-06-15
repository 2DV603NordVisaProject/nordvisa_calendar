import React, { Component } from 'react';
import Redirect from 'react-router/Redirect';
import Client from '../Client';

class Logout extends Component {

  componentWillMount() {
    Client.logout();
  }

  render() {
    return (
      <Redirect
        to="/login"
      />
    );
  }
}


export default Logout;
