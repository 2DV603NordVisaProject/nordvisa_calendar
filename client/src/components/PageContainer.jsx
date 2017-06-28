import React from 'react';
import PropTypes from 'prop-types';
import './PageContainer.css';

const propTypes = {
  children: PropTypes.element.isRequired,
  size: PropTypes.string,
};

const defaultProps = {
  size: 'large',
};

const PageContainer = ({ size, children, className }) => (
  <div className={`${size} ${className}`}>{children}</div>
);

PageContainer.propTypes = propTypes;
PageContainer.defaultProps = defaultProps;

export default PageContainer;
