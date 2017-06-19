import React from 'react';
import PropTypes from 'prop-types';
import './SubTitle.css';

const SubTitle = ({ children }) => (
  <h3 className="sub-title">{children}</h3>
);

SubTitle.defaultProps = {
  children: 'Sub Title',
};

SubTitle.propTypes = {
  children: PropTypes.string,
};

export default SubTitle;
