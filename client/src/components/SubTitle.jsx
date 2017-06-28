import React from 'react';
import PropTypes from 'prop-types';
import './SubTitle.css';

const propTypes = {
  children: PropTypes.string,
};

const defaultProps = {
  children: 'Sub Title',
};

const SubTitle = ({ children }) => (
  <h3 className="sub-title">{children}</h3>
);

SubTitle.propTypes = propTypes;
SubTitle.defaultProps = defaultProps;

export default SubTitle;
