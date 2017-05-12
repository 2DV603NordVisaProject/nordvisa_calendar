import React, { Component } from "react";
import { DateField } from "react-date-picker";
import "react-date-picker/index.css";
import "./CreateView.css";

class CreateView extends Component {
  render() {
    return (
      <div className="view create-event">
        <h2>Create Event</h2>
        <div className="box">
          <h3>New Event</h3>
          <form>
            <label htmlFor="name">Name:</label>
            <input name="name" type="text"></input>
            <br/>
            <label htmlFor="date">Date:</label>
            <DateField dateFormat="YYYY-MM-DD"/>
            <label htmlFor="location">Location:</label>
            <input name="location" type="text"></input>
            <br/>
            <label htmlFor="desc">Description:</label>
            <textarea name="desc"></textarea>
            <label htmlFor="img">Image:</label>
            <input type="file" name="img" accept="image/*"></input>
            <button>save</button>
          </form>
        </div>
      </div>
    );
  }
}

export default CreateView;
