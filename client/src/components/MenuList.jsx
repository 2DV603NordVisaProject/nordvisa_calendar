import React from 'react';
import PropTypes from 'prop-types';
import Link from 'react-router/Link';
import Client from '../Client';

const contextTypes = {
  language: PropTypes.object,
};

const propTypes = {
  onMenuLinkClick: PropTypes.func.isRequired,
};

const MenuList = ({ onMenuLinkClick }, context) => {
  const language = context.language.MenuList;
  const style = {
    textTransform: 'capitalize',
  };

  return (
    <ul style={style}>
      {
        Client.getUserLevel() > 1 ? (
          <div>
            <li onClick={onMenuLinkClick}><Link to="/admin/members" className="menu-link">{language.members}</Link></li>
            <li onClick={onMenuLinkClick}><Link to="/admin/pending-registrations" className="menu-link">{language.pendingReg}</Link></li>
          </div>
        ) : (
          null
        )
      }
      {
        Client.isLogedIn() ? (
          <div>
            <li onClick={onMenuLinkClick}><Link to="/user/account" className="menu-link">{language.myAccount}</Link></li>
            <li onClick={onMenuLinkClick}><Link to="/user/event/create" className="menu-link">{language.createEvent}</Link></li>
            <li onClick={onMenuLinkClick}><Link to="/user/event" className="menu-link">{language.myEvents}</Link></li>
            <li onClick={onMenuLinkClick}><Link to="/logout" className="menu-link">{language.logout}</Link></li>
          </div>
        ) : (
          <div>
            <li onClick={onMenuLinkClick}><Link to="/login" className="menu-link capitalize">{language.login}</Link></li>
            <li onClick={onMenuLinkClick}><Link to="/register" className="menu-link capitalize">{language.register}</Link></li>
            <li onClick={onMenuLinkClick}><Link to="/recover-password" className="menu-link capitalize">{language.forgot}</Link></li>
          </div>
        )
      }
      <li onClick={onMenuLinkClick}><Link to="/generate-widget" className="menu-link capitalize">{language.generate}</Link></li>
    </ul>
  );
};

MenuList.propTypes = propTypes;
MenuList.contextTypes = contextTypes;

export default MenuList;
