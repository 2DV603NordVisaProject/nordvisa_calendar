import React from 'react';
import PropTypes from 'prop-types';
import Redirect from 'react-router/Redirect';
import ViewContainer from './ViewContainer';
import PageTitle from './PageTitle';
import SubTitle from './SubTitle';
import Button from './Button';
import ErrorList from './ErrorList';
import RecursInput from './RecursInput';

const CreateEventView = ({ comeFrom,
  progress, onFormSubmit, fields, onInputChange, fieldErrors, showRecursInput }, context) => {
  const language = context.language.CreateView;
  if (comeFrom === 'event' && progress === 'saved') {
    return <Redirect to="/user/event" />;
  }
  return (
    <ViewContainer className="create-event">
      <PageTitle>{language.createEvent}</PageTitle>
      <div className="box">
        <SubTitle className="capitalize">{language.newEvent}</SubTitle>
        <form onSubmit={onFormSubmit}>
          <label htmlFor="name" style={{ textTransform: 'capitalize' }}>{language.name}:</label>
          <input
            name="name"
            type="text"
            value={fields.name}
            onChange={onInputChange}
          />
          <br />
          <label htmlFor="date" style={{ textTransform: 'capitalize' }}>{language.date}:</label>
          <input
            type="date"
            name="date"
            value={fields.date}
            onChange={onInputChange}
          />
          <label htmlFor="time" style={{ textTransform: 'capitalize' }}>{language.time}:</label>
          <input
            name="startTime"
            type="time"
            value={fields.startTime}
            onChange={onInputChange}
            className="time-form"
          />
          <input
            name="endTime"
            type="time"
            value={fields.endTime}
            onChange={onInputChange}
            placeholder="16.30..."
            className="time-form end-time"
          />
          <RecursInput
            showRecursInput={showRecursInput}
            fields={fields}
            onInputChange={onInputChange}
          />
          <label htmlFor="location" style={{ textTransform: 'capitalize' }}>{language.location}:</label>
          <input
            name="location"
            type="text"
            value={fields.location}
            onChange={onInputChange}
          />
          <br />
          <label htmlFor="desc" style={{ textTransform: 'capitalize' }}>{language.description}:</label>
          <textarea
            name="desc"
            value={fields.desc}
            onChange={onInputChange}
          />
          <label htmlFor="img" style={{ textTransform: 'capitalize' }}>{language.image}:</label>
          <input
            type="file"
            name="img"
            accept="image/jpeg,image/png"
            onChange={onInputChange}
          />
          <ErrorList className={progress === 'saved' ? 'success' : ''} errors={fieldErrors} />
          <Button form>{language.preview}</Button>
        </form>
      </div>
    </ViewContainer>
  );
};

CreateEventView.defaultProps = {
  showRecursInput: false,
};

CreateEventView.contextTypes = {
  language: PropTypes.object,
};

CreateEventView.propTypes = {
  comeFrom: PropTypes.string.isRequired,
  progress: PropTypes.string.isRequired,
  onFormSubmit: PropTypes.func.isRequired,
  onInputChange: PropTypes.func.isRequired,
  fieldErrors: PropTypes.arrayOf(
    PropTypes.string,
  ).isRequired,
  fields: PropTypes.shape({
    name: PropTypes.string,
    date: PropTypes.string,
    startTime: PropTypes.string,
    endTime: PropTypes.string,
    recurring: PropTypes.string,
    recurs: PropTypes.string,
    recursuntil: PropTypes.string,
    location: PropTypes.string,
    desc: PropTypes.string,
  }).isRequired,
  showRecursInput: PropTypes.boolean,
};

export default CreateEventView;
