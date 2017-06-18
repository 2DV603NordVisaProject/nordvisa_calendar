import React from 'react';
import Link from 'react-router/Link';
import PropTypes from 'prop-types';

const Event = (props, context) => (
  <li>
    <div className="event-item">
      <p>{props.event.name}</p>
      <div className="item-action-container">
        <a className="error clickable capitalize" href="" name={props.event.id} onClick={props.delete}>{context.language.MyEventsView.delete}</a>
        <Link to={`/user/event/edit/${props.event.id}`} className="success capitalize">{context.language.MyEventsView.edit}</Link>
        <Link to={`/user/event/view/${props.event.id}`} className="capitalize">{context.language.MyEventsView.view}</Link>
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
