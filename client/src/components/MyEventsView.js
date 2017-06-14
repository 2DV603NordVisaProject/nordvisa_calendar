import React, { Component } from 'react';
import './MyEventsView.css';
import EventsList from './EventsList';
import Client from '../Client';
import ConfirmMessage from './ConfirmMessage';
import PropTypes from 'prop-types';


class MyEventsView extends Component {
  constructor(props) {
    super(props);

    this.handleDeleteClick = this.handleDeleteClick.bind(this);
  }
  state = {
    events: [],
    toDelete: null,
    popup: {
      pop: false,
      msg: '',
    },
  }


  componentWillMount() {
    const uri = '/api/event/get_manageable';
    Client.get(uri)
      .then((events) => {
        console.log(events);
        this.setState({ events });
      });
  }

  handleDeleteClick(evt) {
    evt.preventDefault();
    const eventName = this.state.events.filter((event) => {
      if (event.id == evt.target.name) {
        return event.name;
      }
      return '';
    })[0];

    const popup = {
      pop: true,
      msg: `Are you sure you want to delete the event ${eventName.name}?`,
    };
    this.setState({ popup });
    this.setState({ toDelete: eventName.id });
  }

  onYesClick() {
    const uri = '/api/event/delete';
    const event = {
      id: this.state.toDelete,
    };

    Client.post(event, uri);

    const events = this.state.events.filter(event => event.id !== this.state.toDelete);
    this.setState({ events });
  }

  render() {
    const language = this.context.language.MyEventsView;

    return (
      <div className="view">
        <h2 className="capitalize">{language.myEvents}</h2>
        <EventsList events={this.state.events} delete={this.handleDeleteClick} />
        <ConfirmMessage popup={this.state.popup} onClick={this.onYesClick.bind(this)} />
      </div>
    );
  }
}

export default MyEventsView;

MyEventsView.contextTypes = {
  language: PropTypes.object,
};
