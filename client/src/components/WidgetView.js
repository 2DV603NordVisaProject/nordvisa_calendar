import React, { Component } from "react";
import "./WidgetView.css";


class WidgetView extends Component {
  render() {
    return (
      <div className="lightbox widget-view">
        <h2>Generate Widget Code</h2>
        <form>
          <select>
            <option>Dalarna</option>
            <option>Sweden</option>
          </select>
          <button>generate</button>
        </form>
      </div>
    );
  }
}

export default WidgetView;
