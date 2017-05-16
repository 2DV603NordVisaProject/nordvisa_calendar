import React, { Component } from "react";
import "./WidgetView.css";
import ErrorList from "./ErrorList";
import ProvinceSelect from "./ProvinceSelect";


class WidgetView extends Component {
  constructor(props) {
    super(props)

    this.onProvinceChange = this.onProvinceChange.bind(this)
  }
  state = {
    fields: {
      region: "",
      province: "",
    },
    fieldErrors: [],
    isGenerated: false,
    headCode: "",
    bodyCode: "",
    token: "",
  }

  validate(fields) {
    const errors = []
    if (!fields.region) errors.push("You must choose a region!");
    return errors
  }

  onFormSubmit(event) {
    event.preventDefault();
    console.log(this.state.fields)
    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on Errors
    if (fieldErrors.length) return;

    let bodyCode;
    if (this.state.fields.province === "") {
      bodyCode = this.generateBodyCode(this.state.fields.region);
    } else {
      bodyCode = this.generateBodyCode(this.state.fields.province);
    }

    this.setState({isGenerated: true});
    this.setState({headCode: this.generateHeadCode()})
    this.setState({bodyCode: bodyCode})
  }

  generateHeadCode() {
    return `<script src="example.com/widget.js></script>`;
  }

  generateBodyCode(region) {
    return `<div id="visa-widget" data-region="${region}" token="${this.state.token}"></div>`;
  }

  onProvinceChange(event) {
    console.log("ping");
    const fields = this.state.fields;
    fields.province = event.target.value;
    this.setState(fields);
  }

  onInputChange(event)  {
    let fields = this.state.fields;
    fields[event.target.name] = event.target.value;
    this.setState({fields});

    if (event.target.value !== "") {
      const province = document.querySelector("#on-province-select");
      province.classList.remove("hidden");
    } else {
      const province = document.querySelector("#on-province-select");
      province.classList.add("hidden");
    }
  }

  render() {
    return (
      <div className="lightbox widget-view">
        <h2>Generate Widget Code</h2>
        <form onSubmit={this.onFormSubmit.bind(this)}>
          <select
            name="region"
            onChange={this.onInputChange.bind(this)}
            defaultValue={this.state.fields.region}>
            <option value="">Choose Region</option>
            <option value="all">All Nordic Countries</option>
            <option value="sweden">Sweden</option>
            <option value="norway">Norway</option>
            <option value="denmark">Denmark</option>
            <option value="iceland">Iceland</option>
          </select>
          <div id="on-province-select" className="hidden">
            <ProvinceSelect
              region={this.state.fields.region}
              onProvinceChange={this.onProvinceChange}/>
          </div>
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
