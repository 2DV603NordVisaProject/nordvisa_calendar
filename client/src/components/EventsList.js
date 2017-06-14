import React from 'react';
import Event from './Event';
import PropTypes from 'prop-types';

const EventsList = (props) => {
  const language = this.context.language.MyEventsView;

  return (
    <div className="event-list">
      <div className="list-header">
        <p className="capitalize">{language.events}</p>
      </div>
      <ul>
        {
          this.props.events.map((event, i) => (
            <Event key={i} event={event} delete={this.props.delete} />
          ))
        }
      </ul>
    </div>
  );
};


EventsList.contextTypes = {
  language: PropTypes.object,
};

export default EventsList;
