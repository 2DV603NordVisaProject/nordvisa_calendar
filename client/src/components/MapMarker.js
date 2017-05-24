import React, { Component } from "react";
import "./MapMarker.css";

class MapMarker extends Component {
  render() {
    return (
      <div>
        <div className='pin bounce'></div>
        <div className='pulse'></div>
      </div>
    )
  }
}

export default MapMarker
