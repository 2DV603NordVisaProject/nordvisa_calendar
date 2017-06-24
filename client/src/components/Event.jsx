import React from 'react';
import Link from 'react-router/Link';
import PropTypes from 'prop-types';

const Event = ({event, delete}, context) => (
  <li>
    <div className="event-item">
      <p>{event.name}</p>
      <div className="item-action-container">
        <a className="error-text clickable" style={{ textTransform: 'capitalize' }} href="" name={event.id} onClick={delete}>{context.language.MyEventsView.delete}</a>
        <Link to={`/user/event/edit/${event.id}`} className="success-text" style={{ textTransform: 'capitalize' }}>{context.language.MyEventsView.edit}</Link>
        <Link to={`/user/event/view/${event.id}`} style={{ textTransform: 'capitalize' }}>{context.language.MyEventsView.view}</Link>
      </div>
    </div>
  </li>
);


Event.contextTypes = {
  language: PropTypes.object,
};

Event.propTypes = {
  event: PropTypes.shape({
    id: PropTypes.string,
    name: PropTypes.string,
  }).isRequired,
  delete: PropTypes.func.isRequired,
};

export default Event;
