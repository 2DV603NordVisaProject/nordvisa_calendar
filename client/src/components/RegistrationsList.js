import React, { Component } from "react";
import Registration from "./Registration";

class RegistrationsList extends Component {
  render() {
    return (
      <ul>
        {
          this.props.registrations.map((registration, i) => (
            <Registration key={i} registration={registration}/>
          ))
        }
      </ul>
    );
  }
}

export default RegistrationsList;
