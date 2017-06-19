import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './ConfirmMessage.css';
import Button from './Button';

class ConfirmMessage extends Component {
  constructor() {
    super();
    this.state = {
      pop: null,
    };

    this.onYesClick = this.onYesClick.bind(this);
    this.onNoClick = this.onNoClick.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.popup.pop !== this.state.pop) {
      this.setState({ pop: nextProps.popup.pop });
    }
  }


  onNoClick() {
    this.setState({ pop: false });
  }

  onYesClick() {
    this.props.onClick();
    this.setState({ pop: false });
  }

  render() {
    if (this.state.pop) {
      return (
        <div className="confirm-message">
          <p>{this.props.popup.msg}</p>
          <div className="answer">
            <Button theme="success" onClick={this.onYesClick}>yes</Button>
            <Button theme="error" onClick={this.onNoClick}>no</Button>
          </div>
        </div>
      );
    }
    return (
        null
    );
  }
}

ConfirmMessage.propTypes = {
  popup: PropTypes.shape({
    pop: PropTypes.boolean,
    msg: PropTypes.string,
  }).isRequired,
  onClick: PropTypes.func.isRequired,
};

export default ConfirmMessage;
