import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Router from 'react-router/BrowserRouter';
import TopBar from './components/TopBar';
import ViewContainer from './components/ViewContainer';
import sv from './i18n/sv';
import en from './i18n/en';
import no from './i18n/no';
import da from './i18n/da';
import is from './i18n/is';

class App extends Component {
  constructor() {
    super();
    this.onLanguageChange = this.onLanguageChange.bind(this);
  }

  state = {
    languages: { sv, en, da, is, no },
    currentLanguage: 'en',
  }

  getChildContext() {
    return {
      language: this.state.languages[this.state.currentLanguage],
    };
  }

  onLanguageChange(event) {
    const currentLanguage = event.target.value;
    this.setState({ currentLanguage });
  }

  render() {
    return (
      <Router>
        <div className="App">
          <TopBar onLanguageChange={this.onLanguageChange} />
          <ViewContainer />
        </div>
      </Router>
    );
  }
}

App.childContextTypes = {
  language: PropTypes.object,
};

export default App;
