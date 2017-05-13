import React, { Component } from "react";
import "./WidgetView.css";
import ErrorList from "./ErrorList";


class WidgetView extends Component {
  state = {
    fields: {
      region: "",
    },
    fieldErrors: [],
    isGenerated: false,
    headCode: "",
    bodyCode: "",
  }

  validate(fields) {
    const errors = []
    if (!fields.region) errors.push("You must choose a region!");
    return errors
  }

  onFormSubmit(event) {
    event.preventDefault();

    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on Errors
    if (fieldErrors.length) return;

    this.setState({isGenerated: true});
    this.setState({headCode: `<script src="example.com/widget.js></script>`})
    this.setState({bodyCode: `<div id="visa-widget" data-region="${this.state.fields.region}"></div>`})
  }

  onInputChange(event)  {
    let fields = this.state.fields;
    fields[event.target.name] = event.target.value;
    this.setState({fields});
  }

  render() {
    return (
      <div className="lightbox widget-view">
        <h2>Generate Widget Code</h2>
        <form onSubmit={this.onFormSubmit.bind(this)}>
          <select name="region" onChange={this.onInputChange.bind(this)} value={this.state.fields.region}>
            <option value="">Choose Region</option>
            <option value="dalarna">Dalarna</option>
            <option value="sweden">Sweden</option>
          </select>
          <ErrorList errors={this.state.fieldErrors}/>
          <input type="submit" value="generate" className="btn-primary"></input>
        </form>
        {
          this.state.isGenerated ? (
            <div className="code-container">
              <p>Paste the following code within the head-tags on your website:</p>
              <textarea className="widget-code" defaultValue={this.state.headCode} disabled></textarea>
              <p>Paste the following code where you want to display the widget</p>
              <textarea className="widget-code" defaultValue={this.state.bodyCode} disabled></textarea>
            </div>
          ) : (
            <div></div>
          )
        }
      </div>
    );
  }
}

export default WidgetView;
