import React from 'react';
import PropTypes from 'prop-types';
import './ConfirmMessage.css';
import Button from './Button';

const ConfirmMessage = props => (
  <div className="confirm-message">
    <p>{props.popup.msg}</p>
    <div className="answer">
      <Button theme="success" onClick={props.onYesClick}>yes</Button>
      <Button theme="error" onClick={props.onNoClick}>no</Button>
    </div>
  </div>
  );

ConfirmMessage.propTypes = {
  popup: PropTypes.shape({
    pop: PropTypes.boolean,
    msg: PropTypes.string,
  }).isRequired,
  onYesClick: PropTypes.func.isRequired,
  onNoClick: PropTypes.func.isRequired,
};

export default ConfirmMessage;
