import React, { Component } from "react";
import { DateField } from "react-date-picker";
import "react-date-picker/index.css";
import "./CreateView.css";
import ErrorList from "./ErrorList";
import Client from "../Client";
import Redirect from "react-router/Redirect";

class CreateView extends Component {
  state = {
    fields: {
      name: "",
      date: "",
      recurring: false,
      recursuntil: "",
      recurs: "",
      location: "",
      desc: "",
      img:  "",
      startTime: "",
      endTime: "",
    },
    fieldErrors: [],
    progress: "create",
    comeFrom: "",
  }

  componentWillMount() {
    if (this.props.progress) {
      const progress =  this.props.progress;
      const fields = Client.getEvent(this.props.id);
      this.setState({ progress, fields, comeFrom: "event"})
    }
  }

  validate(fields) {
    const errors = [];
    if (!fields.name) errors.push("You need to enter a name!");
    if (!fields.date) errors.push("You need to ender a date!");
    if (!fields.location) errors.push("You need to enter a location!");
    if (!fields.desc) errors.push("You need to enter a description!");
    if (!fields.startTime) errors.push("Start time is required!");
    if (!fields.endTime) errors.push("End time is required!");
    if (fields.startTime.length !== 5 || fields.endTime.length !== 5) errors.push("Incorrect time format!");

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
    }
    this.setState({ fields });
  }

  onFormSubmit(event) {
    event.preventDefault();
    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors});

    // Return on error.
    if (fieldErrors.length) return;
    this.setState({ progress: "preview" });
  }

  onEditClick(event) {
    event.preventDefault();
    this.setState({progress: "edit"});
  }

  onSaveClick(event) {
    event.preventDefault();
    const fieldErrors = [];
    fieldErrors.push("The event is now saved!");
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
    if (this.state.progress === "preview" || this.state.progress === "view") {
      return (
        <div className="view preview">
          <h2>Create Event</h2>
          <div className="box">
            {
              this.state.progress === "preview" ? <h3>Preview Event</h3> : <h3>View Event</h3>
            }
            <h4 className="preview-text">{this.state.fields.name}</h4>
            <p className="preview-text">{this.state.fields.location} - {this.state.fields.date} - {this.state.fields.startTime} - {this.state.fields.endTime}</p>
            <div className="img-container" style={{backgroundImage: `url(${this.state.fields.img})`}}>
            </div>
            <h4 className="preview-text">Description:</h4>
            <div className="desc">
              <p>{this.state.fields.desc}</p>
            </div>
            <div className="maps">
              <p>G Maps Goes here</p>
            </div>
            <div className="action-container">
              {
                this.state.progress === "preview" ? <button className="btn-primary" onClick={this.onSaveClick.bind(this)}>save</button> : ""
              }
              <button className="btn-primary" onClick={this.onEditClick.bind(this)}>edit</button>
            </div>
          </div>
        </div>
      )
    } else {
      return (
        <div className="view create-event">
          <h2>Create Event</h2>
          <div className="box">
            <h3>New Event</h3>
            <form onSubmit={this.onFormSubmit.bind(this)}>
              <label htmlFor="name">Name:</label>
              <input
                name="name"
                type="text"
                value={this.state.fields.name}
                onChange={this.onInputChange.bind(this)}>
              </input>
              <br/>
              <label htmlFor="date">Date:</label>
              <input
                type="date"
                name="date"
                value={this.state.fields.date}
                onChange={this.onInputChange.bind(this)}>
              </input>
              <label htmlFor="time">Time:</label>
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
                <label htmlFor="recurring">Recurring:</label>
                <input
                  name="recurring"
                  type="checkbox"
                  className="approve"
                  checked={this.state.fields.recurring}
                  onChange={this.onInputChange.bind(this)}></input>
              </div>
              <div className="is-recurring hidden">
                <label htmlFor="recursuntil">Recurs Until:</label>
                <input
                  type="date"
                  name="recursuntil"
                  value={this.state.fields.recursuntil}
                  onChange={this.onInputChange.bind(this)}>
                </input>
                <label htmlFor="recurs">Recurs:</label>
                <select
                  name="recurs"
                  value={this.state.fields.recurs}
                  onChange={this.onInputChange.bind(this)}>
                  <option>Weekly</option>
                  <option>Monthly</option>
                  <option>Yearly</option>
                </select>
              </div>
              <label htmlFor="location">Location:</label>
              <input
                name="location"
                type="text"
                value={this.state.fields.location}
                onChange={this.onInputChange.bind(this)}>
              </input>
              <br/>
              <label htmlFor="desc">Description:</label>
              <textarea
                name="desc"
                value={this.state.fields.desc}
                onChange={this.onInputChange.bind(this)}>
              </textarea>
              <label htmlFor="img">Image:</label>
              <input
                type="file"
                name="img"
                accept="image/*"
                value={this.state.fields.img}
                onChange={this.onInputChange.bind(this)}>
              </input>
              <ErrorList className={this.state.progress === "saved" ? "success" : ""} errors={this.state.fieldErrors}/>
              <input type="submit" value="preview" className="btn-primary"></input>
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

export default CreateView;
