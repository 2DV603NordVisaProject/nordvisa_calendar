import React, { Component } from "react";
import ProvinceSelect from "./ProvinceSelect";
import PropTypes from "prop-types";
import "./Header.css"

class Header extends Component {

  render() {

    // Quick ref to context
    const language = this.context.language.Widget;

    return (
      <header className="header">
        <button className="input btn">{language.calendarView}</button>
        <select
          className="input"
          name="country"
          value={this.props.country}
          onChange={this.props.onRegionChange.bind(this)}
          >
          <option value="sweden">{language.sweden}</option>
          <option value="denmark">{language.denmark}</option>
          <option value="norway">{language.norway}</option>
          <option value="iceland">{language.iceland}</option>
          <option value="all">{language.allNordic}</option>
        </select>
        <ProvinceSelect region={this.props.country} onChange={this.props.onRegionChange.bind(this)} value={this.props.province}/>
        <select
          value={this.props.language}
          onChange={this.props.onLanguageChange.bind(this)}
          className="input"
          >
          <option value="sv">{language.swedish}</option>
          <option value="en">{language.english}</option>
          <option value="is">{language.icelandic}</option>
          <option value="da">{language.danish}</option>
          <option value="no">{language.norwegian}</option>
        </select>
      </header>
    )
  }
}

Header.contextTypes = {
  language: PropTypes.object,
}

export default Header;
