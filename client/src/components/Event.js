import React from 'react';
import Link from 'react-router/Link';
import PropTypes from 'prop-types';

const Event = () => {
  const language = this.context.language.MyEventsView;

  return (
    <li>
      <div className="event-item">
        <p>{this.props.event.name}</p>
        <div className="item-action-container">
          <a className="error clickable capitalize" href="" name={this.props.event.id} onClick={this.props.delete.bind(this)}>{language.delete}</a>
          <Link to={`/user/event/edit/${this.props.event.id}`} className="success capitalize">{language.edit}</Link>
          <Link to={`/user/event/view/${this.props.event.id}`} className="capitalize">{language.view}</Link>
        </div>
      </div>
    </li>
  );
};


Event.contextTypes = {
  language: PropTypes.object,
};

export default Event;
