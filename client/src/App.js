import React, { Component } from 'react';
import logo from './logo.svg';
import TopBar from "./components/TopBar";
import ViewContainer from "./components/ViewContainer"

class App extends Component {
  render() {
    return (
      <div className="App">
        <TopBar/>
        <ViewContainer/>
      </div>
    );
  }
}

export default App;
