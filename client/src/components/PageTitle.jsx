import React from 'react';
import PropTypes from 'prop-types';
import './PageTitle.css';

const propTypes = {
  children: PropTypes.string.isRequired,
};

const PageTitle = ({ children }) => (
  <h2 className="page-title">{children}</h2>
);

PageTitle.propTypes = propTypes;

export default PageTitle;
