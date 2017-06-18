import React from 'react';
import PropTypes from 'prop-types';
import './PageTitle.css';

const PageTitle = ({ children }) => (
  <h2 className="page-title">{children}</h2>
);

PageTitle.propTypes = {
  children: PropTypes.string.isRequired,
};

export default PageTitle;
