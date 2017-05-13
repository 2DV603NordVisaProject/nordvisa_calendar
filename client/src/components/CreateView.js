import React, { Component } from "react";
import { DateField } from "react-date-picker";
import "react-date-picker/index.css";
import "./CreateView.css";
import ErrorList from "./ErrorList";

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
    },
    fieldErrors: [],
    progress: "create",
  }

  validate(fields) {
    const errors = [];
    if (!fields.name) errors.push("You need to enter a name!");
    //if (!fields.date) errors.push("You need to ender a date!");
    if (!fields.location) errors.push("You need to enter a location!");
    if (!fields.desc) errors.push("You need to enter a description!");
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

  render() {
    if (this.state.progress === "preview") {
      return (
        <div className="view preview">
          <h2>Create Event</h2>
          <div className="box">
            <h3>Preview Event</h3>
            <h4 className="preview-text">{this.state.fields.name}</h4>
            <p className="preview-text">{this.state.fields.location} - {this.state.fields.date}</p>
            <div className="img-container" style={{backgroundImage: `url(${this.state.fields.img})`}}>
            </div>
            <h4 className="preview-text">Description:</h4>
            <div className="desc">
              <p>{this.state.fields.desc}</p>
            </div>
            <div className="maps">
              <p>G Maps Goes here</p>
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
              <DateField
                name="date"
                dateFormat="YYYY-MM-DD"
                value={this.state.fields.date}
                onChange={this.onInputChange.bind(this)}/>
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
                <DateField
                  name="recursuntil"
                  dateFormat="YYYY-MM-DD"
                  value={this.state.fields.recursuntil}
                  onChange={this.onInputChange.bind(this)}/>
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
              <ErrorList errors={this.state.fieldErrors}/>
              <input type="submit" value="preview" className="btn-primary"></input>
            </form>
          </div>
        </div>
      );
    }
  }
}

export default CreateView;
