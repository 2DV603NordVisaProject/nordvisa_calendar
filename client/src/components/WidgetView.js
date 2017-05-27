import React, { Component } from "react";
import "./WidgetView.css";
import ErrorList from "./ErrorList";
import ProvinceSelect from "./ProvinceSelect";
import PropTypes from "prop-types";
import Client from "../Client";


class WidgetView extends Component {
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

  componentWillMount() {
    const uri = "/api/token";
    Client.get(uri)
      .then(res => {
        const token = res.token;
        this.setState({ token });
      })
  }

  validate(fields) {
    const errors = []
    if (!fields.region) errors.push(this.context.language.Errors.chooseRegion);
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
    return `<script src="${location.protocol}//${location.host}/widget.js}></script>`;
  }

  generateBodyCode(region) {
    return `<div id="visa-widget" data-region="${region}" data-token="${this.state.token}"></div>`;
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

    const language = this.context.language.WidgetView;

    return (
      <div className="lightbox widget-view">
        <h2 className="uppercase">{language.generateWidgetCode}</h2>
        <form>
          <select
            className="capitalize"
            name="region"
            onChange={this.onInputChange.bind(this)}
            defaultValue={this.state.fields.region}>
            <option value="" className="capitalize">{language.chooseRegion}</option>
            <option value="all" className="capitalize">{language.allNordic}</option>
            <option value="sweden" className="capitalize">{language.sweden}</option>
            <option value="norway" className="capitalize">{language.norway}</option>
            <option value="denmark" className="capitalize">{language.denmark}</option>
            <option value="iceland" className="capitalize">{language.iceland}</option>
          </select>
          <div id="on-province-select" className="hidden">
            <ProvinceSelect
              region={this.state.fields.region}
              onChange={this.onInputChange.bind(this)}/>
          </div>
          <ErrorList errors={this.state.fieldErrors}/>
          <button className="btn-primary" onClick={this.onFormSubmit.bind(this)}>{language.generate}</button>
        </form>
        {
          this.state.isGenerated ? (
            <div className="code-container">
              <p>{language.headCode}:</p>
              <textarea className="widget-code" defaultValue={this.state.headCode} disabled></textarea>
              <p>{language.bodyCode}:</p>
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

WidgetView.contextTypes = {
  language: PropTypes.object,
}

export default WidgetView;
