import React from 'react';
import Link from 'react-router/Link';
import PropTypes from 'prop-types';

const Event = (props) => {
  const language = this.context.language.MyEventsView;

  return (
    <li>
      <div className="event-item">
        <p>{props.event.name}</p>
        <div className="item-action-container">
          <a className="error clickable capitalize" href="" name={props.event.id} onClick={props.delete}>{language.delete}</a>
          <Link to={`/user/event/edit/${props.event.id}`} className="success capitalize">{language.edit}</Link>
          <Link to={`/user/event/view/${props.event.id}`} className="capitalize">{language.view}</Link>
        </div>
      </div>
    </li>
  );
};


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
