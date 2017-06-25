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

const ViewContainer = props => (
  <div className={`${props.size} ${props.className}`}>{props.children}</div>
);

ViewContainer.propTypes = propTypes;
ViewContainer.defaultProps = defaultProps;

export default ViewContainer;
