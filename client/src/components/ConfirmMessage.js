import React, { Component } from 'react';
import './ConfirmMessage.css';

class ConfirmMessage extends Component {
  state = {
    pop: null,
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
            <button className="btn-success" onClick={this.onYesClick.bind(this)}>yes</button>
            <button className="btn-error" onClick={this.onNoClick.bind(this)}>no</button>
          </div>
        </div>
      );
    }
    return (
        null
    );
  }
}

export default ConfirmMessage;
