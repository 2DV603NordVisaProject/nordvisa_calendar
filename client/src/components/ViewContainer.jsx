import React from 'react';
import PropTypes from 'prop-types';
import './ViewContainer.css';

const ViewContainer = props => (
  <div className={`${props.size} ${props.className}`}>{props.children}</div>
);

ViewContainer.defaultProps = {
  size: 'large',
};

ViewContainer.propTypes = {
  children: PropTypes.element.isRequired,
  size: PropTypes.string,
};

export default ViewContainer;
