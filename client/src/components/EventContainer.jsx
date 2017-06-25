import React, { Component } from 'react';
import Redirect from 'react-router/Redirect';
import PropTypes from 'prop-types';
import Moment from 'moment';
import Client from '../Client';
import './EventContainer.css';
import ViewEventView from './ViewEventView';
import CreateEventView from './CreateEventView';

const propTypes = {
  progress: PropTypes.string,
  id: PropTypes.number,
};

const defaultProps = {
  progress: '',
  id: null,
};

const contextTypes = {
  language: PropTypes.object,
};


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
      showRecursInput: false,
    };

    this.onSaveClick = this.onSaveClick.bind(this);
    this.onFormSubmit = this.onFormSubmit.bind(this);
    this.onInputChange = this.onInputChange.bind(this);
    this.onEditClick = this.onEditClick.bind(this);
  }

  componentDidMount() {
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

          this.setState({ progress, fields, comeFrom: 'event' });
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
        this.setState({ showRecursInput: true });
      }
    }

    if (event.target.name === 'img') {
      fields[event.target.name] = URL.createObjectURL(event.target.files[0]);
      const fileName = event.target.value.split('\\');
      fields.imgName = fileName[fileName.length - 1];
      fields.file = event.target.files[0];
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
        console.log(file);
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
    const date = Moment(`${fields.date} ${fields.startTime}`).valueOf();
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

    if (this.state.redirect) {
      return (
        <Redirect to="/user/event" />
      );
    }

    if (this.state.progress === 'preview' || this.state.progress === 'view') {
      return (
        <ViewEventView
          fields={this.state.fields}
          progress={this.state.progress}
          onEditClick={this.onEditClick}
          onSaveClick={this.onSaveClick}
          event={this.state.event}
          resourceURI={resourceURI}
        />
      );
    }
    return (
      <CreateEventView
        comeFrom={this.state.comeFrom}
        progress={this.state.progress}
        onFormSubmit={this.onFormSubmit}
        fields={this.state.fields}
        onInputChange={this.onInputChange}
        fieldErrors={this.state.fieldErrors}
        showRecursInput={this.state.showRecursInput}
      />
    );
  }
}

CreateView.propTypes = propTypes;
CreateView.defaultProps = defaultProps;
CreateView.contextTypes = contextTypes;


export default CreateView;
