import React, { Component } from "react";
import "./ResponsiveMenu.css";
import MenuList from "./MenuList";


class ResponsiveMenu extends Component {

  state = {
    menu: {
      isCollapsed: false,
    },
  }

  onCollapseMenuClick(event) {
    event.preventDefault();

    let isCollapsed;

    if (this.state.menu.isCollapsed) {
      isCollapsed = false;
    } else {
      isCollapsed = true;
    }

    this.setState({ menu: { isCollapsed }})
  }

  render() {
    return (
      <div>
        <div className={this.state.menu.isCollapsed ? "menu-btn change" : "menu-btn"} onClick={this.onCollapseMenuClick.bind(this)}>
	        <div className="bar1"></div>
	        <div className="bar2"></div>
	        <div className="bar3"></div>
        </div>
        <div className={this.state.menu.isCollapsed ? "responsive-menu expand" : "responsive-menu"}>
          <MenuList/>
        </div>
      </div>
    );
  }
}

export default ResponsiveMenu;
