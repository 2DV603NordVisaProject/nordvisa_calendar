import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './MyEventsView.css';
import EventsList from './EventsList';
import Client from '../Client';
import ConfirmMessage from './ConfirmMessage';
import PageTitle from './PageTitle';
import ViewContainer from './ViewContainer';

const contextTypes = {
  language: PropTypes.object,
};

class MyEventsView extends Component {
  constructor(props) {
    super(props);

    this.handleDeleteClick = this.handleDeleteClick.bind(this);
    this.onYesClick = this.onYesClick.bind(this);
    this.onNoClick = this.onNoClick.bind(this);
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
        if (Array.isArray(events)) {
          this.setState({ events });
        }
      });
  }


  onYesClick() {
    const uri = '/api/event/delete';
    const eventToDelete = {
      id: this.state.toDelete,
    };

    Client.post(eventToDelete, uri);

    const events = this.state.events.filter(event => event.id !== this.state.toDelete);
    const popup = this.state.popup;
    popup.pop = false;
    this.setState({ events, popup });
  }

  onNoClick() {
    const popup = this.state.popup;
    popup.pop = false;
    this.setState({ popup });
  }

  handleDeleteClick(evt) {
    evt.preventDefault();
    const eventName = this.state.events.filter((event) => {
      if (event.id === evt.target.name) {
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

  render() {
    const language = this.context.language.MyEventsView;

    return (
      <ViewContainer>
        <PageTitle>{language.myEvents}</PageTitle>
        <EventsList events={this.state.events} onDeleteClick={this.handleDeleteClick} />
        {
          this.state.popup.pop
          ? <ConfirmMessage
            popup={this.state.popup}
            onYesClick={this.onYesClick}
            onNoClick={this.onNoClick}
          />
          : null
        }
      </ViewContainer>
    );
  }
}

MyEventsView.contextTypes = contextTypes;

export default MyEventsView;
