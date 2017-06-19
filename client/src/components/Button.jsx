import React from 'react';
import PropTypes from 'prop-types';
import './Button.css';

const Button = (props) => {
  if (props.form) {
    return <input className={props.theme} type="submit" value={props.children} />;
  }
  return <button className={props.theme} onClick={props.onClick}>{props.children}</button>;
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
