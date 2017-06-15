import React from 'react';
import Link from 'react-router/Link';
import PropTypes from 'prop-types';
import './TopBar.css';
import ResponsiveMenu from './ResponsiveMenu';

const TopBar = (props, context) => {
  const language = context.language.TopBar;

  return (
    <div className="topbar">
      <div className="brand">
        <Link to="/user/event">Event Dashboard</Link>
      </div>
      <ResponsiveMenu />
      <select defaultValue="en" onChange={props.onLanguageChange} className="capitalize">
        <option value="sv" className="capitalize">{language.swedish}</option>
        <option value="en" className="capitalize">{language.english}</option>
        <option value="no" className="capitalize">{language.norwegian}</option>
        <option value="da" className="capitalize">{language.danish}</option>
        <option value="is" className="capitalize">{language.icelandic}</option>
      </select>
    </div>
  );
};

TopBar.contextTypes = {
  language: PropTypes.object,
};

TopBar.propTypes = {
  onLanguageChange: PropTypes.func.isRequired,
};

export default TopBar;
