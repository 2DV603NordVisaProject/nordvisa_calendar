import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './ConfirmMessage.css';

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
    this.onNoClick();
  }

  render() {
    if (this.state.pop) {
      return (
        <div className="confirm-message">
          <p>{this.props.popup.msg}</p>
          <div className="answer">
            <button className="btn-success" onClick={this.onYesClick}>yes</button>
            <button className="btn-error" onClick={this.onNoClick}>no</button>
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
