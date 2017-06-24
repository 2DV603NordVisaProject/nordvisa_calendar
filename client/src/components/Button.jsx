import React from 'react';
import PropTypes from 'prop-types';
import './Button.css';

const propTypes = {
  theme: PropTypes.string,
  children: PropTypes.string,
  onClick: PropTypes.func,
  form: PropTypes.boolean,
};

const defaultProps = {
  theme: 'primary',
  children: 'submit',
  onClick: null,
  form: false,
};

const Button = ({ form, theme, children, onClick }) => {
  if (form) {
    return <input className={theme} type="submit" value={children} />;
  }
  return <button className={theme} onClick={onClick}>{children}</button>;
};

Button.defaultProps = defaultProps;
Button.propTypes = propTypes;

export default Button;
