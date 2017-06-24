import React from 'react';
import PropTypes from 'prop-types';
import './Button.css';

const Button = ({ form, theme, children, onClick }) => {
  if (form) {
    return <input className={theme} type="submit" value={children} />;
  }
  return <button className={theme} onClick={onClick}>{children}</button>;
};

Button.defaultProps = {
  theme: 'primary',
  children: 'submit',
  onClick: null,
  form: false,
};

Button.propTypes = {
  theme: PropTypes.string,
  children: PropTypes.string,
  onClick: PropTypes.func,
  form: PropTypes.boolean,
};

export default Button;
