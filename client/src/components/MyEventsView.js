import React, { Component } from "react";
import "./MyEventsView.css";
import EventsList from "./EventsList";
import Client from "../Client";
import ConfirmMessage from "./ConfirmMessage";


class MyEventsView extends Component {
  constructor(props) {
    super(props)

    this.handleDeleteClick = this.handleDeleteClick.bind(this)
  }
  state = {
    events: [],
    toDelete: null,
    popup: {
      pop: false,
      msg: "",
    },
  }

  componentWillMount() {
    const events = Client.getEvents();
    this.setState({ events });
  }

  handleDeleteClick(evt) {
    evt.preventDefault();
    const eventName = this.state.events.filter((event) => {
      if (event.id == evt.target.name) {
        return event.name;
      }
      return "";
    })[0];

    const popup = {
      pop: true,
      msg: `Are you sure you want to delete the event ${eventName.name}?`
    }
    this.setState({popup});
    this.setState({toDelete: eventName.id})
  }

  onYesClick() {
    const events = this.state.events.filter(event => event.id !== this.state.toDelete);
    this.setState({ events })
  }

  render() {
    return (
      <div className="view">
        <h2>My Events</h2>
        <EventsList events={this.state.events} delete={this.handleDeleteClick}/>
        <ConfirmMessage popup={this.state.popup} onClick={this.onYesClick.bind(this)}/>
      </div>
    );
  }
}

export default MyEventsView;
