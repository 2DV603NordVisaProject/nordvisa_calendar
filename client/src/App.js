import React, { Component } from 'react';
import TopBar from "./components/TopBar";
import ViewContainer from "./components/ViewContainer"
import Router from "react-router/BrowserRouter";
import sv from "./i18n/sv";
import en from "./i18n/en";
import no from "./i18n/no";
import da from "./i18n/da";
import is from "./i18n/is";
import PropTypes from "prop-types";

class App extends Component {
  getChildContext() {
    return {
      language: this.state.languages[this.state.currentLanguage]
    }
  }

  state = {
    languages: {
      sv: sv,
      en: en,
      da: da,
      is: is,
      no: no,
    },
    currentLanguage: "en",
  }

  onLanguageChange(event) {
    const currentLanguage = event.target.value;
    this.setState({currentLanguage});
  }

  render() {
    return (
      <Router>
        <div className="App">
          <TopBar onLanguageChange={this.onLanguageChange.bind(this)}/>
          <ViewContainer/>
        </div>
      </Router>
    );
  }
}

App.childContextTypes = {
  language: PropTypes.object,
};

export default App;
