import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Client from '../Client';
import Link from 'react-router/Link';

class MenuList extends Component {
  render() {
    const language = this.context.language.MenuList;

    return (
      <ul>
        {
          Client.getUserLevel() > 1 ? (
            <div>
              <li><Link to="/admin/members" className="menu-link">{language.members}</Link></li>
              <li><Link to="/admin/pending-registrations" className="menu-link">{language.pendingReg}</Link></li>
            </div>
          ) : (
            <div />
          )
        }
        {
          Client.isLogedIn() ? (
            <div>
              <li><Link to="/user/account" className="menu-link">{language.myAccount}</Link></li>
              <li><Link to="/user/event/create" className="menu-link">{language.createEvent}</Link></li>
              <li><Link to="/user/event" className="menu-link">{language.myEvents}</Link></li>
              <li><Link to="/logout" className="menu-link">{language.logout}</Link></li>
            </div>
          ) : (
            <div>
              <li><Link to="/login" className="menu-link capitalize">{language.login}</Link></li>
              <li><Link to="/register" className="menu-link capitalize">{language.register}</Link></li>
              <li><Link to="/recover-password" className="menu-link capitalize">{language.forgot}</Link></li>
            </div>
          )
        }
        <li><Link to="/generate-widget" className="menu-link capitalize">{language.generate}</Link></li>
      </ul>
    );
  }
}


MenuList.contextTypes = {
  language: PropTypes.object,
};

export default MenuList;
