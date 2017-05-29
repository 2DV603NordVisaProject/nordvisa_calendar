import React, { Component } from 'react';
import './Widget.css';
import Header from "./Header";
import ViewContainer from "./ViewContainer";
import no from "./i18n/no";
import sv from "./i18n/sv";
import en from "./i18n/en";
import da from "./i18n/da";
import is from "./i18n/is";
import PropTypes from "prop-types";
import Client from "./Client";

class Widget extends Component {

  getChildContext() {
    return {
      language: this.state.languages[this.state.currentLanguage]
    }
  }

  state = {
    view: "calendar",
    events: [],
    token: "",
    fields: {
      country: "",
      province: "",
    },
    currentLanguage: "en",
    languages: {
      sv: sv,
      en: en,
      da: da,
      is: is,
      no: no,
    },
  }

  componentWillMount() {

    // Get default values from embedded widget code.
    const widget = document.getElementById("visa-widget");
    const fields = {
      province: widget.getAttribute("data-region"),
      country: widget.getAttribute("data-country")
    }
    const token = widget.getAttribute("data-token");

    // Get events from widget API
    // TODO: Fix, not working
    const config = {
      longitude: "18.643501",
      latitude: "60.128161",
      radius: "10",
      country: widget.getAttribute("data-country"),
      county: widget.getAttribute("data-region"),
      fromDate: "1483234293000",
      toDate: "",
      token: widget.getAttribute("data-token"),
    }

    const URI = `/api/event/get?longitude=${config.longitude}&latitude=${config.latitude}&radius=${config.radius}&country=${config.country}&county=${config.county}&fromDate=${config.fromDate}&toDate=${config.toDate}&token=${config.token}`
    console.log(URI);
    Client.get(URI)
      .then(events => {
        console.log("events", events);
        this.setState({fields, events, token})
      })
  }

  onLanguageChange(event) {
    const currentLanguage = event.target.value;
    this.setState({currentLanguage});
  }

  onRegionChange(event) {
    const fields = this.state.fields;
  }

  render() {
    return (
      <div className="widget">
        <Header
          country={this.state.fields.country}
          province={this.state.fields.province}
          language={this.state.currentLanguage}
          onLanguageChange={this.onLanguageChange.bind(this)}
          onRegionChange={this.onRegionChange.bind(this)}
          />
        <ViewContainer
          view={this.state.view}
          events={this.state.events}
          />
      </div>
    );
  }
}

Widget.childContextTypes = {
  language: PropTypes.object,
};

export default Widget;
