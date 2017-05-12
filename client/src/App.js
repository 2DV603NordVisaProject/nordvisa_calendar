import React, { Component } from 'react';
import TopBar from "./components/TopBar";
import ViewContainer from "./components/ViewContainer"
import Router from "react-router/BrowserRouter";

class App extends Component {
  render() {
    return (
      <Router>
        <div className="App">
          <TopBar/>
          <ViewContainer/>
        </div>
      </Router>
    );
  }
}

export default App;
