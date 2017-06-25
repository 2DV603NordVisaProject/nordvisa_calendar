import React from 'react';
import PropTypes from 'prop-types';
import './ViewContainer.css';

const propTypes = {
  children: PropTypes.element.isRequired,
  size: PropTypes.string,
};

const defaultProps = {
  size: 'large',
};

const ViewContainer = ({ size, children, className }) => (
  <div className={`${size} ${className}`}>{children}</div>
);

ViewContainer.propTypes = propTypes;
ViewContainer.defaultProps = defaultProps;

export default ViewContainer;
