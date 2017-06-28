import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './MyEventsPage.css';
import EventsList from '../components/EventsList';
import Client from '../Client';
import ConfirmMessage from '../components/ConfirmMessage';
import PageTitle from '../components/PageTitle';
import PageContainer from '../components/PageContainer';

const contextTypes = {
  language: PropTypes.object,
};

class MyEventsPage extends Component {
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


  componentDidMount() {
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
    const popup = Object.assign({}, this.state.popup);
    popup.pop = false;
    this.setState({ events, popup });
  }

  onNoClick() {
    const popup = Object.assign({}, this.state.popup);
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
      <PageContainer>
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
      </PageContainer>
    );
  }
}

MyEventsPage.contextTypes = contextTypes;

export default MyEventsPage;
