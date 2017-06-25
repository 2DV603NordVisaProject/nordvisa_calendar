import React from 'react';
import Link from 'react-router/Link';
import PropTypes from 'prop-types';
import './TopBar.css';
import ResponsiveMenu from './ResponsiveMenu';
import LanguageSelect from './LanguageSelect';

const TopBar = ({ onLanguageChange }) => (
  <div className="topbar">
    <div className="brand">
      <Link to="/user/event">Event Dashboard</Link>
    </div>
    <ResponsiveMenu />
    <LanguageSelect onChange={onLanguageChange} />
  </div>
  );

TopBar.propTypes = {
  onLanguageChange: PropTypes.func.isRequired,
};

export default TopBar;
