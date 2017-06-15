import React from 'react';
import PropTypes from 'prop-types';
import Event from './Event';

const EventsList = (props, context) => {
  const language = context.language.MyEventsView;

  return (
    <div className="event-list">
      <div className="list-header">
        <p className="capitalize">{language.events}</p>
      </div>
      <ul>
        {
          props.events.map(event => (
            <Event key={null} event={event} delete={props.delete} />
          ))
        }
      </ul>
    </div>
  );
};


EventsList.contextTypes = {
  language: PropTypes.object,
};

EventsList.propTypes = {
  events: PropTypes.arrayOf(PropTypes.object).isRequired,
  delete: PropTypes.func.isRequired,
};

export default EventsList;
