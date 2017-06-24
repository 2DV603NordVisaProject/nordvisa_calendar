import React from 'react';
import PropTypes from 'prop-types';
import Event from './Event';

const propTypes = {
  events: PropTypes.arrayOf(PropTypes.object).isRequired,
  delete: PropTypes.func.isRequired,
}

const contextTypes = {
  language: PropTypes.object,
}

const EventsList = ({ events, delete }, context) => (
  <div className="event-list">
    <div className="list-header">
      <p className="capitalize">{context.language.MyEventsView.events}</p>
    </div>
    <ul>
      {
        events.map(event => (
          <Event key={event.id} event={event} delete={delete} />
        ))
      }
    </ul>
  </div>
);


EventsList.contextTypes = contextTypes;
EventsList.propTypes = propTypes;

export default EventsList;
