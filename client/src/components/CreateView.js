import React, { Component } from "react";
import { DateField } from "react-date-picker";
import "react-date-picker/index.css";
import "./CreateView.css";
import ErrorList from "./ErrorList";
import Client from "../Client";
import Redirect from "react-router/Redirect";
import PropTypes from "prop-types";
import Moment from "moment";

class CreateView extends Component {
  state = {
    fields: {
      name: "",
      date: "",
      recurring: false,
      recursuntil: "",
      recurs: 0,
      location: "",
      desc: "",
      img:  "",
      file: null,
      startTime: "",
      endTime: "",
      path: "",
      imgName: "",
      createdBy: ""
    },
    fieldErrors: [],
    progress: "create",
    comeFrom: "",
  }

  componentWillMount() {
    const uri = "/api/user/current";

    let fields = {};

    if (this.props.progress) {
      const progress =  this.props.progress;
      fields = Client.getEvent(this.props.id);
      this.setState({ progress, fields, comeFrom: "event"});
    } else {
      fields = this.state.fields;
    }

    Client.get(uri)
      .then(user => {
        fields.createdBy = user.id;
        this.setState({fields});
    });
  }

  validate(fields) {
    const errors = [];
    if (!fields.name) errors.push(this.context.language.Errors.nameNeeded);
    if (!fields.date) errors.push(this.context.language.Errors.dateNeeded);
    if (!fields.location) errors.push(this.context.language.Errors.locationNeeded);
    if (!fields.desc) errors.push(this.context.language.Errors.descriptionNeeded);
    if (!fields.startTime) errors.push(this.context.language.Errors.startNeeded);
    if (!fields.endTime) errors.push(this.context.language.Errors.endNeeded);
    if (fields.startTime.length !== 5 || fields.endTime.length !== 5) errors.push(this.context.language.Errors.incorrectTime);

    return errors;
  }

  onInputChange(event) {
    const fields = this.state.fields;
    fields[event.target.name] = event.target.value;

    if (event.target.name === "recurring") {
      fields[event.target.name] = event.target.checked;
      if (event.target.checked === true) {
        document.querySelector(".recurring").classList.add("hidden");
        document.querySelector(".is-recurring").classList.remove("hidden");
      }
    }

    if (event.target.name === "img") {
      fields[event.target.name] = URL.createObjectURL(event.target.files[0]);
      const fileName = event.target.value.split("\\");
      fields.imgName = fileName[fileName.length - 1];
      fields.file = event.target.files[0];
      console.log(fields);
    }
    this.setState({ fields });
  }

  onFormSubmit(event) {
    event.preventDefault();
    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors});

    // Return on error.
    if (fieldErrors.length) return;

    let file = this.state.fields.file;
    if (file) {
      Client.uploadImage(file).then(res => {
        if(res.hasOwnProperty("message")) {
          let err = "";
          if(res.message == "415") {
            err = "Unsupported file type!";
          } else if(res.message == "413") {
            err = "File size too large!";
          } else {
            err = "Internal Server Error";
          }
          fieldErrors.push(err);
          this.setState({fieldErrors});
          return;
        }

        const fields = this.state.fields;
        fields.path = res.path;
        this.setState({ fields });
        this.setState({ progress: "preview" });
      });
    } else {
      this.setState({ progress: "preview" });
    }
  }

  onEditClick(event) {
    event.preventDefault();
    this.setState({progress: "edit"});
  }

  onSaveClick(event) {
    event.preventDefault();

    const fields = this.state.fields;
    const uri = "/api/event/create";
    const date = Moment(fields.date).valueOf();
    const duration = Moment(fields.date + " " + fields.endTime).valueOf() - Moment(fields.date + " " + fields.startTime).valueOf();
    const eventObj = {
      name: fields.name,
      date: date,
      recurring: fields.recurring,
      location: fields.location,
      description: fields.desc,
      images: fields.imgName,
      duration: duration,
      path: fields.path,
      createdBy: fields.createdBy
    };

    if(fields.recurring) {
      eventObj.recursUntil = Moment(fields.recursuntil).valueOf();
      eventObj.recursEvery = parseInt(fields.recurs, 10);
    }

    console.log(eventObj);

    Client.post(eventObj, uri).then(res => console.log(res));

    const fieldErrors = [];
    fieldErrors.push(this.context.language.Errors.eventSaved);
    this.setState({fieldErrors});
    this.setState({ progress: "saved"});
    this.setState({ fields: {
      name: "",
      date: "",
      recurring: false,
      recursuntil: "",
      recurs: "",
      location: "",
      desc: "",
      img:  "",
    }});
  }

  render() {

    const language = this.context.language.CreateView;

    if (this.state.progress === "preview" || this.state.progress === "view") {
      return (
        <div className="view preview">
          <h2 className="capitalize">{language.createEvent}</h2>
          <div className="box">
            {
              this.state.progress === "preview" ? <h3 className="capitalize">{language.previewEvent}</h3> : <h3 className="capitalize">{language.viewEvent}</h3>
            }
            <h4 className="preview-text">{this.state.fields.name}</h4>
            <p className="preview-text">{this.state.fields.location} - {this.state.fields.date} - {this.state.fields.startTime} - {this.state.fields.endTime}</p>
            <div className="img-container" style={{backgroundImage: `url(${this.state.fields.img})`}}>
            </div>
            <h4 className="preview-text capitalize">{language.description}:</h4>
            <div className="desc">
              <p>{this.state.fields.desc}</p>
            </div>
            <div className="maps">
              <p>G Maps Goes here</p>
            </div>
            <div className="action-container">
              {
                this.state.progress === "preview" ? <button className="btn-primary" onClick={this.onSaveClick.bind(this)}>{language.save}</button> : ""
              }
              <button className="btn-primary" onClick={this.onEditClick.bind(this)}>{language.edit}</button>
            </div>
          </div>
        </div>
      )
    } else {
      return (
        <div className="view create-event">
          <h2 className="capitalize">{language.createEvent}</h2>
          <div className="box">
            <h3 className="capitalize">{language.newEvent}</h3>
            <form onSubmit={this.onFormSubmit.bind(this)}>
              <label htmlFor="name" className="capitalize">{language.name}:</label>
              <input
                name="name"
                type="text"
                value={this.state.fields.name}
                onChange={this.onInputChange.bind(this)}>
              </input>
              <br/>
              <label htmlFor="date" className="capitalize">{language.date}:</label>
              <input
                type="date"
                name="date"
                value={this.state.fields.date}
                onChange={this.onInputChange.bind(this)}>
              </input>
              <label htmlFor="time" className="capitalize">{language.time}:</label>
              <input
                name="startTime"
                type="time"
                value={this.state.fields.startTime}
                onChange={this.onInputChange.bind(this)}
                className="time-form">
              </input>
              <input
                name="endTime"
                type="time"
                value={this.state.fields.endTime}
                onChange={this.onInputChange.bind(this)}
                placeholder="16.30..."
                className="time-form end-time">
              </input>
              <div className="recurring">
                <label htmlFor="recurring" className="capitalize">{language.recurring}:</label>
                <input
                  name="recurring"
                  type="checkbox"
                  className="approve"
                  checked={this.state.fields.recurring}
                  onChange={this.onInputChange.bind(this)}></input>
              </div>
              <div className="is-recurring hidden">
                <label htmlFor="recursuntil" className="capitalize">{language.recursUntil}:</label>
                <input
                  type="date"
                  name="recursuntil"
                  value={this.state.fields.recursuntil}
                  onChange={this.onInputChange.bind(this)}>
                </input>
                <label htmlFor="recurs" className="capitalize">{language.recurs}:</label>
                <select
                  className="capitalize"
                  name="recurs"
                  value={this.state.fields.recurs}
                  onChange={this.onInputChange.bind(this)}>
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
                onChange={this.onInputChange.bind(this)}>
              </input>
              <br/>
              <label htmlFor="desc" className="capitalize">{language.description}:</label>
              <textarea
                name="desc"
                value={this.state.fields.desc}
                onChange={this.onInputChange.bind(this)}>
              </textarea>
              <label htmlFor="img" className="capitalize">{language.image}:</label>
              <input
                type="file"
                name="img"
                accept="image/*"
                onChange={this.onInputChange.bind(this)}>
              </input>
              <ErrorList className={this.state.progress === "saved" ? "success" : ""} errors={this.state.fieldErrors}/>
              <input type="submit" value={language.preview} className="btn-primary"></input>
                {
                  this.state.comeFrom === "event" ? this.state.progress === "saved" ? <Redirect to="/user/event"/> : "" : ""
                }
            </form>
          </div>
        </div>
      );
    }
  }
}

CreateView.contextTypes = {
  language: PropTypes.object,
}

export default CreateView;
