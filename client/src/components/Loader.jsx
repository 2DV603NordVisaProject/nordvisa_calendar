import React, { Component } from 'react';
import './Loader.css';

class Loader extends Component {
  render() {
    return (
      <div className="load-container">
        <div className="loader" />
        <p className="load">Loading</p>
      </div>
    );
  }
}

export default Loader;
