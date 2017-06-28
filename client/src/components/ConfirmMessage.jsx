import React from 'react';
import PropTypes from 'prop-types';
import './ConfirmMessage.css';
import Button from './Button';

const propTypes = {
  popup: PropTypes.shape({
    pop: PropTypes.boolean,
    msg: PropTypes.string,
  }).isRequired,
  onYesClick: PropTypes.func.isRequired,
  onNoClick: PropTypes.func.isRequired,
};

const ConfirmMessage = ({ popup, onYesClick, onNoClick }) => (
  <div className="confirm-message">
    <p>{popup.msg}</p>
    <div className="answer">
      <Button theme="success" onClick={onYesClick}>yes</Button>
      <Button theme="error" onClick={onNoClick}>no</Button>
    </div>
  </div>
  );

ConfirmMessage.propTypes = propTypes;

export default ConfirmMessage;
