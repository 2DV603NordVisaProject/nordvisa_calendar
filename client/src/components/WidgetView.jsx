import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './WidgetView.css';
import ErrorList from './ErrorList';
import ProvinceSelect from './ProvinceSelect';
import Client from '../Client';
import PageTitle from './PageTitle';
import Button from './Button';
import CountrySelect from './CountrySelect';


class WidgetView extends Component {

  constructor() {
    super();

    this.onInputChange = this.onInputChange.bind(this);
    this.onFormSubmit = this.onFormSubmit.bind(this);
  }

  state = {
    fields: {
      region: '',
      province: '',
    },
    fieldErrors: [],
    showProvince: false,
    isGenerated: false,
    headCode: '',
    bodyCode: '',
    token: '',
  }


  componentWillMount() {
    const uri = '/api/token';
    Client.get(uri)
      .then((res) => {
        const token = res.token;
        this.setState({ token });
      });
  }

  onFormSubmit(event) {
    event.preventDefault();
    const fieldErrors = this.validate(this.state.fields);
    this.setState({ fieldErrors });

    // Return on Errors
    if (fieldErrors.length) return;

    const bodyCode = this.generateBodyCode(this.state.fields.region, this.state.fields.province);
    const headCode = `<script src="${location.protocol}//${location.host}/widget.js}></script>`;

    this.setState({ isGenerated: true });
    this.setState({ headCode });
    this.setState({ bodyCode });
  }

  onInputChange(event) {
    const fields = this.state.fields;
    fields[event.target.name] = event.target.value;
    this.setState({ fields });

    let showProvince;

    if (event.target.value !== '') {
      showProvince = true;
    } else {
      showProvince = false;
    }

    this.setState({ showProvince });
  }

  validate(fields) {
    const errors = [];
    if (!fields.region) errors.push(this.context.language.Errors.chooseRegion);
    return errors;
  }

  generateBodyCode(region, province) {
    return `<div id="visa-widget" data-country="${region}" data-region="${province}" data-token="${this.state.token}"></div>`;
  }

  render() {
    const language = this.context.language.WidgetView;

    return (
      <div className="lightbox widget-view">
        <PageTitle>{language.generateWidgetCode}</PageTitle>
        <form>
          <CountrySelect onInputChange={this.onInputChange} region={this.state.fields.region} />
          <div className={this.state.showProvince ? '' : 'hidden'}>
            <ProvinceSelect
              region={this.state.fields.region}
              onChange={this.onInputChange}
            />
          </div>
          <ErrorList errors={this.state.fieldErrors} />
          <Button onClick={this.onFormSubmit}>{language.generate}</Button>
        </form>
        {
          this.state.isGenerated ? (
            <div className="code-container">
              <p>{language.headCode}:</p>
              <textarea className="widget-code" defaultValue={this.state.headCode} disabled />
              <p>{language.bodyCode}:</p>
              <textarea className="widget-code" defaultValue={this.state.bodyCode} disabled />
            </div>
          ) : (
            <div />
          )
        }
      </div>
    );
  }
}

WidgetView.contextTypes = {
  language: PropTypes.object,
};

export default WidgetView;
