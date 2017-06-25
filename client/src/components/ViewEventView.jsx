import React from 'react';
import PropTypes from 'prop-types';
import PageTitle from './PageTitle';
import Button from './Button';
import ViewContainer from './PageContainer';
import EventsMap from './EventsMap';
import SubTitle from './SubTitle';

const propTypes = {
  fields: PropTypes.shape({
    name: PropTypes.string,
    date: PropTypes.string,
    startTime: PropTypes.string,
    endTime: PropTypes.string,
    path: PropTypes.string,
    img: PropTypes.string,
    desc: PropTypes.string,
  }).isRequired,
  progress: PropTypes.string.isRequired,
  onEditClick: PropTypes.func.isRequired,
  onSaveClick: PropTypes.func.isRequired,
  event: PropTypes.shape({
    location: PropTypes.object,
  }).isRequired,
  resourceURI: PropTypes.string.isRequired,
};

const contextTypes = {
  language: PropTypes.object,
};

const ViewEventView = ({
  fields,
  progress,
  onEditClick,
  onSaveClick,
  event,
  resourceURI,
  }, context) => {
  const language = context.language.CreateView;
  return (
    <ViewContainer className="preview">
      <PageTitle>{language.createEvent}</PageTitle>
      <div className="box">
        {
          progress === 'preview' ? <SubTitle style={{ textTransform: 'capitalize' }}>{language.previewEvent}</SubTitle> : <SubTitle style={{ textTransform: 'capitalize' }}>{language.viewEvent}</SubTitle>
        }
        <h4 className="preview-text">{fields.name}</h4>
        <p className="preview-text">{fields.location} - {fields.date} - {fields.startTime} - {fields.endTime}</p>
        {
          fields.path === '' ? '' : <div className="img-container" style={progress === 'view' ? { backgroundImage: `url("${resourceURI}/${fields.path}/${fields.img}")` } : { backgroundImage: `url(${fields.img})` }} />
        }
        <h4 className="preview-text" style={{ textTransform: 'capitalize' }}>{language.description}:</h4>
        <div className="desc">
          <p>{fields.desc}</p>
        </div>
        {
          Object.prototype.hasOwnProperty.call(event, 'location') ? (
            <div className="maps">
              <EventsMap
                events={[event]}
                center={{
                  lat: event.location.coordinates.coordinates[1],
                  lng: event.location.coordinates.coordinates[0],
                }}
              />
            </div>) : ''
        }

        <div className="action-container">
          {
            progress === 'preview' ? <Button onClick={onSaveClick}>{language.save}</Button> : null
          }
          <Button onClick={onEditClick}>{language.edit}</Button>
        </div>
      </div>
    </ViewContainer>
  );
};

ViewEventView.propTypes = propTypes;
ViewEventView.contextTypes = contextTypes;

export default ViewEventView;
