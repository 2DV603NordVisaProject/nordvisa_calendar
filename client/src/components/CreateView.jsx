import React, { Component } from 'react';
import Redirect from 'react-router/Redirect';
import PropTypes from 'prop-types';
import Moment from 'moment';
import ErrorList from './ErrorList';
import Client from '../Client';
import EventsMap from './EventsMap';
import './CreateView.css';
import PageTitle from './PageTitle';


class CreateView extends Component {
  constructor() {
    super();
    this.state = {
      fields: {
        id: '',
        name: '',
        date: '',
        recurring: false,
        recursuntil: '',
        recurs: '',
        location: '',
        desc: '',
        img: '',
        file: null,
        startTime: '',
        endTime: '',
        path: '',
        imgName: '',
        createdBy: '',
      },
      fieldErrors: [],
      progress: 'create',
      event: {},
      comeFrom: '',
      redirect: false,
    };

    this.onSaveClick = this.onSaveClick.bind(this);
    this.onFormSubmit = this.onFormSubmit.bind(this);
    this.onInputChange = this.onInputChange.bind(this);
    this.onEditClick = this.onEditClick.bind(this);
  }

  componentWillMount() {
    const uri = '/api/user/current';

    let fields = {};

    if (this.props.progress) {
      const progress = this.props.progress;

      const eventUri = `/api/event/get?id=${this.props.id}&token=dashboard`;
      Client.get(eventUri)
        .then((events) => {
          event = events[0];
          this.setState({ event });

          const date = Moment(event.startDateTime);


          const endTime = Moment(date.valueOf() + event.duration);


          fields = {
            id: event.id,
            name: event.name,
            date: date.format('YYYY-MM-DD'),
            recurring: event.recurring,
            recursuntil: event.recursUntil,
            recurs: event.recursEvery,
            location: event.location.address,
            desc: event.description,
            img: event.images[0],
            file: null,
            startTime: date.format('HH:mm'),
            endTime: endTime.format('HH:mm'),
            path: event.path,
            imgName: '',
            createdBy: event.createdBy,
          };

          console.log(fields);

          this.setState({ progress, fields, comeFrom: 'event' });

          console.log(this.state);
        });
    } else {
      fields = this.state.fields;
    }

    Client.get(uri)
      .then((user) => {
        fields = this.state.fields;
        fields.createdBy = user.id;
        this.setState({ fields });
      });
  }


  onInputChange(event) {
    const fields = this.state.fields;
    fields[event.target.name] = event.target.value;

    if (event.target.name === 'recurring') {
      fields[event.target.name] = event.target.checked;
      if (event.target.checked === true) {
        document.querySelector('.recurring').classList.add('hidden');
        document.querySelector('.is-recurring').classList.remove('hidden');
      }
    }

    if (event.target.name === 'img') {
      fields[event.target.name] = URL.createObjectURL(event.target.files[0]);
      const fileName = event.target.value.split('\\');
      fields.imgName = fileName[fileName.length - 1];
      fields.file = event.target.files[0];
      console.log(fields);
    }
    this.setState({ fields });
  }

  onFormSubmit(event) {
    event.preventDefault();
    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on error.
    if (fieldErrors.length) return;

    const file = this.state.fields.file;
    if (file) {
      Client.uploadImage(file).then((res) => {
        if (Object.prototype.hasOwnProperty.call(res, 'message')) {
          let err = '';
          if (res.message === '415') {
            err = 'Unsupported file type!';
          } else if (res.message === '413') {
            err = 'File size too large!';
          } else {
            err = 'Internal Server Error';
          }
          fieldErrors.push(err);
          this.setState({ fieldErrors });
          return;
        }

        const fields = this.state.fields;
        fields.path = res.path;
        this.setState({ fields });
        this.setState({ progress: 'preview' });
      });
    } else {
      this.setState({ progress: 'preview' });
    }
  }

  onEditClick(event) {
    event.preventDefault();
    this.setState({ progress: 'edit' });
  }

  onSaveClick(event) {
    event.preventDefault();

    const fields = this.state.fields;
    const uri = '/api/event/create';
    const editUri = '/api/event/update';
    console.log(`${fields.date} ${fields.startTime}`);
    const date = Moment(`${fields.date} ${fields.startTime}`).valueOf();
    console.log(date);
    const duration = Moment(`${fields.date} ${fields.endTime}`).valueOf() - Moment(`${fields.date} ${fields.startTime}`).valueOf();
    const eventObj = {
      id: fields.id,
      name: fields.name,
      startDateTime: date,
      recurring: fields.recurring,
      location: fields.location,
      description: fields.desc,
      images: fields.imgName,
      duration,
      path: fields.path,
      createdBy: fields.createdBy,
    };

    if (fields.recurring) {
      eventObj.recursUntil = Moment(fields.recursuntil).valueOf();
      eventObj.recursEvery = fields.recurs;
    }

    console.log(eventObj);

    if (this.state.comeFrom === 'event') {
      Client.post(eventObj, editUri).then(res => console.log(res));
    } else {
      Client.post(eventObj, uri).then(res => console.log(res));
    }

    const fieldErrors = [];
    fieldErrors.push(this.context.language.Errors.eventSaved);
    this.setState({ fieldErrors });
    this.setState({ progress: 'saved', redirect: true });
    this.setState({ fields: {
      name: '',
      date: '',
      recurring: false,
      recursuntil: '',
      recurs: '',
      location: '',
      desc: '',
      img: '',
    } });
  }

  validate(fields) {
    const errors = [];
    if (!fields.name) errors.push(this.context.language.Errors.nameNeeded);
    if (!fields.date) errors.push(this.context.language.Errors.dateNeeded);
    if (!fields.location) errors.push(this.context.language.Errors.locationNeeded);
    if (!fields.desc) errors.push(this.context.language.Errors.descriptionNeeded);
    if (!fields.startTime) errors.push(this.context.language.Errors.startNeeded);
    if (!fields.endTime) errors.push(this.context.language.Errors.endNeeded);

    return errors;
  }

  render() {
    const resourceURI = '/api/upload';
    const language = this.context.language.CreateView;

    if (this.state.redirect) {
      return (
        <Redirect to="/user/event" />
      );
    }

    if (this.state.progress === 'preview' || this.state.progress === 'view') {
      return (
        <div className="view preview">
          <PageTitle>{language.createEvent}</PageTitle>
          <div className="box">
            {
              this.state.progress === 'preview' ? <h3 className="capitalize">{language.previewEvent}</h3> : <h3 className="capitalize">{language.viewEvent}</h3>
            }
            <h4 className="preview-text">{this.state.fields.name}</h4>
            <p className="preview-text">{this.state.fields.location} - {this.state.fields.date} - {this.state.fields.startTime} - {this.state.fields.endTime}</p>
            {
              this.state.fields.path === '' ? '' : <div className="img-container" style={this.state.progress === 'view' ? { backgroundImage: `url("${resourceURI}/${this.state.fields.path}/${this.state.fields.img}")` } : { backgroundImage: `url(${this.state.fields.img})` }} />
            }
            <h4 className="preview-text capitalize">{language.description}:</h4>
            <div className="desc">
              <p>{this.state.fields.desc}</p>
            </div>
            {
              Object.prototype.hasOwnProperty.call(this.state.event, 'location') ? (
                <div className="maps">
                  <EventsMap
                    events={[this.state.event]}
                    center={{
                      lat: this.state.event.location.coordinates.coordinates[1],
                      lng: this.state.event.location.coordinates.coordinates[0],
                    }}
                  />
                </div>) : ''
            }

            <div className="action-container">
              {
                this.state.progress === 'preview' ? <button className="btn-primary" onClick={this.onSaveClick}>{language.save}</button> : ''
              }
              <button className="btn-primary" onClick={this.onEditClick}>{language.edit}</button>
            </div>
          </div>
        </div>
      );
    }
    return (
      <div className="view create-event">
        <PageTitle>{language.createEvent}</PageTitle>
        <div className="box">
          <h3 className="capitalize">{language.newEvent}</h3>
          <form onSubmit={this.onFormSubmit}>
            <label htmlFor="name" className="capitalize">{language.name}:</label>
            <input
              name="name"
              type="text"
              value={this.state.fields.name}
              onChange={this.onInputChange}
            />
            <br />
            <label htmlFor="date" className="capitalize">{language.date}:</label>
            <input
              type="date"
              name="date"
              value={this.state.fields.date}
              onChange={this.onInputChange}
            />
            <label htmlFor="time" className="capitalize">{language.time}:</label>
            <input
              name="startTime"
              type="time"
              value={this.state.fields.startTime}
              onChange={this.onInputChange}
              className="time-form"
            />
            <input
              name="endTime"
              type="time"
              value={this.state.fields.endTime}
              onChange={this.onInputChange}
              placeholder="16.30..."
              className="time-form end-time"
            />
            <div className="recurring">
              <label htmlFor="recurring" className="capitalize">{language.recurring}:</label>
              <input
                name="recurring"
                type="checkbox"
                className="approve"
                checked={this.state.fields.recurring}
                onChange={this.onInputChange}
              />
            </div>
            <div className="is-recurring hidden">
              <label htmlFor="recursuntil" className="capitalize">{language.recursUntil}:</label>
              <input
                type="date"
                name="recursuntil"
                value={this.state.fields.recursuntil}
                onChange={this.onInputChange}
              />
              <label htmlFor="recurs" className="capitalize">{language.recurs}:</label>
              <select
                className="capitalize"
                name="recurs"
                value={this.state.fields.recurs}
                onChange={this.onInputChange}
              >
                <option selected className="capitalize" value="0">{language.weekly}</option>
                <option className="capitalize" value="1">{language.monthly}</option>
                <option className="capitalize" value="2">{language.yearly}</option>
              </select>
            </div>
            <label htmlFor="location" className="capitalize">{language.location}:</label>
            <input
              name="location"
              type="text"
              value={this.state.fields.location}
              onChange={this.onInputChange}
            />
            <br />
            <label htmlFor="desc" className="capitalize">{language.description}:</label>
            <textarea
              name="desc"
              value={this.state.fields.desc}
              onChange={this.onInputChange}
            />
            <label htmlFor="img" className="capitalize">{language.image}:</label>
            <input
              type="file"
              name="img"
              accept="image/jpeg,image/png"
              onChange={this.onInputChange}
            />
            <ErrorList className={this.state.progress === 'saved' ? 'success' : ''} errors={this.state.fieldErrors} />
            <input type="submit" value={language.preview} className="btn-primary" />
            {
              this.state.comeFrom === 'event'
              ? this.state.progress === 'saved'
                ? <Redirect to="/user/event" />
                : null
              : null
            }
          </form>
        </div>
      </div>
    );
  }
}

CreateView.defaultProps = {
  progress: '',
  id: null,
};

CreateView.contextTypes = {
  language: PropTypes.object,
};

CreateView.propTypes = {
  progress: PropTypes.string,
  id: PropTypes.number,
};

export default CreateView;
