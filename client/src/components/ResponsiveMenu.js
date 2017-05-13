import React, { Component } from "react";
import "./ResponsiveMenu.css";
import MenuList from "./MenuList";


class ResponsiveMenu extends Component {

  onCollapseMenuClick(event) {
    event.preventDefault();
    document.querySelector(".menu-btn").classList.toggle("change");
    document.querySelector(".responsive-menu").classList.toggle("expand");
  }

  render() {
    return (
      <div>
        <div className="menu-btn" onClick={this.onCollapseMenuClick}>
	        <div className="bar1"></div>
	        <div className="bar2"></div>
	        <div className="bar3"></div>
        </div>
        <div className="responsive-menu">
          <MenuList/>
        </div>
      </div>
    );
  }
}

export default ResponsiveMenu;
